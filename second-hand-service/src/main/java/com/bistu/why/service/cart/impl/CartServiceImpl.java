package com.bistu.why.service.cart.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.shaded.com.google.common.reflect.TypeToken;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bistu.why.common.constant.RedisConstant;
import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.common.renren.utils.R;
import com.bistu.why.common.renren.utils.RRException;
import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.dao.cart.CartDao;
import com.bistu.why.model.cart.CartEntity;
import com.bistu.why.model.order.OrderEntity;
import com.bistu.why.model.product.SecondHandSkuInfoEntity;
import com.bistu.why.model.threadlocalVo.UserAuthInfo;
import com.bistu.why.model.dto.CartDto;
import com.bistu.why.service.cart.CartService;
import com.bistu.why.service.order.OrderService;
import com.bistu.why.service.product.SecondHandSkuInfoService;
import com.bistu.why.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    SecondHandSkuInfoService secondHandSkuInfoService;

    @Override
    public R addCart(CartDto cartDto, HttpSession httpSession) {
        String sessionId = httpSession.getId();
        UserAuthInfo userAuthInfo = UserThreadLocal.geteUserThreadLocal().get();
        boolean auth = userAuthInfo.isAuth();
        if (auth) {
            CartEntity cartEntity = baseMapper.selectOne(new LambdaQueryWrapper<CartEntity>()
                    .eq(CartEntity::getUserId, userAuthInfo.getUserId()).eq(CartEntity::getSkuId, cartDto.getSkuId()));
            if (cartEntity != null) {
                cartEntity.setCount(cartEntity.getCount() + cartDto.getCount());
                this.baseMapper.updateById(cartEntity);
                return R.ok();
            } else {
                //不存在该商品
                cartEntity = new CartEntity();
                cartEntity.setUserId(userAuthInfo.getUserId());
                cartEntity.setSkuId(cartDto.getSkuId());
                cartEntity.setCount(cartDto.getCount());
                this.baseMapper.insert(cartEntity);
                return R.ok();
            }
        } else {
            String jsonObj = (String) redisTemplate.opsForValue().get(RedisConstant.CART_LIST_KEY_PREFIX + sessionId);
            List<CartEntity> cartEntities = new ArrayList<>();
            if (jsonObj != null) {
                //拿到原有的数据
                Type type = new TypeToken<List<CartEntity>>() {
                }.getType();
                cartEntities = JSON.parseObject(jsonObj, type);
                List<CartEntity> newCart = new ArrayList<>();

                boolean flag = false;
                for (CartEntity entity : cartEntities) {
                    if (flag) {
                        break;
                    }
                    if (entity.getSkuId().equals(cartDto.getSkuId())) {
                        entity.setCount(entity.getCount() + cartDto.getCount());
                        flag = true;
                    }
                    newCart.add(entity);
                }
                if (!flag) {
                    CartEntity cartEntity = new CartEntity();
                    BeanUtils.copyProperties(cartDto, cartEntity);
                    newCart.add(cartEntity);
                }
                redisTemplate.opsForValue().set(RedisConstant.CART_LIST_KEY_PREFIX + sessionId, JSON.toJSONString(newCart), RedisConstant.REDIS_EXPIRATION_KET_PREFIX, TimeUnit.SECONDS);
            } else {
                CartEntity cartEntity = new CartEntity();
                BeanUtils.copyProperties(cartDto, cartEntity);
                cartEntities.add(cartEntity);
                redisTemplate.opsForValue().set(RedisConstant.CART_LIST_KEY_PREFIX + sessionId, JSON.toJSONString(cartEntities), RedisConstant.REDIS_EXPIRATION_KET_PREFIX, TimeUnit.SECONDS);
            }
        }
        return R.ok();
    }

    @Override
    //TODO 待优化
    public PageUtils queryPage(int curren, int size, HttpSession httpSession) {
        UserAuthInfo userAuthInfo = UserThreadLocal.geteUserThreadLocal().get();
        boolean auth = userAuthInfo.isAuth();
        String sessionId = httpSession.getId();
        List<CartEntity> unAuthCartEntities = null;
        List<CartEntity> authCartEntities = null;
        List<CartEntity> res = new ArrayList<>();
        //先获取未登录时的数据
        String jsonObj = (String) redisTemplate.opsForValue().get(RedisConstant.CART_LIST_KEY_PREFIX + sessionId);
        unAuthCartEntities = JSON.parseObject(jsonObj, List.class);
        //判断unAuthCartEntities是否为空 不为空添加进去
        //如果当前是已登录状态 合并购物车
        if (auth) {
            //查询数据库
            authCartEntities = this.baseMapper.selectList(new LambdaQueryWrapper<CartEntity>().eq(CartEntity::getUserId, userAuthInfo.getUserId()));
            redisTemplate.delete(RedisConstant.CART_LIST_KEY_PREFIX + sessionId);
        }
        if (unAuthCartEntities == null || unAuthCartEntities.size() == 0) {
            return new PageUtils(authCartEntities, authCartEntities == null ? 0 : authCartEntities.size(), curren, size);
        }
        if (authCartEntities == null || authCartEntities.size() == 0) {
            return new PageUtils(unAuthCartEntities, unAuthCartEntities.size(), curren, size);
        }
        //合并
        res = mergeCartList(unAuthCartEntities, authCartEntities);
        return new PageUtils(res, res.size(), curren, size);
    }

    @Override
    @Transactional
    public R toPay(List<CartDto> cartDtos) {
        UserAuthInfo userAuthInfo = UserThreadLocal.geteUserThreadLocal().get();
        String uname = userAuthInfo.getUname();
        String userId = userAuthInfo.getUserId();
        BigDecimal bigDecimal = new BigDecimal(0);
        List<OrderEntity> orderEntities = new ArrayList<>();
        for (CartDto cartDto : cartDtos) {
            //先调用订单服务
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //等等信息
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderSn(uuid);
            orderEntity.setUname(uname);
            orderEntity.setSkuId(cartDto.getSkuId());
            orderEntity.setSaleCount(cartDto.getCount());
            orderEntity.setUserId(userId);
            orderEntity.setCreateTime(new Date());
            orderEntity.setStatus(0);
            orderEntity.setAutoConfirmDay(7);
            //查询sku的价格
            SecondHandSkuInfoEntity secondHandSkuInfoEntity = secondHandSkuInfoService.getOne(new LambdaQueryWrapper<SecondHandSkuInfoEntity>().eq(SecondHandSkuInfoEntity::getSkuId, cartDto.getSkuId()));
            //做优惠的扣减 . . . .
            orderEntity.setPayAmount(secondHandSkuInfoEntity.getPrice());
            //统计价格
            bigDecimal = bigDecimal.add(secondHandSkuInfoEntity.getPrice());
            orderEntities.add(orderEntity);
        }
        orderService.saveBatch(orderEntities);
        //TODO 扣减库存
        //扣减余额
        int i = userService.subAccount(userId, bigDecimal);
        if (i == 0) {
            throw new RRException("账户余额不足");
        }
        //扣减成功 修改订单状态
        orderEntities = orderEntities.stream().peek(o -> o.setStatus(1)).collect(Collectors.toList());
        orderService.updateBatchById(orderEntities);
        return R.ok();
    }

    private List<CartEntity> mergeCartList(List<CartEntity> unAuthCartEntities, List<CartEntity> authCartEntities) {
        ArrayList<CartEntity> cartEntities = new ArrayList<>();
        for (CartEntity unAuthCartEntity : unAuthCartEntities) {
            for (CartEntity authCartEntity : authCartEntities) {
                if (unAuthCartEntity.getSkuId().equals(authCartEntity.getSkuId())) {
                    authCartEntity.setCount(authCartEntity.getCount() + 1);
                }
                cartEntities.add(authCartEntity);
            }
        }
        return cartEntities;
    }
}