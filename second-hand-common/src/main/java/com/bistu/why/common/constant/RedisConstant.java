package com.bistu.why.common.constant;

public class RedisConstant {
    public static final String AUTHENTICATION_CACHE_KEY_PREFIX = "second_hand:admin:";
    public static final String AUTHENTICATION_USER_LOGIN_KEY_PREFIX = "second_hand:user:";
    public static final String CART_LIST_KEY_PREFIX = "second_hand:cart:";
    public static final Integer REDIS_EXPIRATION_KET_PREFIX = 60 * 24;
    public static final String CATEGORY_LIST_LOCK_KET_PREFIX = "category_list_lock";
    public static final String USER_REG_SMS_KET_PREFIX = "user_reg_sms:";
    public static final Integer USER_REG_SMS_TTL_KET_PREFIX  = 5 * 60;


}
