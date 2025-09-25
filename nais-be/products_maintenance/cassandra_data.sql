-- -------------------- LIFECYCLE PHASES --------------------
INSERT INTO product_lifecycle_phase (id, name) VALUES (11111111-1111-1111-1111-111111111111, 'Design');
INSERT INTO product_lifecycle_phase (id, name) VALUES (22222222-2222-2222-2222-222222222222, 'Prototype');
INSERT INTO product_lifecycle_phase (id, name) VALUES (33333333-3333-3333-3333-333333333333, 'Production');
INSERT INTO product_lifecycle_phase (id, name) VALUES (44444444-4444-4444-4444-444444444444, 'Quality Check');
INSERT INTO product_lifecycle_phase (id, name) VALUES (55555555-5555-5555-5555-555555555555, 'Release');

-- -------------------- CATEGORIES --------------------
INSERT INTO category (id, name, description, parent_id, status) VALUES (aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa, 'Electronics', 'All electronic products', null, 'PUBLISHED');
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
