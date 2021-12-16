create table qr_code
(
    id              serial primary key,
    uuid            uuid default uuid_generate_v4() not null unique,
    qrd_id          text                            not null unique,
    short_url       text                            not null,
    folder          text,
    scans           int,
    unique_visitors int,
    creation_date   timestamp                       not null,
    requests_offset int                             not null
);

create table location
(
    id           serial primary key,
    uuid         uuid default uuid_generate_v4() not null unique,
    village      text,
    district     text,
    sub_district text,
    city         text,
    state        text,
    pincode      text
);

create table user_request
(
    id              serial primary key,
    uuid            uuid default uuid_generate_v4() not null unique,
    qr_code_id      int                             not null references qr_code (id),
    location_id     int references location (id),
    unique_id       text                            not null,
    request_date    date                            not null,
    local_scan_time time                            not null,
    anonymized_ip   text,
    lat             double precision,
    lng             double precision,
    accuracy        double precision,
    browser         text,
    browser_version text,
    os              text,
    os_version      text,
    timezone        text,
    model           text,
    brand           text
);