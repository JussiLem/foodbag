CREATE TABLE `users`
(
  `id`            BIGINT       NOT NULL AUTO_INCREMENT,
  `first_name`    varchar(255) NOT NULL,
  `last_name`     varchar(255) NOT NULL,
  `username`      varchar(255) NOT NULL,
  `gender`        varchar(255) NOT NULL,
  `birthday`      DATE         NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `user_password` varchar(60)  NOT NULL,
  `enabled`       BOOLEAN      NOT NULL,
  `roleId`        BIGINT       NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles`
(
  `id`        BIGINT       NOT NULL AUTO_INCREMENT,
  `name`      varchar(255) NOT NULL,
  `planlevel` BIGINT       NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `food`
(
  `id`      BIGINT       NOT NULL AUTO_INCREMENT,
  `name`    varchar(255) NOT NULL,
  `comment` TEXT         NOT NULL,
  `date`    DATE         NOT NULL,
  `price`   DOUBLE       NOT NULL,
  `userId`  BIGINT       NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `users`
  ADD CONSTRAINT `users_fk0` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`);

ALTER TABLE `food`
  ADD CONSTRAINT `foods_fk0` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);
