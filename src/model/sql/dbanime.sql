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

-- Insert sample data into `users` table
INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES
('Alice', 'JP', '2023-01-10'),
('Bob', 'AM', '2023-05-15'),
('Charlie', 'EU', '2023-07-20'),
('Diana', 'AS', '2023-02-25'),
('Ethan', 'AU', '2023-03-30'),
('Fiona', 'AF', '2023-08-15'),
('George', 'EU', '2023-06-10'),
('Hannah', 'AM', '2023-04-05'),
('Ivy', 'AS', '2023-09-12'),
('Jack', 'JP', '2023-10-01');

-- Insert sample data into `studios` table
INSERT INTO `studios` (`studio_name`) VALUES
('Studio Ghibli'),
('Toei Animation'),
('Madhouse'),
('Bones'),
('Sunrise'),
('Kyoto Animation'),
('A-1 Pictures'),
('MAPPA'),
('Studio Trigger'),
('CloverWorks');

-- Insert sample data into `staff` table
INSERT INTO `staff` (`first_name`, `last_name`, `occupation`, `birthday`) VALUES
('Hayao', 'Miyazaki', 'Director', '1941-01-05'),
('Isao', 'Takahata', 'Producer', '1935-10-29'),
('Yoko', 'Kanno', 'Composer', '1964-03-18'),
('Makoto', 'Shinkai', 'Director', '1973-02-09'),
('Yoshiyuki', 'Tomino', 'Animator', '1941-11-05'),
('Satoshi', 'Kon', 'Screenwriter', '1963-10-12'),
('Mamoru', 'Hosoda', 'Director', '1967-09-19'),
('Gen', 'Urobuchi', 'Writer', '1972-12-20'),
('Naoko', 'Yamada', 'Animator', '1984-11-28'),
('Shinichiro', 'Watanabe', 'Director', '1965-05-24');

-- Insert sample data into `animes` table
INSERT INTO `animes` (`studio_id`, `title`, `genre`, `air_date`, `num_of_episodes`, `available_from_date`, `available_to_date`) VALUES
(1, 'My Neighbor Totoro', 'FA', '1988-04-16', 1, '2023-01-01', '2023-12-31'),
(2, 'One Piece', 'AD', '1999-10-20', 1000, '2023-01-01', '2023-12-31'),
(3, 'Death Note', 'TH', '2006-10-04', 37, '2023-01-01', '2023-12-31'),
(4, 'Fullmetal Alchemist', 'AC', '2003-10-04', 51, '2023-01-01', '2023-12-31'),
(5, 'Cowboy Bebop', 'SF', '1998-04-03', 26, '2023-01-01', '2023-12-31'),
(6, 'Attack on Titan', 'AC', '2013-04-06', 75, '2023-01-01', '2023-12-31'),
(7, 'Neon Genesis Evangelion', 'MY', '1995-10-04', 26, '2023-01-01', '2023-12-31'),
(8, 'Your Name', 'RO', '2016-08-26', 1, '2023-01-01', '2023-12-31'),
(9, 'Demon Slayer', 'AD', '2019-04-06', 26, '2023-01-01', '2023-12-31'),
(10, 'Violet Evergarden', 'DR', '2018-01-11', 13, '2023-01-01', '2023-12-31');

-- Insert sample data into `views` table
INSERT INTO `views` (`user_id`, `anime_id`, `watched_episode`, `timestamp_watched`) VALUES
(1, 1, 1, '2023-01-11 10:30:00'),
(2, 2, 10, '2023-05-16 12:45:00'),
(3, 3, 5, '2023-07-21 15:15:00'),
(4, 4, 15, '2023-02-26 16:30:00'),
(5, 5, 20, '2023-04-01 18:00:00'),
(6, 6, 30, '2023-09-01 13:00:00'),
(7, 7, 2, '2023-06-12 09:45:00'),
(8, 8, 5, '2023-04-07 19:30:00'),
(9, 9, 18, '2023-09-15 11:00:00'),
(10, 10, 1, '2023-10-02 17:45:00');

-- Insert sample data into `ratings` table
INSERT INTO `ratings` (`user_id`, `anime_id`, `rating`, `comment`, `last_episode_watched`, `last_edited_timestamp`) VALUES
(1, 1, 5, 'Incredible animation and story.', 1, '2023-01-12 09:30:00'),
(2, 2, 4, 'Great adventure with amazing characters.', 100, '2023-05-20 11:00:00'),
(3, 3, 5, 'Dark and thrilling.', 37, '2023-07-25 13:30:00'),
(4, 4, 3, 'Interesting, but a bit slow.', 20, '2023-02-28 17:00:00'),
(5, 5, 5, 'Sci-fi masterpiece.', 26, '2023-04-03 18:45:00'),
(6, 6, 4, 'Amazing animation.', 60, '2023-09-02 14:00:00'),
(7, 7, 5, 'Mind-bending classic.', 26, '2023-06-13 10:30:00'),
(8, 8, 4, 'Emotional and beautifully animated.', 1, '2023-04-08 20:00:00'),
(9, 9, 5, 'Epic fights and storyline.', 26, '2023-09-16 11:45:00'),
(10, 10, 4, 'Touching and visually stunning.', 13, '2023-10-03 18:15:00');

-- Insert sample data into `credits` table
INSERT INTO `credits` (`staff_id`, `anime_id`, `position`, `department`) VALUES
(1, 1, 'Director', 'DP'),
(2, 1, 'Producer', 'DP'),
(3, 5, 'Composer', 'SS'),
(4, 4, 'Director', 'DP'),
(5, 2, 'Animator', 'AN'),
(6, 6, 'Director', 'DP'),
(7, 7, 'Writer', 'SS'),
(8, 3, 'Screenwriter', 'SS'),
(9, 10, 'Animator', 'AN'),
(10, 8, 'Director', 'DP');

-- Insert sample data into `follows` table
INSERT INTO `follows` (`follower_id`, `followed_id`, `following_since_date`) VALUES
(1, 2, '2023-01-15'),
(2, 3, '2023-06-01'),
(3, 4, '2023-08-01'),
(4, 5, '2023-03-01'),
(5, 1, '2023-04-10'),
(6, 7, '2023-08-15'),
(7, 8, '2023-06-20'),
(8, 9, '2023-04-10'),
(9, 10, '2023-09-13'),
(10, 1, '2023-10-05');