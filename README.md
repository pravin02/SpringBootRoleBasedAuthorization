# SpringBootRoleBasedAuthorization
Spring boot application with role based authorization with mysql database

Libraries,
1) Java 8
2) Spring Boot
3) Spring Security
4) Mysql Database

Their are total 3 Roles,
1) ROLE_USER
2) ROLE_ADMIN
3) ROLE_MODERATOR

Rest End Points to Test
1) http://localhost:8080/api/users/join
 - To register new user in system, Default Role will be ROLE_USER

2) http://localhost:8080/api/users
 - To view registered user name with system

3) http://localhost:8080/api/users/access/{userId}/{userRole}
 - To give access to users if current user have sufficient Role

