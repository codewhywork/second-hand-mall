package com.bistu.why.model.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-25 16:52:10
 */
@Data
@TableName("second_hand_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@TableId
	private String userId;
	/**
	 * 
	 */
	private String uname;
	/**
	 * 
	 */
	private String passwd;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 积分
	 */
	private BigDecimal scoring;
	/**
	 * 头像
	 */
	private String img;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 银行账户
	 */
	private String bankAccount;
	/**
	 * 默认地址
	 */
	private String defaultAddress;

}
