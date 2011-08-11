CREATE DATABASE IF NOT EXISTS testrm;

GRANT select, insert, update, delete, create, drop, alter on `testrm`.* TO 'oscars'@'localhost';
