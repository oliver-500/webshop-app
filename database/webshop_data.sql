
use webshop;
insert into category (name) values ("Nekretnina");
insert into category (name) values ("Vozilo");
insert into category (name) values ("Racunar");



insert into attribute (name, category_id) values ("cpu speed", 3);
insert into attribute (name, category_id) values ("zapremina", 2);
insert into attribute (name, category_id) values ("broj kvadrata", 1);
insert into attribute (name, category_id) values ("broj soba", 1);


insert into user (city, email, active, name, last_name, username, password_hash, type) values ("bl", "olivereric529@outlook.com", true, "ej1", "ej prezime", "user1", "$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa", "transactor"); 
insert into user (city, email, active, name, last_name, username, password_hash, type) values ("bl", "ee@gmail.com", true, "ej1", "ej prezime", "ej5", "$2a$10$RD9qXaimn8XTFtm2nr7LXejOxGwVq.UrFwiwbvfZQuDZZMves52qa", "transactor"); 


insert into user (city, email, active, name, last_name, username, password_hash, type) values ("bl", "ee@gmail.com", true, "ej1", "ej prezime", "operator", SHA2('operator', 256), "operator");

insert into user (city, email, active, name, last_name, username, password_hash, type) values ("bl", "ee@gmail.com", true, "admin", "admin", "admin", SHA2('admin', 256), "admin");

insert into product (title, description, location, used, price, contact, sold, id_seller) values ("audi a4", "vozilo obicno", "bl", true, 30000.0, "+5353533343", false, 1);
insert into product (title, description, location, used, price, contact, sold, id_seller) values ("stan bl", "voziloo obicno", "bl", true, 70000.0, "+533253533343", false, 1);
insert into product (title, description, location, used, price, contact, sold, id_seller) values ("i7 razer ", "racunar obicno", "bl", true, 700.0, "+122533343", false, 2);

insert into product_has_category (product_id, category_id) values (1, 2);
insert into product_has_category (product_id, category_id) values (2, 1);
insert into product_has_category (product_id, category_id) values (3, 3);

insert into product_has_attribute (product_id_product, attribute_id_attribute, value) values (1, 2, "2000ccm");
insert into product_has_attribute (product_id_product, attribute_id_attribute, value) values (2, 3, "88");
insert into product_has_attribute (product_id_product, attribute_id_attribute, value) values (2, 4, "7");
insert into product_has_attribute (product_id_product, attribute_id_attribute, value) values (3, 1, "3.0ghz");

insert into comment(product_id, mcontent) values (1, "sve super");
insert into comment(product_id, mcontent) values (1, "sve top");
insert into comment(product_id, mcontent) values (1, "ne moze bolje");
