DROP database IF EXISTS `e-commerce`;

CREATE DATABASE `e-commerce`;

USE `e-commerce`;

-- Create table for entity User
CREATE TABLE `user` (
	`id` bigint AUTO_INCREMENT PRIMARY KEY,
	`password` varchar(128) NOT NULL,
	`email` varchar(254) NOT NULL UNIQUE,
	`mobile` varchar(10) NOT NULL UNIQUE,
	`first_name` varchar(150) NOT NULL,
	`last_name` varchar(150) NOT NULL,
	`age` integer UNSIGNED NOT NULL CHECK (`age` >= 0),
	`sex` varchar(1) NULL,
	`wallet_amount` bigint DEFAULT 0
);

-- Create table for entity Customer
CREATE TABLE `customer` (
	`user_id` bigint PRIMARY KEY
);

-- Create table for entity Seller
CREATE TABLE `seller` (
	`user_id` bigint PRIMARY KEY,
	`store_address1` varchar(100) NULL,
	`store_address2` varchar(100) NULL,
	`store_city` varchar(50) NULL,
	`store_country` varchar(50) NULL,
	`store_pincode` varchar(6) NULL
);

ALTER TABLE `customer` ADD CONSTRAINT `customer_user_id_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `seller` ADD CONSTRAINT `seller_user_id_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

CREATE TABLE `category` (
	`name` varchar(25) PRIMARY KEY
);

-- Create table for entity Item
CREATE TABLE `item` (
	`id` integer AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(50) NOT NULL,
	`price` FLOAT(10, 2) UNSIGNED NOT NULL CHECK (`price` >= 0),
	`description` longtext NULL,
	`quantity` bigint DEFAULT 0,
	`discount` integer DEFAULT 0
);

-- Add field seller to item
ALTER TABLE `item` ADD COLUMN `seller_id` bigint NOT NULL,
	ADD CONSTRAINT `item_seller_id_fk_seller_user_id` FOREIGN KEY (`seller_id`) REFERENCES `seller`(`user_id`);

-- Alter unique_together for item (1 constraint(s))
ALTER TABLE `item` ADD CONSTRAINT `item_name_seller_id_unique` UNIQUE (`name`, `seller_id`);

ALTER TABLE ITEM ADD COLUMN CATEGORY VARCHAR(25) NOT NULL, ADD CONSTRAINT ITEM_CATEGORY_FK_CATEGORY_NAME FOREIGN KEY (CATEGORY) REFERENCES CATEGORY(NAME);

