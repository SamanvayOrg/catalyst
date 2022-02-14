delete from flyway_schema_history where 1 = 1;
drop table if exists password_reset_token;
drop table if exists access_control;
drop table if exists user_request;
drop table if exists qr_code;
drop table if exists users;
drop table if exists organisation;
