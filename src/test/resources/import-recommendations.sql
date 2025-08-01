ALTER SEQUENCE recommendations_id_seq RESTART WITH 1;
ALTER TABLE recommendations ALTER COLUMN id_recommendation SET DEFAULT nextval('recommendations_id_seq');
INSERT INTO recommendations (details, student_id, company_id) VALUES
            ('El estudiante mostró un alto nivel de responsabilidad y compromiso.', 1, 2),
            ('La empresa brindó un ambiente ideal para el aprendizaje profesional.', 2, 1),
            ('Excelente actitud, proactividad y habilidades tecnicas destacables.', 3, 2);
