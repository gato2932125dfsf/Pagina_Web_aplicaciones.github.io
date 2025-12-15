CREATE DATABASE  IF NOT EXISTS `gestion_tesis_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gestion_tesis_db`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: gestion_tesis_db
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administradores`
--

DROP TABLE IF EXISTS `administradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administradores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administradores`
--

LOCK TABLES `administradores` WRITE;
/*!40000 ALTER TABLE `administradores` DISABLE KEYS */;
INSERT INTO `administradores` VALUES (1,'Juan Perez','admin@univ.edu','admin123','2025-11-16 23:00:51');
/*!40000 ALTER TABLE `administradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigo` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` VALUES (3,'Josué Anthony Arredondo','anthony@gmail.com','T01244A','T01244A','2025-11-17 00:29:30'),(8,'Diego','diego@gmail.com','diego','diego','2025-11-23 21:55:59');
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docentes`
--

DROP TABLE IF EXISTS `docentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docentes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dni` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docentes`
--

LOCK TABLES `docentes` WRITE;
/*!40000 ALTER TABLE `docentes` DISABLE KEYS */;
INSERT INTO `docentes` VALUES (1,'Ana García','areyes@univ.edu','12345678','docente456','2025-11-16 23:00:51'),(2,'Carlos Vega Lopez','cvega@univ.edu','87654321','docente456','2025-11-16 23:00:51'),(4,'Jaime Ortiz','jaime@gmail.com','12312312','12312312','2025-11-23 21:55:01');
/*!40000 ALTER TABLE `docentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_revisiones`
--

DROP TABLE IF EXISTS `historial_revisiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_revisiones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tesis_id` int NOT NULL,
  `numero_version` int NOT NULL,
  `archivo_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `comentarios_docente` text COLLATE utf8mb4_unicode_ci,
  `estado_version` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_subida` datetime DEFAULT CURRENT_TIMESTAMP,
  `puntaje_total` decimal(4,1) DEFAULT '0.0',
  `detalle_rubrica` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `fk_historial_tesis` (`tesis_id`),
  CONSTRAINT `fk_historial_tesis` FOREIGN KEY (`tesis_id`) REFERENCES `tesis` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_revisiones`
--

LOCK TABLES `historial_revisiones` WRITE;
/*!40000 ALTER TABLE `historial_revisiones` DISABLE KEYS */;
INSERT INTO `historial_revisiones` VALUES (7,3,1,NULL,'Perfecto','Rechazado','2025-11-23 19:29:45',0.0,NULL),(8,3,2,NULL,'','Aprobado','2025-11-23 19:31:34',0.0,NULL),(9,3,3,NULL,'','Observado','2025-11-23 19:48:41',0.0,NULL),(11,3,4,NULL,'bien','Aprobado','2025-11-23 20:12:32',0.0,NULL),(12,3,5,NULL,'mejorar observaciones','Observado','2025-11-23 20:49:34',13.0,'0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.5,0.0,1.0,0.5,0.5,0.5'),(13,3,6,NULL,'mejorar observaciones','Aprobado','2025-11-23 20:50:00',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(14,3,7,NULL,'ok','Aprobado','2025-11-23 20:50:35',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(15,3,8,NULL,'ok','Aprobado','2025-11-23 20:52:47',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(16,3,9,NULL,'ok','Aprobado','2025-11-23 20:53:06',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(17,3,10,NULL,'ok','Aprobado','2025-11-23 20:53:39',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(18,3,11,NULL,'maso','Observado','2025-11-23 20:55:09',19.0,'0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5'),(19,3,12,NULL,'ok','Aprobado','2025-11-23 21:09:58',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(20,3,13,NULL,'corregir','Observado','2025-11-23 21:52:14',19.0,'0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5'),(24,3,14,NULL,'OK','Aprobado','2025-11-23 22:20:38',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0'),(25,3,15,'1763954541526_T037_72644847_T.pdf',NULL,'En Revisión','2025-11-23 22:22:21',0.0,NULL),(26,3,16,NULL,'OKOK','Aprobado','2025-11-23 22:22:43',38.0,'1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0');
/*!40000 ALTER TABLE `historial_revisiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mensaje` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tipo` enum('info','alerta','sistema') COLLATE utf8mb4_unicode_ci DEFAULT 'info',
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `leido` tinyint(1) DEFAULT '0',
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
INSERT INTO `notificaciones` VALUES (1,'Bienvenido al nuevo sistema de gestión.','sistema','2025-11-23 19:37:27',1,NULL),(2,'El docente Ana García Perez revisó la tesis ID #3 (Estado: Observado)','info','2025-11-23 19:48:41',1,NULL),(3,'El docente Ana García Perez revisó la tesis ID #4 (Estado: Aprobado)','info','2025-11-23 19:50:51',1,NULL),(4,'El usuario \'T01244A\' solicitó recuperar su contraseña. Verificar identidad.','alerta','2025-11-23 19:52:59',1,NULL),(5,'El docente Ana García revisó la tesis ID #3 (Estado: Aprobado)','info','2025-11-23 20:12:32',1,NULL),(6,'El docente Ana García calificó la tesis ID #3. Nota: 13.0 (Observado)','info','2025-11-23 20:49:34',1,NULL),(7,'El docente Ana García calificó la tesis ID #3. Nota: 38.0 (Aprobado)','info','2025-11-23 20:50:00',1,NULL),(8,'El docente Ana García calificó la tesis ID #3. Nota: 38.0 (Aprobado)','info','2025-11-23 20:50:35',1,NULL),(9,'El docente Ana García calificó la tesis ID #3. Nota: 38.0 (Aprobado)','info','2025-11-23 20:52:47',1,NULL),(10,'El docente Ana García calificó la tesis ID #3. Nota: 38.0 (Aprobado)','info','2025-11-23 20:53:06',1,NULL),(11,'El docente Ana García calificó la tesis ID #3. Nota: 38.0 (Aprobado)','info','2025-11-23 20:53:39',1,NULL),(12,'El docente Ana García calificó la tesis ID #3. Nota: 19.0 (Observado)','info','2025-11-23 20:55:09',0,NULL),(13,'El docente Ana García calificó la tesis ID #3. Nota: 38.0','info','2025-11-23 21:09:58',0,NULL),(14,'Docente Ana García evaluó la tesis #3 (Nota: 19.0)','info','2025-11-23 21:52:14',0,NULL),(15,'Se le ha asignado el proyecto: \'Implementacion de Tienda Virtual\'.','sistema','2025-11-23 21:56:17',0,4),(16,'Docente Jaime Ortiz evaluó la tesis #5 (Nota: 13.5)','info','2025-11-23 21:57:42',0,NULL),(17,'El alumno Diego ha subido una nueva versión de su tesis (Implementacion de Tienda Virtual).','alerta','2025-11-23 21:58:17',0,4),(18,'Docente Jaime Ortiz evaluó la tesis #5 (Nota: 38.0)','info','2025-11-23 21:58:47',0,NULL),(19,'Tu tesis ha sido evaluada por Ana García. Nota: 38.0 (Aprobado)','info','2025-11-23 22:20:38',0,3),(20,'El alumno Josué Anthony Arredondo ha subido una nueva versión de su tesis.','alerta','2025-11-23 22:22:21',0,1),(21,'Tu tesis ha sido evaluada por Ana García. Nota: 38.0 (Aprobado)','info','2025-11-23 22:22:43',0,3);
/*!40000 ALTER TABLE `notificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tesis`
--

DROP TABLE IF EXISTS `tesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tesis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` text COLLATE utf8mb4_unicode_ci,
  `archivo_actual_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `alumno_id` int NOT NULL,
  `docente_id` int NOT NULL,
  `estado` enum('Asignado','En Revisión','Observado','Aprobado','Rechazado') COLLATE utf8mb4_unicode_ci DEFAULT 'Asignado',
  `fecha_asignacion` datetime DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_limite` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tesis_alumno` (`alumno_id`),
  KEY `fk_tesis_docente` (`docente_id`),
  CONSTRAINT `fk_tesis_alumno` FOREIGN KEY (`alumno_id`) REFERENCES `alumnos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_tesis_docente` FOREIGN KEY (`docente_id`) REFERENCES `docentes` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tesis`
--

LOCK TABLES `tesis` WRITE;
/*!40000 ALTER TABLE `tesis` DISABLE KEYS */;
INSERT INTO `tesis` VALUES (3,'MODELO DE GESTIÓN DE SEGURIDAD DE LA INFORMACIÓN BASADO EN LA NORMA TÉCNICA PERUANA 27001:2022 PARA UNA INSTITUCIÓN EDUCATIVA, 2024',NULL,'1763944054398_T037_70896951_T.pdf',3,1,'Aprobado','2025-11-23 19:27:34','2025-11-23 22:22:43',NULL);
/*!40000 ALTER TABLE `tesis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_login_usuarios`
--

DROP TABLE IF EXISTS `vista_login_usuarios`;
/*!50001 DROP VIEW IF EXISTS `vista_login_usuarios`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_login_usuarios` AS SELECT 
 1 AS `id`,
 1 AS `nombre`,
 1 AS `email`,
 1 AS `password`,
 1 AS `rol`,
 1 AS `codigo_usuario`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'gestion_tesis_db'
--

--
-- Dumping routines for database 'gestion_tesis_db'
--

--
-- Final view structure for view `vista_login_usuarios`
--

/*!50001 DROP VIEW IF EXISTS `vista_login_usuarios`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_login_usuarios` AS select `administradores`.`id` AS `id`,`administradores`.`nombre` AS `nombre`,`administradores`.`email` AS `email`,`administradores`.`password` AS `password`,'admin' AS `rol`,`administradores`.`email` AS `codigo_usuario` from `administradores` union all select `docentes`.`id` AS `id`,`docentes`.`nombre` AS `nombre`,`docentes`.`email` AS `email`,`docentes`.`password` AS `password`,'docente' AS `rol`,`docentes`.`dni` AS `codigo_usuario` from `docentes` union all select `alumnos`.`id` AS `id`,`alumnos`.`nombre` AS `nombre`,`alumnos`.`email` AS `email`,`alumnos`.`password` AS `password`,'estudiante' AS `rol`,`alumnos`.`codigo` AS `codigo_usuario` from `alumnos` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-23 23:07:34
