--
-- PostgreSQL database dump
--

\restrict D4qSZ6t3MKq8E5USulZFz8OgtNuIO6jkbtNZe2su8dKw7Ap5xlfsmQICBv0TcFE

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-12-31 13:12:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16686)
-- Name: noteappschema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA noteappschema;


ALTER SCHEMA noteappschema OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 232 (class 1259 OID 24987)
-- Name: note_tags; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.note_tags (
    note_id integer NOT NULL,
    tag_id integer NOT NULL
);


ALTER TABLE noteappschema.note_tags OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 24964)
-- Name: notes; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.notes (
    user_id integer NOT NULL,
    note_id integer NOT NULL,
    note_content text NOT NULL,
    create_date timestamp with time zone DEFAULT now() NOT NULL,
    update_date timestamp with time zone DEFAULT now() NOT NULL,
    ispinned boolean DEFAULT false,
    isarchived boolean DEFAULT false
);


ALTER TABLE noteappschema.notes OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 25004)
-- Name: notes_note_id_seq; Type: SEQUENCE; Schema: noteappschema; Owner: postgres
--

ALTER TABLE noteappschema.notes ALTER COLUMN note_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME noteappschema.notes_note_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 16833)
-- Name: refresh_tokens; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.refresh_tokens (
    tokenid uuid DEFAULT gen_random_uuid() NOT NULL,
    userid integer NOT NULL,
    tokenvalue text NOT NULL,
    expirydate timestamp with time zone NOT NULL,
    isrevoked boolean
);


ALTER TABLE noteappschema.refresh_tokens OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16792)
-- Name: roles; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.roles (
    roleid integer NOT NULL,
    rolename character varying(255) NOT NULL
);


ALTER TABLE noteappschema.roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16791)
-- Name: roles_roleid_seq; Type: SEQUENCE; Schema: noteappschema; Owner: postgres
--

CREATE SEQUENCE noteappschema.roles_roleid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE noteappschema.roles_roleid_seq OWNER TO postgres;

--
-- TOC entry 4879 (class 0 OID 0)
-- Dependencies: 220
-- Name: roles_roleid_seq; Type: SEQUENCE OWNED BY; Schema: noteappschema; Owner: postgres
--

ALTER SEQUENCE noteappschema.roles_roleid_seq OWNED BY noteappschema.roles.roleid;


--
-- TOC entry 231 (class 1259 OID 24980)
-- Name: tags; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.tags (
    tag_id integer NOT NULL,
    tag_name character varying(255)
);


ALTER TABLE noteappschema.tags OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 25002)
-- Name: tags_tag_id_seq; Type: SEQUENCE; Schema: noteappschema; Owner: postgres
--

ALTER TABLE noteappschema.tags ALTER COLUMN tag_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME noteappschema.tags_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 16818)
-- Name: user_roles; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.user_roles (
    userid integer NOT NULL,
    roleid integer NOT NULL
);


ALTER TABLE noteappschema.user_roles OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16779)
-- Name: users; Type: TABLE; Schema: noteappschema; Owner: postgres
--

CREATE TABLE noteappschema.users (
    userid integer NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    createdate timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updatedate timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    isenabled boolean DEFAULT true,
    isaccountnonlocked boolean DEFAULT false
);


ALTER TABLE noteappschema.users OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16778)
-- Name: users_userid_seq; Type: SEQUENCE; Schema: noteappschema; Owner: postgres
--

CREATE SEQUENCE noteappschema.users_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE noteappschema.users_userid_seq OWNER TO postgres;

--
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 218
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: noteappschema; Owner: postgres
--

ALTER SEQUENCE noteappschema.users_userid_seq OWNED BY noteappschema.users.userid;


--
-- TOC entry 4690 (class 2604 OID 16795)
-- Name: roles roleid; Type: DEFAULT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.roles ALTER COLUMN roleid SET DEFAULT nextval('noteappschema.roles_roleid_seq'::regclass);


--
-- TOC entry 4685 (class 2604 OID 16782)
-- Name: users userid; Type: DEFAULT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.users ALTER COLUMN userid SET DEFAULT nextval('noteappschema.users_userid_seq'::regclass);


--
-- TOC entry 4871 (class 0 OID 24987)
-- Dependencies: 232
-- Data for Name: note_tags; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.note_tags (note_id, tag_id) FROM stdin;
\.


--
-- TOC entry 4869 (class 0 OID 24964)
-- Dependencies: 230
-- Data for Name: notes; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.notes (user_id, note_id, note_content, create_date, update_date, ispinned, isarchived) FROM stdin;
\.


--
-- TOC entry 4868 (class 0 OID 16833)
-- Dependencies: 223
-- Data for Name: refresh_tokens; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.refresh_tokens (tokenid, userid, tokenvalue, expirydate, isrevoked) FROM stdin;
\.


--
-- TOC entry 4866 (class 0 OID 16792)
-- Dependencies: 221
-- Data for Name: roles; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.roles (roleid, rolename) FROM stdin;
5	ROLE_USER
6	ROLE_ADMIN
\.


--
-- TOC entry 4870 (class 0 OID 24980)
-- Dependencies: 231
-- Data for Name: tags; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.tags (tag_id, tag_name) FROM stdin;
\.


