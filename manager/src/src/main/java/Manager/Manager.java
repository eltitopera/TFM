package Manager;

import Result.GenerateTweet;
import ToRedis.*;
import Tweet.Tweet;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import org.apache.flink.shaded.com.google.common.collect.ImmutableList;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Manager implements Runnable{
    private Tweet tweet;
    private Integer n_gram;

    public Manager(Tweet tweet, Integer n_gram) {
        this.tweet = tweet;
        this.n_gram = n_gram;
    }

    @Override
    public void run() {

        try {
            List<String> entities = null;
            String sentece = this.tweet.getText();
            sentece = cleanSentece(sentece);
            entities = getEntities(sentece);
            String temp[] = getSignificativeWords(sentece);

            for(int i = 0; i < temp.length; i++){
                entities.add(temp[i]);
            }
            //entidad de emergencia
            entities.add("Sabes");

            String idTweet = this.tweet.getIdStr();

            //Wikipedia to Redis
            Thread wThread = new WikiToRedis("W" + this.tweet.getIdStr(), entities, "WIKI", "10", n_gram);
            wThread.start();
            System.out.println("##RE##Wiki processed ");

            //Caracter to Redis
            String caracter = chooseCaracter(entities);
            Thread cThread = new CaracterToRedis("C" + this.tweet.getIdStr(), entities, "CARACTER", caracter, "50" , n_gram );
            cThread.start();

            System.out.println("##RE##Caracter processed ");

            cThread.join();
            wThread.join();

            //Wait all history tweets
            loadingTweets(idTweet);

            //Tweets to Redis
            Thread tThread = new TwitterToRedis("T" + this.tweet.getIdStr(), entities, "TWITTER", this.tweet.getUser().getScreenName(), "10", n_gram);
            tThread.start();
            tThread.join();
            System.out.println("##RE##Tweets processed ");

            //Generate tweets by markov
            Thread tweet_new = new GenerateTweet("R" + this.tweet.getIdStr(), entities, caracter);
            tweet_new.start();
            System.out.println("##RE##Tweets generated ");

            tweet_new.join();
            delByPatternRedis(this.tweet.getIdStr());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void loadingTweets(String id){
        Integer i = 0;
        while(!checkTweet(id) && i < 3){
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    //check all tweets loaded in redis
    private boolean checkTweet(String id){
        Jedis jedis = new Jedis("redis");
        String keys = jedis.get(id);
        if(keys != null && keys.contains("end"))
            return true;
        return false;
    }

    // find entities by NLP
    private static List<String> getEntities(String text) throws IOException {
        /* NLP */
        String[] textStr = text.split(",");

        List<String> entities = new ArrayList<String>();

        TokenNameFinderModel modelPerson = new TokenNameFinderModel(new File("NLP/es-ner-person.bin"));
        TokenNameFinderModel modelLocation = new TokenNameFinderModel(new File("NLP/es-ner-location.bin"));
        TokenNameFinderModel modelOrg = new TokenNameFinderModel(new File("NLP/es-ner-organization.bin"));
        TokenNameFinderModel modelMisc = new TokenNameFinderModel(new File("NLP/es-ner-misc.bin"));

        // Create a NameFinder using the model
        NameFinderME finderPerson = new NameFinderME(modelPerson);
        NameFinderME finderLocation = new NameFinderME(modelLocation);
        NameFinderME finderOrg = new NameFinderME(modelOrg);
        NameFinderME finderMisc = new NameFinderME(modelMisc);

        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;

        for (String sentence : textStr) {

            // Split the sentence into tokens
            String[] tokens = tokenizer.tokenize(sentence);

            // Find the names in the tokens and return Span objects
            Span[] namePer = finderPerson.find(tokens);
            Span[] nameLoc = finderLocation.find(tokens);
            Span[] nameOrg = finderOrg.find(tokens);
            Span[] nameMis = finderMisc.find(tokens);

            // Print the names extracted from the tokens using the Span data
            System.out.println("Person: " + Arrays.toString(Span.spansToStrings(namePer, tokens)));
            System.out.println("Location: " + Arrays.toString(Span.spansToStrings(nameLoc, tokens)));
            System.out.println("Orgs: " + Arrays.toString(Span.spansToStrings(nameOrg, tokens)));
            System.out.println("Misc: " + Arrays.toString(Span.spansToStrings(nameMis, tokens)));

            String[] Pers = Span.spansToStrings(namePer, tokens);
            String[] Loc = Span.spansToStrings(nameLoc, tokens);
            String[] Orgs = Span.spansToStrings(nameOrg, tokens);
            String[] Misc = Span.spansToStrings(nameMis, tokens);


            for (String aux : Pers) {
                if (!checkEntities(aux))
                    entities.add(aux);
            }

            for (String aux : Loc) {
                if (!checkEntities(aux))
                    entities.add(aux);
            }

            for (String aux : Orgs) {
                if (!checkEntities(aux))
                    entities.add(aux);
            }

            for (String aux : Misc) {
                if (!checkEntities(aux))
                    entities.add(aux);
            }

        /* */
        }
        return entities;
    }

    // Avoid reserved words
    private static boolean checkEntities(String aux) {
        return aux.contains("AND") ||  aux.contains("OR") || aux.contains("DESCRIBE") || aux.contains("GROUP BY");
    }

    // Choose caracter depends of repeated words
    public String chooseCaracter(List<String> words) {

        Integer ind = 0;
        String sentenceToQuery = "";
        for (String word: words) {
            ind++;
            sentenceToQuery += "*\""+word+"\"*";
            if (words.size() != ind)
                sentenceToQuery += " AND ";
        }

        Map<String, String> config = new HashMap<>();
        config.put("bulk.flush.max.actions", "1");
        config.put("cluster.name", "TestCluster");

        List<InetSocketAddress> transportAddresses = new ArrayList<>();
        try {
            transportAddresses.add(new InetSocketAddress(InetAddress.getByName("elassandra"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ArrayList transportNodes = new ArrayList(transportAddresses.size());
        Iterator settings = transportAddresses.iterator();

        while (settings.hasNext()) {
            InetSocketAddress transportClient = (InetSocketAddress) settings.next();
            transportNodes.add(new InetSocketTransportAddress(transportClient));
        }

        Settings settings1 = Settings.settingsBuilder().put(config).build();
        TransportClient transportClient1 = TransportClient.builder().settings(settings1).build();
        Iterator nodes = transportNodes.iterator();

        while (nodes.hasNext()) {
            TransportAddress bulkProcessorBuilder = (TransportAddress) nodes.next();
            transportClient1.addTransportAddress(bulkProcessorBuilder);
        }

        ImmutableList nodes1 = ImmutableList.copyOf(transportClient1.connectedNodes());
        if (nodes1.isEmpty()) {
            throw new RuntimeException("Client is not connected to any Elasticsearch nodes!");
        } else {
            TransportClient client = transportClient1;

            SearchRequestBuilder response = client.prepareSearch("twitter");
            response.setTypes("caracter");

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.simpleQueryStringQuery(sentenceToQuery));
            //boolQueryBuilder.minimumShouldMatch("95%");

            TermsBuilder termsb = AggregationBuilders.terms("car").field("caracter");
            response.setQuery(boolQueryBuilder).addAggregation(termsb);

            SearchResponse searchRes = response.execute().actionGet();



            Terms fieldATerms = searchRes.getAggregations().get("car");

            for (Terms.Bucket filedABucket : fieldATerms.getBuckets()) {
                //fieldA
                String fieldAValue = (String) filedABucket.getKey();

                //COUNT(fieldA)
                long fieldACount = filedABucket.getDocCount();
                System.out.println("car: "+ fieldAValue +" "+ fieldACount);
            }

            // by default
            String car = "Penny";

            if(fieldATerms.getBuckets().size() > 0) {
                car = (String) fieldATerms.getBuckets().get(0).getKey();
                //Uppercase first caracter of word
                car = car.substring(0, 1).toUpperCase() + car.substring(1);
            }

            return car;
        }
    }

    //delete keys to clean redis
    public void delByPatternRedis(String value){
        Jedis jedis = new Jedis("redis");
        Set<String> keys = jedis.keys("*"+value+"*");
        for (String key : keys) {
            jedis.del(key);
        }
        jedis.del(value);
        jedis.close();
    }

    //get main words of sentence
    private String[] getSignificativeWords(String text){
            InputStream modelIn = null;
            String words = "";

            try {
                modelIn = new FileInputStream("NLP/opennlp-es-maxent-pos-universal.bin");
                POSModel model = new POSModel(modelIn);
                POSTaggerME tagger = new POSTaggerME(model);
                String tags = tagger.tag(text);
                String wordsTags[] = tags.split(" ");


                //Sequence topSequences[] = tagger.topKSequences(text.split(""));
                for(int i = 0; i < wordsTags.length; i++){
                    if(checkVb(wordsTags[i]) /*|| checkNoum(wordsTags[i])*/){
                        words += wordsTags[i].substring(0,wordsTags[i].indexOf("/")) + " ";
                    }
                }

                System.out.print(words);

            }
            catch (IOException e) {
                // Model loading failed, handle the error
                e.printStackTrace();
            }
            finally {
                if (modelIn != null) {
                    try {
                        modelIn.close();
                    }
                    catch (IOException e) {
                    }
                }
            }

        return words.split(" ");

    }

    //clean caracters invalid
    public String cleanSentece(String s){

        s = s.replaceAll("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]","");
        s = s.replaceAll("RT","");
        s = s.replaceAll("[^a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ]","");
        s = s.replaceAll("RT","");
        s = s.replaceAll(":","");
        s = s.replaceAll("@","");
        s = s.replaceAll("   "," ");
        return s.replaceAll("  "," ");
    }

    //// /VB /VBD","/VBG","/VBN","/VBP","/VBZ"
    private static boolean checkVb(String wordsTag) {
        return wordsTag.contains("/V");
    }
    private static boolean checkNoum(String wordsTag) {
        return wordsTag.contains("/N");
    }
}