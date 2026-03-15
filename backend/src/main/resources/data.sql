-- Initial data

INSERT INTO area (id, name) VALUES (1, 'Main Hall');
INSERT INTO area (id, name) VALUES (2, 'Terrace');
INSERT INTO area (id, name) VALUES (3, 'VIP Room');

-- Main Hall
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (1, 4, 'AVAILABLE', 1, true, false, false);
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (2, 2, 'AVAILABLE', 1, true, false, false);
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (3, 3, 'AVAILABLE', 1, false, false, false);
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (4, 3, 'AVAILABLE', 1, false, false, false);

-- Terrace
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (5, 4, 'AVAILABLE', 2, false, false, true);
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (6, 4, 'AVAILABLE', 2, false, false, true);
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (7, 2, 'AVAILABLE', 2, false, false, true);

-- Private Room
INSERT INTO restaurant_table (id, capacity, availability_status, area_id, is_by_the_window, is_private, smoking_allowed) VALUES (8, 8, 'AVAILABLE', 3, false, true, false);

INSERT INTO customer (id, first_name, last_name, phone_number, email) VALUES (1, 'John', 'Doe', '+1555123456', 'john.doe@gmail.com');
INSERT INTO customer (id, first_name, last_name, phone_number, email) VALUES (2, 'Jane', 'Smith', '+1555987654', 'jane.smith@egmail.com');

ALTER TABLE customer ALTER COLUMN id RESTART WITH 3;

INSERT INTO reservation (date_time, number_of_guests, customer_id, table_id) VALUES ('2026-03-16 18:00:00', 2, 1, 2);
