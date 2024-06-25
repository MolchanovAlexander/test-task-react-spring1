-- db/migration/V15__add_categories.sql

-- Insert statement for 'programming' category
INSERT INTO categories (name, description, is_deleted)
VALUES ('programming', 'for programmers', false);


INSERT INTO categories (name, description, is_deleted)
VALUES ('databases', 'for databases', false);
