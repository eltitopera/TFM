package ToRedis;

import com.datastax.driver.core.Cluster;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.batch.connectors.cassandra.CassandraInputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import scala.Int;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaracterToRedis extends Thread implements Serializable{

    private String threadName;
    private List<String> entities;
    private String query;
    public String type;
    private String caracter;
    private String max;
    private Integer n_gram;

    private static final long serialVersionUID = 1L;

    public CaracterToRedis(String name, List<String> ent, String typ, String caracter , String max_query, Integer n_gram) {
        this.threadName = name;
        this.type = typ;
        this.max = max_query;
        this.entities = ent;
        this.caracter = caracter;
        this.n_gram = n_gram;
        this.query =  "caracter:"+caracter;

        if(ent.size() > 0)
            this.query += " OR ";

        int i = 0;
        //Generate query
        while(ent.size() > i){
            query += ent.get(i);
            if(ent.size() != i + 1 && ent.get(i) != "")
                query += " OR ";
            i++;
        }


    }

    @Override
    public void run(){
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();//.createRemoteEnvironment("192.168.1.58",6123);

        //get values from elassandra
        DataSet<Tuple2<String, String>> ds = env.createInput(new ElasticInputFormat<Tuple2<String,String>>(query,max,"caracter"),
                TupleTypeInfo.of(new TypeHint<Tuple2<String, String>>() {
                }));


        DataSet<String> car = ds.map(new MapFunction<Tuple2<String, String>, String>() {
            @Override
            public String map(Tuple2<String, String> val) throws Exception {
                return val.f0;
            }
        });

        DataSet<Tuple2<String, String>> caracterSin = car.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
            public void flatMap(String line_val, Collector<Tuple2<String, String>> out) throws Exception {
                int i = 1;
                String key = "";
                String val = "";

                String line = cleanSentece(line_val);

                //generate n_gram to redis
                if (line.split(" ").length >= n_gram) {
                    for (String word : line.split(" ")) {

                        if (i < n_gram+1) {
                            key += word+";";

                        } else  {
                            val = word;
                            out.collect(new Tuple2<String, String>(key.substring(0,key.length()-1), val));
                            key = key.substring(key.indexOf(';')+1,key.length()) + val+";";
                        }
                        i++;

                    }
                    //end of ngram is .
                    out.collect(new Tuple2<String, String>(key.substring(0,key.length()-1), "."));
                }
            }
        });
        /* REDIS */

        caracterSin.output(new OutputFormat<Tuple2<String, String>>() {

            private JedisPool pool;
            private Jedis jedis;

            @Override
            public void configure(Configuration configuration) {
                pool = new JedisPool("redis", 6379);

            }

            @Override
            public void open(int i, int i1) throws IOException {
                //get a jedis connection jedis connection pool
                jedis = pool.getResource();
            }

            @Override
            public void writeRecord(Tuple2<String, String> tuple) throws IOException {

                //insert ngram to redis
                if(jedis.hexists(caracter+threadName.substring(1,threadName.length()) + ";"+tuple.f0, tuple.f1)){
                    jedis.hset(caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , tuple.f1,
                            String.valueOf(Integer.parseInt(jedis.hget( caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , tuple.f1)  )+ 1));

                }else{
                    jedis.hset( caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , tuple.f1, "1");

                }

                //insert repeated values of ngram for random algorithm
                if(!jedis.hexists(caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , "TTL")){
                    jedis.hset( caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , "TTL", "1");

                }else {
                    jedis.hset( caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , "TTL", String.valueOf(Integer.parseInt(jedis.hget( caracter+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , "TTL")  )+ 1));
                }

            }

            @Override
            public void close() throws IOException {
                jedis.close();
            }
            });
        /* */

        try {
            env.execute("Elassandra to Redis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String cleanSentece(String s){
        s = s.replaceAll("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]","");
        s = s.replaceAll("RT","");
        s = s.replaceAll("[^a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ]","");
        s = s.replaceAll("   "," ");
        return s.replaceAll("  "," ");
    }
}
