# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

#Create Databases
CREATE DATABASE recipes_dev;
CREATE DATABASE recipes_prod;

#Create database service accounts
CREATE USER 'dev'@'%' IDENTIFIED BY 'dev';
CREATE USER 'prod'@'%' IDENTIFIED BY 'prod';

#User privileges
GRANT SELECT ON recipes_dev.* to 'dev'@'%';
GRANT INSERT ON recipes_dev.* to 'dev'@'%';
GRANT DELETE ON recipes_dev.* to 'dev'@'%';
GRANT UPDATE ON recipes_dev.* to 'dev'@'%';
GRANT SELECT ON recipes_prod.* to 'prod'@'%';
GRANT INSERT ON recipes_prod.* to 'prod'@'%';
GRANT DELETE ON recipes_prod.* to 'prod'@'%';
GRANT UPDATE ON recipes_prod.* to 'prod'@'%';
