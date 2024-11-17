DROP DATABASE IF EXISTS `dbanime`;
CREATE DATABASE IF NOT EXISTS`dbanime`;
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
	`user_id` INT NOT NULL,
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
('AnimeFan88', 'JP', '2023-01-10'),
('PirateKingBob', 'AM', '2023-05-15'),
('MysticCharlie', 'EU', '2023-07-20'),
('DragonQueenDiana', 'AS', '2023-02-25'),
('AussieOtaku', 'AU', '2023-03-30'),
('SafariFiona', 'AF', '2023-08-15'),
('GundamGeorge', 'EU', '2023-06-10'),
('SpaceCowgirl', 'AM', '2023-04-05'),
('Ninja_Ivy', 'AS', '2023-09-12'),
('KitsuneJack', 'JP', '2023-10-01'),
('Tohru_Adachi_From_P4', 'JP', '2024-01-01');

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
(10, 'Violet Evergarden', 'DR', '2018-01-11', 13, '2023-01-01', '2023-12-31'),
(1, 'Dragon Ball Z', 'AC', '1989-04-26', 291, '2023-01-01', '2023-12-31'),
(2, 'Naruto', 'AD', '2002-10-03', 220, '2023-01-01', '2023-12-31'),
(3, 'Sword Art Online', 'SF', '2012-07-08', 25, '2023-01-01', '2023-12-31'),
(4, 'Jujutsu Kaisen', 'AC', '2020-10-03', 24, '2023-01-01', '2023-12-31'),
(5, 'Hunter x Hunter', 'AD', '2011-10-02', 148, '2023-01-01', '2023-12-31'),
(6, 'Mob Psycho 100', 'CO', '2016-07-12', 25, '2023-01-01', '2023-12-31'),
(7, 'Steins;Gate', 'TH', '2011-04-06', 24, '2023-01-01', '2023-12-31'),
(8, 'Tokyo Ghoul', 'HO', '2014-07-04', 12, '2023-01-01', '2023-12-31'),
(9, 'Black Clover', 'FA', '2017-10-03', 170, '2023-01-01', '2023-12-31'),
(10, 'Fruits Basket', 'SL', '2019-04-06', 63, '2023-01-01', '2023-12-31'),
(7, 'Persona 4 Golden Animation', 'SU','2011-10-07', 26,'2023-01-01', '2023-12-31' );

-- Insert sample data into `views` table
INSERT INTO `views` (`user_id`, `anime_id`, `watched_episode`, `timestamp_watched`) VALUES
(1, 1, 1, '2023-01-11 10:30:00'),
(2, 2, 10, '2023-05-16 12:45:00'),
(2, 2, 11, '2023-05-16 13:45:00'),
(2, 2, 12, '2023-05-16 14:45:00'),
(2, 2, 13, '2023-05-16 15:45:00'),
(2, 2, 14, '2023-05-16 16:45:00'),
(3, 3, 5, '2023-07-21 15:15:00'),
(4, 4, 15, '2023-02-26 16:30:00'),
(5, 5, 20, '2023-04-01 18:00:00'),
(6, 6, 30, '2023-09-01 13:00:00'),
(7, 7, 2, '2023-06-12 09:45:00'),
(8, 8, 5, '2023-04-07 19:30:00'),
(9, 9, 18, '2023-09-15 11:00:00'),
(10, 10, 1, '2023-10-02 17:45:00'),
(1, 11, 1, '2023-01-20 11:15:00'),
(2, 12, 5, '2023-06-15 14:20:00'),
(3, 13, 10, '2023-08-22 12:10:00'),
(4, 14, 8, '2023-03-05 17:00:00'),
(5, 15, 6, '2023-04-10 18:30:00'),
(6, 16, 3, '2023-09-20 09:00:00'),
(7, 17, 15, '2023-06-25 10:30:00'),
(8, 18, 18, '2023-05-01 20:15:00'),
(9, 19, 7, '2023-10-10 13:20:00'),
(10, 20, 2, '2023-10-15 16:45:00'),
(1, 2, 25, '2023-02-10 12:00:00'),
(2, 3, 10, '2023-05-18 15:40:00'),
(3, 4, 30, '2023-09-05 11:30:00'),
(4, 5, 18, '2023-03-10 18:45:00'),
(5, 6, 50, '2023-04-12 19:00:00'),
(6, 7, 15, '2023-10-01 14:30:00'),
(7, 8, 1, '2023-06-15 16:45:00'),
(8, 9, 7, '2023-07-05 20:30:00'),
(9, 10, 12, '2023-11-01 14:20:00'),
(10, 1, 3, '2023-10-12 10:10:00'),
(1, 11, 2, '2023-02-25 13:15:00'),
(2, 12, 12, '2023-06-20 14:55:00'),
(3, 13, 24, '2023-09-10 12:40:00'),
(4, 14, 20, '2023-03-20 17:15:00'),
(5, 15, 13, '2023-05-10 18:55:00');

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
(10, 10, 4, 'Touching and visually stunning.', 13, '2023-10-03 18:15:00'),
(1, 11, 4, 'Classic action scenes.', 10, '2023-01-25 11:00:00'),
(2, 12, 5, 'Beautiful storytelling.', 220, '2023-06-18 15:00:00'),
(3, 13, 5, 'Engaging plot twists.', 24, '2023-08-25 12:20:00'),
(4, 14, 3, 'Solid story but slow pacing.', 10, '2023-03-10 18:00:00'),
(5, 15, 4, 'Intense battles.', 6, '2023-04-15 18:45:00'),
(6, 16, 5, 'Top-tier animation and plot.', 148, '2023-09-20 09:10:00'),
(7, 17, 4, 'Funny and thrilling.', 25, '2023-06-30 10:00:00'),
(8, 18, 5, 'A masterpiece.', 24, '2023-05-10 20:30:00'),
(9, 19, 3, 'Not as good as expected.', 12, '2023-10-11 13:00:00'),
(10, 20, 4, 'Surprisingly good.', 63, '2023-10-20 16:00:00');

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

