package SaveToElassandra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch2.ElasticsearchSink;
import org.apache.flink.streaming.connectors.elasticsearch2.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch2.RequestIndexer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.log4j.BasicConfigurator;
import Tweet.Tweet;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by josesantas on 30/4/17.
 */
public class SaveTweets {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        BasicConfigurator.configure();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000);

        String LastCreatedByUser = "";

        /* KAFKA */
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9092");
        properties.setProperty("zookeeper.connect", "kafka:2181");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("group.id", "test");
        /**/

        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer08<String>("tweets", new SimpleStringSchema(), properties));

        DataStream<Tweet> dt = dataStream.flatMap(new FlatMapFunction<String, Tweet>() {
            //@Override
            public void flatMap(String value, Collector<Tweet> out) throws Exception {
                ObjectMapper jsonParser = new ObjectMapper();
                jsonParser.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                jsonParser.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

                try {

                    Tweet tweet = jsonParser.readValue(value, Tweet.class);

                    out.collect(new Tweet(tweet));
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        System.out.print("##TWEETS");
        dt.print();

        Map<String, String> config = new HashMap<>();
        config.put("bulk.flush.max.actions", "1");
        config.put("cluster.name", "TestCluster");

        List<InetSocketAddress> transports = new ArrayList<>();
        transports.add(new InetSocketAddress(InetAddress.getByName("elassandra"), 9300));

        dt.addSink(new ElasticsearchSink<Tweet>(config, transports, new ElasticsearchSinkFunction<Tweet>() {
            public IndexRequest createIndexRequest(Tweet element) {
                Map<String, Object> json = new HashMap<>();
                json.put("created_at", String.valueOf(new Date(element.getCreatedAt()).getTime()).substring(0,10));
                json.put("coordinates_Tweet", element.getAllCoordinates());
                json.put("hashtags", element.getEntities().getHashtags().toString());
                json.put("media_url", element.getEntities().getAllMediaURL());
                json.put("favorite_count", element.getFavoriteCount().toString());
                json.put("id_str", element.getIdStr());
                json.put("in_reply_to_status_id_str", element.getInReplyToStatusIdStr());
                json.put("in_reply_to_user_id_str", element.getInReplyToUserIdStr());
                json.put("lang", element.getLang());
                //json.put("possibly_sensitive", element.getPossiblySensitive().toString());
                json.put("reTweet_count", element.getRetweetCount().toString());
                json.put("reTweeted", element.getRetweeted().toString());
                json.put("source", element.getSource());
                json.put("text", element.getText());
                json.put("truncated", element.getTruncated().toString());
                json.put("user_created", String.valueOf(new Date(element.getUser().getCreatedAt()).getTime()).substring(0,10));
                json.put("user_description", element.getUser().getDescription());
                json.put("user_display_url", element.getEntities().getAllDisplayURL());
                json.put("user_favourites_count", element.getUser().getFavouritesCount().toString());
                json.put("user_followers_count", element.getUser().getFollowersCount().toString());
                json.put("user_friends_count", element.getUser().getFriendsCount().toString());
                json.put("user_geo_enabled", element.getUser().getGeoEnabled().toString());
                json.put("user_lang", element.getUser().getLang());
                json.put("user_listed_count", element.getUser().getListedCount().toString());
                json.put("user_location", element.getUser().getLocation());
                json.put("user_name", element.getUser().getName());
                json.put("user_profile_background_image_url", element.getUser().getProfileBackgroundImageUrl());
                json.put("user_profile_image_url", element.getUser().getProfileImageUrl());
                json.put("user_screen_name", element.getUser().getScreenName());
                json.put("user_statuses_count", element.getUser().getStatusesCount().toString());
                json.put("user_time_zone", element.getUser().getTimeZone());
                json.put("user_url", element.getUser().getUrl());

                return Requests.indexRequest()
                        .index("twitter")
                        .type("tweet")
                        .source(json);
            }

            @Override
            public void process(Tweet element, RuntimeContext ctx, RequestIndexer indexer) {
                indexer.add(createIndexRequest(element));
            }
        }));


        env.execute("save Tweets");

    }



}
