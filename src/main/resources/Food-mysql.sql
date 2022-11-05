CREATE TABLE IF NOT EXISTS `food` (
  `store_id` varchar(32) NOT NULL,
  `food_id` varchar(32) NOT NULL,
  `foodprice` int NOT NULL,
  `foodpoint` double NOT NULL,
  PRIMARY KEY (`store_id`,`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
