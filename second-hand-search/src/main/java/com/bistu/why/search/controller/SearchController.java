package com.bistu.why.search.controller;

import com.bistu.why.model.dto.SearchProductDto;
import com.bistu.why.search.entity.Product;
import com.bistu.why.search.service.SearchProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author why
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchProductService searchProductService;

    @GetMapping("/search/{current}/{size}")
    public List<Product> search(@RequestBody SearchProductDto searchProductDto, @PathVariable("current") Integer current, @PathVariable("size") Integer size) throws IOException {
        return searchProductService.search(searchProductDto, current, size);
    }

    @GetMapping("/list")
    public List<Product> list(Map<String,Integer> params) {
        return searchProductService.list(params);
    }
}
