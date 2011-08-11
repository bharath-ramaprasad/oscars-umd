CREATE DATABASE IF NOT EXISTS testauthn;

GRANT select, insert, update, delete, create, drop, alter on `testauthn`.* TO 'oscars'@'localhost';
