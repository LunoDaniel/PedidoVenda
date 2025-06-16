# Usa a imagem oficial do MySQL 8.0
FROM mysql:8.0

# Configurações seguras (evitando warnings)
ARG MYSQL_ROOT_PASSWORD=root
ARG MYSQL_DATABASE=pedidovenda
ARG MYSQL_USER=app_user
ARG MYSQL_PASSWORD=app_password

# Copia scripts de inicialização
COPY ./docker/mysql/sql/init.sql /docker-entrypoint-initdb.d/
COPY ./docker/mysql/conf.d/my.cnf /etc/mysql/conf.d/

# Expõe a porta padrão do MySQL
EXPOSE 3306

# Define variáveis de ambiente no final (melhor prática)
ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
ENV MYSQL_DATABASE=${MYSQL_DATABASE}
ENV MYSQL_USER=${MYSQL_USER}
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}