ALTER TABLE tap
    ADD promoter_id int,
    FOREIGN KEY(promoter_id) REFERENCES promoter(id);