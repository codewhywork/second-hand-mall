package com.bistu.why.search.dao;

import com.bistu.why.search.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author why
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product,Long> {

}
