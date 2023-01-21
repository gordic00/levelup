CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) COLLATE utf8_bin NOT NULL,
                        `password` varchar(255) COLLATE utf8_bin NOT NULL,
                        `email` varchar(255) COLLATE utf8_bin NOT NULL,
                        `token` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                        `token_ts` bigint(20) DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `username` (`username`) USING BTREE,
                        UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;