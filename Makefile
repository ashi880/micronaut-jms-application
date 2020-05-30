SHELL=bash

.PHONY: clean build build-mq start start-mq stop stop-mq

clean: stop
	@docker container ls -q --filter status=exited --filter status=created | docker rm || true
	@docker image rm "rma/mq" || true
	@docker image rm "rma/micronaut-jms-mq" || true

build:
	@docker build -f Dockerfile -t "rma/micronaut-jms-mq" .

build-mq:
	@docker build -f Dockerfile-mq -t "rma/mq" .

start: build build-mq
	@docker-compose up

start-mq: build-mq
	@docker-compose up mqserver

stop:
	@docker-compose down

stop-mq:
	@docker-compose down mqserver
