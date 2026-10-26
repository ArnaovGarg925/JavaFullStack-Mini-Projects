# Employee Management System — Mini Project with Web Pages

A full-stack Spring Boot web application featuring dynamic Thymeleaf server-side rendering, role-based Spring Security, HMAC-SHA256 JSON Web Token (JWT) integration, and in-memory `ArrayList` data persistence.

Developed as **Module 3 (Mini Project with Web Pages)** for Java Full Stack.

---

## 📋 Features

1. **Employee CRUD Operations:**
   - Full lifecycle management: Add new employees, view comprehensive profiles, update information, and delete records.
2. **Department Relationship:**
   - Each employee is linked directly to an organizational department.
   - Dynamic department dropdowns and integrity validation before deleting departments.
3. **In-Memory ArrayList Storage:**
   - As per project specifications, data is maintained in Java `ArrayList` collections inside `EmployeeService` and `DepartmentService`.
4. **Spring Security & Authentication:**
   - Custom login screen (`login.html`) with session management, flash alerts, and logout invalidation.
   - Role-based authorization (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_USER`).
5. **Spring JWT Integration:**
   - Built with `jjwt-api`, `jjwt-impl`, and `jjwt-jackson` (configured manually in `pom.xml`).
   - `JwtService.java` generates and verifies 256-bit signed tokens.
   - Real-time token display on the dashboard plus dedicated REST endpoint at `/api/jwt/token`.
6. **Executive Dashboard & Analytics (Bonus Features):**
   - KPI metric cards: Total staff, department count, total payroll, and average salary.
   - Live search bar and department filter on the employee directory.
   - Responsive CSS layout with status and role badges.

---

## 🛠️ Technologies & Dependencies

| Technology | Role |
| :--- | :--- |
| **Java 17+** | Core programming language |
| **Spring Boot 3.3.4** | Application framework |
| **Spring MVC** | Model-View-Controller request routing and attribute binding |
| **Spring Security** | Authentication, authorization, and route protection |
| **JJWT (0.12.5)** | JWT creation, HMAC-SHA256 signing, and validation |
| **Thymeleaf** | Server-side template rendering |
| **CSS3** | Custom responsive styling (`style.css`) |
| **Maven** | Dependency management and build lifecycle |

---

## 📂 Project Structure

```text
employee-management-web/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/example/
        │   ├── EmployeeManagementWebApplication.java
        │   ├── controller/
        │   │   ├── DashboardController.java
        │   │   ├── DepartmentController.java
        │   │   ├── EmployeeController.java
        │   │   └── LoginController.java
        │   ├── model/
        │   │   ├── Department.java
        │   │   ├── Employee.java
        │   │   └── Role.java
        │   ├── security/
        │   │   ├── JwtService.java
        │   │   └── SecurityConfig.java
        │   └── service/
        │       ├── DepartmentService.java
        │       └── EmployeeService.java
        └── resources/
            ├── static/
            │   └── style.css
            └── templates/
                ├── dashboard.html
                ├── department.html
                ├── employee-form.html
                ├── employees.html
                └── login.html
```

---

## 🔑 Demo Credentials

| Username | Password | Role | Permissions |
| :--- | :--- | :--- | :--- |
| `admin` | `admin123` | `ROLE_ADMIN` | Full access (View, Create, Edit, Delete) |
| `manager` | `manager123` | `ROLE_MANAGER` | View, Create, and Edit |
| `user` | `user123` | `ROLE_USER` | View and Browse |

---

## 🚀 How to Run

### Option 1: Running from Terminal

Navigate to the project directory and run with Maven:

```bash
cd employee-management-web
mvn spring-boot:run
```

Once started, open your web browser and navigate to:
👉 **`http://localhost:8080/`** (or `http://localhost:8080/login`)

### Option 2: Running in IntelliJ IDEA / Eclipse / VS Code

1. Open `employee-management-web` as a Maven project.
2. Allow Maven to import dependencies.
3. Run `src/main/java/com/example/EmployeeManagementWebApplication.java`.
4. Open `http://localhost:8080/` in your browser.
