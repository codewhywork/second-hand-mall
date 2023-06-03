package com.bistu.why.search;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.search.dao.ProductRepository;
import com.bistu.why.search.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = SearchApplication.class)
public class esTest {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("second_hand_products");
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.matchQuery("spuName", "product"));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query);
        searchRequest.source(sourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        log.info("hits:{}", (Object) hits);
    }

    @Test
    public void testSave() throws IOException {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setSpuName("product: " + i);
            product.setSpuDescription("spuDescription");
            productRepository.save(product);
        }
    }

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("second_hand_products");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testAddDocument() throws IOException {
        Product product = new Product();
        IndexRequest request = new IndexRequest();
        request.index("second_hand_products").id("1");
        request.source(JSON.toJSONString(product), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
        System.out.println(response.status());
        log.info("response:{}",response);
    }
}
