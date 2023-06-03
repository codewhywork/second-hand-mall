package com.bistu.why.search.service;

import com.bistu.why.model.dto.SearchProductDto;
import com.bistu.why.search.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author why
 */
public interface SearchProductService {
    List<Product> search(SearchProductDto searchProductDto, Integer current, Integer size) throws IOException;

    List<Product> list(Map<String, Integer> params);
}
