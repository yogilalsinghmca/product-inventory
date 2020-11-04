-- Categories
insert into category (id, name, parentid) values (1, 'Electronics', null);
insert into category (id, name, parentid) values (2, 'Clothing', null);
insert into category (id, name, parentid) values (3, 'Beauty & Personal Care', null);
-- Subcategories for 'Electronics'
insert into category (id, name, parentid) values (4, 'Audio & Video', 1);
insert into category (id, name, parentid) values (5, 'Camera', 1);
insert into category (id, name, parentid) values (6, 'Computers and TV', 1);
-- Subcategories for 'Clothing'
insert into category (id, name, parentid) values (7, 'Mens Clothing', 2);
insert into category (id, name, parentid) values (8, 'Womens Clothing', 2);
insert into category (id, name, parentid) values (9, 'Babys Clothing', 2);
-- Subcategories for 'Beauty & Personal Care'
insert into category (id, name, parentid) values (10, 'Mens Beauty & Personal Care', 3);
insert into category (id, name, parentid) values (11, 'Womens beauty and personal care', 3);
insert into category (id, name, parentid) values (12, 'Babys beauty and personal care', 3);

-- Products for Category 'Electronics > Audio & Video'
insert into product (id, name, price) values (1, 'TV 4K (32 GB)', 223.22);
insert into product (id, name, price) values (2, 'DVD CD', 49.22);
insert into product (id, name, price) values (3, 'FM Radio', 299.00);
insert into product_category (productid, categoryid) values (1, 4);
insert into product_category (productid, categoryid) values (2, 4);
insert into product_category (productid, categoryid) values (3, 4);
-- Products for Category 'Electronics > Camera'
insert into product (id, name, price) values (4, 'CAMERA1', 379.95);
insert into product (id, name, price) values (5, 'CAMERA2 ', 49.22);
insert into product (id, name, price) values (6, 'CAMERA3', 42.99);
insert into product_category (productid, categoryid) values (4, 5);
insert into product_category (productid, categoryid) values (5, 5);
insert into product_category (productid, categoryid) values (6, 5);
-- Products for Category 'Electronics > Computers and TV'
insert into product (id, name, price) values (7, 'LAPTOP1', 1052.49);
insert into product (id, name, price) values (8, 'LAPTOP1', 479.99);
insert into product (id, name, price) values (9, 'LAPTOP1', 239.00);
insert into product_category (productid, categoryid) values (7, 6);
insert into product_category (productid, categoryid) values (8, 6);
insert into product_category (productid, categoryid) values (9, 6);
-- Products for Category 'Clothing > Mens Clothing'
insert into product (id, name, price) values (10, 'Shirt', 26.99);
insert into product (id, name, price) values (11, 'PANTS', 11.00);
insert into product (id, name, price) values (12, 'T-Shirt', 12.28);
insert into product_category (productid, categoryid) values (10, 7);
insert into product_category (productid, categoryid) values (11, 7);
insert into product_category (productid, categoryid) values (12, 7);
-- Products for Category 'Clothing> Womens clothing'
insert into product (id, name, price) values (13, 'Kurta', 24.15);
insert into product (id, name, price) values (14, 'ladies jeans', 48.94);
insert into product (id, name, price) values (15, 'Saari', 82.94);
insert into product_category (productid, categoryid) values (13, 8);
insert into product_category (productid, categoryid) values (14, 8);
insert into product_category (productid, categoryid) values (15, 8);
-- Products for Category 'Clothing > babys clothing'
insert into product (id, name, price) values (16, 'baby t-shirt', 103.99);
insert into product (id, name, price) values (17, 'baby pant', 24.95);
insert into product (id, name, price) values (18, 'baby jeans', 20.49);
insert into product_category (productid, categoryid) values (16, 9);
insert into product_category (productid, categoryid) values (17, 9);
insert into product_category (productid, categoryid) values (18, 9);
-- Products for Category 'Beauty & Personal Care > Mens Beauty & Personal Care'
insert into product (id, name, price) values (19, 'Saving cream', 24.99);
insert into product (id, name, price) values (20, 'mens cold cream', 57.79);
insert into product (id, name, price) values (21, 'Mens powder', 19.98);
insert into product_category (productid, categoryid) values (19, 10);
insert into product_category (productid, categoryid) values (20, 10);
insert into product_category (productid, categoryid) values (21, 10);
-- Products for Category 'Beauty & Personal Care > Womens Beauty & Personal Care'
insert into product (id, name, price) values (22, ' Womens face cream', 37.99);
insert into product (id, name, price) values (23, 'Kajal', 7.49);
insert into product (id, name, price) values (24, 'womens powder', 13.99);
insert into product_category (productid, categoryid) values (22, 11);
insert into product_category (productid, categoryid) values (23, 11);
insert into product_category (productid, categoryid) values (24, 11);
-- Products for Category 'Beauty & Personal Care > Babys Beauty & Personal Care'
insert into product (id, name, price) values (25, 'baby powder', 11.63);
insert into product (id, name, price) values (26, 'baby cream', 12.14);
insert into product (id, name, price) values (27, 'baby kajal', 10.37);
insert into product_category (productid, categoryid) values (23, 12);
insert into product_category (productid, categoryid) values (24, 12);
insert into product_category (productid, categoryid) values (25, 12);
--more generic product---
insert into product (id, name, price) values (28, 'Generic beauty cream', 10.37);
create sequence if not exists hibernate_sequence start with 29;




