--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--





--
-- Drop roles
--

DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:MErMi6nCWrLKUxwJJ5EG/w==$MBIqKtYYMGaQpQ0BTlqeyyzqj4/HJk29ojviA+V9ONY=:mo32X+OQ2eB1TpScYMzszYsaQfI66FRl3AjedJlJ7Qo=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: category_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.category_enum AS ENUM (
    'elettronica',
    'informatica',
    'musica',
    'sport'
);


ALTER TYPE public.category_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Auction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Auction" (
    id integer NOT NULL,
    title character varying(50)[] NOT NULL,
    type character varying(7)[] NOT NULL,
    description text,
    image character varying(100)[] NOT NULL,
    starting_price bigint NOT NULL,
    timer integer NOT NULL,
    variation_amount bigint NOT NULL,
    minimum_price bigint,
    owner_email character varying(30)[] NOT NULL,
    category public.category_enum
);


ALTER TABLE public."Auction" OWNER TO postgres;

--
-- Name: Notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Notification" (
    state character varying(8)[] NOT NULL,
    user_email character varying(30)[] NOT NULL,
    auction_id integer NOT NULL
);


ALTER TABLE public."Notification" OWNER TO postgres;

--
-- Name: Offer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Offer" (
    amount bigint NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    offerer_email character varying(30)[] NOT NULL,
    target_auction integer NOT NULL
);


ALTER TABLE public."Offer" OWNER TO postgres;

--
-- Name: User; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."User" (
    name character varying(20)[] NOT NULL,
    surname character varying(20)[] NOT NULL,
    email character varying(30)[] NOT NULL,
    password character varying(30)[] NOT NULL,
    short_bio text,
    links text,
    geographical_area character varying(30)[]
);


ALTER TABLE public."User" OWNER TO postgres;

--
-- Data for Name: Auction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Auction" (id, title, type, description, image, starting_price, timer, variation_amount, minimum_price, owner_email, category) FROM stdin;
\.


--
-- Data for Name: Notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Notification" (state, user_email, auction_id) FROM stdin;
\.


--
-- Data for Name: Offer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Offer" (amount, "timestamp", offerer_email, target_auction) FROM stdin;
\.


--
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."User" (name, surname, email, password, short_bio, links, geographical_area) FROM stdin;
\.


--
-- Name: Auction Auction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Auction"
    ADD CONSTRAINT "Auction_pkey" PRIMARY KEY (id);


--
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (email);


--
-- Name: Notification auction_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Notification"
    ADD CONSTRAINT auction_id FOREIGN KEY (auction_id) REFERENCES public."Auction"(id);


--
-- Name: CONSTRAINT auction_id ON "Notification"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT auction_id ON public."Notification" IS 'ID of the auction that ended, causing the notification';


--
-- Name: Offer offerer_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Offer"
    ADD CONSTRAINT offerer_email FOREIGN KEY (offerer_email) REFERENCES public."User"(email);


--
-- Name: CONSTRAINT offerer_email ON "Offer"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT offerer_email ON public."Offer" IS 'Email of the user that made the offer';


--
-- Name: Auction owner_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Auction"
    ADD CONSTRAINT owner_email FOREIGN KEY (owner_email) REFERENCES public."User"(email) NOT VALID;


--
-- Name: CONSTRAINT owner_email ON "Auction"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT owner_email ON public."Auction" IS 'Owner of the auction';


--
-- Name: Offer target_auction; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Offer"
    ADD CONSTRAINT target_auction FOREIGN KEY (target_auction) REFERENCES public."Auction"(id);


--
-- Name: CONSTRAINT target_auction ON "Offer"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT target_auction ON public."Offer" IS 'ID of the auction, target of the offer';


--
-- Name: Notification user_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Notification"
    ADD CONSTRAINT user_email FOREIGN KEY (user_email) REFERENCES public."User"(email);


--
-- Name: CONSTRAINT user_email ON "Notification"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT user_email ON public."Notification" IS 'Email of the user that took part in the auction referred by auction_id';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

