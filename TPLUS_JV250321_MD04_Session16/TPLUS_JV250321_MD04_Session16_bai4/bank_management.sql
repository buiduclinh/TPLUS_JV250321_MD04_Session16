CREATE DATABASE bank_management;
USE bank_management;

CREATE TABLE accounts
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0
);


DELIMITER $$
CREATE PROCEDURE transfer_funds(
    IN from_account_id INT,
    IN to_account_id INT,
    IN amount DECIMAL(15, 2)
)
BEGIN
    DECLARE from_balance DECIMAL(15, 2);

    START TRANSACTION;

    -- Lấy số dư tài khoản nguồn
    SELECT balance
    INTO from_balance
    FROM accounts
    WHERE id = from_account_id
        FOR
    UPDATE;

    -- Kiểm tra số dư
    IF from_balance < amount THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Số dư không đủ';
    ELSE
        -- Trừ tiền tài khoản nguồn
        UPDATE accounts
        SET balance = balance - amount
        WHERE id = from_account_id;

        -- Cộng tiền tài khoản đích
        UPDATE accounts
        SET balance = balance + amount
        WHERE id = to_account_id;
    END IF;
    COMMIT;
END $$
DELIMITER ;