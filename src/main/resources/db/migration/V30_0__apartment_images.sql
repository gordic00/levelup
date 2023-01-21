CREATE TABLE `apartment_images` (
                                    `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                                    `apartment_id` INT ( 11 ) NOT NULL,
                                    `name` VARCHAR ( 255 ) COLLATE utf8_bin NOT NULL,
                                    `path` VARCHAR (255) COLLATE utf8_bin NOT NULL,
                                    `sorting` INT ( 11 ) DEFAULT NULL,
                                    `created_by` VARCHAR ( 255 ) COLLATE utf8_bin DEFAULT NULL,
                                    `created_date` datetime DEFAULT NULL,
                                    `last_modified_by` VARCHAR ( 255 ) COLLATE utf8_bin DEFAULT NULL,
                                    `last_modified_date` datetime DEFAULT NULL,
                                    PRIMARY KEY ( `id` ) USING BTREE,
                                    UNIQUE KEY `sorting` (`sorting`) USING BTREE,
                                    KEY `fk_apartment_images_v1` ( `apartment_id` ) USING BTREE,
                                    CONSTRAINT `fk_apartment_images_v1` FOREIGN KEY ( `apartment_id` ) REFERENCES `apartment` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB AUTO_INCREMENT = 66 DEFAULT CHARSET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;