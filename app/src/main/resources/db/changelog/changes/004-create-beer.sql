CREATE TABLE beer (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR,
    price_per_litre FLOAT,
    created_at timestamp default NOw(),
    updated_at timestamp default NOW() 
)