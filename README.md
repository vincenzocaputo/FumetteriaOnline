# Fumetteria Online



### Requirements
  - Apache Tomcat 9.0.8
  - MySql Server 5.7.22
  - Eclipse IDE 

### Installation and Configuration
Use database/createfumetteriadb.sql script to create a new db schema
```
mysql> source database/createfumetteriadb.sql
```
It creates a new user 'utente'@'localhost' with password 'pass'.
Create war file
```
jar -cvf FumetteriaOnline.war <pathFumetteriaOnlineFolder>
```
Move FumetteriaOnline.war in apache-tomcat-9.0.8/webapps
### Execution
Run Apache Tomcat Server
```
bin/startup.sh
```
You can now test the webapp at 
```
http:\\localhost:8080\FumetteriaOnline
```
The default log-in information of web site administrator is
```
user: admin@email.it
password: rootpass
```
You can change credentials in database/createfumetteriadb.sql
```
INSERT INTO Utente VALUES('<user>','','','',0,00000,'','SA',SHA1('<pass>'),1);
```
