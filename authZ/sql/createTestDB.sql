CREATE DATABASE IF NOT EXISTS testauthz;

GRANT select, insert, update, delete, create, drop, alter on `testauthz`.* TO 'oscars'@'localhost';
