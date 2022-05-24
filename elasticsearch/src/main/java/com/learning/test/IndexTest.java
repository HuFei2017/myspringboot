package com.learning.test;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author hufei
 * @Date 2022/5/24 13:45
 * @Version 1.0
 */
public class IndexTest {

    public static void main(String[] args) throws IOException {

        String indexName = "test-index";

        //创建索引
        Map<String, String> properties = new HashMap<>();
        properties.put("column1", "keyword");
        properties.put("column2", "keyword");
        properties.put("column3", "integer");
        properties.put("column4", "integer");
        createIndex(indexName, properties);

//        //删除索引
//        deleteIndex(indexName);
    }

    private static String createIndex(String indexName, Map<String, String> properties) throws IOException {

        RestHighLevelClient client = RestClient.initRestClient();

        Map<String, Object> allPropMap = new HashMap<>();
        Map<String, Object> propMap = new HashMap<>();
        for (Map.Entry<String, String> property : properties.entrySet()) {
            Map<String, String> propTypeMap = new HashMap<>();
            propTypeMap.put("type", property.getValue());
            propMap.put(property.getKey().toLowerCase(), propTypeMap);
        }
        allPropMap.put("properties", propMap);

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(
                Settings.builder()
                        .put("number_of_shards", 3)
                        .put("max_result_window", 1000000)
                        .put("number_of_replicas", 0)
        );
        request.mapping(allPropMap);

        request.setTimeout(TimeValue.timeValueSeconds(5));
        request.setTimeout(TimeValue.timeValueSeconds(5));

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.index();
    }

    private static void deleteIndex(String indexName) throws IOException {
        RestHighLevelClient client = RestClient.initRestClient();
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        request.timeout(TimeValue.timeValueSeconds(5));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(5));
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

}
