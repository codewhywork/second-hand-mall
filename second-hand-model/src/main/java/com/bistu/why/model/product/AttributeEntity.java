package com.bistu.why.model.product;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品属性
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@Data
@TableName("second_hand_attribute")
public class AttributeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@TableId(type = IdType.AUTO)
	private Long attrId;
	/**
	 * 属性名
	 */
	private String attrName;
	/**
	 * 属性图标
	 */
	private String icon;
	/**
	 * 可选值列表[用分号分隔]
	 */
	private String valueSelect;
	/**
	 * 属性类型[0-销售属性，1-基本属性
	 */
	private Integer attrType;
	/**
	 * 启用状态[0 - 禁用，1 - 启用]
	 */
	private Long enable;
	/**
	 * 所属分类
	 */
	private Long catelogId;

}
