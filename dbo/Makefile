help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

init: clean up ## Initialize the project execution environment (compiles and starts docker containers)

up: ## Start all containers
	mkdir -p data
	docker compose up -d 

stop: ## Stop all container
	docker compose stop

down: ## Down all container
	docker compose down

clean: ## Down and clean colume
	docker compose down -v 

load-sakila-db: ## Drop and Load Sakila db
	docker compose exec -w /opt/data/ db sh -c 'gunzip < /opt/data/sakila-db.sql.gz  | mysql -uroot  -p$$MYSQL_ROOT_PASSWORD'

bash: ## Enter commandline
	docker compose exec db bash
