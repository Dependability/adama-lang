/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See https://www.adama-platform.com/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber ( http://jeffrey.io )
 */
package org.adamalang.mysql;

import java.sql.Connection;

/** The installer for the database */
public class Installer {
  public final DataBase dataBase;

  public Installer(DataBase dataBase) {
    this.dataBase = dataBase;
  }

  public void install() throws Exception {
    String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS `" + dataBase.databaseName + "`";

    String createDeployedTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `").append(dataBase.databaseName).append("`.`deployed` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `space` VARCHAR(128) NOT NULL,") //
        .append("  `target` VARCHAR(256) NOT NULL,") //
        .append("  `hash` VARCHAR(256) NOT NULL,") //
        .append("  `plan` LONGTEXT NOT NULL,") //
        .append("  PRIMARY KEY (`id`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createDirectoryTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `").append(dataBase.databaseName).append("`.`directory` (") //
        .append("  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `space` VARCHAR(128) NOT NULL,") //
        .append("  `key` VARCHAR(512) NOT NULL,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,") //
        .append("  `head_seq` INT(4) UNSIGNED NOT NULL,") //
        .append("  `active` BOOLEAN NOT NULL,") //
        .append("  `type` INT(2) UNSIGNED NOT NULL,") //
        .append("  `region` VARCHAR(64) NOT NULL,") //
        .append("  `machine` VARCHAR(512) NOT NULL,") //
        .append("  `archive` VARCHAR(512) NOT NULL,") //
        .append("  `delta_bytes` BIGINT UNSIGNED NOT NULL,") //
        .append("  `asset_bytes` BIGINT UNSIGNED NOT NULL,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  UNIQUE  `u` (`space`, `key`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createEmailsTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`emails` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `email` VARCHAR(128) NOT NULL,") //
        .append("  `password` TEXT NOT NULL,") //
        .append("  `balance` INT(4) DEFAULT 0,") //
        .append("  `credit_carry_limit` INT(4) DEFAULT -500,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  `validations` INT(4) UNSIGNED NOT NULL,") //
        .append("  `last_validated` DATETIME NULL,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  UNIQUE  `u` (`email`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createInitiationsTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`initiations` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `user` INT(4) UNSIGNED NOT NULL,") //
        .append("  `hash` TEXT NOT NULL,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  `expires` DATETIME,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `u` (`user` ASC))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createAccessKeysTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`email_keys` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `user` INT(4) UNSIGNED NOT NULL,") //
        .append("  `public_key` TEXT NOT NULL,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  `expires` DATETIME,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `u` (`user` ASC))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createSpaceTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`spaces` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `owner` INT(4) UNSIGNED NOT NULL,") //
        .append("  `name` VARCHAR(128) NOT NULL,") //
        .append("  `enabled` BOOLEAN DEFAULT TRUE,") //
        .append("  `storage_bytes` INT(8) DEFAULT 0,") //
        .append("  `unbilled_storage_bytes_hours` BIGINT DEFAULT 0,") //
        .append("  `unbilled_bandwidth_hours` BIGINT DEFAULT 0,") //
        .append("  `unbilled_first_party_service_calls` INT(4) DEFAULT 0,") //
        .append("  `unbilled_third_party_service_calls` INT(4) DEFAULT 0,") //
        .append("  `latest_billing_hour` INT(4) UNSIGNED DEFAULT 0,") //
        .append("  `plan` TEXT NOT NULL,") //
        .append("  `hash` VARCHAR(256) NOT NULL,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  UNIQUE `u` (`name`),") //
        .append("  INDEX `c` (`owner`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createGrantTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`grants` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `space` INT(4) UNSIGNED NOT NULL,") //
        .append("  `user` INT(4) UNSIGNED NOT NULL,") //
        .append("  `role` INT(1) UNSIGNED NOT NULL,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX  `u` (`space`, `user` ASC))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createAuthoritiesTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`authorities` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `owner` INT(4) UNSIGNED NOT NULL,") //
        .append("  `authority` VARCHAR(64) NOT NULL,") //
        .append("  `keystore` TEXT NOT NULL,") //
        .append("  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `o` (`owner`),") //
        .append("  UNIQUE `s` (`authority`),") //
        .append("  INDEX `c` (`created` DESC))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createMeteringTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`metering` (") //
        .append("  `id` INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `target` VARCHAR(256) NOT NULL,") //
        .append("  `batch` LONGTEXT NOT NULL,") //
        .append("  `created` DATETIME NOT NULL,") //
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `t` (`target`),") //
        .append("  INDEX `c` (`created` DESC))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createBillingTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`bills` (") //
        .append("  `id` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `space` INT(4) UNSIGNED NOT NULL,") // (i.e. who is going to pay)
        .append("  `hour` INT(8) UNSIGNED NOT NULL,") // the UTC hour for the resource consumption
        .append("  `summary` LONGTEXT NOT NULL,")
        .append("  `pennies` INT(4) UNSIGNED NOT NULL,")
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `s` (`space`),") //
        .append("  INDEX `h` (`hour`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createWebHostsTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`web_hosts` (") //
        .append("  `id` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `region` VARCHAR(64) NOT NULL,") //
        .append("  `machine` VARCHAR(512) NOT NULL,") //
        .append("  `public_key` LONGTEXT NOT NULL,")
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `m` (`machine`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    String createSecretsTableSQL = new StringBuilder() //
        .append("CREATE TABLE IF NOT EXISTS `" + dataBase.databaseName + "`.`secrets` (") //
        .append("  `id` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,") //
        .append("  `space` VARCHAR(128) NOT NULL,") //
        .append("  `encrypted_private_key` LONGTEXT NOT NULL,")
        .append("  PRIMARY KEY (`id`),") //
        .append("  INDEX `space` (`space`))") //
        .append(" ENGINE = InnoDB") //
        .append(" DEFAULT CHARACTER SET = utf8mb4;") //
        .toString();

    Connection connection = dataBase.pool.getConnection();
    try {
      DataBase.execute(connection, createDatabaseSQL);
      DataBase.execute(connection, createDeployedTableSQL);
      DataBase.execute(connection, createDirectoryTableSQL);
      DataBase.execute(connection, createInitiationsTableSQL);
      DataBase.execute(connection, createEmailsTableSQL);
      DataBase.execute(connection, createAccessKeysTableSQL);
      DataBase.execute(connection, createSpaceTableSQL);
      DataBase.execute(connection, createGrantTableSQL);
      DataBase.execute(connection, createAuthoritiesTableSQL);
      DataBase.execute(connection, createMeteringTableSQL);
      DataBase.execute(connection, createBillingTableSQL);
      DataBase.execute(connection, createWebHostsTableSQL);
      DataBase.execute(connection, createSecretsTableSQL);
    } finally {
      connection.close();
    }
  }

  public void uninstall() throws Exception {
    Connection connection = dataBase.pool.getConnection();
    try {
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`emails`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`initiations`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`email_keys`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`spaces`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`grants`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`authorities`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`metering`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`bills`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`directory`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`deployed`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`web_hosts`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP TABLE IF EXISTS `").append(dataBase.databaseName).append("`.`secrets`;").toString());
      DataBase.execute(connection, new StringBuilder("DROP DATABASE IF EXISTS `").append(dataBase.databaseName).append("`;").toString());
    } finally {
      connection.close();
    }
  }
}