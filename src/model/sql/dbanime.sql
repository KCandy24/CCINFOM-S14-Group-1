CREATE DATABASE IF NOT EXISTS `dbanime`;
USE `dbanime`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`user_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(32) NOT NULL,
    `region` ENUM('JP', 'AM', 'EU', 'AS', 'AU', 'AF', 'AC') NOT NULL,
    -- REGIONS: Japan, Americas, Europe, Asia, Australia, Africa, Antarctica
    `join_date` DATE NOT NULL
);

DROP TABLE IF EXISTS `studios`;
CREATE TABLE `studios` (
	`studio_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `studio_name` VARCHAR(32) NOT NULL
);

DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
	`staff_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(16) NOT NULL,
    `last_name` VARCHAR(16) NOT NULL,
    `occupation` VARCHAR(32) NOT NULL,
    `birthday` DATE NOT NULL
);

DROP TABLE IF EXISTS `animes`;
CREATE TABLE `animes` (
	`anime_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `studio_id` INT NOT NULL,
    `title` VARCHAR(64) UNIQUE,
    `genre` ENUM('AC', 'AD', 'CO', 'DR', 'FA', 'HO', 'MY', 'RO', 'SF', 'SL', 'SP', 'TH', 'SU') NOT NULL,
    -- GENRES: Action, Adventure, Comedy, Drama, Fantasy, Horror, Mystery, Romance, Sci-Fi, Slice of Life, Sports, Thriller, Supernatural
    `air_date` DATE NOT NULL,
    `num_of_episodes` INT DEFAULT 0,
    `available_from_date` DATE NOT NULL,
    `available_to_date` DATE NOT NULL,
    FOREIGN KEY(`studio_id`) REFERENCES `studios`(`studio_id`)
);

DROP TABLE IF EXISTS `views`;
CREATE TABLE `views` (
	`user_id` INT NOT NULL,
    `anime_id` INT NOT NULL,
    `watched_episode` INT NOT NULL,
    `timestamp_watched` TIMESTAMP NOT NULL,
    PRIMARY KEY(`user_id`, `timestamp_watched`),
    FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
	`user_id` INT NOT NULL PRIMARY KEY,
    `anime_id` INT NOT NULL,
    `rating` INT NOT NULL,
    `comment` VARCHAR(2048),
    `last_episode_watched` INT NOT NULL,
    `last_edited_timestamp` TIMESTAMP NOT NULL,
    FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `credits`;
CREATE TABLE `credits` (
	`staff_id` INT NOT NULL PRIMARY KEY,
    `anime_id` INT NOT NULL,
    `position` VARCHAR(32) NOT NULL,
    `department` ENUM('DP', 'AD', 'AN', 'EV', 'SS', 'TO') NOT NULL,
    -- DEPARTMENTS: Direction and Production, Art and Design, Animation, Sound and Music, Editing and Visual Effects, Script and Storyboarding, Technical and Other Staff
    FOREIGN KEY(`staff_id`) REFERENCES `staff`(`staff_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
	`follower_id` INT NOT NULL PRIMARY KEY,
    `followed_id` INT NOT NULL,
    `following_since_date` DATE NOT NULL,
    FOREIGN KEY(`follower_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`followed_id`) REFERENCES `users`(`user_id`)
);