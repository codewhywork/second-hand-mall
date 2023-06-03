package com.bistu.why.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author why
 */
@Configuration
public class GateWayRouterConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-router"
                        , p -> p.path("/api/product/**")
                                .filters(r -> r.rewritePath("/api/product/(?<segment>/?.*)"
                                        , "/product/$\\{segment}")).uri("lb://second-hand-mall-product"))
                .route("admin-router"
                        , p -> p.path("/api/admin/**")
                                .filters(r -> r.rewritePath("/api/admin/(?<segment>/?.*)"
                                        , "/admin/$\\{segment}")).uri("lb://second-hand-mall-admin"))
                .route("user-router"
                        , p -> p.path("/api/user/**")
                                .filters(r -> r.rewritePath("/api/user/(?<segment>/?.*)"
                                        , "/user/$\\{segment}")).uri("lb://second-hand-mall-user"))
                .route("order-router"
                        , p -> p.path("/api/order/**")
                                .filters(r -> r.rewritePath("/api/order/(?<segment>/?.*)"
                                        , "/order/$\\{segment}")).uri("lb://second-hand-mall-order"))
                .route("cart-router"
                        , p -> p.path("/api/cart/**")
                                .filters(r -> r.rewritePath("/api/cart/(?<segment>/?.*)"
                                        , "/cart/$\\{segment}")).uri("lb://second-hand-mall-cart"))
                .build();
    }
}
