ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');

INSERT INTO public.users (create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES ('2020-08-30 18:23:52.000000', 'mike@guzmanalan.com', true, 'Maikol',
        'Guzman', '$2a$10$sboEGvf5fx/dyLIhwBdiZuX7DtN0bTSMpt5MEUqiv0HzWV9uFlvf6', false),
       ('2024-04-25 10:00:00', 'sofia.mendez@example.com', true, 'Sofía',
        'Méndez', '$2a$10$QX9Sy7OB29XG01FKr3ALoej3A/cOGTagh0G3G9g6EnQ36xabIbRyO', false),
       ('2024-04-25 10:05:00', 'carlos.rojas@example.com', true, 'Carlos',
        'Rojas', '$2a$10$ETpRCMDg0QTJlKqsSJpkK.jHipoqhqaRet3Q00kV8ehHErIjlIxKC', false),
       ('2024-04-25 10:10:00', 'laura.martinez@example.com', true, 'Laura',
        'Martínez', '$2a$10$kbsEXtOycIybNbEbctZ0zO1rarNr7O274BWPWDVuuL5KaY81XcVPi', false),
       ( '2024-04-25 10:15:00', 'andres.solis@example.com', true, 'Andrés',
        'Solis', '$2a$10$t2Nt7D644m2k3A1P/tyTA.EayG3L5V7DVdORHD.VlAs5oQn9uw6LO', false),
       ( '2024-04-26 11:15:00', 'pepe.mendez@example.com', true, 'Pepe',
        'Mendez', '$2a$10$35Zi970hl0A8wNbben1xhOnl5aNRKp/puhM0v6dlkOgEdMQyh8AFS', false),
       ('2024-04-26 12:00:00', 'mariana.torres@example.com', true, 'Mariana',
        'Torres', '$2a$10$qwLMhpYRAif/wie2bTZiweHwe29Q2yxqRcQuYXBVpPtegNZyzavjO', false),
       ('2024-04-26 12:30:00', 'diego.ramirez@example.com', true, 'Diego',
        'Ramírez', '$2a$10$2dODS3w/MFaaEek2AANLHerwVG.biypZW0FgA5j2DwoLZfE5ciZya', false);

-- mike@guzmanalan.com, password: 1234
-- sofia.mendez@example.com, password: sofia123
-- carlos.rojas@example.com, password: carlos456
-- laura.martinez@example.com, password: laura789
-- andres.solis@example.com, password: andres321
-- pepe.mendez@example.com, password: pepe124
-- mariana.torres@example.com, password: mariana789
-- diego.ramirez@example.com, password: diego456


