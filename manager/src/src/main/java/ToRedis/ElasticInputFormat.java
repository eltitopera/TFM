package ToRedis;

import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.NonParallelInput;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.apache.flink.shaded.com.google.common.collect.ImmutableList;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;


public class ElasticInputFormat<OUT extends Tuple> extends RichInputFormat<OUT, InputSplit> implements NonParallelInput {

    private Node node;
    private Settings settings;
    private TransportClient client;
    private List<InetSocketAddress> transportAddresses;
    private Map<String, String> config;
    private SearchHit[] results;
    private Integer i;
    private String query;
    private String maxVal;
    private String type;
    private String value;

    public ElasticInputFormat(String query, String maxVal, String type) {
        this.maxVal = maxVal;
        this.query = query;
        this.type = type;
        if(type == "wiki")
            this.value = "description";
        else if(type == "caracter")
            this.value = "said";
        else if (type == "tweet")
            this.value = "text";

    }

    public ElasticInputFormat(String query) {
        this.query = query;
    }

    @Override
    public void configure(Configuration configuration) {
        i=0;

        config = new HashMap<>();
        config.put("bulk.flush.max.actions", "1");
        config.put("cluster.name", "TestCluster");

        transportAddresses = new ArrayList<>();
        try {
            transportAddresses.add(new InetSocketAddress(InetAddress.getByName("elassandra"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    @Override
    public BaseStatistics getStatistics(BaseStatistics baseStatistics) throws IOException {
        return null;
    }

    @Override
    public InputSplit[] createInputSplits(int minNumSplits) throws IOException {
        GenericInputSplit[] split = new GenericInputSplit[]{new GenericInputSplit(0, 1)};
        return split;
    }

    @Override
    public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
        return new DefaultInputSplitAssigner(inputSplits);
    }

    @Override
    public void open(InputSplit inputSplit) throws IOException {
        ArrayList transportNodes = new ArrayList(this.transportAddresses.size());
        Iterator settings = this.transportAddresses.iterator();

        while(settings.hasNext()) {
            InetSocketAddress transportClient = (InetSocketAddress)settings.next();
            transportNodes.add(new InetSocketTransportAddress(transportClient));
        }

        Settings settings1 = Settings.settingsBuilder().put(this.config).build();
        TransportClient transportClient1 = TransportClient.builder().settings(settings1).build();
        Iterator nodes = transportNodes.iterator();

        while(nodes.hasNext()) {
            TransportAddress bulkProcessorBuilder = (TransportAddress)nodes.next();
            transportClient1.addTransportAddress(bulkProcessorBuilder);
        }

        ImmutableList nodes1 = ImmutableList.copyOf(transportClient1.connectedNodes());
        if(nodes1.isEmpty()) {
            throw new RuntimeException("Client is not connected to any Elasticsearch nodes!");
        } else {
            this.client = transportClient1;
            SearchResponse response = client.prepareSearch("twitter")
                    .setTypes(this.type)
                    .setSearchType(SearchType.DEFAULT)
                    .setQuery(QueryBuilders.queryStringQuery(query)).get();

            results = response.getHits().getHits();

        }

    }

    @Override
    public boolean reachedEnd() throws IOException {
        return this.results.length <= i;
    }

    @Override
    public OUT nextRecord(OUT out) throws IOException {
        Map<String,Object> item = this.results[i].getSource();
        for(int ind = 0; ind < out.getArity(); ++ind) {
            out.setField(item.get(this.value), ind);
        }
        i++;
        return out;
    }

    @Override
    public void close() throws IOException {
        if(this.client != null) {
            this.client.close();
        }
    }
}
