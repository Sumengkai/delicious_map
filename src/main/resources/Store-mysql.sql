CREATE TABLE IF NOT EXISTS`store` (
  `store_id` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `storepoint` double DEFAULT NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
