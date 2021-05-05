#!/bin/bash
# This sript is intend to clean 'postgres' database to perform queries further

# sed -E 's,([0-9]{2})\.([0-9]{2})\.([0-9]{4}),\3-\2-\1,g' postgres_create.sql > improved


PGDATABASE=postgres
PGUSER=postgres
PGPASSWORD=postgres

dropdb -U $PGUSER $PGDATABASE
createdb -U $PGUSER $PGDATABASE