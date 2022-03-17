## Setup Server

#### Install required software
1. Install Java version 15
2. Install Postgres 12
3. Create database user `create user commengage with password 'password' createrole`
4. Create a database with above user as owner `create database commengage with owner commengage`
5. Install UUID generator extension `create extension if not exists "uuid-ossp"`

Command 3-5 can be run via postgres user/role.

#### Deploy build
1. Download the jar from - https://github.com/SamanvayOrg/comm-engage/releases/download/0.1/server-0.0.1-SNAPSHOT.jar

#### Configure service
1. Define environment variable `export QRD_AUTH_TOKEN=TOKEN_VALUE`
2. Define environment variable `export COMMENGAGE_MAIN_CRON="0 0 * * * *"` This will make the data download run every hour. You can change the cron express to run it whenever you need it.

#### Recommendation
We want to make sure that the download is not happening when new QR codes are being defined. One way to make sure that this doesn't happen is to run the scheduled job only in the night. 

## Build
In case you want to create a new build by making some changes to the code you can do the following. Postgres server needs to be running on your machine for running the build. For details please see the Makefile.
1. `make build-server`
2. `make test-server` for running unit tests
