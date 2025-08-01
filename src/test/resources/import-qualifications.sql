ALTER SEQUENCE qualifications_id_seq RESTART WITH 1;
ALTER TABLE qualifications ALTER COLUMN id_qualification SET DEFAULT nextval('qualifications_id_seq');
INSERT INTO public.qualifications (name_qualification) VALUES
        ('English'),
        ('Java'),
        ('Leading'),
        ('Spring Boot');