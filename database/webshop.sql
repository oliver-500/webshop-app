-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema webshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema webshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `webshop` DEFAULT CHARACTER SET utf8 ;
USE `webshop` ;

-- -----------------------------------------------------
-- Table `webshop`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`user` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NULL,
  `avatar` MEDIUMBLOB NULL,
  `email` VARCHAR(50) NULL,
  `active` TINYINT(1) NULL,
  `name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `username` VARCHAR(20) NULL,
  `password_hash` VARCHAR(150) NULL,
  `type` ENUM("admin", "operator", "transactor") NULL,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) NULL,
  `location` VARCHAR(100) NULL,
  `used` TINYINT(1) NULL,
  `price` DECIMAL(10,2) NULL,
  `contact` VARCHAR(15) NULL,
  `sold` TINYINT(1) NULL,
  `id_seller` INT NOT NULL,
  PRIMARY KEY (`id_product`),
  INDEX `fk_product_trader1_idx` (`id_seller` ASC) VISIBLE,
  CONSTRAINT `fk_product_trader1`
    FOREIGN KEY (`id_seller`)
    REFERENCES `webshop`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`category` (
  `id_category` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_category`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`image` (
  `id_image` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `data` MEDIUMBLOB NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id_image`),
  INDEX `fk_image_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_image_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `webshop`.`product` (`id_product`) ON DELETE CASCADE
   
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`product_has_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`product_has_category` (
  `product_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `category_id`),
  INDEX `fk_product_has_category_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_product_has_category_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_category_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `webshop`.`product` (`id_product`) 
	ON DELETE CASCADE
   
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `webshop`.`category` (`id_category`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`purchase` (
  `id_purchase` INT NOT NULL AUTO_INCREMENT,
  `paying_method` ENUM("card", "delivery") NOT NULL,
  `product_id` INT NOT NULL,
  `paying_info` VARCHAR(100) NULL,
  `id_buyer` INT NOT NULL,
  PRIMARY KEY (`id_purchase`),
  INDEX `fk_purchase_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_purchase_trader1_idx` (`id_buyer` ASC) VISIBLE,
  CONSTRAINT `fk_purchase_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `webshop`.`product` (`id_product`) ON DELETE CASCADE
    
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_trader1`
    FOREIGN KEY (`id_buyer`)
    REFERENCES `webshop`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`attribute` (
  `id_attribute` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id_attribute`),
  INDEX `fk_attribute_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_attribute_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `webshop`.`category` (`id_category`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`message` (
  `id_message` INT NOT NULL AUTO_INCREMENT,
  `mcontent` VARCHAR(500) NULL,
  `mread` TINYINT(1) NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id_message`),
  INDEX `fk_message_trader1_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_message_trader1`
    FOREIGN KEY (`id_user`)
    REFERENCES `webshop`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`product_has_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`product_has_attribute` (
  `product_id_product` INT NOT NULL,
  `attribute_id_attribute` INT NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`product_id_product`, `attribute_id_attribute`),
  INDEX `fk_product_has_attribute_attribute1_idx` (`attribute_id_attribute` ASC) VISIBLE,
  INDEX `fk_product_has_attribute_product1_idx` (`product_id_product` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_attribute_product1`
    FOREIGN KEY (`product_id_product`)
    REFERENCES `webshop`.`product` (`id_product`) 
	ON DELETE CASCADE
   
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_attribute_attribute1`
    FOREIGN KEY (`attribute_id_attribute`)
    REFERENCES `webshop`.`attribute` (`id_attribute`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `webshop`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webshop`.`comment` (
  `id_comment` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `username` VARCHAR(20) NULL,
  `mcontent` VARCHAR(300) NULL,
  PRIMARY KEY (`id_comment`, `product_id`),
  INDEX `fk_comment_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `webshop`.`product` (`id_product`) ON DELETE CASCADE
   
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
