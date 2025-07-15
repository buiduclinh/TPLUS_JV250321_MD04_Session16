CREATE DATABASE `book_data_base`;
USE `book_data_base`;

CREATE TABLE `Book_Type`
(
    Type_id          INT AUTO_INCREMENT PRIMARY KEY, -- Mã loại sách
    Type_name        VARCHAR(100) NOT NULL UNIQUE,   -- Tên loại sách
    Type_description TEXT         NOT NULL,-- Mô tả loại sách
    Type_status      BIT default (1)                 -- Trạng thái loại sách
);

CREATE TABLE `Book`
(
    Book_id     CHAR(5) PRIMARY KEY,                            -- Mã sách
    Book_name   VARCHAR(100) NOT NULL UNIQUE,                   -- Tên sách
    Book_title  VARCHAR(200) NOT NULL,                          -- Tiêu đề sách
    Book_pages  INT          NOT NULL CHECK (Book_pages > 0),   -- Số trang sách
    Book_Author VARCHAR(200) NOT NULL,                          -- Book_pages
    Book_price  FLOAT        NOT NULL CHECK ( Book_price > 0 ), -- Giá sách
    Type_id     INT          NOT NULL,                          -- Mã loại sách
    Book_status BIT DEFAULT (1)                                 -- Trạng thái sách
);

ALTER TABLE `Book`
    ADD CONSTRAINT `Book_Type_id`
        FOREIGN KEY (`Type_id`)
            REFERENCES `Book` (Type_id)
            ON DELETE CASCADE;


-- PROCEDURE BOOK_TYPE

DELIMITER  $$ -- Lấy tất cả thông tin loại sách
CREATE PROCEDURE `view_all_information`
BEGIN
    SELECT *
    FROM `Book` AS b
             INNER JOIN Book_Type AS bt
                        ON b.Type_id = bt.Type_id;
END
$$
DELIMITER ;

CALL view_all_information();

