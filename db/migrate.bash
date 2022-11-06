#!/bin/bash
echo "About to migrate it all.."

schemas="user todo"

for schema in $schemas
do
    echo "Migrating $schema schema.."
    docker run --rm -v "$PWD/schemas/$schema:/flyway/sql" \
        flyway/flyway -url="jdbc:postgresql://db-postgresql-fra1-08861-do-user-12816929-0.b.db.ondigitalocean.com:25060/experimental-db" \
        -schemas="$schema" \
        -user="experimental-user" \
        -password="$DB_PASSWORD" \
        migrate
done