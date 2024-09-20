INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$X9FI60iqNCb/a.DxOFQmM.iXLYBE116lD0Nd.qZkSwryHgv5V6gPC', 'admin@example.com');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);