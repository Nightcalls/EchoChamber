--liquibase formatted sql

--changeset shimmermare:add-user-crud
CREATE TABLE u.user
(   id            bigserial   not null primary key,
    name          text        not null,
    created_ts    timestamptz not null,
    updated_ts    timestamptz not null,
    last_login_ts timestamptz not null,
    last_login_ip text        not null,
    deleted       bool        not null default false
);