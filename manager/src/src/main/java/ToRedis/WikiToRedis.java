package ToRedis;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.common.config.FlinkJedisPoolConfig;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class WikiToRedis  extends Thread implements Serializable {

    private String threadName;
    private List<String> entities;
    private String query;
    public String type;
    private String max;
    private Integer n_gram;

    private static final long serialVersionUID = 1L;

    public WikiToRedis(String name, List<String> ent, String typ, String max_query, Integer n_gram) {
        this.threadName = name;
        this.type = typ;
        this.max = max_query;
        this.entities = ent;
        this.n_gram = n_gram;
        this.query ="";

        int i = 0;

        //generate query
        while(ent.size() > i){
            this.query += ent.get(i);
            if(ent.size() != i + 1 && ent.get(i) != "")
                this.query += " OR ";
            i++;
        }

    }

    @Override
    public void run() {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();//

        DataSet<Tuple2<String, String>> ds = env.createInput(new ElasticInputFormat<Tuple2<String,String>>(query,max,"wiki"),
                TupleTypeInfo.of(new TypeHint<Tuple2<String, String>>() {
        }));


        //ds.print();
        DataSet<String> wiki = ds.map(new MapFunction<Tuple2<String, String>, String>() {
            @Override
            public String map(Tuple2<String, String> val) throws Exception {
                return val.f0;
            }
        });

        DataSet<Tuple2<String, String>> sintaxisWiki = wiki.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
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
        //FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("redis").build();
        sintaxisWiki.output(new OutputFormat<Tuple2<String, String>>() {

            private JedisPool pool;
            private Jedis jedis;
            private String caracter;

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

                //TLL count all values of the bigram
                if(jedis.hexists("WIKI"+threadName.substring(1,threadName.length()) + ";"+tuple.f0,tuple.f1))
                    jedis.hset("WIKI" +threadName.substring(1,threadName.length())+ ";" + tuple.f0 , tuple.f1, String.valueOf(Integer.parseInt(jedis.hget("WIKI"+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , tuple.f1) )+ 1));

                else
                    jedis.hset("WIKI" +threadName.substring(1,threadName.length())+ ";" + tuple.f0 , tuple.f1, "1");




                if(!jedis.hexists("WIKI" +threadName.substring(1,threadName.length())+ ";" + tuple.f0, "TTL"))
                    jedis.hset("WIKI" +threadName.substring(1,threadName.length())+ ";" + tuple.f0 , "TTL", "1");
                else
                    jedis.hset("WIKI" +threadName.substring(1,threadName.length())+ ";" + tuple.f0, "TTL", String.valueOf(Integer.parseInt(jedis.hget("WIKI"+threadName.substring(1,threadName.length()) + ";" + tuple.f0 , "TTL")  )+ 1));

            }

            @Override
            public void close() throws IOException {
                jedis.close();
            }
        });

        try {
            env.execute("Wiki to Redis");
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
