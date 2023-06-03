package com.bistu.why.model.stock;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 库存表，代表库存，秒杀库存等信息
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-29 14:13:01
 */
@Data
@TableName("second_hand_stock")
public class StockEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存对应的商品sku id
	 */
	@TableId
	private Long skuId;
	/**
	 * 可秒杀库存
	 */
	private Integer seckillStock;
	/**
	 * 秒杀总数量
	 */
	private Integer seckillTotal;
	/**
	 * 库存数量
	 */
	private Integer stock;

}
