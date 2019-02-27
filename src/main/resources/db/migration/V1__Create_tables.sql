create table if not exists Privilege
(
  id   bigint       not null
    primary key,
  name varchar(255) null
);

create table if not exists Role
(
  id   bigint       not null
    primary key,
  name varchar(255) null
);


create table if not exists roles_privileges
(
  role_id      bigint not null,
  privilege_id bigint not null,
  constraint FK2rfl694fu6ls2f2mqcxesqc6p
    foreign key (role_id) references Role (id),
  constraint FKp0x1d9k5aksyqd1akwwfkh0ki
    foreign key (privilege_id) references Privilege (id)
);

create table if not exists user_account
(
  id        bigint       not null
    primary key,
  email     varchar(255) null,
  enabled   bit          not null,
  firstName varchar(255) null,
  lastName  varchar(255) null,
  password  varchar(60)  null,
  secret    varchar(255) null
);

create table if not exists Food
(
  id      bigint       not null
    primary key,
  comment varchar(255) null,
  name    varchar(255) null,
  user_id bigint       null,
  constraint FKhajt1kuky9wa4at0nyp8899a1
    foreign key (user_id) references user_account (id)
);

create table if not exists PasswordResetToken
(
  id         bigint       not null
    primary key,
  expiryDate datetime(6)  null,
  token      varchar(255) null,
  user_id    bigint       not null,
  constraint FKgdew0adk8xruaoq2rgdsy34w2
    foreign key (user_id) references user_account (id)
);

create table if not exists VerificationToken
(
  id         bigint       not null
    primary key,
  expiryDate datetime(6)  null,
  token      varchar(255) null,
  user_id    bigint       not null,
  constraint FK_VERIFY_USER
    foreign key (user_id) references user_account (id)
);

create table if not exists users_roles
(
  user_id bigint not null,
  role_id bigint not null,
  constraint FKa9r8g5hiyy57ts5u4tkf0lbab
    foreign key (role_id) references Role (id),
  constraint FKci4mdvg1fmo9eqmwno1y9o0fa
    foreign key (user_id) references user_account (id)
);

