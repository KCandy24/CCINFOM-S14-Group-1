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

-- USERS table
INSERT INTO `users` (`user_name`, `region`, `join_date`)
VALUES 
('AnimeFan101', 'AM', '2020-05-21'),
('OtakuMaster', 'JP', '2018-07-14'),
('MangaLover', 'EU', '2019-02-08'),
('KawaiiKitten', 'AS', '2021-03-25'),
('ShonenWarrior', 'AU', '2022-11-15');

-- STUDIOS table
INSERT INTO `studios` (`studio_name`)
VALUES 
('Studio Ghibli'),
('Bones'),
('Sunrise'),
('Toei Animation'),
('Madhouse');

-- STAFF table
INSERT INTO `staff` (`first_name`, `last_name`, `occupation`, `birthday`)
VALUES 
('Hayao', 'Miyazaki', 'Director', '1941-01-05'),
('Shinji', 'Aramaki', 'Animator', '1960-10-02'),
('Yoko', 'Kanno', 'Composer', '1964-03-18'),
('Takeshi', 'Honda', 'Character Designer', '1968-06-02'),
('Masashi', 'Kishimoto', 'Writer', '1974-11-08');

-- ANIMES table
INSERT INTO `animes` (`studio_id`, `title`, `genre`, `air_date`, `num_of_episodes`, `available_from_date`, `available_to_date`)
VALUES 
(1, 'Spirited Away', 'FA', '2001-07-20', 1, '2021-01-01', '2023-01-01'),
(2, 'Fullmetal Alchemist: Brotherhood', 'AC', '2009-04-05', 64, '2020-01-01', '2025-01-01'),
(3, 'Cowboy Bebop', 'SF', '1998-04-03', 26, '2022-01-01', '2027-01-01'),
(4, 'One Piece', 'AD', '1999-10-20', 1000, '2019-01-01', '2030-01-01'),
(5, 'Death Note', 'TH', '2006-10-03', 37, '2023-01-01', '2026-01-01');

-- VIEWS table
INSERT INTO `views` (`user_id`, `anime_id`, `watched_episode`, `timestamp_watched`)
VALUES 
(1, 1, 1, '2022-05-21 18:20:15'),
(2, 2, 5, '2021-08-15 20:45:00'),
(3, 3, 10, '2021-12-25 17:30:00'),
(4, 4, 500, '2023-03-15 22:10:05'),
(5, 5, 25, '2024-05-07 14:55:55');

-- RATINGS table
INSERT INTO `ratings` (`user_id`, `anime_id`, `rating`, `comment`, `last_episode_watched`, `last_edited_timestamp`)
VALUES 
(1, 1, 10, 'Absolutely amazing movie!', 1, '2022-05-21 18:30:15'),
(2, 2, 9, 'Great story and characters.', 64, '2021-08-16 12:10:45'),
(3, 3, 8, 'Loved the soundtrack and setting.', 26, '2021-12-26 18:00:00'),
(4, 4, 7, 'Very long but entertaining.', 700, '2023-03-16 23:20:10'),
(5, 5, 10, 'A masterpiece!', 37, '2024-05-08 15:05:55');

-- CREDITS table
INSERT INTO `credits` (`staff_id`, `anime_id`, `position`, `department`)
VALUES 
(1, 1, 'Director', 'DP'),
(2, 2, 'Animator', 'AN'),
(3, 3, 'Composer', 'SS'),
(4, 4, 'Character Designer', 'AD'),
(5, 5, 'Writer', 'TO');

-- FOLLOWS table
INSERT INTO `follows` (`follower_id`, `followed_id`, `following_since_date`)
VALUES 
(1, 2, '2021-01-10'),
(2, 3, '2019-07-15'),
(3, 4, '2020-11-23'),
(4, 5, '2022-04-12'),
(5, 1, '2023-08-25');