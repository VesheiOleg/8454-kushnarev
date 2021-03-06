START TRANSACTION;
INSERT INTO `task5`.`manufacturer` (`title`) VALUES ('Noname');			-- 1
INSERT INTO `task5`.`manufacturer` (`title`) VALUES ('APC');			-- 2
INSERT INTO `task5`.`manufacturer` (`title`) VALUES ('Phoenix Contact');	-- 3
INSERT INTO `task5`.`manufacturer` (`title`) VALUES ('Hakel');			-- 4
COMMIT;

START TRANSACTION;
INSERT INTO `task5`.`category` (`title`) VALUES ('Разное');			-- 1
INSERT INTO `task5`.`category` (`title`) VALUES ('ИБП');				-- 2
INSERT INTO `task5`.`category` (`title`) VALUES ('Клемма');			-- 3
INSERT INTO `task5`.`category` (`title`) VALUES ('Реле');			-- 4
INSERT INTO `task5`.`category` (`title`) VALUES ('Предохранитель');		-- 5
INSERT INTO `task5`.`category` (`title`) VALUES ('Защита от перенапряжений');	-- 6
COMMIT;

START TRANSACTION;
INSERT INTO `task5`.`product` (`title`, `categoryId`, vendorCode, manufacturerId, `description`)
VALUES ('Smart-UPS SRT 3000 ВА', 2, 'SRT3000XLI', 2, 'Источник бесперебойного питания');
INSERT INTO `task5`.`product` (`title`, `categoryId`, vendorCode, manufacturerId, `description`)
VALUES ('STS 2,5-TWIN', 3, '3031720', 3, 'Клемма 2,5 кв.мм, серая');
INSERT INTO `task5`.`product` (`title`, `categoryId`, vendorCode, manufacturerId, `description`)
VALUES ('STS 2,5-TWIN OG', 3, '3037504', 3, 'Клемма 2,5 кв.мм, оранжевая');
INSERT INTO `task5`.`product` (`title`, `categoryId`, vendorCode, manufacturerId, `description`)
VALUES ('RIF-0-RPT-24DC/21', 4, '2903370', 3, 'Реле 24 В');
INSERT INTO `task5`.`product` (`title`, `categoryId`, vendorCode, manufacturerId, `description`)
VALUES ('ГСД3-230/TNS ', 6, '300004', 4, 'Защита от перенапряжений');
COMMIT;