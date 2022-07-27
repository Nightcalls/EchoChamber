--liquibase formatted sql

--changeset shimmermare:create-schema
CREATE SCHEMA IF NOT EXISTS c;

--changeset shimmermare:add-user-crud
CREATE TABLE c.channel
(   id            bigserial   not null primary key,
    name          text        not null,
    owner         bigserial   not null,
    created_ts    timestamptz not null,
    updated_ts    timestamptz not null,
    deleted       bool        not null default false
);