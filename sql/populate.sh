#!/bin/bash

psql -h 127.0.0.1 -d postgres -U postgres -f create.sql &&
psql -h 127.0.0.1 -d postgres -U postgres -f postgres_insert.sql
