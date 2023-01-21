CREATE TABLE `structure` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `code` varchar(255) COLLATE utf8_bin NOT NULL,
                        `name` varchar(255) COLLATE utf8_bin NOT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;