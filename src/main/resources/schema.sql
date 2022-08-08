CREATE TABLE if not exists Taco_Order (
id bigint primary key auto_increment, 
delivery_Name varchar(50) not null,
delivery_Street varchar(50) not null,
delivery_City varchar(50) not null,
delivery_State varchar(2) not null,
delivery_Zip varchar(10) not null,
cc_number varchar(16) not null,
cc_expiration varchar(5) not null,
cc_cvv varchar(3) not null,
placed_at timestamp not null
);

CREATE TABLE if not exists Taco (
id bigint primary key auto_increment, 
name varchar(50) not null,
taco_order bigint not null,
taco_order_key bigint not null,
created_at timestamp not null
);

CREATE TABLE if not exists Ingredient_Ref (
ingredient varchar(4) not null,
taco bigint not null,
taco_key bigint not null
);

CREATE TABLE if not exists Ingredient (
id varchar(4) primary key not null,
name varchar(25) not null,
type varchar(10) not null
);

ALTER TABLE taco
ADD CONSTRAINT taco_order_fk FOREIGN KEY (taco_order)
REFERENCES taco_order(id);

ALTER TABLE Ingredient_Ref
ADD CONSTRAINT ingredient_ref_fk FOREIGN KEY (ingredient)
REFERENCES Ingredient(id);