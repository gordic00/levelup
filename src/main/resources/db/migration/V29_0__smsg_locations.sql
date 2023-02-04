CREATE TABLE `smsg_locations`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `location_code` varchar(255) COLLATE utf8_bin NOT NULL,
    `full_name`     varchar(255) COLLATE utf8_bin NOT NULL,
    `location_id`   int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;