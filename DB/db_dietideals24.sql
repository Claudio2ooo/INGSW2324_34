CREATE TYPE category_ENUM AS ENUM('ARREDAMENTO', 'ELETTRONICA', 'INFORMATICA', 'MUSICA', 'SPORT');
CREATE TYPE state_ENUM AS ENUM('VINTA', 'PERSA', 'FALLITA');

CREATE TABLE dieti_user (
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 214793647
        CACHE 1
        ) PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    links TEXT,
    geographical_area VARCHAR(10), --forse da cambiare con ENUM se troviamo una libreria
    CONSTRAINT unique_email UNIQUE(email)
);

CREATE TABLE english_auction(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 214793647
        CACHE 1
        ) PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    category category_ENUM,
    image_url TEXT,
    starting_price BIGINT NOT NULL,
    current_price BIGINT,
    timer BIGINT,
    increase_amount BIGINT NOT NULL DEFAULT 10,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES dieti_user(id) ON DELETE CASCADE
);

CREATE TABLE reverse_auction(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 214793647
        CACHE 1
        ) PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    category category_ENUM,
    image_url TEXT,
    starting_price BIGINT NOT NULL,
    current_price BIGINT,
    minimum_price BIGINT NOT NULL,
    decrease_amount BIGINT NOT NULL,
    timer BIGINT,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES dieti_user(id) ON DELETE CASCADE
);

CREATE TABLE offer(
    offer_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 214793647
        CACHE 1
        ) PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount BIGINT,
    offerer_id BIGINT,
    english_auction_id BIGINT,
    reverse_auction_id BIGINT,
    FOREIGN KEY (offerer_id) REFERENCES dieti_user(id) ON DELETE CASCADE,
    FOREIGN KEY (english_auction_id) REFERENCES english_auction(id) ON DELETE CASCADE,
    FOREIGN KEY (reverse_auction_id) REFERENCES reverse_auction(id) ON DELETE CASCADE
);

CREATE TABLE notification(
    notification_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 214793647
        CACHE 1
        ) PRIMARY KEY,
    state state_ENUM,
    dieti_user_receiver_id BIGINT,
    ended_english_auction_id BIGINT,
    ended_reverse_auction_id BIGINT, 
    FOREIGN KEY (dieti_user_receiver_id) REFERENCES dieti_user(id) ON DELETE CASCADE,
    FOREIGN KEY (ended_english_auction_id) REFERENCES english_auction(id) ON DELETE CASCADE,
    FOREIGN KEY (ended_reverse_auction_id) REFERENCES reverse_auction(id) ON DELETE CASCADE
);