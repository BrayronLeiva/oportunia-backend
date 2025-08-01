ALTER SEQUENCE companies_id_seq RESTART WITH 1;
ALTER TABLE companies ALTER COLUMN id_company SET DEFAULT nextval('companies_id_seq');
INSERT INTO public.companies (
    name_company, description, history, mision, vision, corporate_cultur,
    contact_company, rating_company, internship_type, user_id
) VALUES
      (
          'TechNova Solutions',
          'Empresa dedicada a soluciones innovadoras en inteligencia artificial.',
          'Fundada en 2010 por ingenieros apasionados por la tecnologia.',
          'Crear herramientas que mejoren la vida de las personas mediante IA.',
          'Ser lideres mundiales en soluciones inteligentes para el futuro.',
          'Colaborativa, inclusiva y orientada a resultados.',
          12345678,
          4.8,
          'REM',
          3
      ),
      (
          'GreenFuture Corp',
          'Consultora ambiental que trabaja con gobiernos y ONGs.',
          'Inicio como un proyecto estudiantil en 2015 y crecio rapidamente.',
          'Promover practicas sostenibles en sectores publicos y privados.',
          'Un planeta equilibrado y sostenible para futuras generaciones.',
          'Comprometida, etica y eco-amigable.',
          87654321,
          4.5,
          'PRE',
          2
      ),
      (
          'HealthBridge International',
          'Desarrolladora de tecnologia medica enfocada en sistemas de diagnostico remoto.',
          'Establecida en 2012 por un grupo de medicos y desarrolladores. Ha expandido operaciones a mas de 15 paises.',
          'Mejorar el acceso a la salud mediante tecnologia innovadora.',
          'Ser un puente entre la tecnologia y el bienestar global.',
          'Empatica, innovadora y centrada en el paciente.',
          11223344,
          4.7,
          'MIX',
          1
      );
