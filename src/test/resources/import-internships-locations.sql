ALTER SEQUENCE internshiplocation_id_seq RESTART WITH 1;
ALTER TABLE internships_locations ALTER COLUMN id_internship_location SET DEFAULT nextval('internshiplocation_id_seq');
INSERT INTO public.internships_locations(location_id, internship_id) VALUES
(1, 1),
(2, 2),
(3, 3);