ALTER SEQUENCE questions_id_seq RESTART WITH 1;
ALTER TABLE questions ALTER COLUMN id_question SET DEFAULT nextval('questions_id_seq');
INSERT INTO public.questions (question,answer,company_id) VALUES
            ('Do you offer night shifts?', 'Yes, just choose the timezone in preferences.', 1),
            ('Are internships paid?', 'Yes, all our internships include a stipend.', 1),
            ('Can I work remotely?', 'Remote work is available depending on the project.', 2),
            ('Do you provide mentorship?', 'Yes, every intern is assigned a mentor.', 2);

