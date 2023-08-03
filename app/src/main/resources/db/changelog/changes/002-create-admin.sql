CREATE TABLE admin (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR,
    created_at timestamp default NOw(),
    updated_at timestamp default NOW() 
)