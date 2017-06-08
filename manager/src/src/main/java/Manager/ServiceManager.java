package Manager;


import Tweet.Tweet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ServiceManager {
    public static void main(String[] args) throws Exception {
        Integer ngram = 5;
        if (args.length > 0)
            ngram = Integer.parseInt(args[0]);

        System.out.println("##RE##N_GRAM: " + ngram);

        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "group-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Arrays.asList("tweet"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {

                ObjectMapper jsonParser=new ObjectMapper();
                jsonParser.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                jsonParser.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                jsonParser.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                Tweet tweet = jsonParser.readValue(record.value(), Tweet.class);

                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("##RE##TWEETS: " + tweet.getText());
                System.out.println("---------------------------------------------------------------------------------");

                Runnable proceso1 = new Manager(tweet, 5);
                proceso1.run();
            }
        }

    }
}
