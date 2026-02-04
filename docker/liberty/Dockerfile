########################################
# STAGE 1 – Build da aplicação com Maven
########################################
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

# Copia pom.xml primeiro (melhora cache)
COPY pom.xml .

# Baixa dependências
RUN mvn -B -q dependency:go-offline

# Copia o código da aplicação
COPY src ./src

# Gera o WAR
RUN mvn -B clean package -DskipTests


########################################
# STAGE 2 – Runtime Open Liberty
########################################
FROM openliberty/open-liberty:full-java17-openj9

# Copiar driver MySQL
COPY --chown=1001:0 mysql-connector-j-8.0.33.jar \
    /opt/ol/wlp/usr/shared/resources/mysql/

# Copiar configuração do servidor Liberty
COPY --chown=1001:0 server.xml \
    /opt/ol/wlp/usr/servers/defaultServer/

# Copiar o WAR gerado no stage de build
COPY --chown=1001:0 \
    --from=build /build/target/*.war \
    /opt/ol/wlp/usr/servers/defaultServer/apps/

# Expor portas
EXPOSE 9080 9443

# Configurações de log
ENV WLP_LOGGING_CONSOLE_FORMAT=basic
ENV WLP_LOGGING_CONSOLE_LOGLEVEL=info
ENV WLP_LOGGING_CONSOLE_SOURCE=message,trace,accessLog,ffdc,audit

# Iniciar o servidor
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]