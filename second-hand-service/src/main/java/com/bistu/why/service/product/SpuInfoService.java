package com.bistu.why.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.product.
SpuInfoEntity;
import com.bistu.why.model.dto.SpuSavaDto;

import java.util.List;
import java.util.Map;

/**
 * spu信息
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save( List<SpuSavaDto> spuSavaDto);
}

