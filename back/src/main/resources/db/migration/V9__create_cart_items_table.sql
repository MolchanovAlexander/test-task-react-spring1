CREATE TABLE cart_items
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    shopping_cart_id BIGINT NOT NULL,
    book_id          BIGINT NOT NULL,
    quantity         INT    NOT NULL,
    CONSTRAINT fk_cart_item_shopping_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_carts (id),
    CONSTRAINT fk_cart_item_books FOREIGN KEY (book_id) REFERENCES books (id)
);
