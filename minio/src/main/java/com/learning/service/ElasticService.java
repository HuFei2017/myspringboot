package com.learning.service;

import com.learning.configuration.ElasticConfiguration;
import com.learning.dto.Devlevel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ElasticService
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 18:49
 * @Version 1.0
 */
@Service
public class ElasticService {

    private ElasticsearchRestTemplate restTemplate = null;
    private RestHighLevelClient restHighLevelClient = null;
    private final ElasticConfiguration configuration;
    private final Logger log = LoggerFactory.getLogger(ElasticService.class);

    ElasticService(ElasticConfiguration configuration) {
        this.configuration = configuration;
        getRestHighLevelClient();
        getRestTemplate();
    }

    void addDeviceInfoToElastic(Devlevel devlevel) {

        RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

        if (null != restHighLevelClient) {
            try {
                BulkRequest bulkRequest = new BulkRequest();
                IndexRequest indexRequest = new IndexRequest("sim-devlevel-data");
                indexRequest.source(devlevel.getMapValue());
                bulkRequest.add(indexRequest);
                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    List<Devlevel> getDevlevels(String ordernumber, String filename) {

        List<Devlevel> list = new ArrayList<>();

        //构建es查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (null != ordernumber) {
            boolQueryBuilder.must(QueryBuilders.termQuery("ordernumber", ordernumber));
        }
        if (null != filename) {
            boolQueryBuilder.must(QueryBuilders.termQuery("filename", filename));
        }

        SearchHits<Devlevel> hits = queryData(boolQueryBuilder);

        if (null != hits) {
            for (SearchHit<Devlevel> hit : hits.getSearchHits()) {
                Devlevel devlevel = new Devlevel();
                devlevel.setFilePath(hit.getContent().getFilePath());
                devlevel.setCreator(hit.getContent().getCreator());
                devlevel.setOrdernumber(hit.getContent().getOrdernumber());
                devlevel.setFilename(hit.getContent().getFilename());
                list.add(devlevel);
            }
        }

        return list;
    }

    private SearchHits<Devlevel> queryData(BoolQueryBuilder boolQueryBuilder) {

        ElasticsearchRestTemplate restTemplate = getRestTemplate();

        if (null != restTemplate) {

            //构建es查询条件
            PageRequest pageRequest = PageRequest.of(0, 10000);
            //组建查询体
            NativeSearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withPageable(pageRequest)
                    .build();
            return restTemplate.search(query, Devlevel.class);
        }

        return null;
    }

    private RestHighLevelClient getRestHighLevelClient() {
        if (null == restHighLevelClient) {
            if (null != configuration) {
                ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                        .connectedTo(configuration.getConnection().split(","))
                        .build();
                this.restHighLevelClient = RestClients.create(clientConfiguration).rest();
            }
        }
        return restHighLevelClient;
    }

    private ElasticsearchRestTemplate getRestTemplate() {
        if (null == restTemplate) {
            if (null != configuration) {
                RestHighLevelClient restHighLevelClient = getRestHighLevelClient();
                if (null != restHighLevelClient) {
                    this.restTemplate = new ElasticsearchRestTemplate(restHighLevelClient);
                }
            }
        }
        return restTemplate;
    }

}
