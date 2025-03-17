Spring Boot Multiple Data Sources
This project demonstrates how to configure and use multiple data sources in a Spring Boot application. It includes examples of setting up different data sources, configuring them, and using them in your application.

Features
Multiple Data Sources: Configure and use multiple data sources in a single Spring Boot application.
Spring Data JPA: Utilize Spring Data JPA for database interactions.
HikariCP: Use HikariCP as the connection pool for efficient database connections.
Entity Management: Manage entities across different data sources.

Getting Started
Prerequisites
Java 21 or higher
Maven
Spring Boot 3.x

Installation
Clone the repository:
git clone https://github.com/anascreations/spring-boot-multiple-datasources.git

Navigate to the project directory:
cd spring-boot-multiple-datasources

Build the project:
mvn clean install

Configuration
Update application.yml file with your database configurations.
set environment variable for below:
1)${TARGET_HOST} - target ip address
2)${SOURCE_HOST} - source ip address

Running the Application
Run the application using the following command:
mvn spring-boot:run

Contributing:
Contributions are welcome! Please fork the repository and submit a pull request.

License:
This project is licensed under the MIT License.
Feel free to customize this description to better fit your project's specifics. Let me know if you need any more help!
