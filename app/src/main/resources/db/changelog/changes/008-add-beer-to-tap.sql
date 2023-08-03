ALTER TABLE tap
    ADD beer_id int,
    FOREIGN KEY(beer_id) REFERENCES beer(id);