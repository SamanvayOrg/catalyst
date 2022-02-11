create table qr_code
(
    id                 serial primary key,
    uuid               uuid      default uuid_generate_v4() not null unique,
    qrd_id             text                                 not null unique,
    short_url          text                                 not null,
    folder             text,
    scans              int,
    unique_visitors    int,
    creation_date      timestamp default (now()):: timestamp without time zone not null,
    last_modified_date timestamp default (now()):: timestamp without time zone not null,
    requests_offset    int                                  not null
);

create table coded_location
(
    id                 serial primary key,
    uuid               uuid      default uuid_generate_v4() not null unique,
    villageCity        text,
    sub_district       text,
    district           text,
    state              text,
    pin_code           int,
    lat                double precision,
    lng                double precision,
    creation_date      timestamp default (now()):: timestamp without time zone not null,
    last_modified_date timestamp default (now()):: timestamp without time zone not null
);

create table user_request
(
    id                    serial primary key,
    uuid                  uuid default uuid_generate_v4() not null unique,
    qr_code_id            int                             not null references qr_code (id),
    coded_location_id     int                             not null references coded_location (id),
    unique_qrd_request_id text                            not null,
    request_date          date                            not null,
    local_scan_time       time                            not null,
    anonymized_ip         text,
    lat                   double precision,
    lng                   double precision,
    accuracy              double precision,
    browser               text,
    browser_version       text,
    os                    text,
    os_version            text,
    timezone              text,
    model                 text,
    brand                 text
);
