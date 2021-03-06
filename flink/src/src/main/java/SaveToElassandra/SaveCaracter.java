package SaveToElassandra;

import POJO.CaracterPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.java.tuple.Tuple2;
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
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;

public class SaveCaracter {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        BasicConfigurator.configure();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000);

        /* KAFKA */
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9092");
        properties.setProperty("zookeeper.connect", "kafka:2181");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("group.id", "test");
        /******/

        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer08<String>("caracter", new SimpleStringSchema(), properties));

        DataStream<Tuple2<String,String>> dt = dataStream.flatMap(new FlatMapFunction<String, Tuple2<String,String>>() {
            //@Override
            public void flatMap(String value, Collector<Tuple2<String,String>> out) throws Exception {
                try{
                    ObjectMapper jsonParser = new ObjectMapper();

                    CaracterPOJO caracter = jsonParser.readValue(value, CaracterPOJO.class);
                    out.collect(new Tuple2<String,String>(caracter.getCaracter(),caracter.getSaid()));
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        Map<String, String> config = new HashMap<>();
        // This instructs the sink to emit after every element, otherwise they would be buffered
        config.put("bulk.flush.max.actions", "1");
        config.put("cluster.name", "TestCluster");

        List<InetSocketAddress> transports = new ArrayList<>();
        transports.add(new InetSocketAddress(InetAddress.getByName("elassandra"), 9300));

        dt.addSink(new ElasticsearchSink<Tuple2<String,String>>(config, transports, new ElasticsearchSinkFunction<Tuple2<String,String>>() {
            public IndexRequest createIndexRequest(Tuple2<String,String> element) {
                Map<String, String> json = new HashMap<>();
                json.put("caracter", element.f0);
                json.put("said", element.f1);

                return Requests.indexRequest()
                        .index("twitter")
                        .type("caracter")
                        .source(json);
            }

            @Override
            public void process(Tuple2<String,String> element, RuntimeContext ctx, RequestIndexer indexer) {
                indexer.add(createIndexRequest(element));
            }
        }));

        env.execute("save Caracter");

    }
}
