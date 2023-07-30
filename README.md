# ABN Amro assessment assignment
Repository containing my solution to the programming assessment provided to me by ABN Amro.

## Requirements:
### Database: PostgreSQL 
PostgreSQL is assumed. To some degree, the assignment is database agnostic.
Assuming you create the database yourself, use the following commands to replicate the used tables:

CREATE TABLE bank (
    id SERIAL PRIMARY KEY,
    bank_code VARCHAR(4) UNIQUE NOT NULL,
    bank_name VARCHAR(100) NOT NULL
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    bsn VARCHAR(9) UNIQUE NOT NULL,
    mobile_phone_number VARCHAR(15),
    firstname VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    address VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    salt VARCHAR(100) NOT NULL,
    bank_account_number VARCHAR(20) UNIQUE NOT NULL,
    bank_account_balance NUMERIC(12, 2) DEFAULT 0,
    bank_id INT REFERENCES bank(id)-- Foreign key column to reference bank table
);

These commands can be directly called to ensure the database is equal to mine.

Your username and password should not matter, but should be included in the /backend/src/main/resources/application.properties file.
My own file is shared as an example. 
#### If you copy any of this work and plan on using it more seriously, NEVER share this file on your git repo! Always use ENV variables.

On with the rest :)

### Backend: Spring boot Java
I've chosen to go ahead with Spring Boot, as it allows very quick deployment of new API calls once you get the hang of it. The maven dependencies can be found in /backend/pom.xml.

Backend code can be run from the /backend folder using command:
$ ./mvnw spring-boot:run

I have incorporated JUnit5 tests, mainly integration tests. They can be found in /backend/src/test/ClientBankPostgreSQLTest.java. Ideally, I would split these tests up more. As I am getting more experienced with these tests, I surely will in the future.

Tests can be run from the /backend folder using command:
$ mvn test

### Frontend: Vue.js
A Javascript framework which allows easy creation of UI design with a router to serve components dynamically based on which buttons are pressed by the user. Truly awesome stuff. I enjoyed it very much.

Can be run from the /frontend/mybank-app/ folder using command:

$ npm run serve

## Design decisions:
Frontend: Vue.js. Initially because I wanted to learn about this framework already, secondly because it is one of the main languages used for the frontend.  

Backend: Spring Boot Java, since this is the most common language used at ABN Amro, this is a fitting choice.  

Database: PostgreSQL, which uses JDBC to connect to Java: https://www.postgresqltutorial.com/postgresql-jdbc/  

## Hardware used:
Tested to work on the Apple M1 ARM-based SoC. Should work on any x86 system as well. Will still test this after the assessment!
##### Tested:
- Macbook Pro 14" 2021 (M1 Pro, 16GB RAM, MacOS Ventura 13.4.1)
##### Will test:
- Mac Mini 2018 (Intel Core i5-8500B, 64GB RAM, MacOS Ventura 13.4.1)

## Additional remarks:
I enjoyed this assignment and given my newfound experience, I would do a lot different. I enjoyed learning about PostgreSQL custom types; which are similar to compound attributes. How to get them to work in Spring Boot was a little too far beyond my knowledge at that time, but given more time with my newly acquired tools, this should be no problem.

I also worked with Spring Boot for the first time, which was rather easy to get working but initially difficult to get familiar with. I am pleased with how much I have gotten done in the short amount of time experimenting with Spring Boot, especially since Java itself has been a while for me.

Finally, working with Vue.js has given me insight into what is possible with proper website/webclient building. Having some experience with Flutter, I find Vue.js to be slightly more appealing since it allows for even cleaner code and faster component creation. I feel I've really learned a great deal but still have a long way to go also.

## Log:

28-07-2023
Start with the assignment. Reading up on connecting a Java backend to a PostgreSQL database.
- Found a tutorial online: https://www.postgresqltutorial.com/postgresql-jdbc/connecting-to-postgresql-database/
- Following this tutorial initially.
- Downloaded the JDBC jar file, stored in Applications folder for later use.
- Installed OpenJDK with homebrew, after which it required symlinking with:  
  sudo ln -sfn $HOMEBREW_PREFIX/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
- Installed pgAdmin to easily manage and oversee the database management in an easy to use GUI.
- Created a new user 'postgres' in the database for connecting to it. Used command: createuser -s postgres.
- Using pgAdmin, I can now connect to the database running locally.
- Created a NetBeans project: Java with Ant, as this is the most basic, plain version. Initially tried Maven but could not easily integrate the JDBC library using online tutorials.
- Made sure the postgreSQL database uses scram-sha-256 user authentication, instead of 'trust' or 'md5'. This is the most secure way currently, but does not support older clients. Not something I would need to currently be bothered with, since only new versions of the clients need to connect.
- Source: https://www.prisma.io/dataguide/postgresql/authentication-and-authorization/configuring-user-authentication
- Added env variable for using the password for the database. Should still be setup in GitHub as well.
- Added alias to run netbeans from terminal; since otherwise the environment variables would not be available to netbeans for some reason.
- Switched to running Maven after all. Much more easy to add new dependencies in the pom.xml file.
- Found it was much easier to just connect to the PostgreSQL database from the terminal, as the pgAdmin tool built-in shell was constantly having formatting issues. No more issues through Terminal.

29-07-2023
- Have been looking into using Springboot instead of plain Java for the backend. I will base my application on the following repository: https://github.com/in28minutes/spring-boot-vuejs-fullstack-examples/tree/master/spring-boot-crud-full-stack
- I have chosen the communication protocol of RESTapi, since the ABN Amro also uses this in other software systems and since I have only limited knowledge on this subject, it is a good learning opportunity.
- https://spring.io/guides/gs/securing-web/ <- attempted, but seems to not have worked for POST. Interesting to add later if there is time, otherwise leave it for now.
- https://www.bezkoder.com/vue-3-crud/ <- continuing with this guide.
- Got a first working version of Fullstack! 

30-07-2023
- Today is dedicated to writing tests, cleaning code and seeing if fixes can still be applied.
- Fixes such as: Security, Login/Logout, Improved Database design, Layout fixes, database merge for clients and bank.
- Fixes that could be applied: Layout fixes, some error handling (client side primarily).
- Improved UI.
- Created integration testing, some unit testing as well.

Sources used frequently:
https://www.bezkoder.com/spring-boot-vue-js-crud-example/
https://www.bezkoder.com/spring-boot-postgresql-example/
https://www.bezkoder.com/spring-boot-vue-js-postgresql/
https://www.bezkoder.com/vue-3-crud/