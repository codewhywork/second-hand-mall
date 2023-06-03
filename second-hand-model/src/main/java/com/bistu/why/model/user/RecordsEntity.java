package com.bistu.why.model.user;

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
 * @date 2023-05-27 23:22:47
 */
@Data
@TableName("second_hand_records")
public class RecordsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String recordsId;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 0:退货，1:收货
	 */
	private Integer status;
	/**
	 * sku_id
	 */
	private Integer skuId;

	private Long orderId;

}
