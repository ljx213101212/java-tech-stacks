-- Insert users
INSERT INTO users (email, password) VALUES ('user@example.com', '$2a$12$8psvSgCS3QR8Oygh6ZPsvOul/Yhm3JjoEFp9q8k29da6zsNdiaTou');
INSERT INTO users (email, password) VALUES ('admin@example.com', '$2a$12$mT4nGnRvaD.EKWc/VS50BOhmQe.Ku.karmiKm6wjwSuxjCnHtOvPm');

-- Insert authorities
INSERT INTO authorities (authority) VALUES ('VIEW_INFO');
INSERT INTO authorities (authority) VALUES ('VIEW_ADMIN');

-- Link users to authorities
INSERT INTO user_authorities (user_id, authority_id) VALUES (1, 1); -- User 1 has VIEW_INFO
INSERT INTO user_authorities (user_id, authority_id) VALUES (2, 2); -- User 2 has VIEW_ADMIN
