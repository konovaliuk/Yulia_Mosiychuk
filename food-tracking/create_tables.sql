drop table food_tracking.ration;
drop table food_tracking.client;
drop table food_tracking.food;
create table if not exists `food_tracking`.`food`(
	id bigint not null auto_increment,
    `name` varchar(255) not null unique,
    protein float default 0,
    fat float default 0,
    carbohydrate float default 0,
	constraint food_pk primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table if not exists `food_tracking`.`client`(
	id bigint not null auto_increment,
    username varchar(255) not null unique,
    age int not null,
    weight float not null,
    height int not null,
    smoking boolean not null,
    drinking boolean not null,
    sport boolean not null,
	constraint client_pk primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
create table if not exists `food_tracking`.`ration`(
	client_id bigint not null,
    food_id bigint not null,
    amount float not null default 0,
    `date` date not null default current_timestamp,
	constraint ration_pk primary key (client_id, food_id, `date`),
    constraint client_fk foreign key (client_id) references `food_tracking`.`client` (id) 
    on delete cascade,
    constraint food_fk foreign key (food_id) references `food_tracking`.`food` (id) 
    on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;