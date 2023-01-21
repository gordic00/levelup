CREATE TABLE `apartment_additional` (
                        `apartment_id` int(11) NOT NULL,
                        `additional_id` int(11) NOT NULL,
                        CONSTRAINT apartment_additional_pk PRIMARY KEY  (apartment_id, additional_id),
                        KEY `fk_apartment_additional_apartment_v1` ( `apartment_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_additional_apartment_v1` FOREIGN KEY ( `apartment_id` ) REFERENCES
                            `apartment` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `fk_apartment_additional_additional_v1` ( `additional_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_additional_additional_v1` FOREIGN KEY ( `additional_id` ) REFERENCES
                            `additional` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;