CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    coffee_id serial,
    quantity int,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);