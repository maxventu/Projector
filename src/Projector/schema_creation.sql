CREATE USER 'projector_admin'@'%' IDENTIFIED BY '1234567';
GRANT ALL PRIVILEGES ON projector . * TO 'projector_admin'@'%';
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema projector
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projector
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projector` DEFAULT CHARACTER SET utf8 ;
USE `projector` ;

-- -----------------------------------------------------
-- Table `projector`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`employee` ;

CREATE TABLE IF NOT EXISTS `projector`.`employee` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `deleted` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`project` ;

CREATE TABLE IF NOT EXISTS `projector`.`project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `creation_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `due_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`team` ;

CREATE TABLE IF NOT EXISTS `projector`.`team` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_team_project1_idx` (`project_id` ASC),
  CONSTRAINT `fk_team_project1`
  FOREIGN KEY (`project_id`)
  REFERENCES `projector`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`emp_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`emp_role` ;

CREATE TABLE IF NOT EXISTS `projector`.`emp_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`employee_team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`employee_team` ;

CREATE TABLE IF NOT EXISTS `projector`.`employee_team` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `employee_id` BIGINT(20) NOT NULL,
  `team_id` BIGINT(20) NOT NULL,
  `emp_role_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_has_project_team_project_team1_idx` (`team_id` ASC),
  INDEX `fk_employee_has_project_team_employee_idx` (`employee_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_employee_team_emp_role1_idx` (`emp_role_id` ASC),
  CONSTRAINT `fk_employee_has_project_team_employee`
  FOREIGN KEY (`employee_id`)
  REFERENCES `projector`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_has_project_team_project_team1`
  FOREIGN KEY (`team_id`)
  REFERENCES `projector`.`team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_team_emp_role1`
  FOREIGN KEY (`emp_role_id`)
  REFERENCES `projector`.`emp_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`task` ;

CREATE TABLE IF NOT EXISTS `projector`.`task` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `project_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_task_project1_idx` (`project_id` ASC),
  CONSTRAINT `fk_task_project1`
  FOREIGN KEY (`project_id`)
  REFERENCES `projector`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`sprint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`sprint` ;

CREATE TABLE IF NOT EXISTS `projector`.`sprint` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `creation_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `due_date` DATETIME NOT NULL,
  `task_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_sprint_task1_idx` (`task_id` ASC),
  CONSTRAINT `fk_sprint_task1`
  FOREIGN KEY (`task_id`)
  REFERENCES `projector`.`task` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`subtask_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`subtask_type` ;

CREATE TABLE IF NOT EXISTS `projector`.`subtask_type` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`subtask`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`subtask` ;

CREATE TABLE IF NOT EXISTS `projector`.`subtask` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `open_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estimated_hours` INT(11) NOT NULL,
  `closing_date` DATETIME NULL DEFAULT NULL,
  `subtask_type_id` BIGINT(20) NOT NULL,
  `sprint_id` BIGINT(20) NOT NULL,
  `solver_id` BIGINT(20) NULL,
  `assigned_by_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_subtask_subtask_type1_idx` (`subtask_type_id` ASC),
  INDEX `fk_subtask_sprint1_idx` (`sprint_id` ASC),
  INDEX `fk_subtask_employee1_idx` (`solver_id` ASC),
  INDEX `fk_subtask_employee2_idx` (`assigned_by_id` ASC),
  CONSTRAINT `fk_subtask_subtask_type1`
  FOREIGN KEY (`subtask_type_id`)
  REFERENCES `projector`.`subtask_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtask_sprint1`
  FOREIGN KEY (`sprint_id`)
  REFERENCES `projector`.`sprint` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtask_employee1`
  FOREIGN KEY (`solver_id`)
  REFERENCES `projector`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtask_employee2`
  FOREIGN KEY (`assigned_by_id`)
  REFERENCES `projector`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projector`.`subtask_lock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projector`.`subtask_lock` ;

CREATE TABLE IF NOT EXISTS `projector`.`subtask_lock` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `subtask_locking_id` BIGINT(20) NOT NULL,
  `subtask_locked_by_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_subtask_lock_subtask1_idx` (`subtask_locking_id` ASC),
  INDEX `fk_subtask_lock_subtask2_idx` (`subtask_locked_by_id` ASC),
  CONSTRAINT `fk_subtask_lock_subtask1`
  FOREIGN KEY (`subtask_locking_id`)
  REFERENCES `projector`.`subtask` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtask_lock_subtask2`
  FOREIGN KEY (`subtask_locked_by_id`)
  REFERENCES `projector`.`subtask` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
