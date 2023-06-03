package com.bistu.why.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-27 12:54:01
 */
@Data
@TableName("second_hand_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)

	private Integer brandId;
	/**
	 * 
	 */
	private String brandName;

}
