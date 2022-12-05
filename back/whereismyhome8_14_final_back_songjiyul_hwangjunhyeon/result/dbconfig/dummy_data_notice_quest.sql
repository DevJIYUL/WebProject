-- ----------------------------------------------
-- notice dummy data
-- ----------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS noticeInsert$$
 
CREATE PROCEDURE noticeInsert()
BEGIN
    DECLARE j INT DEFAULT 1;
        
    WHILE j <= 124 DO
        INSERT INTO notice(title , content)
          VALUES(concat('제목',j,j,j), concat('내용',j,j,j,j,j,j,j,j,j,j,j,j,j,j,j,j));
        SET j = j + 1;
    END WHILE;
END$$
DELIMITER $$


CALL noticeInsert;


-- ----------------------------------------------
-- quest dummy data
-- ----------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS questInsert$$
 
CREATE PROCEDURE questInsert()
BEGIN
    DECLARE i INT DEFAULT 1;
        
    WHILE i <= 214 DO
        INSERT INTO quest(title , content, privateFlag, username)
          VALUES(concat('제목',i), concat('내용',i), false, 'ssafy');
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL questInsert;