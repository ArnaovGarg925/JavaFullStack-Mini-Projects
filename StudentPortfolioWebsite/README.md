# Student Portfolio Website

A bright, responsive five-page website built with Java, Spring Boot, Spring Web, HTML, CSS, and a small amount of JavaScript.

## Pages

1. **Home** — `/`
2. **Creator** — `/about.html`
3. **Website Overview** — `/product.html`
4. **Features** — `/features.html`
5. **Contact** — `/contact.html`

The site uses static files served by Spring Boot. Thymeleaf, JPA, and a database are not used.

## Technology

- Java 17+
- Spring Boot 3.5.5
- Spring Web
- HTML5 and CSS3
- Vanilla JavaScript

## Run in IntelliJ IDEA, Eclipse, or VS Code

1. Install a JDK (Java 17 or later) and Maven, or use an IDE with Maven support.
2. Open the `StudentPortfolioWebsite` folder as a Maven project.
3. Wait for Maven dependencies to download.
4. Run `src/main/java/com/studentportfolio/StudentPortfolioApplication.java`.
5. Open **http://localhost:8080/** in your browser.

### Run from a terminal

From the project root, run:

```bash
mvn spring-boot:run
```

Then visit `http://localhost:8080/`.

If Maven is not installed but the IDE includes Maven support, use the IDE's Maven panel to run the `spring-boot:run` goal.

## Personalization

The Creator page deliberately uses a sample profile rather than presenting a full real-life biography. The Contact page displays the supplied creator name, and leaves email and phone as placeholders. Replace those placeholders only if you want to publish actual contact information.

## Project structure

```text
StudentPortfolioWebsite/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/studentportfolio/
        │       └── StudentPortfolioApplication.java
        └── resources/
            └── static/
                ├── index.html
                ├── about.html
                ├── product.html
                ├── features.html
                ├── contact.html
                ├── css/
                │   └── style.css
                └── js/
                    └── main.js
```
