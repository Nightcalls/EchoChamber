--liquibase formatted sql

--changeset firebat:create-schema
CREATE SCHEMA IF NOT EXISTS c;

--changeset firebat:add-channel-crud
CREATE TABLE c.channel
(   id            bigserial   not null primary key,
    name          text        not null,
    owner         bigint      not null, -- user id
    created_ts    timestamptz not null,
    updated_ts    timestamptz not null,
    deleted       bool        not null default false
);