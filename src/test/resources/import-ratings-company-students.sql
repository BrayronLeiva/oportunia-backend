ALTER SEQUENCE ratings_id_seq RESTART WITH 1;
ALTER TABLE ratings_companies_students ALTER COLUMN id_rating SET DEFAULT nextval('ratings_id_seq');
INSERT INTO public.ratings_companies_students(rating, type, comment, student_id, company_id) VALUES
(4.5, 'STU', 'Excellent collaboration and support', 1, 1),
(3.8, 'COM', 'Overall good experience, but needs improvement in communication', 2, 1),
(5.0, 'COM', 'Amazing mentorship and learning environment', 3, 2);