CREATE TABLE serving (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    start_time datetime,
    end_time datetime,
    beer_id int,
    tap_id int,
    promoter_id int,
    attendee_id int,
    flow_per_second FLOAT,
    price_per_litre FLOAT,
    total FLOAT,
    created_at timestamp default NOw(),
    updated_at timestamp default NOW(),
    FOREIGN KEY (tap_id) REFERENCES tap(id),
    FOREIGN KEY (beer_id) REFERENCES beer(id),
    FOREIGN KEY (promoter_id) REFERENCES promoter(id),
    FOREIGN KEY (attendee_id) REFERENCES attendee(id)
)