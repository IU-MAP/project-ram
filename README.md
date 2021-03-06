# Innopolis University MAP-S21 Project

## Contents

* [Context](#context)
* [Technology stack](#technology-stack)
* [Initial Product Backlog](#initial-product-backlog)
* [Sprints](#sprints)
* [Team Members](#team-members)
* [Useful Links](#useful-links)

## Context

A simple database management system which designed to manage hospital. The system should be able to keep track of the records of the doctors, patients, nurses, and other hospital staff. The main aim is to minimize the paperwork of the hospital as minimum as possible, if not completely.

**[Final report](https://github.com/IU-MAP/project-ram/blob/master/MAP%20Report.pdf)**

## Technology stack

* Database - PostgreSQL
* Database management - Java/Python
* User Interface - Java
* Tests - Python

## Initial Product Backlog

### Initial Client Features

The initial functionalities requested are as follows:

1. Multi user account system

2. Monitoring the whole hospital system

3. Management of all types of users’ account

4. Notice Board

5. Appointment Management

6. View Appointments

7. Notifications

8. Medical History

9. Invoice Management

10. Medical Report Management

11. Internal Communication

12. Responsive User Interfaces

## Setup Java
1. Clone repository
2. Setup SDK 11 version in IntelliJ
3. Go to File -> Project Structure -> Modules -> '+' -> JARs or Directories
4. Select `project-ram/libs/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib`
5. Go to File -> Project Structure -> Libraries -> '+' -> Java
6. Select `project-ram/libs/postgresql-42.2.20.jar`
7. Click Apply and OK
8. Run 'GUI_Main.main()'
9. Go to Run -> Edit Configuration -> Modify Options -> Add VM Options
10. Add this: `<path-to-folder>/project-ram/libs/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib`

## Setup Database
1. Install PostgreSQL `sudo apt install postgresql`
2. Open file `/etc/postgresql/10/main/pg_hba.conf`
3. Replace _md5_ with _trust_
```bash
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
```

```bash
# IPv4 local connections:
host    all             all             127.0.0.1/32            trust
```
4. Restart service `sudo service postgresql restart`
5. Populate database by running `cd project-ram/sql && ./populate.sh`
6. (Optional) To delete database `cd project-ram/sql && ./drop.sh`

## Sprints

### Sprint 0

1. The project "Hospital management system" was selected

2. Updated the Readme page

3. Selected product owner: Renat Valeev

4. Selected technologies to be used: PosgreSQL, Python, Java

5. Determined the Initial Client Features

6. Wrote a simple program to make sure everything is correct

### Sprint 1

1. Finish work from Sprint0 (created branch policy, [user story] and [task] templates)

2. Created database schema

3. Created Use case diagram of the project

4. Update documentation

![use_case_diagram](https://github.com/IU-MAP/project-ram/blob/master/images/use_case_diagram.png)

### Sprint 2

1. Finish work from Sprint 1

2. Read and understand Continuous integration in github (curret issue: never worked with GitHub Action, need more time to get used to it)

3. Made initial user interface

4. Update documentation

![mvp](https://github.com/IU-MAP/project-ram/blob/master/images/mvp.png)

### Sprint 3

1. Fill the database by random values (took files from the Internet with random names and surnames)

### Sprint 4

1. Connected database to java

2. Generated data for tests

### Sprint 5

1. Finilize GUI

2. Add tests for app

3. Update documentation 

Relational Scheme: 

![relational_scheme](https://github.com/IU-MAP/project-ram/blob/dev_alfiya/images/Relational_Scheme.png)

GUI:

![gui](https://github.com/IU-MAP/project-ram/blob/dev_alfiya/images/Screenshot%20from%202021-05-11%2016-47-55.png)

Text areas are name, surname, login, age, role.

Buttons are for clean data and to perform search query by name, surname, login, age, role.





## Team Members

* Alfiya Musabekova <a.musabekova@innopolis.university>

* Renat Valeev <r.valeev@innopolis.university>

* Marina Nikolaeva <m.nikolaeva@innopolis.university>

## Useful Links

* [The materials for the course](http://bit.ly/innopolis-map)
* [Trello](https://trello.com/b/JTL05fyN)

