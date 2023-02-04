CREATE TABLE `apartment_included` (
                        `apartment_id` int(11) NOT NULL,
                        `included_id` int(11) NOT NULL,
                        CONSTRAINT apartment_included_pk PRIMARY KEY  (apartment_id, included_id),
                        KEY `fk_apartment_included_apartment_v1` ( `apartment_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_included_apartment_v1` FOREIGN KEY ( `apartment_id` ) REFERENCES
                            `apartment` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `fk_apartment_included_included_v1` ( `included_id` ) USING BTREE,
                        CONSTRAINT `fk_apartment_included_included_v1` FOREIGN KEY ( `included_id` ) REFERENCES
                            `included` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;