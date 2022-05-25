package com.learning.test;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName DocTest
 * @Description TODO
 * @Author hufei
 * @Date 2022/5/24 15:04
 * @Version 1.0
 */
public class DocTest {

    public static void main(String[] args) throws IOException {

        String indexName = "test-index";
        Map<String, Object> value = new HashMap<>();
        value.put("column1", "1111");
        value.put("column2", "2222");
        value.put("column3", 111);
        value.put("column4", 222);

//        addDoc(indexName, value);

//        GetResponse response = getDoc(indexName, "ef9ba5b1-77e6-4e62-914f-3c1a79bb20d4");
//        System.out.println(response.getSource());

//        MultiGetResponse responseList = multiGetDoc(indexName, new String[]{"gTLl9IABp0RgfPVFm95S", "ef9ba5b1-77e6-4e62-914f-3c1a79bb20d4"});
//        for (MultiGetItemResponse item : responseList) {
//            System.out.println(item.getResponse().getSource());
//        }

//        SearchResponse response1 = queryDoc(indexName, "column1", "1111", true);
//        SearchResponse response2 = queryDoc(indexName, "column1", "11", false);
//        System.out.println();

//        updateDoc(indexName, "ef9ba5b1-77e6-4e62-914f-3c1a79bb20d4");

//        deleteDoc(indexName, "ef9ba5b1-77e6-4e62-914f-3c1a79bb20d4");

//        multiDeleteDoc(indexName);

        SearchResponse response = groupDoc(indexName);
        Terms aa = response.getAggregations().get("agg");
        System.out.println();
    }

    private static void addDoc(String indexName, Map<String, Object> value) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        IndexRequest request = new IndexRequest(indexName).id(UUID.randomUUID().toString()).source(value);
        request.timeout(TimeValue.timeValueSeconds(5));
        client.index(request, RequestOptions.DEFAULT);
    }

    private static GetResponse getDoc(String indexName, String docId) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        GetRequest request = new GetRequest(
                indexName,
                docId);
        return client.get(request, RequestOptions.DEFAULT);
    }

    private static void updateDoc(String indexName, String docId) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("column1", "1111111111");
        jsonMap.put("reason", "daily update");
        UpdateRequest request = new UpdateRequest(indexName, docId).doc(jsonMap);
        request.timeout(TimeValue.timeValueSeconds(5));
        client.update(request, RequestOptions.DEFAULT);
    }

    private static void deleteDoc(String indexName, String docId) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        DeleteRequest request = new DeleteRequest(indexName, docId);
        request.timeout(TimeValue.timeValueSeconds(5));
        client.delete(request, RequestOptions.DEFAULT);
    }

    private static MultiGetResponse multiGetDoc(String indexName, String[] docIds) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        MultiGetRequest request = new MultiGetRequest();
        for (String docId : docIds) {
            request.add(new MultiGetRequest.Item(indexName, docId));
        }
        return client.mget(request, RequestOptions.DEFAULT);
    }

    private static void multiDeleteDoc(String indexName) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("column1", "1111")));
        request.setTimeout(TimeValue.timeValueSeconds(5));
        request.setMaxDocs(1000000);
        client.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    private static SearchResponse queryDoc(String indexName, String columnName, String value, boolean matchAll) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (matchAll) {
            //精确
            searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(columnName, value)));
        } else {
            //模糊
            searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.wildcardQuery(columnName, value)));
        }
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private static SearchResponse groupDoc(String indexName) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("column1", "a1")));
        searchSourceBuilder.fetchSource(new String[]{"column1", "column3"}, new String[0]);
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").script(
                new Script("doc['column1']+'_'+doc['column3']")
        ).size(10000);
        searchSourceBuilder.aggregation(aggregation);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        searchSourceBuilder.fetchSource(false);
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
