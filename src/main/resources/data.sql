
/*

INSERT INTO genre (genre_name) VALUES
                                                ('Ficción'),
                                                ('Ciencia Ficción'),
                                                ('Fantasía'),
                                                ('Terror'),
                                                ('Romance'),
                                                ('Misterio'),
                                                ('Biografía'),
                                                ('Unknown');

-- Tabla user
INSERT INTO user (username, password, mail) VALUES
                                                ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrYV5XxZHqrB6Q39AbTkQx4Zx.9NQqO', 'admin@bookstore.com'),
                                                ('user1', '$2a$10$N9qo8uLOickgx2ZMRZ9NQqO', 'user1@bookstore.com');

-- Tabla book (depende de genre)
INSERT INTO book (title, author, description, price, image, quantity, genre_id) VALUES
                                                                                    ('Cien años de soledad', 'Gabriel García Márquez', 'Una obra maestra del realismo mágico', 19.99, 'cien-anios.jpg', 50, 1),
                                                                                    ('Dune', 'Frank Herbert', 'Clásico de la ciencia ficción', 15.50, 'dune.jpg', 30, 2),
                                                                                    ('El Hobbit', 'J.R.R. Tolkien', 'Aventuras en la Tierra Media', 12.75, 'hobbit.jpg', 45, 3);

-- Tabla cart (depende de user)
INSERT INTO cart (user_id, total_price) VALUES
                                            (1, 0.0),
                                            (2, 0.0);

-- Tabla item_cart (depende de cart y book)
INSERT INTO item_cart (quantity, book_price, cart_id, book_id) VALUES
                                                                   (2, 19.99, 1, 1),
                                                                   (1, 15.50, 1, 2),
                                                                   (3, 12.75, 2, 3);
*/