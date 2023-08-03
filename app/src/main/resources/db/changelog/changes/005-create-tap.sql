CREATE TABLE tap (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR,
    flow_per_second FLOAT,
    created_at timestamp default NOw(),
    updated_at timestamp default NOW() 
)