-- ===================================REPORT PROCEDURES===================================

-- PROCEDURE
-- Usage "CALL SelectBestAnimeOverall()"
-- Creates a table called `best_anime` for overall besst anime based on rating
DELIMITER //
-- DROP PROCEDURE IF EXISTS `SelectBestAnimeOverall`; 
CREATE PROCEDURE `SelectBestAnimeOverall`()
BEGIN
	DROP TABLE IF EXISTS `best_anime`;
	CREATE TABLE IF NOT EXISTS `best_anime` (
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    ); 
    INSERT INTO `best_anime` (title, genre, studio_name, rating)
    SELECT 
        title, 
        genre, 
        studio_name,
        ROUND(AVG(r.rating), 2) AS rating
    FROM 
        animes a
    JOIN 
        studios s ON a.studio_id = s.studio_id
    JOIN 
        ratings r ON a.anime_id = r.anime_id
    GROUP BY 
        a.anime_id
    ORDER BY 
        rating DESC;
END //
DELIMITER ;



-- PROCEDURE
-- Usage: "CALL SelectBestAnimeInMonth(<param_month>)
-- Inserts the top 5 animes based on ratings in the specified month
-- Helper function for SelectBestAnimeMonth
DELIMITER //
-- DROP PROCEDURE IF EXISTS SelectBestAnimeInMonth;
CREATE PROCEDURE `SelectBestAnimeInMonth`(
	IN param_month INT
)
BEGIN
	CREATE TABLE IF NOT EXISTS `best_anime` (
		month_ VARCHAR(9),
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );
    
    INSERT INTO `best_anime` (month_, title, genre, studio_name, rating)
    SELECT 
        MONTHNAME(CONCAT("0000-", param_month, "-00")) AS month_,
        a.title, 
        a.genre, 
        s.studio_name,
        ROUND(AVG(r.rating), 2) AS rating
    FROM 
        animes a
    JOIN 
        studios s ON a.studio_id = s.studio_id
    JOIN 
        ratings r ON a.anime_id = r.anime_id
    WHERE
        MONTH(a.air_date) = param_month
    GROUP BY 
        a.anime_id
    ORDER BY 
        rating DESC
	LIMIT 5;
END //
DELIMITER ;


-- PROCEDURE
-- Usage: "CALL SelectBestAnimeMonths()"
-- Creates a table called `best_anime` for all the months with their top 5 animes
DELIMITER //
-- DROP PROCEDURE IF EXISTS SelectBestAnimeMonths;
CREATE PROCEDURE `SelectBestAnimeMonths`()
BEGIN
	DECLARE i INT DEFAULT 1;
	DROP TABLE IF EXISTS `best_anime`;
	CREATE TABLE IF NOT EXISTS `best_anime` (
		month_ VARCHAR(9),
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );
	
	WHILE i <= 12 DO
		CALL SelectBestAnimeInMonth(i);
		SET i = i + 1;
	END WHILE;
