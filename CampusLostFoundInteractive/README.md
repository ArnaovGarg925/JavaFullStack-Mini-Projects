# Campus Lost & Found

## Java Mini Project

**Project Title:** Campus Lost & Found  
**Developer:** Arnaov Garg  
**Subject:** Java Full Stack

### About

A Java Swing desktop application for managing lost and found items on a college campus.

### Features

- Create account and login
- Separate Lost Items and Found Items sections
- Add lost/found reports
- Edit and delete reports
- Search/filter items
- Item status management
- Claim found items
- Admin view
- Spring IoC / Dependency Injection
- Maven build
- JUnit test
- Local file storage

### Data Storage

Account and item information is stored locally in the project's `data/` directory. This version does **not** require MySQL.

### Prerequisites

- JDK 24 or compatible JDK
- Apache Maven 3.9.x

### Run

Open Command Prompt in the project folder:

```bash
mvn clean package
mvn exec:java
```

The application opens with the login screen.

### Admin Login

```text
Email: admin@campus.com
Password: admin123
```

### Project Structure

```text
CampusLostFoundInteractive/
├── src/
│   ├── main/java/com/campus/lostfound/
│   └── test/java/com/campus/lostfound/
├── data/
├── pom.xml
├── README.md
└── .gitignore
```

### Developer

Arnaov Garg
