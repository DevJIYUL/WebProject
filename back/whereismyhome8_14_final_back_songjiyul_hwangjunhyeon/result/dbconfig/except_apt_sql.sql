-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyweb` DEFAULT CHARACTER SET utf8mb4 ;
USE `ssafyweb` ;

-- -----------------------------------------------------
-- Table `ssafyweb`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`category` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`category` (
  `categoryCode` VARCHAR(3) NOT NULL,
  `tag` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`categoryCode`),
  INDEX `categoryCode_idx` (`categoryCode` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- --------------------------------------------
-- insert cateogry
-- --------------------------------------------
insert into category(`categoryCode`, `tag`) values('MT1','대형마트'),
('CS2','편의점'), ('PS3','어린이집'), ('SC4','학교'), ('AC5','학원'), ('PK6','주차장'),
('OL7','주유소'), ('SW8','지하철역'), ('BK9','은행'), ('CT1','문화시설'), ('AG2','중개업소'),
('PO3','공공기관'), ('AT4','관광명소'), ('AD5','숙박'), ('FD6','음식점'), ('CE7','카페'),
('HP8','병원'), ('PM9','약국');

-- -----------------------------------------------------
-- Table `ssafyweb`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`notice` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`notice` (
  `noticeNo` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `content` BLOB NULL DEFAULT NULL,
  `hit` INT(11) NULL DEFAULT '0',
  `wdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`noticeNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;
-- -----------------------------------------------------
-- Table `ssafyweb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`user` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`user` (
  `id` VARCHAR(16) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `email` VARCHAR(16) NULL DEFAULT NULL,
  `domain` VARCHAR(20) NULL DEFAULT NULL,
  `role` VARCHAR(15) NULL DEFAULT 'USER',
  `joinDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `residence` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_dongcode` (`residence` ASC) ,
  CONSTRAINT `fk_user_dongcode`
    FOREIGN KEY (`residence`)
    REFERENCES `ssafyweb`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafyweb`.`favoritecate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`favoritecate` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`favoritecate` (
  `id` VARCHAR(16) NOT NULL,
  `categoryCode` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`, `categoryCode`),
  INDEX `fk_favorite_user_idx2` (`id` ASC) ,
  INDEX `fk_favorite_category213_idx` (`categoryCode` ASC) ,
  CONSTRAINT `fk_favorite_category213`
    FOREIGN KEY (`categoryCode`)
    REFERENCES `ssafyweb`.`category` (`categoryCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favorite_user222`
    FOREIGN KEY (`id`)
    REFERENCES `ssafyweb`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafyweb`.`favoriteloc`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`favoriteloc` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`favoriteloc` (
  `id` VARCHAR(16) NOT NULL,
  `dongCode` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`, `dongCode`),
  INDEX `fk_favorite_users_idx` (`id` ASC) ,
  INDEX `fk_favorite_dongcode1_idx` (`dongCode` ASC) ,
  CONSTRAINT `fk_favorite_dongcode3231`
    FOREIGN KEY (`dongCode`)
    REFERENCES `ssafyweb`.`dongcode` (`dongCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favorite_users123`
    FOREIGN KEY (`id`)
    REFERENCES `ssafyweb`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;





-- -----------------------------------------------------
-- Table `ssafyweb`.`fileinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`fileinfo` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`fileinfo` (
  `idx` INT(11) NOT NULL AUTO_INCREMENT,
  `noticeNo` INT(11) NULL DEFAULT NULL,
  `saveFolder` VARCHAR(45) NULL DEFAULT NULL,
  `originalFile` VARCHAR(50) NULL DEFAULT NULL,
  `saveFile` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`idx`),
  INDEX `file_info_to_board_notice_no_fk` (`noticeNo` ASC) ,
  CONSTRAINT `fk_file_info_to_board_notice_no_fk`
    FOREIGN KEY (`noticeNo`)
    REFERENCES `ssafyweb`.`notice` (`noticeNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `ssafyweb`.`quest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`quest` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`quest` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `content` BLOB NULL DEFAULT NULL,
  `privateFlag` TINYINT(4) NULL DEFAULT '0',
  `answeredFlag` TINYINT(4) NULL DEFAULT '0',
  `regDate` DATETIME NULL DEFAULT NULL,
  `answer` BLOB NULL DEFAULT NULL,
  `username` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quest_user1_idx13` (`id` ASC) ,
  INDEX `fk_quest_user123` (`username` ASC) ,
  CONSTRAINT `fk_quest_user123`
    FOREIGN KEY (`username`)
    REFERENCES `ssafyweb`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 231
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- token
----------------------------------------------------------

CREATE TABLE `ssafyweb`.`token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accessToken` BLOB NULL,
  `refreshToken` BLOB NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
