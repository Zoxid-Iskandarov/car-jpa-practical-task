create table car
(
    id                          bigserial primary key,
    number                      varchar(20) not null unique,
    year                        int         not null,
    color                       varchar(20) not null,
    actual_technical_inspection boolean     not null,
    model_id                    bigint      not null references model (id),
    created                     timestamp   not null,
    updated                     timestamp   not null
);