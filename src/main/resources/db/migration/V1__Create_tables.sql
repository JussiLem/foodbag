create table if not exists Privilege
(
  id   bigint       not null
    primary key,
  name varchar(255) null
);

create table if not exists food
(
  id      bigint auto_increment
    primary key,
  comment text         null,
  date    datetime(6)  null,
  name    varchar(255) null,
  price   double       null
);

create table if not exists hibernate_sequence
(
  next_not_cached_value bigint(21)          not null,
  minimum_value         bigint(21)          not null,
  maximum_value         bigint(21)          not null,
  start_value           bigint(21)          not null comment 'start value when sequences is created or value if RESTART is used',
  increment             bigint(21)          not null comment 'increment value',
  cache_size            bigint(21) unsigned not null,
  cycle_option          tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  cycle_count           bigint(21)          not null comment 'How many cycles have been done'
);

create table if not exists roles
(
  id   bigint       not null
    primary key,
  name varchar(255) null
);

create table if not exists roles_privileges
(
  role_id      bigint not null,
  privilege_id bigint not null,
  constraint FK629oqwrudgp5u7tewl07ayugj
    foreign key (role_id) references roles (id),
  constraint FKp0x1d9k5aksyqd1akwwfkh0ki
    foreign key (privilege_id) references Privilege (id)
);

create table if not exists users
(
  id            bigint       not null
    primary key,
  birthDay      date         null,
  enabled       bit          not null,
  first_name    varchar(255) null,
  gender        varchar(255) null,
  last_name     varchar(255) null,
  email_address varchar(255) null,
  user_password varchar(255) null,
  username      varchar(255) null
);

create table if not exists PasswordResetToken
(
  id         bigint       not null
    primary key,
  expiryDate datetime(6)  null,
  token      varchar(255) null,
  user_id    bigint       not null,
  constraint FKg3fbfo1tc9louotgq5r940avr
    foreign key (user_id) references users (id)
);

create table if not exists VerificationToken
(
  id         bigint       not null
    primary key,
  expiryDate datetime(6)  null,
  token      varchar(255) null,
  user_id    bigint       not null,
  constraint FK_VERIFY_USER
    foreign key (user_id) references users (id)
);

create table if not exists user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint FKj345gk1bovqvfame88rcx7yyx
    foreign key (user_id) references users (id),
  constraint FKt7e7djp752sqn6w22i6ocqy6q
    foreign key (role_id) references roles (id)
);

