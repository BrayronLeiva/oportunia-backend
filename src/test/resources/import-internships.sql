ALTER SEQUENCE internships_id_seq RESTART WITH 1;
ALTER TABLE internships ALTER COLUMN id_internship SET DEFAULT nextval('internships_id_seq');
INSERT INTO public.internships(details) VALUES ('Internship of Linux Server'),('Front end development'),('Cloud Development');