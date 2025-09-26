-- -------------------- LIFECYCLE PHASES --------------------
INSERT INTO product_lifecycle_phase (id, name) VALUES (11111111-1111-1111-1111-111111111111, 'Design');
INSERT INTO product_lifecycle_phase (id, name) VALUES (22222222-2222-2222-2222-222222222222, 'Prototype');
INSERT INTO product_lifecycle_phase (id, name) VALUES (33333333-3333-3333-3333-333333333333, 'Production');
INSERT INTO product_lifecycle_phase (id, name) VALUES (44444444-4444-4444-4444-444444444444, 'Quality Check');
INSERT INTO product_lifecycle_phase (id, name) VALUES (55555555-5555-5555-5555-555555555555, 'Release');

-- -------------------- CATEGORIES --------------------
INSERT INTO category (id, name, description, parent_id, status) VALUES (aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'Electronics', 'All electronic products', 00000000-0000-0000-0000-000000000000, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, 'Phones', 'Smartphones and mobile phones', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (cccccccc-cccc-cccc-cccc-cccccccccccc, 'Laptops', 'All kinds of laptops', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (dddddddd-dddd-dddd-dddd-dddddddddddd, 'Home Appliances', 'Appliances for home use', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee, 'Refrigerators', 'Cooling appliances', dddddddd-dddd-dddd-dddd-dddddddddddd, 'PUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (ffffffff-ffff-ffff-ffff-ffffffffffff, 'Washing Machines', 'Laundry appliances', dddddddd-dddd-dddd-dddd-dddddddddddd, 'UNPUBLISHED');
INSERT INTO category (id, name, description, parent_id, status) VALUES (99999999-9999-9999-9999-999999999999, 'Audio', 'Speakers, headphones, and audio devices', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'PUBLISHED');

-- -------------------- PRODUCTS --------------------
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (aaaaaaaa-1111-aaaa-1111-aaaaaaaaaaaa, 'Apple', 'iPhone 15', 33333333-3333-3333-3333-333333333333, bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (bbbbbbbb-2222-bbbb-2222-bbbbbbbbbbbb, 'Samsung', 'Galaxy S24', 33333333-3333-3333-3333-333333333333, bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (cccccccc-3333-cccc-3333-cccccccccccc, 'Sony', 'PlayStation 6', 33333333-3333-3333-3333-333333333333, aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (dddddddd-4444-dddd-4444-dddddddddddd, 'Dell', 'XPS 17', 22222222-2222-2222-2222-222222222222, cccccccc-cccc-cccc-cccc-cccccccccccc, 'description');
INSERT INTO product (id, brand, name, phase_id, category_id, description) VALUES (eeeeeeee-5555-eeee-5555-eeeeeeeeeeee, 'HP', 'Spectre x360', 22222222-2222-2222-2222-222222222222, cccccccc-cccc-cccc-cccc-cccccccccccc, 'description');


-- -------------------- PRODUCT VARIANTS --------------------
INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(aaaaaaaa-1111-aaaa-1111-aaaaaaaaaaaa, 11111111-1111-1111-1111-111111111111, 'IP15-128GB-BLK', 'iPhone 15 128GB Black', 'A1111');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(aaaaaaaa-1111-aaaa-1111-aaaaaaaaaaaa, 11111111-1111-1111-1111-111111111112, 'IP15-256GB-WHT', 'iPhone 15 256GB White', 'A1112');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(bbbbbbbb-2222-bbbb-2222-bbbbbbbbbbbb, 22222222-2222-2222-2222-222222222221, 'GS24-128GB-BLU', 'Galaxy S24 128GB Blue', 'S2221');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(bbbbbbbb-2222-bbbb-2222-bbbbbbbbbbbb, 22222222-2222-2222-2222-222222222222, 'GS24-256GB-BLK', 'Galaxy S24 256GB Black', 'S2222');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(cccccccc-3333-cccc-3333-cccccccccccc, 33333333-3333-3333-3333-333333333331, 'PS6-STD', 'PlayStation 6 Standard Edition', 'PS3331');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(cccccccc-3333-cccc-3333-cccccccccccc, 33333333-3333-3333-3333-333333333332, 'PS6-DELUXE', 'PlayStation 6 Deluxe Edition', 'PS3332');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(dddddddd-4444-dddd-4444-dddddddddddd, 44444444-4444-4444-4444-444444444441, 'XPS17-I7', 'Dell XPS 17 i7', 'D4441');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(dddddddd-4444-dddd-4444-dddddddddddd, 44444444-4444-4444-4444-444444444442, 'XPS17-I9', 'Dell XPS 17 i9', 'D4442');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(eeeeeeee-5555-eeee-5555-eeeeeeeeeeee, 55555555-5555-5555-5555-555555555551, 'SX360-I7', 'HP Spectre x360 i7', 'H5551');

INSERT INTO variant (product_id, id, variant_code, description, model_number) VALUES 
(eeeeeeee-5555-eeee-5555-eeeeeeeeeeee, 55555555-5555-5555-5555-555555555552, 'SX360-I9', 'HP Spectre x360 i9', 'H5552');

-- -------------------- PRODUCT HISTORY --------------------
INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, 11111111-aaaa-1111-aaaa-111111111111, 'Apple', 'iPhone 14', 'Apple', 'iPhone 15', 'Old description', 'Updated description', bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb, 22222222-2222-2222-2222-222222222222, 33333333-3333-3333-3333-333333333333, '2025-09-26T12:00:00+0000', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaa0001);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(cccccccc-cccc-cccc-cccc-cccccccccccc, 22222222-cccc-2222-cccc-222222222222, 'Sony', 'PlayStation 5', 'Sony', 'PlayStation 6', 'Old console description', 'Updated console description', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 33333333-3333-3333-3333-333333333333, 33333333-3333-3333-3333-333333333333, '2025-09-26T12:05:00+0000', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaa0002);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(dddddddd-4444-dddd-4444-dddddddddddd, 33333333-4444-3333-4444-333333333333, 'Dell', 'XPS 16', 'Dell', 'XPS 17', 'Old laptop description', 'Updated laptop description', cccccccc-cccc-cccc-cccc-cccccccccccc, cccccccc-cccc-cccc-cccc-cccccccccccc, 22222222-2222-2222-2222-222222222222, 22222222-2222-2222-2222-222222222222, '2025-09-26T12:10:00+0000', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaa0003);

INSERT INTO product_history (product_id, id, old_brand, old_name, new_brand, new_name, old_description, new_description, old_category_id, new_category_id, old_phase_id, new_phase_id, update_timestamp, user_id)
VALUES 
(eeeeeeee-5555-eeee-5555-eeeeeeeeeeee, 44444444-5555-4444-5555-444444444444, 'HP', 'Spectre x350', 'HP', 'Spectre x360', 'Old description', 'Updated description', cccccccc-cccc-cccc-cccc-cccccccccccc, cccccccc-cccc-cccc-cccc-cccccccccccc, 22222222-2222-2222-2222-222222222222, 22222222-2222-2222-2222-222222222222, '2025-09-26T12:15:00+0000', aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaa0004);
