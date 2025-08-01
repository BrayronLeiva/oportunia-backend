ALTER SEQUENCE certifications_id_seq RESTART WITH 1;
ALTER TABLE certifications ALTER COLUMN id_certification SET DEFAULT nextval('certifications_id_seq');
INSERT INTO public.certifications (name_certification, provider, file_path, student_id) VALUES
    ('Java SE 11 Developer', 'Oracle', '/files/java11.pdf', 1),
    ('AWS Cloud Practitioner', 'Amazon', '/files/awsccp.pdf', 1),
    ('Kotlin Associate', 'JetBrains', '/files/kotlincert.pdf', 1);
