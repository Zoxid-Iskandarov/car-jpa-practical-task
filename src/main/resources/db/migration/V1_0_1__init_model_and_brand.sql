create table brand
(
    id      bigserial primary key,
    name    varchar(100) not null unique,
    created timestamp    not null,
    updated timestamp    not null
);

create table model
(
    id       bigserial primary key,
    name     varchar(100) not null,
    brand_id bigint       not null references brand (id),
    created  timestamp    not null,
    updated  timestamp    not null,
    unique (name, brand_id)
);