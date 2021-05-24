FROM postgres:13.2-alpine

ENV POSTGRES_PASSWORD amithsai
ENV APP_DB_NAME ecommerce_db_test
EXPOSE 5432

COPY ./init.sh /docker-entrypoint-initdb.d/