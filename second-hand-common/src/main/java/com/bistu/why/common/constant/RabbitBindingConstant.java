package com.bistu.why.common.constant;

/**
 * @author why
 */
public class RabbitBindingConstant {
    public static final String USER_REG_QUEUE = "user_reg_queue";
    public static final String USER_REG_EXCHANGE = "user_reg_exchange";
    public static final String PRODUCT_SEARCH_EXCHANGE = "product_search_exchange";
    public static final String PRODUCT_SEARCH_QUEUE = "product_search_queue";
    public static final String ITEM_STATIC_EXCHANGE = "item_static_exchange";
    public static final String ITEM_STATIC_QUEUE = "item_static_queue";
    public static final String ORDER_TTL_EXCHANGE = "order_ttl_exchange";
    public static final String ORDER_TTL_QUEUE = "order_ttl_queue";
    public static final String ORDER_DEAD_QUEUE = "order_dead_queue";
    public static final String ORDER_TTL_ROUTING_KEY = "order.ttl.routingKey";
    public static final String ORDER_DEAD_ROUTING_KEY = "order.dead.routingKey";
    public static final Integer ORDER_EXPIRES_TIME = 10 * 1000;


}
