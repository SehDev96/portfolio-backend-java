###Steps to run backend code

$ git clone git@github.com:SehDev96/portfolio-backend-java.git

$ cd docker

$ docker-compose up -d

----------------------------------------------------------------------------------

###Steps to access postgres container as superuser

$ docker exec -it shutterfind_container /bin/bash

$ psql --host=database --username=shutterfind_user --dbname=shutterfind_database --> password in .env file