# E-Commerce WebApp (JSP/Servlet + Maven)

A minimal, production-ready demo e-commerce web application using classic Java Servlets and JSP, packaged as a WAR for Apache Tomcat (port 8080).

## Requirements
- Java 11+ (JDK)
- Apache Maven 3.6+
- Apache Tomcat 9.x (Servlet 4.0, **javax** namespace). Also works on Tomcat 8.5.
  - For Tomcat 10+, use the migration tool to switch to `jakarta.*` packages or deploy on Tomcat 9.

## Build
```bash
mvn clean package
```
The WAR will be created at: `target/webapp.war`

## Deploy
Copy `target/webapp.war` into Tomcat's `webapps/` folder and start Tomcat.

Then open:
- Products: http://localhost:8080/webapp/products
- Cart:     http://localhost:8080/webapp/cart
- Health:   http://localhost:8080/webapp/health

## Features
- In-memory product catalog (seeded at startup)
- Session-based shopping cart (add/update/remove)
- Simple checkout confirmation
- Clean JSPs with JSTL, basic CSS
- Health endpoint for smoke tests

## Default Credentials / Data
No login. All data is in-memory for demo purposes.

## Project Layout
```
src/main/java/...        # Java sources
src/main/webapp          # Static assets and JSPs
src/main/webapp/WEB-INF  # web.xml and JSPs under /WEB-INF/jsp
```

## Notes
- This is deliberately simple and dependency-light for clarity and easy deployment.
- If you must run on Tomcat 10/10.1 (Jakarta EE 9/10), run the Tomcat migration tool on the WAR to transform `javax.*` to `jakarta.*`.
