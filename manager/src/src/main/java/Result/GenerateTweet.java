package Result;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Spanish;
import org.languagetool.rules.RuleMatch;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.Map.Entry;

public class GenerateTweet extends Thread{

    private String threadName;
    private List<String> entities;
    private Jedis jedis;
    private String caracter;

    public GenerateTweet(String name, List<String> ent, String car) {
        threadName = name;
        entities = ent;
        jedis = new Jedis("redis");
        caracter = car;
    }

    @Override
    public void run() {

        String nextWord = "";
        String lastWord = "";
        String findWord = "";
        Set<String> list;
        Integer i = 0;

         do{
            /* Random Hash to Start in word*/
            lastWord = entities.get(i).toString();
            findWord = "*" + threadName.substring(1, threadName.length()) + ";" + lastWord + ";*";
            list = jedis.keys(findWord);
            i++;

        }while(list.size() == 0 && entities.size() > i  );


        /* Get Word from Key of previous Random Hash*/
        String Key = getRandomStartWord(list);

        // get indices of key
        Integer car1 = Key.indexOf(";");
        Integer car2 = Key.indexOf(";",car1+1);
        Integer car3 = Key.lastIndexOf(";");
        Integer car4 = Key.length();


        try{
            //decode values of key of previous Random Hash
            String Type = Key.substring(0, car1);
            lastWord = Key.substring(car1+1, car2);
            nextWord = Key.substring(car3+1, car4);
        }catch (Exception e){
            e.printStackTrace();
        }
        /* */

        Boolean end = false;

        //concat for sentence
        String sentence = lastWord + " " + nextWord;


            while (!end) {

                //get all list of key value from key
                Iterator<Entry<String, String>> keyValueMapIter = jedis.hgetAll(Key).entrySet().iterator();

                if (!nextWord.contains(".")) {
                    // get all repeat values in this keys for random algorithm
                    Integer total = Integer.parseInt(jedis.hget(Key, "TTL"));

                    //get random word to continue the sentence
                    lastWord = nextWord;
                    nextWord = getRandomWord(total, keyValueMapIter);


                    if (!nextWord.contains("."))
                        //get Next key
                        Key = nextKey(lastWord, nextWord, Key);


                    sentence += " " + nextWord;
                    System.out.println(sentence);
                } else {
                    end = true;
                }



            }

        System.out.print("##RE##FINAL_TWEET " + sentence);

        sentTweet(sentence);

    }

    private String nextKey(String lastWord, String nextWord, String key){
        String[] possible_keys = new String[3];
        Integer first_dot_com = key.indexOf(";");
        String intKey = key.substring(key.indexOf(";",first_dot_com+1)+1, key.lastIndexOf(";"));
        // 3 possibilities of keys
        possible_keys[0] = caracter+threadName.substring(1, threadName.length())+ ";" + intKey + ";" + lastWord + ";" + nextWord;
        possible_keys[1] = "WIKI"+threadName.substring(1, threadName.length()) + ";" + intKey + ";" +  lastWord + ";" + nextWord;
        possible_keys[2] = "TWITTER"+threadName.substring(1, threadName.length())+ ";" + intKey + ";" +  lastWord + ";" + nextWord;

        String final_key = "";

        Random rand = new Random();
        Integer rRand = rand.nextInt(2);

        // Randon a find new valid key
        while(jedis.hgetAll(possible_keys[rRand]).size() == 0 ){

            if(rRand == 2)
                rRand = 0;
            else
                rRand++;

        }

        final_key = possible_keys[rRand];
        return final_key;
    }

    /* Random Start sentence */
    private String getRandomStartWord(Set<String> words){
        String word = "";
        int i = 0;

        Random  rand = new Random();
        Integer rRand = rand.nextInt(words.size() + 1 );

        Iterator<String> keysIterator = words.iterator();
        //find in collection "Random result". when the key is found it return the current word.
        while(keysIterator.hasNext() && i <= rRand){
            word = keysIterator.next();
            i++;
        }

        return word;
    }

    private String getRandomWord(Integer Total, Iterator<Entry<String, String>> IKeyVal){
        Random  rand = new Random();
        Integer rRand = rand.nextInt(Total) + 1; //  because inside there is a key TTL.

        Boolean found = false;
        Integer ValProcesed = 0;

        String word = "";

        //find in collection "Random result". when the key is found it return the current word.
        while(IKeyVal.hasNext() && !found){
            Entry<String, String> KeyVal = IKeyVal.next();
            if (!KeyVal.getKey().contains("TTL")) { // Purpose of TTL is outside choose Old_versions.NUI_SaveToCassandra.words.
                ValProcesed += Integer.parseInt(KeyVal.getValue());
                if (rRand <= ValProcesed) {
                    word = KeyVal.getKey();
                    found = true;
                }
            }

        }

        return word;
    }


    //sent tweet to Rethinkdb
    private void sentTweet(String sentence){

        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String msg = "{\"text\":\"" + sentence + "\" ,  \"id_str\":\""+threadName.substring(1, threadName.length())+"\" }";
        producer.send(new ProducerRecord<String, String>("sentTweet", msg));

    }


}