--
-- TOC entry 4867 (class 0 OID 16818)
-- Dependencies: 222
-- Data for Name: user_roles; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.user_roles (userid, roleid) FROM stdin;
\.


--
-- TOC entry 4864 (class 0 OID 16779)
-- Dependencies: 219
-- Data for Name: users; Type: TABLE DATA; Schema: noteappschema; Owner: postgres
--

COPY noteappschema.users (userid, username, password, createdate, updatedate, isenabled, isaccountnonlocked) FROM stdin;
\.


--
-- TOC entry 4881 (class 0 OID 0)
-- Dependencies: 234
-- Name: notes_note_id_seq; Type: SEQUENCE SET; Schema: noteappschema; Owner: postgres
--

SELECT pg_catalog.setval('noteappschema.notes_note_id_seq', 3, true);


--
-- TOC entry 4882 (class 0 OID 0)
-- Dependencies: 220
-- Name: roles_roleid_seq; Type: SEQUENCE SET; Schema: noteappschema; Owner: postgres
--

SELECT pg_catalog.setval('noteappschema.roles_roleid_seq', 6, true);


--
-- TOC entry 4883 (class 0 OID 0)
-- Dependencies: 233
-- Name: tags_tag_id_seq; Type: SEQUENCE SET; Schema: noteappschema; Owner: postgres
--

SELECT pg_catalog.setval('noteappschema.tags_tag_id_seq', 2, true);


--
-- TOC entry 4884 (class 0 OID 0)
-- Dependencies: 218
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: noteappschema; Owner: postgres
--

SELECT pg_catalog.setval('noteappschema.users_userid_seq', 20, true);


--
-- TOC entry 4711 (class 2606 OID 24991)
-- Name: note_tags note_tags_note_tag_uniqe; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.note_tags
    ADD CONSTRAINT note_tags_note_tag_uniqe UNIQUE (note_id, tag_id);


--
-- TOC entry 4705 (class 2606 OID 24974)
-- Name: notes notes_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.notes
    ADD CONSTRAINT notes_pkey PRIMARY KEY (note_id);


--
-- TOC entry 4703 (class 2606 OID 16840)
-- Name: refresh_tokens refreshtokens_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.refresh_tokens
    ADD CONSTRAINT refreshtokens_pkey PRIMARY KEY (tokenid);


--
-- TOC entry 4699 (class 2606 OID 16797)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (roleid);


--
-- TOC entry 4707 (class 2606 OID 24984)
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (tag_id);


--
-- TOC entry 4709 (class 2606 OID 24986)
-- Name: tags tags_tag_name_unique; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.tags
    ADD CONSTRAINT tags_tag_name_unique UNIQUE (tag_name);


--
-- TOC entry 4701 (class 2606 OID 16822)
-- Name: user_roles userroles_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.user_roles
    ADD CONSTRAINT userroles_pkey PRIMARY KEY (userid, roleid);


--
-- TOC entry 4697 (class 2606 OID 16790)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- TOC entry 4716 (class 2606 OID 24992)
-- Name: note_tags note_tags_note_fk; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.note_tags
    ADD CONSTRAINT note_tags_note_fk
    FOREIGN KEY (note_id) REFERENCES noteappschema.notes(note_id) ON DELETE CASCADE;



--
-- TOC entry 4717 (class 2606 OID 24997)
-- Name: note_tags note_tags_tag_fk; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.note_tags
    ADD CONSTRAINT note_tags_tag_fk
    FOREIGN KEY (tag_id) REFERENCES noteappschema.tags(tag_id) ON DELETE CASCADE;



--
-- TOC entry 4715 (class 2606 OID 24975)
-- Name: notes notes_user_fk; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.notes
    ADD CONSTRAINT notes_user_fk FOREIGN KEY (user_id) REFERENCES noteappschema.users(userid) ON DELETE CASCADE;


--
-- TOC entry 4714 (class 2606 OID 16841)
-- Name: refresh_tokens refreshtokens_userid_fkey; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.refresh_tokens
    ADD CONSTRAINT refreshtokens_userid_fkey FOREIGN KEY (userid) REFERENCES noteappschema.users(userid);


--
-- TOC entry 4712 (class 2606 OID 16828)
-- Name: user_roles userroles_roleid_fkey; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.user_roles
    ADD CONSTRAINT userroles_roleid_fkey FOREIGN KEY (roleid) REFERENCES noteappschema.roles(roleid);


--
-- TOC entry 4713 (class 2606 OID 24877)
-- Name: user_roles userroles_userid_fkey; Type: FK CONSTRAINT; Schema: noteappschema; Owner: postgres
--

ALTER TABLE ONLY noteappschema.user_roles
    ADD CONSTRAINT userroles_userid_fkey FOREIGN KEY (userid) REFERENCES noteappschema.users(userid) ON DELETE CASCADE;


-- Completed on 2025-12-31 13:12:15

--
-- PostgreSQL database dump complete
--

\unrestrict D4qSZ6t3MKq8E5USulZFz8OgtNuIO6jkbtNZe2su8dKw7Ap5xlfsmQICBv0TcFE

