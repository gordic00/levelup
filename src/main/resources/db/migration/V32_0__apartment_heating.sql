CREATE TABLE `apartment_heating` (
                        `apartment_id` int(11) NOT NULL,
                        `heating_id` int(11) NOT NULL,
                        CONSTRAINT apartment_heating_pk PRIMARY KEY  (apartment_id, heating_id),
                        KEY `fk_apartment_heating_apartment_v1` ( `apartment_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_heating_apartment_v1` FOREIGN KEY ( `apartment_id` ) REFERENCES
                            `apartment` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `fk_apartment_heating_heating_v1` ( `heating_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_heating_heating_v1` FOREIGN KEY ( `heating_id` ) REFERENCES
                            `heating` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;