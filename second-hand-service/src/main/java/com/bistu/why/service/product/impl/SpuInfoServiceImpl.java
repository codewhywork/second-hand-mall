package com.bistu.why.service.product.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bistu.why.common.constant.RabbitBindingConstant;
import com.bistu.why.common.renren.utils.Query;
import com.bistu.why.model.product.*;
import com.bistu.why.model.dto.SpuSavaDto;
import com.bistu.why.model.stock.StockEntity;
import com.bistu.why.service.product.*;
import com.bistu.why.service.stock.StockService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.renren.utils.PageUtils;


import com.bistu.why.dao.product.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    SpuAttributeValueService spuAttributeValueService;

    @Autowired
    SecondHandSkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    SkuSaleAttributeValueService skuSaleAttributeValueService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttributeGroupService attributeGroupService;

    @Autowired
    AttributeAttrgroupRelationService attributeAttrgroupRelationService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StockService stockService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<SpuInfoEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );
        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Transactional
    @Override
    public void save(List<SpuSavaDto> savaVo) {
        for (SpuSavaDto spuSavaDto : savaVo) {
            HashMap<String, Object> hashMap = new HashMap<>();
            spuSavaDto.setCreateTime(new Date());
            //1.先保存spu信息
            SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
            BeanUtils.copyProperties(spuSavaDto, spuInfoEntity);
            this.save(spuInfoEntity);
            hashMap.put("spuInfo", spuInfoEntity);
            Long spuId = spuInfoEntity.getId();
            Long catalogId = spuSavaDto.getCatalogId();
            //2.保存spu图片信息
            List<String> spuImages = spuSavaDto.getSpuImages();
            List<SpuImagesEntity> spuImagesEntityList = spuImages.stream().map(img -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuId);
                spuImagesEntity.setImgName(spuSavaDto.getSpuName());
                spuImagesEntity.setImgUrl(img);
                spuImagesEntity.setImgSort(0);
                spuImagesEntity.setDefaultFlag(0);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(spuImagesEntityList);
            hashMap.put("spuImg", spuImagesEntityList);
            //3.保存spu规格参数
            //spu选中的基本属性
            List<SpuAttributeValueEntity> attributeValueEntityList = new ArrayList<>();
            List<AttributeEntity> attributeEntities = spuSavaDto.getAttributeEntities();
            for (AttributeEntity attributeEntity : attributeEntities) {
                Long attrId = attributeEntity.getAttrId();
                String attrName = attributeEntity.getAttrName();
                String attrValue = attributeEntity.getValueSelect();
                //基本属性 保存到spu
                SpuAttributeValueEntity spuAttributeValueEntity = new SpuAttributeValueEntity();
                spuAttributeValueEntity.setAttrName(attrName);
                spuAttributeValueEntity.setAttrValue(attrValue);
                spuAttributeValueEntity.setAttrId(attrId);
                spuAttributeValueEntity.setAttrSort(0);
                spuAttributeValueEntity.setQuickShow(0);
                spuAttributeValueEntity.setSpuId(spuId);
                attributeValueEntityList.add(spuAttributeValueEntity);
            }
            //保存spu基本属性
            spuAttributeValueService.saveBatch(attributeValueEntityList);
            hashMap.put("spuBaseAttr", attributeValueEntityList);
            //保存sku信息
            List<SecondHandSkuInfoEntity> skuInfoEntities = spuSavaDto.getSkuInfoEntities();
            skuInfoEntities = skuInfoEntities.stream().peek(skuItem -> {
                skuItem.setSpuId(spuId);
                skuItem.setCatalogId(catalogId);
            }).collect(Collectors.toList());

            //5.保存sku的图片信息
            List<SkuImagesEntity> skuImagesEntities = skuInfoEntities.stream().map(item -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                skuImagesEntity.setDefaultFlag(0);
                skuImagesEntity.setSkuId(item.getSkuId());
                skuImagesEntity.setImgUrl(item.getSkuDefaultImg());
                skuImagesEntity.setImgSort(0);
                return skuImagesEntity;
            }).collect(Collectors.toList());
            skuImagesService.saveBatch(skuImagesEntities);
            hashMap.put("skuImg", skuImagesEntities);

            List<SkuSaleAttributeValueEntity> skuSaleAttributeValueEntityList = new ArrayList<>();
            for (SecondHandSkuInfoEntity skuInfoEntity : skuInfoEntities) {
                skuInfoService.save(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                //保存sku销售属性
                List<SkuSaleAttributeValueEntity> skuSaleAttributeValueEntity = skuInfoEntity.getSkuSaleAttributeValueEntity();
                for (SkuSaleAttributeValueEntity saleAttributeValueEntity : skuSaleAttributeValueEntity) {
                    saleAttributeValueEntity.setSkuId(skuId);
                    skuSaleAttributeValueEntityList.add(saleAttributeValueEntity);
                }
                //库存信息 暂时写死
                StockEntity stockEntity = new StockEntity();
                stockEntity.setStock(20);
                stockEntity.setSkuId(skuId);
                stockService.save(stockEntity);
                skuInfoEntity.setSkuSaleAttributeValueEntity(skuSaleAttributeValueEntity);
                //发送消息 保存sku静态模板
                hashMap.put("skuId", skuInfoEntity.getSkuId());
                hashMap.put("skuInfo", skuInfoEntity);
                rabbitTemplate.convertAndSend(RabbitBindingConstant.ITEM_STATIC_EXCHANGE, "", JSON.toJSONString(hashMap));
            }
            skuSaleAttributeValueService.saveBatch(skuSaleAttributeValueEntityList);
            //6.优惠券，库存，满减规则，积分
            //发送消息同步到es
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(RabbitBindingConstant.PRODUCT_SEARCH_EXCHANGE, "", JSON.toJSONString(spuInfoEntity), correlationData);
        }
    }
}