CREATE DATABASE IF NOT EXISTS `dbanime`;
USE `dbanime`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`user_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(32) NOT NULL,
    `region` ENUM('North America', 'South America', 'Europe', 'Asia', 'Australia', 'Africa', 'Antarctica'),
    `join_date` DATE
);

DROP TABLE IF EXISTS `studios`;
CREATE TABLE `studios` (
	`studio_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `studio_name` VARCHAR(32) NOT NULL
);

DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
	`staff_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `staff_name` VARCHAR(32) NOT NULL
);

-- DROP TABLE IF EXISTS `staff_affiliations`;
-- CREATE TABLE `staff_affiliations` (
-- 	`staff_affiliation_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     `staff_id` INT NOT NULL,
--     `studio_id` INT NOT NULL,
--     FOREIGN KEY(`staff_id`) REFERENCES `staff`(`staff_id`),
--     FOREIGN KEY(`studio_id`) REFERENCES `studios`(`studio_id`)
-- );

DROP TABLE IF EXISTS `animes`;
CREATE TABLE `animes` (
	`anime_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `studio_id` INT NOT NULL,
    `title` VARCHAR(64) UNIQUE,
    `genre` ENUM('Action', 'Adventure', 'Comedy', 'Drama', 'Fantasy', 'Horror', 'Mystery', 'Romance', 'Sci-Fi', 'Slice of Life', 'Sports', 'Thriller', 'Supernatural') NOT NULL,
    `air_date` DATE,
    `num_of_episodes` INT DEFAULT 0,
    `available_from_date` DATE,
    `available_to_date` DATE,
    FOREIGN KEY(`studio_id`) REFERENCES `studios`(`studio_id`)
);

DROP TABLE IF EXISTS `views`;
CREATE TABLE `views` (
	`user_id` INT NOT NULL PRIMARY KEY,
    `anime_id` INT NOT NULL,
    `latest_episode_watched` INT NOT NULL DEFAULT 0,
    -- i forgot what `status` was supposed to be
    FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
	`user_id` INT NOT NULL PRIMARY KEY,
    `anime_id` INT NOT NULL,
    `rating` INT NOT NULL,
    `comment` VARCHAR(2048), -- adding a separate date to `last_edited_date` feels redundant
    `last_edited_date` DATE,
    FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `credits`;
CREATE TABLE `credits` (
	`staff_id` INT NOT NULL PRIMARY KEY,
    `anime_id` INT NOT NULL,
    `position` VARCHAR(16) NOT NULL,
    FOREIGN KEY(`staff_id`) REFERENCES `staff`(`staff_id`),
    FOREIGN KEY(`anime_id`) REFERENCES `animes`(`anime_id`)
);

DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
	`follower_id` INT NOT NULL PRIMARY KEY,
    `followed_id` INT NOT NULL,
    FOREIGN KEY(`follower_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY(`followed_id`) REFERENCES `users`(`user_id`)
);

-- Insert sample data into `users` table
INSERT INTO `users` (`user_name`, `region`, `join_date`)
VALUES
('Alice', 'North America', '2022-01-15'),
('Bob', 'Europe', '2021-11-22'),
('Charlie', 'Asia', '2023-03-05'),
('Diana', 'South America', '2020-09-10'),
('Eve', 'Australia', '2022-07-18');

-- Insert sample data into `studios` table
INSERT INTO `studios` (`studio_name`)
VALUES
('Studio Ghibli'),
('Madhouse'),
('Bones'),
('Toei Animation'),
('MAPPA');

-- Insert sample data into `staff` table
INSERT INTO `staff` (`staff_name`)
VALUES
('Hayao Miyazaki'),
('Mamoru Hosoda'),
('Hiroshi Kamiya'),
('Masashi Kishimoto'),
('Takeshi Obata');

-- Insert sample data into `animes` table
INSERT INTO `animes` (`studio_id`, `title`, `genre`, `air_date`, `num_of_episodes`, `available_from_date`, `available_to_date`)
VALUES
(1, 'My Neighbor Totoro', 'Fantasy', '1988-04-16', 1, '2019-01-01', '2024-01-01'),
(2, 'One Punch Man', 'Action', '2015-10-05', 24, '2021-06-01', '2025-06-01'),
(3, 'Fullmetal Alchemist: Brotherhood', 'Adventure', '2009-04-05', 64, '2019-04-05', '2025-04-05'),
(4, 'Dragon Ball Z', 'Action', '1989-04-26', 291, '2018-04-26', '2025-04-26'),
(5, 'Jujutsu Kaisen', 'Supernatural', '2020-10-03', 24, '2021-10-03', '2026-10-03');

-- Insert sample data into `views` table
INSERT INTO `views` (`user_id`, `anime_id`, `latest_episode_watched`)
VALUES
(1, 1, 1),
(2, 2, 24),
(3, 3, 64),
(4, 4, 291),
(5, 5, 24);

-- Insert sample data into `ratings` table
INSERT INTO `ratings` (`user_id`, `anime_id`, `rating`, `comment`, `last_edited_date`)
VALUES
(1, 1, 10, 'A beautiful, timeless story!', '2023-02-12'),
(2, 2, 9, 'Incredibly fun and exciting!', '2023-03-18'),
(3, 3, 10, 'Masterpiece of storytelling!', '2023-05-21'),
(4, 4, 8, 'A classic of my childhood.', '2023-07-15'),
(5, 5, 9, 'Action-packed and well-animated!', '2023-09-20');

-- Insert sample data into `credits` table
INSERT INTO `credits` (`staff_id`, `anime_id`, `position`)
VALUES
(1, 1, 'Director'),
(2, 2, 'Director'),
(3, 3, 'Voice Actor'),
(4, 4, 'Mangaka'),
(5, 5, 'Char Designer');

-- Insert sample data into `follows` table
INSERT INTO `follows` (`follower_id`, `followed_id`)
VALUES
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 1);