create table qr_code
(
    id              serial primary key,
    qrd_id          text      not null unique,
    short_url       text      not null,
    folder          text,
    scans           int,
    unique_visitors int,
    creation_date   timestamp not null
);

create table location
(
    id           serial primary key,
    village      text,
    district     text,
    sub_district text,
    city         text,
    state        text,
    pincode      text
);

create table user_scan
(
    id              serial primary key,
    qr_code_id      int  not null references qr_code (id),
    location_id     int  not null references location (id),
    unique_id       text not null,
    anonymized_ip   text not null,
    request_date    date not null,
    local_scan_time time not null,
    lng             point,
    lat             point,
    browser         text not null,
    browser_version text not null,
    os              text not null,
    os_version      text not null,
    timezone        text not null,
    model           text not null,
    brand           text not null,
);