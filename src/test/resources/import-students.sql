ALTER SEQUENCE students_id_seq RESTART WITH 1;
ALTER TABLE students ALTER COLUMN id_student SET DEFAULT nextval('students_id_seq');

INSERT INTO students (name_student, identification, personal_info, experience, rating_student, image_profile, home_latitude, home_longitude, user_id) VALUES
    ('Maikol', '1111', 'Estudiante de Ingeniería de Software, interesado en IA y desarrollo web.', 'Prácticas en desarrollo de software con énfasis en backend. Experiencia en Python y Node.js.', 4.5, '', 1,1,1),
    ('Laura', '1112', 'Ingeniera de Software en formación, apasionada por la programación y el desarrollo de aplicaciones móviles.', 'Poco experiencia, pero ha trabajado en proyectos universitarios de desarrollo de apps móviles.', 3.8,'', 4,4,4),
    ('Andres', '3333', 'Interesado en redes y bases de datos, habilidades en programación orientada a objetos.', 'Sin experiencia laboral, pero ha tomado proyectos de redes en su universidad.', 3.0,'', 5,5,5),
    ('Brayron', '4444', 'Deseo desarrolar aprecio por todo lo que etos temas pero nolo se mucho soy nuevo en algo de programacion', 'Me gustaria aprender mas cosas sobre programacion y redes y base de datos y webs', 2.0,'', 2,2,2);




