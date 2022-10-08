# Java Backend with Postgres Database 

This is a REST Api server which connects to postgres database. Once the server is up, it runs at http://localhost:8080. 

---

### Steps to run backend code

$ git clone git@github.com:SehDev96/portfolio-backend-java.git

$ cd docker

$ docker-compose up -d

---

### Steps to run backend code for development 

Only postgres container will run. The java server should be opened and run in IDE. 

$ git clone git@github.com:SehDev96/portfolio-backend-java.git

$ cd docker

$ docker-compose -f dev-docker-compose.yml up -d

---
