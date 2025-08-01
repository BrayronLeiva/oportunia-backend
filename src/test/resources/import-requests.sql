ALTER SEQUENCE requests_id_seq RESTART WITH 1;
ALTER TABLE requests ALTER COLUMN id_request SET DEFAULT nextval('requests_id_seq');
INSERT INTO requests (state, student_id, internship_location_id) VALUES
    (true, 1, 1),
    (true, 2, 1),
    (true, 3, 2),
    (true, 4, 1);