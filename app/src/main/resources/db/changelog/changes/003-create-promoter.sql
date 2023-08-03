CREATE TABLE promoter (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR,
    active BOOLEAN,
    created_at timestamp default NOw(),
    updated_at timestamp default NOW()
)