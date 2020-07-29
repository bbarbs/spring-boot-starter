## Getting Started
This is a sample token authentication project using JWT. This project show how to manage both the roles and privileges/permissions of the user.

### Feature
* [jjwt](https://github.com/jwtk/jjwt)
* [redis](https://redis.io/)
* Spring AOP(for logging) and also using ApplicationListener for event logging.

### Prerequisites
* PostgreSQL
* redis
* Java JDK 11

### Installing
Since we are using redis to store token make sure to run the redis server.
For windows you can download it [here](https://github.com/dmajkic/redis/downloads)

### Configuration
Create database and schema.
```
database = avocadodb
schema = avocado
```
By default, ROLE and PRIVILEGE is persisted in the db during application start up refer to AppStartUpListener.java.

## Testing 
* API for create employee and adding role and login afterwards
```
POST - http://localhost:8080/v1/api/employees - Create employee
Request Payload
{
    "email":"my@gmail.com",
    "password": "test",
    "firstName": "test",
    "lastName": "test",
    "enabled": true
}
PUT - http://localhost:8080/v1/api/employees/{employeeId}/roles/{roleId} - Add role to employee
GET - http://localhost:8080/v1/api/roles - Get roles
POST - http://localhost:8080/login - Login
Request Payload
{
    "email": "my@gmail.com",
    "password": "test"
}
```
* After that you can proceed to authentication and authorization process.

### Built With
* Spring Boot
* Gradle
