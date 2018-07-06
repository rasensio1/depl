begin transaction;

create table if not exists clicks(
       id serial primary key,
       username varchar(256)
);

commit transaction;