END //
DELIMITER ;



-- PROCEDURE
-- Usage "CALL SelectBestAnimeInSeason(<from_month>, <to_month>, <season_name>)"
-- Inserts the top 5 animes based on rating for the specified season
-- Used as a helper Procedure in SelectBestAnimeSeason
DELIMITER //
-- DROP PROCEDURE IF EXISTS `SelectBestAnimeInSeason`;
CREATE PROCEDURE `SelectBestAnimeInSeason`(
    IN from_month INT,
    IN to_month INT,
    IN season_name VARCHAR(6)
)
BEGIN
	CREATE TABLE IF NOT EXISTS `best_anime` (
        season VARCHAR(6),
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );

    INSERT INTO `best_anime` (season, title, genre, studio_name, rating)
    SELECT 
        season_name AS season,
        a.title, 
        a.genre, 
        s.studio_name,
        ROUND(AVG(r.rating), 2) AS rating
    FROM 
        animes a
    JOIN 
        studios s ON a.studio_id = s.studio_id
    JOIN 
        ratings r ON a.anime_id = r.anime_id
    WHERE
        MONTH(a.air_date) BETWEEN from_month AND to_month
    GROUP BY 
        a.anime_id
    ORDER BY 
        rating DESC
    LIMIT 5;
END //
DELIMITER ;

-- PROCEDURE
-- Usage: "CALL SelectBestAnimeSeason()"
-- Creates a table called `best_anime` with each season containing the top 5 animes aired in that season
DELIMITER //
-- DROP PROCEDURE IF EXISTS `SelectBestAnimeSeason`;
CREATE PROCEDURE `SelectBestAnimeSeason`()
BEGIN
	DROP TABLE IF EXISTS `best_anime`;
	CREATE TABLE IF NOT EXISTS `best_anime` (
        season VARCHAR(6),
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );

    CALL SelectBestAnimeInSeason(1, 3, 'Winter');
    CALL SelectBestAnimeInSeason(4, 6, 'Spring');
    CALL SelectBestAnimeInSeason(7, 9, 'Summer');
    CALL SelectBestAnimeInSeason(10, 12, 'Fall');
END //
DELIMITER ;

-- PROCEDURE
-- Usage: "CALL SelectBestAnimeInMonth(<param_month>)
-- Inserts the top 5 animes based on ratings in the specified year
-- Helper function for SelectBestAnimeYear
DELIMITER //
-- DROP PROCEDURE IF EXISTS `SelectBestAnimeInYear`;
CREATE PROCEDURE `SelectBestAnimeInYear`(
	IN param_year INT
)
BEGIN
    CREATE TABLE IF NOT EXISTS `best_anime`(
        year_ INT,
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );

    INSERT INTO `best_anime` (year_, title, genre, studio_name, rating)
    SELECT 
        param_year AS year_,
        a.title, 
        a.genre, 
        s.studio_name,
        ROUND(AVG(r.rating), 2) AS rating
    FROM 
        animes a
    JOIN 
        studios s ON a.studio_id = s.studio_id
    JOIN 
        ratings r ON a.anime_id = r.anime_id
    WHERE
        YEAR(a.air_date) = param_year
    GROUP BY 
        a.anime_id
    ORDER BY 
        rating DESC
    LIMIT 5;
END //
DELIMITER ;

-- PROCEDURE
-- Usage: "CALL SelectBestAnimeMonths()"
-- Creates a table called `best_anime` for all the years with their top 5 animes
DELIMITER //
-- DROP PROCEDURE IF EXISTS `SelectBestAnimeYear`;
CREATE PROCEDURE `SelectBestAnimeYear`()
BEGIN
	DECLARE min INT;
    DECLARE max INT;

	SELECT MIN(YEAR(a.air_date)) INTO min FROM animes a;
    SELECT MAX(YEAR(a.air_date)) INTO max FROM animes a;
		
	DROP TABLE IF EXISTS `best_anime`;
	CREATE TABLE IF NOT EXISTS `best_anime`(
        year_ INT,
        title VARCHAR(255),
        genre VARCHAR(255),
        studio_name VARCHAR(255),
        rating DECIMAL(5, 2)
    );
    
	WHILE min <= max DO
		CALL SelectBestAnimeInYear(min);
		SET min = min + 1;
	END WHILE;
