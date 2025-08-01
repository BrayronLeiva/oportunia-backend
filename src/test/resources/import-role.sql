ALTER SEQUENCE privilege_id_seq RESTART WITH 1;
ALTER TABLE privilege ALTER COLUMN id_privilege SET DEFAULT nextval('privilege_id_seq');

INSERT INTO public.privilege (name) VALUES ('WRITE_PRIVILEGE');
INSERT INTO public.privilege (name) VALUES ('READ_PRIVILEGE');

ALTER SEQUENCE role_id_seq RESTART WITH 1;
ALTER TABLE role ALTER COLUMN id_role SET DEFAULT nextval('role_id_seq');

INSERT INTO public.role (name) VALUES ('STU');
INSERT INTO public.role (name) VALUES ('COM');

INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 1);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 2);