INSERT INTO "user" (id, email, enabled, password, username) 
VALUES (1, 'mail@mail.com', 1, '$2a$06$gjqrCNcp8GProaJqmkpTC.MBOylepX4gqpWK5AXT.gcuXF23Y6nLm', 'user');
--PASSWORD=user

INSERT INTO "user" (id, email, enabled, password, username) 
VALUES (2, 'mail2@mail2.com', 1, '$2a$06$KrDLg67vuDRKDU8tcG.60uLklo/YGR9JNS2PFCpNrgIc5Ang8Vpom', 'admin');
--PASSWORD=admin

INSERT INTO  user_roles (user_role_id, role, user_id) VALUES (1, 'ROLE_USER', 1);
INSERT INTO  user_roles (user_role_id, role, user_id) VALUES (2, 'ROLE_ADMIN', 2);