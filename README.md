# ABN Amro assessment assignment
Repository containing my solution to the programming assessment provided to me by ABN Amro.

## Requirements:
Placeholder. This section will describe how to create a working environment for the assignment.

## Usage:
Placeholder. This section will describe the steps to get the small application running.

## Design decisions:
Frontend: Vue.js. Initially because I wanted to learn about this framework already, secondly because it is one of the main languages used for the frontend.  

Backend: Java, since this is the most common language used at ABN Amro, this is a fitting choice.  

Database: PostgreSQL, which uses JDBC to connect to Java: https://www.postgresqltutorial.com/postgresql-jdbc/  

## Hardware used:
Tested to work on both Intel x86/x46 based CPU as well as the Apple M1 ARM-based SoC.
- Mac Mini 2018 (Intel Core i5-8500B, 64GB RAM, MacOS Ventura 13.4.1)
- Macbook Pro 14" 2021 (M1 Pro, 16GB RAM, MacOS Ventura 13.4.1)

## Additional remarks:
Placeholder. This section will describe any additional remarks.

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
- Have been looking into using Springboot instead of plain Java for the backend. I will base my application on the following repository: https://github.com/in28minutes/spring-boot-vuejs-fullstack-examples/tree/master/spring-boot-crud-full-stack
- I have chosen the communication protocol of RESTapi, since the ABN Amro also uses this in other software systems and since I have only limited knowledge on this subject, it is a good learning opportunity.
- Looked into using mvnw (mvn wrapper) since the guide at https://spring.io/guides/gs/rest-service/ requires its use.
- Then I continued using the following source: https://www.bezkoder.com/spring-boot-postgresql-example/
- 