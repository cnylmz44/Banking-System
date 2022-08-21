# Banking Project
_Prepared with inspiration from current technologies; It is a Spring Boot application that can be used to create a bank account, bring account information, delete account, update account, load money into account, transfer money from one account to another account, create a user for user transactions, login and enable operations on behalf of banking transactions._

_In the project designed with REST and a layered architecture system, MySQL was used as the database, MyBatis and JDBC for database relations, JWT for user authentication, Apache Kafka for logging and CollectApi for exchange operations._
### Technologies
- _Java_
- _Spring Boot_
- _MyBatis_
- _JDBC_
- _Apache Kafka_
- _Spring Security_
- _JWT_
- _MySQL_
- _Collect API_

### Entities
- Bank
_{
int id PRIMARY KEY AUTO_INCREMENT,
string name NOT NULL UNIQUE
}_

- Bank User
_{
int id PRIMARY KEY AUTO_INCREMENT,
String username NOT NULL UNIQUE,
String email NOT NULL UNIQUE,
String password NOT NULL,
boolean enabled DEFAULT true,
String authorities
}_

- Bank Account
_{
int id PRIMARY KEY AUTO_INCREMENT,
user_id FOREING KEY(users.id),
bank_id FOREIGN_KEY(banks.id),
number String,
String type(TL,USD,GAU),
double balance DEFAULT 0,
timestamp creation_date,
timestamp last_update_date,
boolean is_deleted DEFAULT false
}_
