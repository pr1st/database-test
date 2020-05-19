--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3
-- Dumped by pg_dump version 12.3

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: Admin
--

CREATE TABLE public.customer (
    id bigint NOT NULL,
    firstname character varying(255),
    lastname character varying(255)
);


ALTER TABLE public.customer OWNER TO "Admin";

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: Admin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO "Admin";

--
-- Name: product; Type: TABLE; Schema: public; Owner: Admin
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    cost integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.product OWNER TO "Admin";

--
-- Name: purchase; Type: TABLE; Schema: public; Owner: Admin
--

CREATE TABLE public.purchase (
    id bigint NOT NULL,
    purchasedate date,
    customer_id bigint,
    product_id bigint
);


ALTER TABLE public.purchase OWNER TO "Admin";

--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: Admin
--

COPY public.customer (id, firstname, lastname) FROM stdin;
1	Иван	Иванович
2	Дмитрий	Гелетей
3	Лев	Князев
4	Лука	Иванович
5	Ленар	Афанасьев
6	Ярослав	Петренко
7	Кузьма	Молчанов
8	Глеб	Тимофеев
9	Оскар	Сорокин
10	Вадим	Буров
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: Admin
--

COPY public.product (id, cost, name) FROM stdin;
11	100	Макороны
12	200	Шоколад
13	300	Хлеб
14	400	Вода
15	500	Носки
16	600	Жилье
17	700	Удочка
18	800	Мороженное
19	900	Гречка
20	1000	Туалетная бумага
\.


--
-- Data for Name: purchase; Type: TABLE DATA; Schema: public; Owner: Admin
--

COPY public.purchase (id, purchasedate, customer_id, product_id) FROM stdin;
21	2020-01-01	1	11
22	2020-01-02	2	12
23	2020-01-02	2	11
24	2020-01-02	2	11
25	2020-01-03	3	13
26	2020-01-04	3	14
27	2020-01-01	3	15
28	2020-01-03	3	17
29	2020-01-04	4	15
30	2020-01-02	5	19
31	2020-01-03	5	19
32	2020-01-02	5	19
33	2020-01-04	5	20
34	2020-01-02	6	19
35	2020-01-03	7	18
36	2020-01-02	7	15
37	2020-01-01	7	11
38	2020-01-01	7	12
39	2020-01-02	7	13
40	2020-01-01	9	19
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: Admin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 40, true);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: Admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: Admin
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: purchase purchase_pkey; Type: CONSTRAINT; Schema: public; Owner: Admin
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_pkey PRIMARY KEY (id);


--
-- Name: purchase fk15nvfxt1otlr20atpbi9c2uo3; Type: FK CONSTRAINT; Schema: public; Owner: Admin
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT fk15nvfxt1otlr20atpbi9c2uo3 FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- Name: purchase fk93t8gvf0r076j4uejkb0injck; Type: FK CONSTRAINT; Schema: public; Owner: Admin
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT fk93t8gvf0r076j4uejkb0injck FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- PostgreSQL database dump complete
--

