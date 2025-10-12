-- -------------------- ID COUNTER --------------------
DROP TABLE IF EXISTS id_counter;
CREATE TABLE IF NOT EXISTS id_counter (
    entity_name text PRIMARY KEY,
    last_id bigint
);

INSERT INTO id_counter (entity_name, last_id) VALUES ('category', 7);
INSERT INTO id_counter (entity_name, last_id) VALUES ('product_lifecycle_phase', 5);
INSERT INTO id_counter (entity_name, last_id) VALUES ('product', 5);
INSERT INTO id_counter (entity_name, last_id) VALUES ('variant', 10);
INSERT INTO id_counter (entity_name, last_id) VALUES ('product_history', 5);

-- -------------------- LIFECYCLE PHASES --------------------
INSERT INTO product_lifecycle_phase (id, name) VALUES (1, 'Design');
INSERT INTO product_lifecycle_phase (id, name) VALUES (2, 'Prototype');
INSERT INTO product_lifecycle_phase (id, name) VALUES (3, 'Production');
INSERT INTO product_lifecycle_phase (id, name) VALUES (4, 'Quality Check');
INSERT INTO product_lifecycle_phase (id, name) VALUES (5, 'Release');

-- -------------------- CATEGORIES --------------------
INSERT INTO category (id, name, description, parent_id, status) VALUES (1, 'Electronics', 'All electronic products', 0, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (2, 'Phones', 'Smartphones and mobile phones', 1, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (3, 'Laptops', 'All kinds of laptops', 1, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (4, 'Home Appliances', 'Appliances for home use', 1, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (5, 'Refrigerators', 'Cooling appliances', 4, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (6, 'Washing Machines', 'Laundry appliances', 4, 'UNPUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (7, 'Audio', 'Speakers, headphones, and audio devices', 1, 'PUBLISHED');

-- -------------------- PRODUCTS --------------------
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (1, 'Apple', 'iPhone 15', 5, 2, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (2, 'Samsung', 'Galaxy S24', 3, 2, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (3, 'Sony', 'PlayStation 6', 3, 1, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (4, 'Dell', 'XPS 17', 2, 3, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (5, 'HP', 'Spectre x360', 2, 3, 'description');


-- -------------------- PRODUCT VARIANTS --------------------
INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(1, 1, 'IP15-128GB-BLK', 'iPhone 15 128GB Black', 'A1111');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(1, 2, 'IP15-256GB-WHT', 'iPhone 15 256GB White', 'A1112');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(2, 3, 'GS24-128GB-BLU', 'Galaxy S24 128GB Blue', 'S2221');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(2, 4, 'GS24-256GB-BLK', 'Galaxy S24 256GB Black', 'S2222');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(3, 5, 'PS6-STD', 'PlayStation 6 Standard Edition', 'PS3331');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(3, 6, 'PS6-DELUXE', 'PlayStation 6 Deluxe Edition', 'PS3332');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(4, 7, 'XPS17-I7', 'Dell XPS 17 i7', 'D4441');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(4, 8, 'XPS17-I9', 'Dell XPS 17 i9', 'D4442');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(5, 9, 'SX360-I7', 'HP Spectre x360 i7', 'H5551');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(5, 10, 'SX360-I9', 'HP Spectre x360 i9', 'H5552');

-- -------------------- PRODUCT HISTORY --------------------
INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(1, 1, 'Apple', 'iPhone 14', 'Apple', 'iPhone 15', 'Old description', 'Updated description', 2, 2, 2, 3, '2025-09-26T12:00:00+0000', 1);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(3, 2, 'Sony', 'PlayStation 5', 'Sony', 'PlayStation 6', 'Old console description', 'Updated console description', 1, 1, 3, 3, '2025-09-26T12:05:00+0000', 2);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(4, 3, 'Dell', 'XPS 16', 'Dell', 'XPS 17', 'Old laptop description', 'Updated laptop description', 3, 3, 2, 2, '2025-09-26T12:10:00+0000', 3);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(5, 4, 'HP', 'Spectre x350', 'HP', 'Spectre x360', 'Old description', 'Updated description', 3, 3, 2, 2, '2025-09-26T12:15:00+0000', 4);
