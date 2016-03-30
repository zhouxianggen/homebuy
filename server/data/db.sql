-- MySQL dump 10.13  Distrib 5.1.40, for unknown-linux-gnu (x86_64)
--
-- Host: 10.99.20.92    Database: test7
-- ------------------------------------------------------
-- Server version	5.1.47-community-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agency`
--

DROP TABLE IF EXISTS `agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agency` (
  `id` bigint(20) NOT NULL,
  `title` text NOT NULL,
  `pswd` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agency`
--

LOCK TABLES `agency` WRITE;
/*!40000 ALTER TABLE `agency` DISABLE KEYS */;
/*!40000 ALTER TABLE `agency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barcode`
--

DROP TABLE IF EXISTS `barcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barcode` (
  `seller_id` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `item_name` text NOT NULL,
  `item_size` varchar(255) NOT NULL,
  `unit_no` varchar(64) NOT NULL,
  `product_area` varchar(255) NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`seller_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barcode`
--

LOCK TABLES `barcode` WRITE;
/*!40000 ALTER TABLE `barcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `barcode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `title` varchar(1024) NOT NULL,
  `parent` bigint(20) NOT NULL DEFAULT '0',
  `image_1` text NOT NULL,
  `image_2` text NOT NULL,
  `image_3` text NOT NULL,
  `image_4` text NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '',
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,1,'休闲食品',0,'http://a1.att.hudong.com/82/12/01300000165488121889128992656.jpg','http://mall.flyker.cn/temp/thumb/8f/85/8f8530c4b4caa8f61353febe381f79bb2012.jpg','http://www.xmdiwei.com/products/UploadFiles_3828/201309/2013092211413934.jpg','','',1458833013),(2,1,'酒水饮料',0,'http://www.glhyd.com/images/201404/goods_img/5158_P_1398655166164.JPG','http://img007.hc360.cn/m5/M05/70/2E/wKhQ61UD142EY5gNAAAAAJOSTsE360.jpg..300x300.jpg','http://preview.dldcdn.com/upload/article/20150713/32/81356d58a71dd4afe6992214cd9dc8fd.jpg?serverno=1&upload=preview','','',1458833013),(3,1,'生活用纸',0,'http://img7.ph.126.net/gGADI_lg7YPIAqnXDVo6tA==/2391129927174322037.jpg','http://img1.windmsn.com/b/2/279/27979/2797988.jpg','http://img.chinaibi.cn/spaceimg/00/06/17/1044175276370_s0151557458.jpg','','',1458833013),(4,1,'清洁用品',0,'http://www.spp365.com/images/goods/20120822/14da01b66fd0ff27.jpg','http://d7.yihaodianimg.com/V00/M09/78/C1/CgQDslSZRK-AHYACAAP6jGNuN4E74401_600x600.jpg','http://i1.dpfile.com/groups/grouppic/2013-04-25/hundun_3750510_12246357_m.jpg','','',1458833013);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commodity`
--

DROP TABLE IF EXISTS `commodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `barcode` varchar(255) NOT NULL DEFAULT '',
  `title` text NOT NULL,
  `description` text NOT NULL,
  `price` float NOT NULL DEFAULT '0',
  `thumbnail` text NOT NULL,
  `image_1` text NOT NULL,
  `image_2` text NOT NULL,
  `image_3` text NOT NULL,
  `image_4` text NOT NULL,
  `image_5` text NOT NULL,
  `image_6` text NOT NULL,
  `support_return` tinyint(1) NOT NULL DEFAULT '1',
  `in_discount` tinyint(4) NOT NULL DEFAULT '0',
  `in_stock` int(11) NOT NULL DEFAULT '1',
  `category_id` bigint(255) NOT NULL,
  `status` varchar(64) NOT NULL DEFAULT '',
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  KEY `barcode` (`barcode`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commodity`
--

LOCK TABLES `commodity` WRITE;
/*!40000 ALTER TABLE `commodity` DISABLE KEYS */;
INSERT INTO `commodity` VALUES (1,1,'6901668053208','奥利奥夹心饼干','',13.5,'http://image.6695.com/pic/business/showpic/2014-07/28/53d5e6e1f19c8.jpg','http://image.6695.com/pic/business/showpic/2014-07/28/53d5e6e1f19c8.jpg','http://www.justeasy.com.cn/img/upload/20120710/3607%282%29.jpg','http://www.jpjy365.com/images/201406/source_img/39063_P_1403118409322.jpg','','','',1,0,1,1,'',1458833013);
/*!40000 ALTER TABLE `commodity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expressman`
--

DROP TABLE IF EXISTS `expressman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expressman` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pswd` varchar(255) NOT NULL,
  `thumbnail` text NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expressman`
--

LOCK TABLES `expressman` WRITE;
/*!40000 ALTER TABLE `expressman` DISABLE KEYS */;
INSERT INTO `expressman` VALUES (1,'小周','','http://img1.gtimg.com/ent/pics/hv1/95/207/2026/131793530.jpg','18776544321');
/*!40000 ALTER TABLE `expressman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `sender` bigint(20) NOT NULL,
  `receiver` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agency_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) NOT NULL,
  `commodity_id` bigint(20) NOT NULL,
  `expressman_id` bigint(20) NOT NULL,
  `amount` int(11) NOT NULL,
  `payment` double NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `pswd` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(255) NOT NULL,
  `thumbnail` text NOT NULL,
  `image_1` text NOT NULL,
  `image_2` text NOT NULL,
  `image_3` text NOT NULL,
  `image_4` text NOT NULL,
  `payment_id` text NOT NULL,
  `payment_bank` text NOT NULL,
  `payment_card` text NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'新世纪超市','xsjcs','廉邵路23号','0738-66668888','http://imgsrc.baidu.com/forum/w%3D580/sign=eaee6f3a123853438ccf8729a312b01f/3c128816fdfaaf512c1a1de98e5494eef11f7af1.jpg','','','','','','','',1458833013);
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-30 14:28:39
