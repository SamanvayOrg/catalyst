create table qr_code
(
    id                 serial primary key,
    uuid               uuid      default uuid_generate_v4() not null unique,
    created_date       timestamp default (now()):: timestamp without time zone not null,
    last_modified_date timestamp default (now()):: timestamp without time zone not null,
    qrd_id             text                                 not null unique,
    short_url          text                                 not null,
    folder             text,
    scans              int,
    unique_visitors    int,
    creation_date      timestamp                            not null,
    requests_offset    int                                  not null
);

create table coded_location
(
    id                        serial primary key,
    uuid                      uuid                      default uuid_generate_v4() not null unique,
    created_date              timestamp                 default (now()):: timestamp without time zone not null,
    last_modified_date        timestamp                 default (now()):: timestamp without time zone not null,
    country                   text,
    state                     text,
    district                  text,
    sub_district              text,
    block                     text,
    panchayat                 text,
    village_city              text,
    lat                       double precision not null,
    lng                       double precision not null,
    number_of_times_looked_up integer          not null default 0
);

alter table coded_location
    add unique (lat, lng);

create table user_request
(
    id                    serial primary key,
    uuid                  uuid default uuid_generate_v4() not null unique,
    qr_code_id            int                             not null references qr_code (id),
    coded_location_id     int references coded_location (id),
    unique_qrd_request_id text                            not null,
    request_date          date                            not null,
    local_scan_time       time                            not null,
    anonymized_ip         text,
    lat                   double precision                not null,
    lng                   double precision                not null,
    accuracy              double precision,
    browser               text,
    browser_version       text,
    os                    text,
    os_version            text,
    timezone              text,
    model                 text,
    brand                 text
);
