CREATE TABLE `apartment` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `ad_code` varchar(255) COLLATE utf8_bin NOT NULL,
                             `type_id` int(11) NOT NULL,
                             `title_sr` varchar(255) COLLATE utf8_bin NOT NULL,
                             `title_ru` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `title_en` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `location_id` int(11) NOT NULL,
                             `address` varchar(255) COLLATE utf8_bin NOT NULL,
                             `address_no` int(11) NOT NULL,
                             `quadrature` int(11) NOT NULL,
                             `description_sr` varchar(900) COLLATE utf8_bin NOT NULL,
                             `description_ru` varchar(900) COLLATE utf8_bin DEFAULT NULL,
                             `description_en` varchar(900) COLLATE utf8_bin DEFAULT NULL,
                             `rooms` int(11) NOT NULL,
                             `floor_id` int(11) DEFAULT NULL,
                             `number_storeys_id` int(11) DEFAULT NULL,
                             `structure_id` int(11) NOT NULL,
                             `advertiser_id` int(11) NOT NULL,
                             `construction_type_id` int(11) NOT NULL,
                             `condition_id` int(11) DEFAULT NULL,
                             `bathrooms_id` int(11) DEFAULT NULL,
                             `furnished_id` int(11) DEFAULT NULL,
                             `payment_type_id` int(11) DEFAULT NULL,
                             `monthly_utilities` float DEFAULT NULL,
                             `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `created_date` datetime DEFAULT NULL,
                             `last_modified_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                             `last_modified_date` datetime DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `ad_code` (`ad_code`),
                             KEY `fk_apartment_types_v1` ( `type_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_types_v1` FOREIGN KEY ( `type_id` ) REFERENCES
                                 `type` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_floor_v1` ( `floor_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_floor_v1` FOREIGN KEY ( `floor_id` ) REFERENCES
                                 `floor` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_number_storeys_v1` ( `number_storeys_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_number_storeys_v1` FOREIGN KEY ( `number_storeys_id` ) REFERENCES
                                 `number_storeys` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_structure_v1` ( `structure_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_structure_v1` FOREIGN KEY ( `structure_id` ) REFERENCES
                                 `structure` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_advertiser_v1` ( `advertiser_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_advertiser_v1` FOREIGN KEY ( `advertiser_id` ) REFERENCES
                                 `advertiser` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_construction_type_v1` ( `construction_type_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_construction_type_v1` FOREIGN KEY ( `construction_type_id` ) REFERENCES
                                 `construction_type` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_condition_v1` ( `condition_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_condition_v1` FOREIGN KEY ( `condition_id` ) REFERENCES
                                 `apartment_conditions` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `fk_apartment_bathrooms_v1` ( `bathrooms_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_bathrooms_v1` FOREIGN KEY ( `bathrooms_id` ) REFERENCES
                                 `bathrooms` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                                 KEY `fk_apartment_furnished_v1` ( `furnished_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_furnished_v1` FOREIGN KEY ( `furnished_id` ) REFERENCES
                                 `furnished` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                                 KEY `fk_apartment_payment_type_v1` ( `payment_type_id` ) USING BTREE,
                             CONSTRAINT `fk_apartment_payment_type_v1` FOREIGN KEY ( `payment_type_id` ) REFERENCES
                                 `payment_type` ( `id` ) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT `fk_apartment_smsg_locations_v_1`
                                 FOREIGN KEY (`location_id`) REFERENCES `smsg_locations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;