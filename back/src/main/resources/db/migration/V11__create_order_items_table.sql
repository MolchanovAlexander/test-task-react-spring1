CREATE TABLE order_items
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT                NOT NULL,
    book_id    BIGINT                NOT NULL,
    quantity   INT                   NOT NULL,
    price      DECIMAL(10, 2)        NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT fk_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_book_order_items FOREIGN KEY (book_id) REFERENCES books (id)
);
