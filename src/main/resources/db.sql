CREATE TABLE `User` (
	`id` int NOT NULL,
	`email` varchar(100) NOT NULL UNIQUE,
	`password` varchar(100) NOT NULL,
	`name` varchar(200) NOT NULL,
	`birth_date` DATE NOT NULL,
	`language` varchar(2) NOT NULL,
	`reset_password_code` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Point` (
	`id` int NOT NULL,
	`question` TEXT NOT NULL,
	`order` int NOT NULL,
	`latitude` double NOT NULL,
	`longitude` double NOT NULL,
	`answer` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Progress` (
	`id` int NOT NULL,
	`user_id` int NOT NULL,
	`point_id` int NOT NULL,
	`start` TIMESTAMP NOT NULL,
	`end` TIMESTAMP NOT NULL,
	`end_latitude` double NOT NULL,
	`end_longitude` double NOT NULL,
	`last_used_hint_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Hint` (
	`id` int NOT NULL,
	`order` int NOT NULL,
	`hint_text` TEXT NOT NULL,
	`point_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Progress` ADD CONSTRAINT `Progress_fk0` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`);

ALTER TABLE `Progress` ADD CONSTRAINT `Progress_fk1` FOREIGN KEY (`point_id`) REFERENCES `Point`(`id`);

ALTER TABLE `Progress` ADD CONSTRAINT `Progress_fk2` FOREIGN KEY (`last_used_hint_id`) REFERENCES `Hint`(`id`);

ALTER TABLE `Hint` ADD CONSTRAINT `Hint_fk0` FOREIGN KEY (`point_id`) REFERENCES `Point`(`id`);
