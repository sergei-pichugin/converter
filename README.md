## Создание базы данных в postgresql
create database currency;
create user curradmin with encrypted password 'd2s80+H';
grant all privileges on database currency to curradmin;

## В адресной строке
localhost:8080/main

## Логин и пароль
admin
1572