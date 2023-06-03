package com.bistu.why.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author why
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "second_hand_products")
public class Product {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String spuName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String spuDescription;

    @Field(type = FieldType.Long)
    private Long catalogId;

    @Field(type = FieldType.Double)
    private BigDecimal weight;

    @Field(type = FieldType.Integer)
    private Integer publishStatus;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String introduce;
}