END //
DELIMITER ;

-- PROCEDURE
-- Usage: "CALL ViewUserProfiel(<param_user_id>)"
-- Returns the given user's name, number of unique anime's watched, and number of ratings made
DELIMITER //
-- DROP PROCEDURE IF EXISTS `ViewUserProfile`;
CREATE PROCEDURE `ViewUserProfile`(
    IN param_user_id INT
)
BEGIN
    SELECT 
        u.user_name,
        COUNT(DISTINCT v.anime_id) AS viewed_anime_count,
        COUNT(v.watched_episode) AS episodes_watched,
		COUNT(r.rating) AS ratings_made
    FROM 
        users u
    LEFT JOIN 
        views v ON u.user_id = v.user_id
	LEFT JOIN 
        ratings r ON u.user_id = r.user_id
    WHERE
        u.user_id = param_user_id
    GROUP BY
        u.user_id;
END //
DELIMITER ;

-- PROCEDURE
-- Usage: "CALL ViewUserGenreAnime(<param_user_id>)"
-- Returns all the genres the user viewed on and their count, 
-- as well as the top anime based on the user's review
DELIMITER //
-- DROP PROCEDURE IF EXISTS `ViewUserGenreAnime`;
CREATE PROCEDURE `ViewUserGenreAnime`(
    IN param_user_id INT
)
BEGIN
	SELECT
		a.genre,
		COUNT(a.anime_id) AS `genre_count`,
		(
			SELECT 
				asub.title            
			FROM
				ratings r
			LEFT JOIN
				animes asub ON r.anime_id = asub.anime_id
			WHERE
				r.user_id = param_user_id
				AND asub.genre = a.genre
			GROUP BY
				asub.anime_id
			ORDER BY
				AVG(r.rating)
			LIMIT 1
		)	AS `top_rated_anime`
	FROM
		users u
	LEFT JOIN
		views v ON u.user_id = v.user_id
	LEFT JOIN
		animes a ON v.anime_id = a.anime_id
	WHERE
		u.user_id = param_user_id
	GROUP BY
		a.genre
	ORDER BY
		`genre_count` DESC;
END //
DELIMITER ;

-- PROCEDURE
-- Usage "CALL ViewBestStudio(<param_year>, <param_genre>)"
-- Returns each studio with their average rating
DELIMITER //
-- DROP PROCEDURE IF EXISTS `ViewBestStudio`;
CREATE PROCEDURE `ViewBestStudio`(
	IN param_year YEAR,
    IN param_genre VARCHAR(2)
)
BEGIN
	SELECT 
		s.studio_name,
		ROUND(AVG(r.rating), 2) AS `studio_rating`
	FROM
		studios s
	JOIN
		animes asub ON s.studio_id = asub.studio_id
	JOIN
		ratings r ON asub.anime_id = r.anime_id
	WHERE
		(param_year IS NULL OR YEAR(asub.air_date) = param_year)
			AND
				(param_genre IS NULL OR asub.genre = param_genre)
	GROUP BY
		s.studio_id
	ORDER BY
		`studio_rating` DESC;
END//
DELIMITER ;

-- ===================================TRANSACTION PROCEDURES===================================


-- PROCEDURE
-- Usage: "CALL WatchEpisode(<param_user_id>, <param_anime_id>)"
-- Inserts a new entry to views of the same user and anime and advanced with 1 episode
DELIMITER //
-- DROP PROCEDURE IF EXISTS WatchAnime;
CREATE PROCEDURE WatchAnime(
	IN param_user_id INT,
    IN param_anime_id INT
)
BEGIN
	DECLARE lastWatched INT;
    
	SELECT MAX(v.watched_episode) 
    INTO 	lastWatched 
    FROM 	views v
    WHERE 	v.anime_id = param_anime_id
    AND 	v.user_id = param_user_id;
    
	INSERT INTO `views` (`user_id`, `anime_id`, `watched_episode`, `timestamp_watched`) VALUES
	(param_user_id, param_anime_id, lastWatched + 1, CURRENT_TIMESTAMP());
END //  
DELIMITER ; 