### Steps to run backend code

$ git clone git@github.com:SehDev96/portfolio-backend-java.git

$ cd docker

$ docker-compose up -d

---

### Steps to access postgres container as superuser

$ docker exec -it postgres_dev_container /bin/bash

$ psql --host=database --username=postgres_user --dbname=postgres_database --> password in .env file