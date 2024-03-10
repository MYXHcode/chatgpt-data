# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 8.0.21)
# 数据库: openai
# 生成时间: 2024-2-12 18:55:00 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO', SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

CREATE DATABASE IF NOT EXISTS `openai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `openai`;


# 转储表 openai_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `openai_order`;

CREATE TABLE `openai_order`
(
    `id`                  BIGINT(20)     NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
    `openid`              VARCHAR(128)   NOT NULL COMMENT '用户 ID：微信分配的唯一 ID 编码',
    `product_id`          INT(4)         NOT NULL COMMENT '商品 ID',
    `product_name`        VARCHAR(32)    NOT NULL COMMENT '商品名称',
    `product_quota`       INT(8)         NOT NULL COMMENT '商品额度',
    `product_model_types` VARCHAR(256) DEFAULT NULL COMMENT '可用模型：gpt-3.5-turbo,gpt-3.5-turbo-16k,chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo,dall-e-2,dall-e-3',
    `order_id`            VARCHAR(12)    NOT NULL COMMENT '订单编号',
    `order_time`          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    `order_status`        TINYINT(1)     NOT NULL COMMENT '订单状态：0-创建完成、1-等待发货、2-发货完成、3-系统关单',
    `total_amount`        DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
    `pay_type`            TINYINT(1)     NOT NULL DEFAULT '0' COMMENT '支付方式：0-微信支付',
    `pay_url`             VARCHAR(128) DEFAULT NULL COMMENT '支付地址：创建支付后，获得的 URL 地址',
    `pay_amount`          DECIMAL(10, 2)          DEFAULT NULL COMMENT '支付金额：支付成功后，以回调信息更新金额',
    `transaction_id`      varchar(32)             DEFAULT NULL COMMENT '交易单号：支付成功后，回调信息的交易单号',
    `pay_status`          TINYINT(1)              DEFAULT NULL COMMENT '支付状态：0-等待支付、1-支付完成、2-支付失败、3-放弃支付',
    `pay_time`            DATETIME                DEFAULT NULL COMMENT '支付时间',
    `create_time`         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_order_id` (`order_id`),
    KEY `idx_openid` (`openid`),
    KEY `idx_order_status_pay_status_order_time` (`order_time`, `order_status`, `pay_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

LOCK TABLES `openai_order` WRITE;
/*!40000 ALTER TABLE `openai_order`
    DISABLE KEYS */;

INSERT INTO `openai_order` (`id`, `openid`, `product_id`, `product_name`, `product_quota`, `product_model_types`,
                            `order_id`, `order_time`, `order_status`, `total_amount`, `pay_type`, `pay_url`,
                            `pay_amount`, `transaction_id`, `pay_status`, `pay_time`, `create_time`, `update_time`)
VALUES (1, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 1001, 'OpenAi 测试商品(ChatGPT 3.5)', 100, 'gpt-3.5-turbo,gpt-3.5-turbo-16',
        '118845061424', '2024-02-18 17:02:42', 3, 10,
        0,
        'weixin://wxpay/bizpayurl?pr=NFByxsizz', NULL, NULL, 3, NULL, '2024-02-18 17:02:41', '2024-02-18 19:03:40'),


       (2, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 1002, 'OpenAi 测试商品(ChatGLM 3.5)', 100,
        'chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_turbo', '730807176035', '2024-02-18 20:35:46',
        2, 9.99,
        0,
        'weixin://wxpay/bizpayurl?pr=0ckFdM2zz', 9.99, '4200001993202310054200967494', 1, '2024-02-18 20:09:10',
        '2024-02-18 20:08:46', '2024-02-18 20:42:16'),

       (3, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 1003, 'OpenAi 测试商品(ChatGLM 3.5&4.0)', 200,
        'chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo', '175715149006',
        '2024-02-18 18:41:17', 2, 19.99, 0,
        'weixin://wxpay/bizpayurl?pr=EMrrTSYzz', 19.99, '4200002003202310079361212463', 1, '2024-02-18 18:52:06',
        '2024-02-18 18:51:17', '2024-02-18 18:56:00'),

       (4, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 1004, 'OpenAi 测试商品(DALL-E-2,DALL-E-3)', 200, 'dall-e-2,dall-e-3',
        '535514648535',
        '2024-02-18 18:57:27', 3, 49.99, 0,
        'weixin://wxpay/bizpayurl?pr=YlCE6Xszz', NULL, NULL, 3, NULL, '2024-02-18 18:57:27', '2024-02-18 19:03:38'),

       (5, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 1003, 'OpenAi 测试商品(ChatGLM 3.5&4.0)', 200,
        'chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo', 759913647201,
        '2024-02-24 06:52:18', 0, 19.99, 0,
        '因你未配置支付渠道，所以暂时不能生成有效的支付 URL。请配置支付渠道后，在 application-dev.yml 中配置支付渠道信息',
        NULL, NULL, 0, NULL, '2024-02-24 14:52:18', '2024-02-24 14:52:18');

/*!40000 ALTER TABLE `openai_order`
    ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 openai_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `openai_product`;

CREATE TABLE `openai_product`
(
    `id`                  BIGINT(20)     NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
    `product_id`          INT(4)         NOT NULL COMMENT '商品 ID',
    `product_name`        VARCHAR(32)    NOT NULL COMMENT '商品名称',
    `product_desc`        VARCHAR(128)   NOT NULL COMMENT '商品描述',
    `product_model_types` VARCHAR(256) DEFAULT NULL COMMENT '可用模型：gpt-3.5-turbo,gpt-3.5-turbo-16k,chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo,dall-e-2,dall-e-3',
    `quota`               INT(8)         NOT NULL COMMENT '额度次数',
    `price`               DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    `sort`                INT(4)         NOT NULL COMMENT '商品排序',
    `is_enabled`          TINYINT(1)     NOT NULL DEFAULT '1' COMMENT '是否有效：0 无效、1 有效',
    `create_time`         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_product_id_enabled` (`product_id`, `is_enabled`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

LOCK TABLES `openai_product` WRITE;
/*!40000 ALTER TABLE `openai_product`
    DISABLE KEYS */;

INSERT INTO `openai_product` (`id`, `product_id`, `product_name`, `product_desc`, `product_model_types`, `quota`,
                              `price`, `sort`, `is_enabled`, `create_time`, `update_time`)
VALUES (1, 1001, 'OpenAi 测试商品(ChatGPT 3.5)', '测试商品请勿下单', 'gpt-3.5-turbo,gpt-3.5-turbo-16', 100, 10, 1, 1,
        '2024-02-18 18:45:36', '2024-02-18 18:45:36'),
       (2, 1002, 'OpenAi 测试商品(ChatGLM 3.5)', '测试商品请勿下单',
        'chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_turbo', 100, 9.99, 2, 1,
        '2024-02-18 18:45:42', '2024-02-18 18:45:42'),
       (3, 1003, 'OpenAi 测试商品(ChatGLM 3.5&4.0)', '测试商品请勿下单',
        'chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo', 200, 19.99, 3,
        1, '2024-02-18 18:46:41', '2024-02-18 18:46:41'),
       (4, 1004, 'OpenAi 测试商品(DALL-E-2,DALL-E-3)', '测试商品请勿下单', 'dall-e-2,dall-e-3', 200, 49.99, 4,
        1, '2024-02-18 09:28:47', '2024-02-18 09:28:47');

/*!40000 ALTER TABLE `openai_product`
    ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_account
# ------------------------------------------------------------
DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
    `openid`        VARCHAR(64)  NOT NULL COMMENT '用户 ID：这里用的是微信 ID 作为唯一 ID，你也可以给用户创建唯一 ID，之后绑定微信 ID',
    `total_quota`   INT(11)      NOT NULL DEFAULT '0' COMMENT '总量额度：分配的总使用次数',
    `surplus_quota` INT(11)      NOT NULL DEFAULT '0' COMMENT '剩余额度：剩余的可使用次数',
    `model_types` VARCHAR(128) NOT NULL COMMENT '可用模型：gpt-3.5-turbo,gpt-3.5-turbo-16k,chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo,dall-e-2,dall-e-3',
    `status`        TINYINT(1)   NOT NULL DEFAULT '0' COMMENT '账户状态：0-可用、1-冻结',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_openid` (`openid`),
    KEY `idx_surplus_quota_status` (`surplus_quota`, `status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account`
    DISABLE KEYS */;

INSERT INTO `user_account` (`id`, `openid`, `total_quota`, `surplus_quota`, `model_types`, `status`, `create_time`,
                            `update_time`)
VALUES (1, 'o0G6z6h-nHpZFUZVrcPJayOdN884', 200, 200,
        'gpt-3.5-turbo,gpt-3.5-turbo-16k,chatGLM_6b_SSE,chatglm_lite,chatglm_lite_32k,chatglm_std,chatglm_pro,chatglm_turbo,dall-e-2,dall-e-3',
        0, '2024-2-12 18:55:00',
        '2024-2-12 19:00:00');

/*!40000 ALTER TABLE `user_account`
    ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
