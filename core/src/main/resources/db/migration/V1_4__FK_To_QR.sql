drop table access_control;
create table access_control (
    id     serial primary key,
    uuid   uuid default uuid_generate_v4() not null unique,
    email  text,
    qrd_code_id int references qr_code(id) not null
);
