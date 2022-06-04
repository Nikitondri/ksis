CREATE TABLE `role` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) CHARSET=utf8mb3;

CREATE TABLE `user` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `login` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `role_id` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_role` (`role_id`),
    CONSTRAINT `FK_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE KEY `login` (`login`)
) CHARSET=utf8mb3;


CREATE TABLE `package` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `user_id` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_user` (`user_id`),
    CONSTRAINT `FK_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) CHARSET=utf8mb3;

CREATE TABLE `myFile` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `size` int unsigned NOT NULL,
    `package_id` int unsigned NOT NULL,
    `path` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_package` (`package_id`),
    CONSTRAINT `FK_package` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) CHARSET=utf8mb3;

