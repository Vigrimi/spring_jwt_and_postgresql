CREATE TABLE IF NOT EXISTS tb_inside_roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS tb_inside_users (
    user_id SERIAL PRIMARY KEY,
    login VARCHAR(255) PRIMARY KEY,
    password TEXT,
    role_id INTEGER,
    FOREIGN KEY (role_id) REFERENCES tb_inside_roles (id)
);

CREATE TABLE IF NOT EXISTS tb_inside_messages (
    id_of_msg SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    message_my_user TEXT
);

insert into tb_inside_roles(name) values ('ROLE_USER');
insert into tb_inside_roles(name) values ('ROLE_ADMIN');
insert into tb_inside_users(login,password,role_id) values ('user11','$2a$10$3K3eSL2siY1PwT0fYcOxLeqKOiIuJoI2ZxudxL2bI/uA0pWpWfiUi','1');
insert into tb_inside_users(login,password,role_id) values ('user12','$2a$10$vVf/dQLe7g9UKJlf2cnUt.gSttR1.J.5vlyV3ZcmEur.Zx35egZu6','1');
insert into tb_inside_users(login,password,role_id) values ('user13','$2a$10$PYZ0h3r7KCNp/2EzcL8dbe97TGxu56Vi6aMo9GiOHb.FmmRlI8HQC','1');
insert into tb_inside_users(login,password,role_id) values ('user14','$2a$10$8d6j1sTr6Cn9E88eM/BylelMMCgQPvXkB3XaBBW6tg.E6MiiAvufO','1');
insert into tb_inside_users(login,password,role_id) values ('user15','$2a$10$FG.T23yxOT/zOwHa1baOt.AQY9mx6zqxZ9rs3QiA4nXB72zAlSrKy','1');
insert into tb_inside_users(login,password,role_id) values ('user16','$2a$10$sSnf4QkRKbqRkqxKTnf7A.bzJ4moWb0h7kstcST4tx6T9mp4vDIgG','1');
insert into tb_inside_messages(user_name,message_my_user) values ('user11','testMsg1UserId1');
insert into tb_inside_messages(user_name,message_my_user) values ('user12','testMsg1UserId2');
insert into tb_inside_messages(user_name,message_my_user) values ('user11','testMsg2UserId1');