-- Create table for entity Order
CREATE TABLE `order` (
	`id` integer AUTO_INCREMENT PRIMARY KEY,
	`order_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
	`coupon_code` varchar(10) DEFAULT NULL,
	`payment_UID` varchar(10) NOT NULL,
	`delivery_address1` varchar(100) NULL,
	`delivery_address2` varchar(100) NULL,
	`delivery_city` varchar(50) NULL,
	`delivery_country` varchar(50) NULL,
	`delivery_pincode` varchar(6) NULL
);

-- Add field customer to entity order
ALTER TABLE `order` ADD COLUMN `customer_id` bigint NOT NULL,
	ADD CONSTRAINT `order_customer_id_fk_customer_user_id` FOREIGN KEY (`customer_id`) REFERENCES `customer`(`user_id`);

-- Create table for entity OrderItem
CREATE TABLE `orderitem` (
	`status` ENUM("delivered", "shipping", "processing") DEFAULT "processing",
	`quantity` integer UNSIGNED NOT NULL CHECK (`quantity` >= 0),
	`item_id` integer NOT NULL,
	`order_id` integer NOT NULL,
	`price` integer NOT NULL CHECK (`price` >= 0)
);

ALTER TABLE `orderitem` ADD CONSTRAINT `orderitem_item_id_fk_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);
ALTER TABLE `orderitem` ADD CONSTRAINT `orderitem_order_id_fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

-- Create table for entity feedback
CREATE TABLE `feedback` (
	`rating` INTEGER UNSIGNED NOT NULL CHECK (`rating` BETWEEN 0 AND 5),
	`message` longtext NOT NULL,
	`order_id` integer NOT NULL UNIQUE,
	`item_id` integer NOT NULL UNIQUE
);

ALTER TABLE `feedback` ADD CONSTRAINT `feedback_order_item_id_fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `feedback` ADD CONSTRAINT `feedback_order_item_id_fk_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);


-- Create table for relation "Keep in Cart"
CREATE TABLE `cart` (
	`customer_id` bigint NOT NULL,
	`item_id` integer NOT NULL,
	`quantity` integer NOT NULL CHECK (`quantity` >= 0)
);

ALTER TABLE `cart` ADD CONSTRAINT `cart_customer_id_fk_customer_user_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`user_id`);
ALTER TABLE `cart` ADD CONSTRAINT `cart_item_id_fk_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);
ALTER TABLE `cart` ADD CONSTRAINT `cart_items_cart_id_item_id_unique` UNIQUE (`customer_id`, `item_id`);

-- Create table for Coupons

CREATE TABLE `coupon` (
	`coupon_code` varchar(20) PRIMARY KEY NOT NULL,
	`discount` integer DEFAULT 0,
    `seller_id` bigint NOT NULL,
	FOREIGN KEY (seller_id) REFERENCES seller(user_id) ON DELETE CASCADE
);






-- INSERTING DUMMY DATA


-- Dummy data for user table
INSERT INTO `user` (`password`, `email`, `mobile`, `first_name`, `last_name`, `age`, `sex`, `wallet_amount`) VALUES
('password1', 'user1@example.com', '1234567890', 'John', 'Doe', 30, 'M', 100),
('password2', 'user2@example.com', '9876543210', 'Jane', 'Smith', 25, 'F', 150),
('password3', 'user3@example.com', '5556667777', 'Alice', 'Johnson', 35, 'F', 200),
('password4', 'user4@example.com', '9998887777', 'Bob', 'Brown', 40, 'M', 50),
('password5', 'user5@example.com', '1111111111', 'Ray', 'William', 35, 'M', 100),
('password6', 'user6@example.com', '2222222222', 'Emily', 'Ray', 40, 'F', 250);

-- Dummy data for customer table
INSERT INTO `customer` (`user_id`) VALUES (1), (2), (3), (4);

-- Dummy data for seller table
INSERT INTO `seller` (`user_id`, `store_address1`, `store_address2`, `store_city`, `store_country`, `store_pincode`) VALUES
(5, '123 Main St', 'Apt 101', 'New York', 'USA', '10001'),
(6, '456 Elm St', NULL, 'Los Angeles', 'USA', '90001');

-- Dummy data for category table
INSERT INTO `category` (`name`) VALUES ('Electronics'), ('Clothing'), ('Books');

-- Dummy data for item table
INSERT INTO `item` (`name`, `price`, `description`, `quantity`, `discount`, `seller_id`, `category`) VALUES
('Laptop', 999.99, 'High performance laptop', 100, 0, 5, 'Electronics'),
('T-shirt', 19.99, 'Cotton t-shirt', 200, 10, 6, 'Clothing');

-- Dummy data for order table
INSERT INTO `order` (`order_time`, `coupon_code`, `payment_UID`, `delivery_address1`, `delivery_address2`, `delivery_city`, `delivery_country`, `delivery_pincode`, `customer_id`) VALUES
('2024-04-08 10:00:00', NULL, 'PAY123', '123 Street', 'Apt 1', 'New York', 'USA', '10001', 1),
('2024-04-08 11:00:00', 'DISC10', 'PAY456', '456 Avenue', NULL, 'Los Angeles', 'USA', '90001', 2);

-- Dummy data for orderitem table
INSERT INTO `orderitem` (`status`, `quantity`, `item_id`, `order_id`, `price`) VALUES
('delivered', 2, 1, 1, 1999.98),
('processing', 1, 2, 2, 17.99);

-- Dummy data for feedback table
INSERT INTO `feedback` (`rating`, `message`, `order_id`, `item_id`) VALUES
(5, 'Great product!', 1, 1),
(4, 'Good service', 2, 2);

-- Dummy data for cart table
INSERT INTO `cart` (`customer_id`, `item_id`, `quantity`) VALUES
(1, 1, 1),
(2, 2, 2);

-- Dummy data for coupon table
INSERT INTO `coupon` (`coupon_code`, `discount`, `seller_id`) VALUES
('DISC10', 10, 5),
('SALE20', 20, 6);
