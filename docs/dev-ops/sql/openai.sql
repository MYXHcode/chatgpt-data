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

# 转储表 user_account
# ------------------------------------------------------------
DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
    `openid`        VARCHAR(64)  NOT NULL COMMENT '用户 ID：这里用的是微信 ID 作为唯一 ID，你也可以给用户创建唯一 ID，之后绑定微信 ID',
    `total_quota`   INT(11)      NOT NULL DEFAULT '0' COMMENT '总量额度：分配的总使用次数',
    `surplus_quota` INT(11)      NOT NULL DEFAULT '0' COMMENT '剩余额度：剩余的可使用次数',
    `model_types`   VARCHAR(128) NOT NULL COMMENT '可用模型：gpt-3.5-turbo, gpt-3.5-turbo-16k, gpt-4, gpt-4-32k',
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
VALUES (1, 'openid=o0G6z6h-nHpZFUZVrcPJayOdN884', 10, 5, 'gpt-3.5-turbo,gpt-3.5-turbo-16k', 0, '2024-2-12 18:55:00', '2024-2-12 19:00:00');

/*!40000 ALTER TABLE `user_account`
    ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
