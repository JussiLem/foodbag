create schema foods collate utf8mb4_unicode_ci;

create table Privilege
(
  id bigint not null
    primary key,
  name varchar(255) null
);

create table Role
(
  id bigint not null
    primary key,
  name varchar(255) null
);

create table hibernate_sequence
(
  next_not_cached_value bigint(21) not null,
  minimum_value bigint(21) not null,
  maximum_value bigint(21) not null,
  start_value bigint(21) not null comment 'start value when sequences is created or value if RESTART is used',
  increment bigint(21) not null comment 'increment value',
  cache_size bigint(21) unsigned not null,
  cycle_option tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  cycle_count bigint(21) not null comment 'How many cycles have been done'
);

create table roles_privileges
(
  role_id bigint not null,
  privilege_id bigint not null,
  constraint FK2rfl694fu6ls2f2mqcxesqc6p
    foreign key (role_id) references Role (id),
  constraint FKp0x1d9k5aksyqd1akwwfkh0ki
    foreign key (privilege_id) references Privilege (id)
);

create table user_account
(
  id bigint not null
    primary key,
  email varchar(255) null,
  enabled bit not null,
  firstName varchar(255) null,
  lastName varchar(255) null,
  password varchar(60) null,
  secret varchar(255) null
);

create table Food
(
  id bigint not null
    primary key,
  comment varchar(255) null,
  name varchar(255) null,
  user_id bigint null,
  constraint FKhajt1kuky9wa4at0nyp8899a1
    foreign key (user_id) references user_account (id)
);

create table PasswordResetToken
(
  id bigint not null
    primary key,
  expiryDate datetime(6) null,
  token varchar(255) null,
  user_id bigint not null,
  constraint FKgdew0adk8xruaoq2rgdsy34w2
    foreign key (user_id) references user_account (id)
);

create table VerificationToken
(
  id bigint not null
    primary key,
  expiryDate datetime(6) null,
  token varchar(255) null,
  user_id bigint not null,
  constraint FK_VERIFY_USER
    foreign key (user_id) references user_account (id)
);

create table user_account_Food
(
  User_id bigint not null,
  foods_id bigint not null,
  constraint UK_msu1yssreurkmvd9hve0ouros
    unique (foods_id),
  constraint FKfpc03cnw979ax788r1fq7vje0
    foreign key (User_id) references user_account (id),
  constraint FKsa30nl93t4x1f0y6uqom0gamv
    foreign key (foods_id) references Food (id)
);

create table users_roles
(
  user_id bigint not null,
  role_id bigint not null,
  constraint FKa9r8g5hiyy57ts5u4tkf0lbab
    foreign key (role_id) references Role (id),
  constraint FKci4mdvg1fmo9eqmwno1y9o0fa
    foreign key (user_id) references user_account (id)
);

