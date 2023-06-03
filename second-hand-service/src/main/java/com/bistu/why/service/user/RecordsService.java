package com.bistu.why.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.common.renren.utils.PageUtils;

import com.bistu.why.model.user.RecordsEntity ;

import java.util.Map;
/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-27 23:22:47
 */
public interface RecordsService extends IService<RecordsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

