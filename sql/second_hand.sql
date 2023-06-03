/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.58.190
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.58.190:3306
 Source Schema         : second_hand

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 28/05/2023 16:18:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/admin/**');
INSERT INTO `menu` VALUES (2, '/user/**');
INSERT INTO `menu` VALUES (3, '/guest/**');

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `mid` int(0) NULL DEFAULT NULL,
  `rid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mid`(`mid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (1, 1, 1);
INSERT INTO `menu_role` VALUES (2, 2, 2);
INSERT INTO `menu_role` VALUES (3, 3, 3);
INSERT INTO `menu_role` VALUES (4, 3, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nameZh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO `role` VALUES (2, 'ROLE_USER', '普通用户');
INSERT INTO `role` VALUES (3, 'ROLE_GUEST', '游客');

-- ----------------------------
-- Table structure for second_hand_attribute
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_attribute`;
CREATE TABLE `second_hand_attribute`  (
  `attr_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `attr_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性图标',
  `value_select` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可选值列表[用分号分隔]',
  `attr_type` tinyint(0) NULL DEFAULT NULL COMMENT '属性类型[0-销售属性，1-基本属性',
  `enable` bigint(0) NULL DEFAULT NULL COMMENT '启用状态[0 - 禁用，1 - 启用]',
  `catelog_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类',
  PRIMARY KEY (`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品属性' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_attribute
-- ----------------------------
INSERT INTO `second_hand_attribute` VALUES (7, '入网型号', 'xxx', 'A2217;C3J;以官网信息为准', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (8, '入市年份', 'xxx', '2018;2019', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (9, '颜色', 'xxx', '黑色;白色;蓝色;', 0, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (10, '内存', 'xxx', '4GB;6GB;8GB;12GB', 0, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (11, '机身颜色', 'xxx', '黑色;白色', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (12, '版本', 'xxx', NULL, 0, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (13, '机身长度', 'xxx', '158.3;135.9', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (14, '机身材质工艺', 'xxx', '以官网信息为准;陶瓷;玻璃', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (15, 'CPU品牌', 'xxx', '高通;海思;以官网信息为准', 1, 1, 225);
INSERT INTO `second_hand_attribute` VALUES (16, 'CPU型号', 'xxx', '骁龙665;骁龙845;骁龙855;骁龙730', 1, 1, 225);

-- ----------------------------
-- Table structure for second_hand_attribute_attrgroup_relation
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_attribute_attrgroup_relation`;
CREATE TABLE `second_hand_attribute_attrgroup_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attr_id` bigint(0) NULL DEFAULT NULL COMMENT '属性id',
  `attr_group_id` bigint(0) NULL DEFAULT NULL COMMENT '属性分组id',
  `attr_sort` int(0) NULL DEFAULT NULL COMMENT '属性组内排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '属性&属性分组关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_attribute_attrgroup_relation
-- ----------------------------
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (23, 7, 1, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (24, 8, 1, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (26, 11, 2, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (27, 13, 2, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (28, 14, 2, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (29, 15, 7, NULL);
INSERT INTO `second_hand_attribute_attrgroup_relation` VALUES (30, 16, 7, NULL);

-- ----------------------------
-- Table structure for second_hand_attribute_group
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_attribute_group`;
CREATE TABLE `second_hand_attribute_group`  (
  `attr_group_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `attr_group_name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组图标',
  `catelog_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类id',
  PRIMARY KEY (`attr_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '属性分组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_attribute_group
-- ----------------------------
INSERT INTO `second_hand_attribute_group` VALUES (1, '主体', 0, '主体', 'xx', 225);
INSERT INTO `second_hand_attribute_group` VALUES (2, '基本信息', 0, '基本信息', 'xx', 225);
INSERT INTO `second_hand_attribute_group` VALUES (4, '屏幕', 0, '屏幕', 'xx', 225);
INSERT INTO `second_hand_attribute_group` VALUES (7, '主芯片', 0, '主芯片', 'xx', 225);

-- ----------------------------
-- Table structure for second_hand_category
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_category`;
CREATE TABLE `second_hand_category`  (
  `cat_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `parent_cid` bigint(0) NULL DEFAULT NULL COMMENT '父分类id',
  `cat_level` int(0) NULL DEFAULT NULL COMMENT '层级',
  `show_flag` tinyint(0) NULL DEFAULT NULL COMMENT '0不显示，1显示',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `icon` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标地址',
  `product_unit` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位',
  PRIMARY KEY (`cat_id`) USING BTREE,
  INDEX `parent_cid`(`parent_cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1433 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品三级分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_category
-- ----------------------------
INSERT INTO `second_hand_category` VALUES (2, '手机', 0, 1, 1, 0, NULL, NULL);
INSERT INTO `second_hand_category` VALUES (34, '手机通讯', 2, 2, 1, 0, NULL, NULL);
INSERT INTO `second_hand_category` VALUES (225, '手机', 34, 3, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for second_hand_order
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_order`;
CREATE TABLE `second_hand_order`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(0) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(0) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(0) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(0) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(0) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(0) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(0) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(0) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_order
-- ----------------------------

-- ----------------------------
-- Table structure for second_hand_records
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_records`;
CREATE TABLE `second_hand_records`  (
  `records_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL,
  `sku_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`records_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_records
-- ----------------------------

-- ----------------------------
-- Table structure for second_hand_sku_images
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_sku_images`;
CREATE TABLE `second_hand_sku_images`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(0) NULL DEFAULT NULL COMMENT 'sku_id',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `default_flag` tinyint(0) NULL DEFAULT 0 COMMENT '默认图[0 - 不是默认图，1 - 是默认图]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_sku_images
-- ----------------------------
INSERT INTO `second_hand_sku_images` VALUES (111, 27, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (112, 28, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (113, 29, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (114, 30, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (115, 31, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (116, 32, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (117, 33, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (118, 34, 'sku_img1', 0, 0);
INSERT INTO `second_hand_sku_images` VALUES (119, 35, 'sku_img1', 0, 0);

-- ----------------------------
-- Table structure for second_hand_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_sku_info`;
CREATE TABLE `second_hand_sku_info`  (
  `sku_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` bigint(0) NULL DEFAULT NULL COMMENT 'spuId',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `catalog_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类id',
  `brand_id` bigint(0) NULL DEFAULT NULL COMMENT '品牌id',
  `sku_default_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认图片',
  `sku_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `sku_subtitle` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '价格',
  `sale_count` bigint(0) NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_sku_info
-- ----------------------------
INSERT INTO `second_hand_sku_info` VALUES (27, 17, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 11 最新款 pro max', 'iphone 11 最新款 pro max', 5300.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (28, 17, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (29, 17, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (30, 18, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 11 最新款 pro max', 'iphone 11 最新款 pro max', 5300.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (31, 18, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (32, 18, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (33, 19, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 11 最新款 pro max', 'iphone 11 最新款 pro max', 5300.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (34, 19, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);
INSERT INTO `second_hand_sku_info` VALUES (35, 19, 'Apple IPhone13', 225, 1, 'sku_img1', 'iphone 12 最新款 pro max max', 'iphone 11 最新款 pro max', 5800.0000, 10);

-- ----------------------------
-- Table structure for second_hand_sku_sale_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_sku_sale_attribute_value`;
CREATE TABLE `second_hand_sku_sale_attribute_value`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(0) NULL DEFAULT NULL COMMENT 'sku_id',
  `attr_id` bigint(0) NULL DEFAULT NULL COMMENT 'attr_id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售属性值',
  `attr_sort` int(0) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku销售属性&值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_sku_sale_attribute_value
-- ----------------------------
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (53, 27, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (54, 27, 12, '版本', '128G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (55, 27, 9, '颜色', '黑色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (56, 28, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (57, 28, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (58, 28, 9, '颜色', '白色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (59, 29, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (60, 29, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (61, 29, 9, '颜色', '黑色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (62, 30, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (63, 30, 12, '版本', '128G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (64, 30, 9, '颜色', '黑色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (65, 31, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (66, 31, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (67, 31, 9, '颜色', '白色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (68, 32, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (69, 32, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (70, 32, 9, '颜色', '黑色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (71, 33, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (72, 33, 12, '版本', '128G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (73, 33, 9, '颜色', '黑色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (74, 34, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (75, 34, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (76, 34, 9, '颜色', '白色;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (77, 35, 10, '内存', '4GB;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (78, 35, 12, '版本', '256G;', NULL);
INSERT INTO `second_hand_sku_sale_attribute_value` VALUES (79, 35, 9, '颜色', '黑色;', NULL);

-- ----------------------------
-- Table structure for second_hand_spu_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_spu_attribute_value`;
CREATE TABLE `second_hand_spu_attribute_value`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `attr_id` bigint(0) NULL DEFAULT NULL COMMENT '属性id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性值',
  `attr_sort` int(0) NULL DEFAULT NULL COMMENT '顺序',
  `quick_show` tinyint(0) NULL DEFAULT NULL COMMENT '快速展示【是否展示在介绍上；0-否 1-是】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu属性值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_spu_attribute_value
-- ----------------------------
INSERT INTO `second_hand_spu_attribute_value` VALUES (80, 17, 7, '上网型号', 'A2217', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (81, 17, 8, '上市年份', '2018', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (82, 17, 13, '机身长度', '158.3;135.9', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (83, 17, 9, '颜色', '黑色;白色', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (84, 17, 15, 'CPU品牌', '以官网信息为主', 0, 1);
INSERT INTO `second_hand_spu_attribute_value` VALUES (85, 17, 16, 'CPU型号', 'A13仿生', 0, 1);
INSERT INTO `second_hand_spu_attribute_value` VALUES (86, 18, 7, '上网型号', 'A2217', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (87, 18, 8, '上市年份', '2018', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (88, 18, 13, '机身长度', '158.3;135.9', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (89, 18, 9, '颜色', '黑色;白色', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (90, 19, 7, '上网型号', 'A2217', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (91, 19, 8, '上市年份', '2018', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (92, 19, 13, '机身长度', '158.3;135.9', 0, 0);
INSERT INTO `second_hand_spu_attribute_value` VALUES (93, 19, 9, '颜色', '黑色;白色', 0, 0);

-- ----------------------------
-- Table structure for second_hand_spu_images
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_spu_images`;
CREATE TABLE `second_hand_spu_images`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(0) NULL DEFAULT NULL COMMENT 'spu_id',
  `img_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int(0) NULL DEFAULT NULL COMMENT '顺序',
  `default_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否默认图（0否 1是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_spu_images
-- ----------------------------
INSERT INTO `second_hand_spu_images` VALUES (101, 17, 'iphone 11', 'img2', 0, 0);
INSERT INTO `second_hand_spu_images` VALUES (102, 17, 'iphone 11', 'img2', 0, 0);
INSERT INTO `second_hand_spu_images` VALUES (103, 18, 'iphone 11', 'img2', 0, 0);
INSERT INTO `second_hand_spu_images` VALUES (104, 18, 'iphone 11', 'img2', 0, 0);
INSERT INTO `second_hand_spu_images` VALUES (105, 19, 'iphone 11', 'img2', 0, 0);
INSERT INTO `second_hand_spu_images` VALUES (106, 19, 'iphone 11', 'img2', 0, 0);

-- ----------------------------
-- Table structure for second_hand_spu_info
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_spu_info`;
CREATE TABLE `second_hand_spu_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `spu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `spu_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `catalog_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类id',
  `weight` decimal(18, 4) NULL DEFAULT NULL,
  `publish_status` tinyint(0) NULL DEFAULT NULL COMMENT '上架状态[0 - 下架，1 - 上架]',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `introduce` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品介绍（富文本）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_spu_info
-- ----------------------------
INSERT INTO `second_hand_spu_info` VALUES (17, 'iphone 11', '苹果手机 就是比华为牛', 225, 0.1918, 0, '2023-05-28 15:22:31', NULL, 'iphone 11 最新款 pro max');
INSERT INTO `second_hand_spu_info` VALUES (18, 'iphone 11', '苹果手机 就是比华为牛', 225, 0.1918, 0, '2023-05-28 16:06:30', NULL, 'iphone 11 最新款 pro max');
INSERT INTO `second_hand_spu_info` VALUES (19, 'iphone 11', '苹果手机 就是比华为牛', 225, 0.1918, 0, '2023-05-28 16:07:15', NULL, 'iphone 11 最新款 pro max');

-- ----------------------------
-- Table structure for second_hand_stock
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_stock`;
CREATE TABLE `second_hand_stock`  (
  `sku_id` bigint(0) NOT NULL COMMENT '库存对应的商品sku id',
  `seckill_stock` int(0) NULL DEFAULT 0 COMMENT '可秒杀库存',
  `seckill_total` int(0) NULL DEFAULT 0 COMMENT '秒杀总数量',
  `stock` int(0) NOT NULL COMMENT '库存数量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '库存表，代表库存，秒杀库存等信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_stock
-- ----------------------------

-- ----------------------------
-- Table structure for second_hand_user
-- ----------------------------
DROP TABLE IF EXISTS `second_hand_user`;
CREATE TABLE `second_hand_user`  (
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uname` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `passwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` decimal(10, 0) NULL DEFAULT NULL,
  `scoring` decimal(10, 0) NULL DEFAULT NULL,
  `img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` int(0) NULL DEFAULT NULL,
  `city` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bank_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `default_address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_hand_user
-- ----------------------------
INSERT INTO `second_hand_user` VALUES ('1662729028470185986', 'user123123', '73aff6f9bca4de952ecbb870d2296b6b', NULL, NULL, NULL, '123123123@qq.com', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  `locked` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '{noop}123', 1, 0);
INSERT INTO `user` VALUES (2, 'user', '{noop}123', 1, 0);
INSERT INTO `user` VALUES (3, 'blr', '{noop}123', 1, 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NULL DEFAULT NULL,
  `rid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (3, 2, 2);
INSERT INTO `user_role` VALUES (4, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
