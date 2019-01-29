create table admin
(
	adminId bigint auto_increment primary key,
	name text not null,
	password text not null,
	enabled tinyint(1) not null,
	role blob not null
)
comment 'järjestelmän pääkäyttäjä' collate=utf8mb4_general_ci;

create index admin_adminId_index
	on admin (adminId);

create table user
(
	id bigint auto_increment comment 'käyttäjän tunniste'
		primary key,
	first_name text not null comment 'Etunimi',
	last_name text not null comment 'Sukunimi',
	user_account tinytext not null,
	user_usergroup bigint null comment 'käyttäjäryhmä',
	gender tinytext null comment 'Sukupuoli',
	birthDay date not null comment 'syntymäaika',
	mailAddress text not null comment 'sposti osoite',
	user_password text not null,
	enabled tinyint(1) not null comment 'onko tili aktiivinen',
	secret varchar(255) null,
	role blob not null
)
comment 'sovelluksen peruskäyttäjät' collate=utf8mb4_general_ci;

create index user_userId_index
	on user (id);

create table usergroup
(
	groupId BIGINT auto_increment comment 'taulun tunniste',
	groupName TEXT not null comment 'Ryhmän nimi',
	constraint usergroup_pk
		primary key (groupId),
	constraint usergroup_user_userId_fk
		foreign key (groupId) references user (id)
)
comment 'peruskäyttäjien ryhmät' collate=utf8mb4_general_ci;

create table food
(
	foodId bigint auto_increment primary key,
	name text not null comment 'ruoan nimi',
	date date not null comment 'syönti päiväys',
	comment text null comment 'Vapaa valintainen kommentti ruoasta',
	price double null comment 'ruoan hinta',
	constraint food_user_userId_fk
		foreign key (foodId) references user (id)
)
comment 'kaikille safkoille yhteinen taulu' collate=utf8mb4_general_ci;

create table role
(
	roleId LONG auto_increment,
	adminId LONG null,
	userId LONG null,
	privileges BLOB not null,
	name text not null,
	constraint role_pk
		primary key (roleId),
	constraint role_admin_adminId_fk
		foreign key (adminId) references admin (adminId),
	constraint role_user_userId_fk
		foreign key (userId) references user (id)
)
comment 'säilöö sovelluksen käyttäjien roolit' collate=utf8mb4_general_ci;


