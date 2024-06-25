CREATE TABLE shopping_carts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT                NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT fk_shopping_cart_user FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO shopping_carts (user_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@admin.com')),
       ((SELECT id FROM users WHERE email = 'alice@alice.com'));
