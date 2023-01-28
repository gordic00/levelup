ALTER TABLE `apartment`
    ADD COLUMN IF NOT EXISTS `location_id` int (11) NOT NULL AFTER `title_en`;
ALTER TABLE `apartment`
    DROP COLUMN IF EXISTS `municipality`;
ALTER TABLE `apartment`
    DROP COLUMN IF EXISTS `city`;
ALTER TABLE `apartment`
    ADD CONSTRAINT `fk_apartment_smsg_locations_v_1`
        FOREIGN KEY (`location_id`) REFERENCES `smsg_locations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;