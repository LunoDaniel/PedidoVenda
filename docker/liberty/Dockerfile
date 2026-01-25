FROM openliberty/open-liberty:full-java21-openj9-ubi-minimal

# Copiar o driver MySQL para Liberty
COPY --chown=1001:0 mysql-connector-j-8.0.33.jar /opt/ol/wlp/usr/shared/resources/mysql/

# Copiar configuração do servidor Liberty
COPY --chown=1001:0 server.xml /opt/ol/wlp/usr/servers/defaultServer/

# Copiar a aplicação WAR (copiado para o diretório via volume no docker-compose)
# O WAR será montado como volume no docker-compose

# Expor portas
EXPOSE 9080 9443

# Configurar para rodar como servidor (não dev mode)
ENV WLP_LOGGING_CONSOLE_FORMAT=basic
ENV WLP_LOGGING_CONSOLE_LOGLEVEL=info
ENV WLP_LOGGING_CONSOLE_SOURCE=message,trace,accessLog,ffdc,audit

# Comando para iniciar o servidor
CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]
