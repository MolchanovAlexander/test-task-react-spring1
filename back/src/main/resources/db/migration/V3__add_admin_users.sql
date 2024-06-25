INSERT INTO users (email, first_name, last_name, password, shipping_address, is_deleted)
VALUES ('admin@admin.com', 'admin', 'admin',
        '$2a$10$duEFuKqhT34aILFFxJYP0eGjtsiYBBtCJ48floHAXahw7ju4oaEpO',
        'NO address', FALSE),
       ('alice@alice.com', 'alice', 'Buchenwald',
        '$2a$10$duEFuKqhT34aILFFxJYP0eGjtsiYBBtCJ48floHAXahw7ju4oaEpO',
        'yes address', FALSE);