DELIMITER $$ -- Kiểm tra sự tồn tại của tên loại sách
CREATE PROCEDURE `book_name_is_exist`(
    check_book_name VARCHAR(100)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM `Book_Type` AS b
    WHERE Type_name = check_book_name;
END $$
DELIMITER ;


DELIMITER $$ -- Thêm mới loại sách
CREATE PROCEDURE `add_book_type`(
    IN add_Type_name VARCHAR(100),
    add_Type_description TEXT
)
BEGIN
    INSERT INTO `Book_Type`
        (`Type_name`, `Type_description`)
    VALUES (`add_Type_name`, `add_Type_description`);
END
$$
DELIMITER ;


DELIMITER $$ -- Lấy thông tin loại sách theo mã loại sách
CREATE PROCEDURE `view_information_by_Book_Type_Type_id`(
    IN view_Book_Type_Type_id INT
)
BEGIN
    SELECT *
    FROM Book_Type
    WHERE view_Book_Type_Type_id = Type_id;
END $$
DELIMITER ;


DELIMITER $$ -- Cập nhật loại sách
CREATE PROCEDURE `update_book_type`(
    IN update_book_type_id INT,
    update_book_type_name VARCHAR(100),
    update_Type_description TEXT
)
BEGIN
    UPDATE `Book_Type` AS bt
        INNER JOIN Book AS b
        ON b.Type_id = bt.Type_id
    SET Type_name = update_book_type_name
        AND Type_description = update_Type_description
    WHERE Type_id = update_book_type_id;
END $$
DELIMITER ;


DELIMITER $$ -- Xóa loại sách (Xóa khi loại sách chưa chứa sách, khi đã chứa
-- sách chỉ cập nhật trạng thái loại sách là 0)
CREATE PROCEDURE `delete_book_type`(
    delete_book_type_id INT
)
BEGIN
    DECLARE Type_status INT default (0);
    SELECT COUNT(*)
    INTO Type_status
    FROM Book_Type
    WHERE Type_id = delete_book_type_id;
    IF Type_status = 0
    THEN
        DELETE
        FROM `Book_Type`
        WHERE Type_id = delete_book_type_id;
    ELSE
        UPDATE `Book_Type`
        SET Type_status = 0
        WHERE Type_id = delete_book_type_id;
    END IF;
END;
$$


-- PROCEDURE BOOK

DELIMITER $$ -- Lấy tất cả thông tin sách sắp xếp theo giá sách tăng dần (gồm cả
-- mã loại sách và tên loại sách)
CREATE PROCEDURE `view_book_information`
BEGIN
    SELECT *, bt.`Type_id`, bt.`Type_name`
    FROM Book AS b
             INNER JOIN Book_Type AS bt
                        ON b.Type_id = bt.Type_id
    ORDER BY b.Book_price
        DESC;
END $$
DELIMITER ;

CALL view_book_information();

DELIMITER $$ -- Kiểm tra sự tồn tại của tên sách
CREATE PROCEDURE `check_book_information_is_exist`(
    IN check_book_name VARCHAR(5)
)
BEGIN
    SELECT COUNT(*)
    FROM Book
    WHERE Book_name = check_book_name;
END $$
DELIMITER ;


DELIMITER $$ -- Thêm mới sách
CREATE PROCEDURE `add_book`(
    IN
        add_book_id VARCHAR(5),
    add_book_name VARCHAR(100),
    add_book_title VARCHAR(200),
    add_book_page INT,
    add_book_author VARCHAR(200),
    add_book_price FLOAT,
    add_type_id INT,
    add_book_status BIT
)
BEGIN
    INSERT INTO `Book`
    (Book_id, Book_name, Book_title, Book_pages, Book_Author, Book_price, Type_id, Book_status)
    VALUES (add_book_id, add_book_name, add_book_title, add_book_page, add_book_author, add_book_price, add_type_id,
            add_book_status);
END
$$
DELIMITER ;


DELIMITER $$ -- Lấy thông tin sách theo mã sách
CREATE PROCEDURE `view_book_information_by_book_id`(
    IN input_book_id VARCHAR(5)
)
BEGIN
    SELECT *
    FROM Book
    WHERE Book_id = input_book_id;
END $$
DELIMITER ;


DELIMITER $$ -- Cập nhật sách
CREATE PROCEDURE `update_book`(
    IN
        update_book_id VARCHAR(5),
    update_book_name VARCHAR(100),
    update_book_title VARCHAR(200),
    update_book_page INT,
    update_book_author VARCHAR(200),
    update_book_price FLOAT,
    update_type_id INT,
    update_book_status BIT
)
BEGIN
    UPDATE `Book`
    SET Book_name   = update_book_name,
        Book_title  = update_book_title,
        Book_pages  = update_book_page,
        Book_Author = update_book_author,
        Book_price  = update_book_price,
        Type_id     = update_type_id,
        Book_status = update_book_status
    WHERE Book_id = update_book_id;
END $$
DELIMITER ;


DELIMITER $$ -- Xóa sách
CREATE PROCEDURE `delete_book`(
    IN
        delete_book_id VARCHAR(5)
)
BEGIN
    DELETE
    FROM Book
    WHERE Book_id = delete_book_id;
END $$
DELIMITER ;


DELIMITER $$ -- Tìm kiếm sách theo tên sách (tìm tương đối)
CREATE PROCEDURE `find_book_name`(
    IN
        find_book_name VARCHAR(100)
)
BEGIN
    DECLARE find_book_name VARCHAR(100);
    SET find_book_name = concat(concat('%', find_book_name), '%');
    SELECT *
    FROM Book
    WHERE Book_name Like find_book_name;
END $$
DELIMITER ;


DELIMITER $$ -- Thống kê số lượng sách theo tác giả
CREATE PROCEDURE total_book_by_author()
BEGIN
    SELECT Book_Author,      -- Tên tác giả
           COUNT(*) AS total -- Số lượng sách của từng tác giả
    FROM Book
    GROUP BY Book_Author;
END $$
DELIMITER ;


