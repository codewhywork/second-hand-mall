package com.bistu.why.search.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.bistu.why.model.dto.SearchProductDto;
import com.bistu.why.search.dao.ProductRepository;
import com.bistu.why.search.entity.Product;
import com.bistu.why.search.service.SearchProductService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author why
 */
@Service
@Slf4j
public class SearchProductServiceImpl implements SearchProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestHighLevelClient client;

    @Override
    public List<Product> search(SearchProductDto searchProductDto, Integer current, Integer size) throws IOException {
        SearchRequest searchRequest = new SearchRequest("second_hand_products");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlighter = new HighlightBuilder();
        highlighter.preTags("<em>");
        highlighter.postTags("</em>");
        highlighter.requireFieldMatch(false);
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (current == null || size == null) {
            current = 0;
            size = 10;
        }
        if (StringUtils.hasLength(searchProductDto.getSpuName()) && StringUtils.hasText(searchProductDto.getSpuName())) {
            String spuName = searchProductDto.getSpuName();
            query.must(QueryBuilders.matchQuery("spuName", spuName));
            highlighter.field("spuName");
        }
        if (StringUtils.hasLength(searchProductDto.getIntroduce()) && StringUtils.hasText(searchProductDto.getIntroduce())) {
            String introduce = searchProductDto.getIntroduce();
            query.must(QueryBuilders.matchQuery("introduce", introduce));
            highlighter.field("introduce");
        }
        if (StringUtils.hasLength(searchProductDto.getSpuDescription()) && StringUtils.hasText(searchProductDto.getSpuDescription())) {
            String description = searchProductDto.getSpuDescription();
            query.must(QueryBuilders.matchQuery("spuDescription", description));
        }
        if (StringUtils.hasLength(searchProductDto.getSort()) && StringUtils.hasText(searchProductDto.getSort())) {
            String sort = searchProductDto.getSort();
            //saleCount;asc;price;desc
            String[] split = sort.split(";");
            for (int i = 0; i < split.length; i = i + 2) {
                sourceBuilder.sort(split[i], SortOrder.valueOf(split[i + 1]));
            }
        }
        sourceBuilder.query(query);
        sourceBuilder.highlighter(highlighter);
        sourceBuilder.from(current);
        sourceBuilder.size(size);
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        List<Product> products = new ArrayList<>();
        for (SearchHit hit : hits) {
            Product product = JSONObject.parseObject(hit.getSourceAsString(), Product.class);
            HighlightField spuName = hit.getHighlightFields().get("spuName");
            if (spuName != null) {
                product.setSpuName(spuName.getFragments()[0].toString());
            }
            HighlightField introduce = hit.getHighlightFields().get("introduce");
            if (introduce != null) {
                product.setIntroduce(introduce.getFragments()[0].toString());
            }
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> list(Map<String, Integer> params) {
        int current = params.get("current") == null ? 0 : params.get("current");
        int size = params.get("size") == null ? 10 : params.get("size");
        Page<Product> all = productRepository.findAll(PageRequest.of(current, size));
        return all.getContent();
    }
}
