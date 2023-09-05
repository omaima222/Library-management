
-- Structure de la table `book`

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author_name` varchar(255) NOT NULL,
  `quantity` bigint(20) NOT NULL,
  `ISBN` bigint(20) NOT NULL,
   PRIMARY KEY (`id`)
);

-- Structure de la table `user`

CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `cin` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Structure de la table `borrowing_list`

CREATE TABLE `borrowing_list` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `quantity` bigint(20) NOT NULL,
    `borrowing_date` date NOT NULL,
    `return_date` date NOT NULL,
    `book_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `book_id` (`book_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
);

-- Structure de la table `missing_books_list`

CREATE TABLE `missing_books_list` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `book_id` bigint(20) NOT NULL,
    `quantity` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `book_id` (`book_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
);


DELIMITER //

CREATE TRIGGER borrow
    AFTER INSERT ON borrowing_list
    FOR EACH ROW
BEGIN
    UPDATE book
    SET quantity = quantity - NEW.quantity
    WHERE id = NEW.book_id;
END;

//
DELIMITER ;
