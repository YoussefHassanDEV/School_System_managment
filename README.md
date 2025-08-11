1. Project Overview
The Student Management System is a Spring Boot REST API that manages:

Users (SUPER_MANAGER, MANAGER, TEACHER, STUDENT)

Managers, Teachers, Students

Subjects

Exams

Student enrollment and promotion

It uses JWT-based authentication and role-based authorization with role hierarchy:

nginx
Copy
Edit
SUPER_MANAGER > MANAGER > TEACHER > STUDENT
Meaning higher roles inherit permissions from lower ones.

2. Features & Technologies
Features:

User registration & login with JWT authentication

Role-based access control with hierarchy

CRUD operations for Managers, Teachers, Students

Student subject enrollment and promotion

Exam creation and result tracking

Password encryption using BCrypt

Protection of endpoints based on user role

Tech stack:

Java 17+

Spring Boot 3.x

Spring Security 6+

JWT (io.jsonwebtoken)

Spring Data JPA + Hibernate

H2/MySQL/PostgreSQL (configurable)

Lombok

Maven

3. Authentication & Role Hierarchy
Roles:

SUPER_MANAGER: Can manage managers, teachers, students, exams

MANAGER: Can manage teachers, students, exams

TEACHER: Can manage students, exams

STUDENT: Can view GPA, level, take exams

Hierarchy:

text
Copy
Edit
ROLE_SUPER_MANAGER > ROLE_MANAGER
ROLE_MANAGER > ROLE_TEACHER
ROLE_TEACHER > ROLE_STUDENT
JWT authentication flow:

Sign up (/api/auth/signup)

Login (/api/auth/login) → Receive JWT

Send JWT in Authorization: Bearer <token> header for protected endpoints

4. Installation & Setup
Prerequisites
Java 17 or higher

Maven 3.6+

Git

(Optional) MySQL/PostgreSQL if not using H2

Clone the repository
bash
Copy
Edit
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
Configure application properties
Edit src/main/resources/application.properties:

properties
Copy
Edit
server.port=8000

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_256bit_secret_key_here
jwt.expiration=86400000 # 1 day in ms
For MySQL:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
Build & Run
bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Server will start at: http://localhost:8000

5. API Documentation
Auth APIs
Method	Endpoint	Role	Description
POST	/api/auth/signup	PUBLIC	Register a new user (specify role)
POST	/api/auth/login	PUBLIC	Login and get JWT token

Signup Example:

json
Copy
Edit
{
  "name": "John Manager",
  "username": "john123",
  "password": "pass123",
  "role": "MANAGER"
}
Login Example:

json
Copy
Edit
{
  "username": "john123",
  "password": "pass123"
}
Manager APIs
(Accessible by ROLE_MANAGER and above)

Method	Endpoint	Description
GET	/api/managers	Get all managers
GET	/api/managers/{id}	Get manager by ID
POST	/api/managers	Create a new manager
PUT	/api/managers/{id}	Update manager
DELETE	/api/managers/{id}	Delete manager

Teacher APIs
(Accessible by ROLE_TEACHER and above)

Method	Endpoint	Description
GET	/api/teachers	Get all teachers
GET	/api/teachers/{id}	Get teacher by ID
POST	/api/teachers	Create a new teacher
PUT	/api/teachers/{id}	Update teacher
DELETE	/api/teachers/{id}	Delete teacher

Student APIs
(Accessible by ROLE_STUDENT and above)

Method	Endpoint	Description
GET	/api/students	Get all students
POST	/api/students	Create student
PUT	/api/students/{id}	Update student
DELETE	/api/students/{id}	Delete student
POST	/api/students/{studentId}/subjects/{subjectId}	Add subject to student
POST	/api/students/{studentId}/promote	Promote student level

Exam APIs
(Accessible by ROLE_STUDENT and above)

Method	Endpoint	Description
POST	/api/exams/create	Create exam result
GET	/api/exams?studentId={sid}&subjectId={subid}	Get exam results for student & subject

6. Usage Flow
SUPER_MANAGER creates managers.

Managers create teachers.

Teachers create students.

Students enroll in subjects and take exams.

Passing all subjects in a level promotes a student.

7. Important Notes
Always send JWT in Authorization: Bearer <token> header.

Passwords are stored encrypted (BCrypt).

SUPER_MANAGER can do everything MANAGER, TEACHER, and STUDENT can.

You can change DB from H2 to MySQL/PostgreSQL in application.properties.

Role hierarchy is defined in SecurityConfig and applied to all endpoints.

