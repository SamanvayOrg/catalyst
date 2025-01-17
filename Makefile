help:
	@IFS=$$'\n' ; \
	help_lines=(`fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//'`); \
	for help_line in $${help_lines[@]}; do \
	    IFS=$$'#' ; \
	    help_split=($$help_line) ; \
	    help_command=`echo $${help_split[0]} | sed -e 's/^ *//' -e 's/ *$$//'` ; \
	    help_info=`echo $${help_split[2]} | sed -e 's/^ *//' -e 's/ *$$//'` ; \
	    printf "%-30s %s\n" $$help_command $$help_info ; \
	done

SU:=$(shell id -un)
DB=commengage
TEST_DB=commengage_test
ADMIN_USER=commengage
postgres_user := $(shell id -un)

define _build_db
	-psql -h localhost -U $(SU) -d postgres -c "create user $(ADMIN_USER) with password 'password' createrole";
	-psql -h localhost -U $(SU) -d postgres -c 'create database $1 with owner $(ADMIN_USER)';
	-psql -h localhost -U $(SU) -d $1 -c 'create extension if not exists "uuid-ossp"';
endef

define _drop_db
    -psql postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$1' AND pid <> pg_backend_pid()"
    -psql postgres -c 'drop database $1';
endef

define _run_server
	java -jar server/build/libs/server-0.0.1-SNAPSHOT.jar --app.cron.main="0/3 * * * * ?" --app.cron.full.error="0 1 * * * ?"
endef


######## DATABASE LOCAL
# hashed password when password is password = $2a$10$RipvsoEJg4PtXOExTjg7Eu2WzHH1SBntIkuR.bzmZeU2TrbQoFtMW
# kept here for emergency purposes as we are not developing the entire login functionality
rebuild-db: drop-db build-db

build-db:
	$(call _build_db,$(DB))

drop-db:
	$(call _drop_db,$(DB))

create-test-db:
	$(call _build_db,$(TEST_DB))

drop-test-db:
	$(call _drop_db,$(TEST_DB))

rebuild-test-db: drop-test-db create-test-db

drop-roles:
	-psql -h localhost -U $(SU) -d postgres -c 'drop role $(ADMIN_USER)';
#######

####### BUILD, TEST, LOCAL RUN
build-server: ## Builds the jar file
	./gradlew clean build -x test

setup-log-dir:
	-sudo mkdir /var/log/abi
	-sudo chown $(SU) /var/log/abi

run-server: build-db build-server
	$(call _run_server)

run-server-without-background: build-server
	java -jar server/build/libs/server-0.0.1-SNAPSHOT.jar --commengage.main.cron="0 3 0 28 2 *"

test-server: drop-test-db create-test-db
	./gradlew clean build

setup-external-test-db: drop-test-db create-test-db
	sudo -u ${postgres_user} psql $(DB) -f dump.sql

test-server-external: drop-test-db setup-external-test-db
	./gradlew clean build

open-test-results-server:
	open server/build/reports/tests/test/index.html
#######


####### Tunnels
tunnel-server-debug-vagrant:
	ssh -p 2222 -i ~/.vagrant.d/insecure_private_key vagrant@127.0.0.1 -L 6031:localhost:6031
#######


####### SOURCE CONTROL
tag-release:
ifndef version
	$(error ERROR: version not provided.)
endif
	git tag -a v$(version) -m "version $(version)"
	git push origin --tags
#######


####### Deployment
deploy-to-vagrant-only:
	echo vagrant | pbcopy
	scp -P 2222 -i ~/.vagrant.d/insecure_private_key server/build/libs/server-0.0.1-SNAPSHOT.jar root@127.0.0.1:/root

deploy-to-vagrant: build-server deploy-to-vagrant-only
#######

#Production management
deploy-to-prod: build-server
	scp server/build/libs/server-0.0.1-SNAPSHOT.jar commeng:/tmp
	ssh commeng "sudo cp /tmp/server-0.0.1-SNAPSHOT.jar /root/source/comm-engage-service/"

start_service_prod:
	ssh commeng "sudo systemctl start comm-engage.service"

stop_service_prod:
	ssh commeng "sudo systemctl stop comm-engage.service"

restart_service_prod:
	ssh commeng "sudo systemctl restart comm-engage.service"

backup-db-prod:
	ssh commeng "pg_dump -Ucommengage -hlocalhost commengage > /tmp/backup_$(shell date +%a).sql"
