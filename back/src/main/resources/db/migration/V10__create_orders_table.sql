CREATE TABLE orders
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT                NOT NULL,
    status           ENUM('COMPLETED', 'PENDING', 'DELIVERED') NOT NULL,
    total            DECIMAL(10, 2)        NOT NULL,
    order_date       DATETIME              NOT NULL,
    shipping_address VARCHAR(255)          NOT NULL,
    is_deleted       BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users (id)
);
