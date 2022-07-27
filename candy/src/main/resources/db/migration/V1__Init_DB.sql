create table hibernate_sequence (next_val bigint auto_increment primary key) engine=MyISAM auto_increment = 2;

insert into hibernate_sequence values(2);

create table message (
    id bigint not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(511) not null,
    user_id bigint,
    primary key (id))
    engine=MyISAM;

create table user (
    id bigint not null,
    activation_code varchar(255),
    email varchar(255),
    is_active bit not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id))
    engine=MyISAM;

create table user_role (
    user_id bigint not null,
    roles varchar(255))
    engine=MyISAM;

alter table message
    add constraint message_user_fk
    foreign key (user_id) references user (id);

alter table user_role
    add constraint user_role_user_fk
    foreign key (user_id) references user (id);