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

### Steps to access postgres container as superuser

$ docker exec -it postgres_dev_container /bin/bash

$ psql --host=database --username=postgres_user --dbname=postgres_database --> password in .env file
