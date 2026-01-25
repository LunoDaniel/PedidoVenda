# PedidoVenda - Sistema de Pedidos

Sistema de gerenciamento de pedidos desenvolvido com Jakarta EE 10, JSF 4.0, PrimeFaces 12 e WildFly 30.

## Pré-requisitos

### Software Necessário

1. **Java 21 ou superior**
   - Download: https://adoptium.net/
   - Configure JAVA_HOME

2. **Maven 3.6 ou superior**
   - Download: https://maven.apache.org/download.cgi
   - Configure MVN_HOME

3. **Docker Desktop**
   - Download: https://www.docker.com/products/docker-desktop/
   - Certifique-se que o Docker está rodando

4. **Docker Compose**
   - Geralmente vem com o Docker Desktop
   - Ou instale separadamente

### Verificação Automática

Execute o script para verificar se todas as dependências estão instaladas:

```bash
setup-wildfly.bat
```

## Configuração e Execução

### 1. Compilar o Projeto

```bash
mvn clean package
```

### 2. Subir os Containers

```bash
docker-compose -f docker-compose-wildfly.yml up -d
```

### 3. Verificar Status

```bash
check-logs.bat
```

### 4. Acessar a Aplicação

- **URL da Aplicação**: http://localhost:8080/PedidoVenda-1.0.0-SNAPSHOT/
- **Console WildFly**: http://localhost:9990

### 5. Credenciais de Acesso

- **Usuário**: admin
- **Senha**: admin

## Scripts Disponíveis

### setup-wildfly.bat
Verifica se todas as dependências estão instaladas corretamente.

### build-and-deploy.bat
Compila o projeto e faz deploy automaticamente.

### check-logs.bat
Verifica logs dos containers e status da aplicação.

## Estrutura do Projeto

```
PedidoVenda/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/pedidovenda/
│   │   │       ├── controller/     # Beans JSF
│   │   │       ├── model/          # Entidades JPA
│   │   │       ├── repository/     # Repositórios
│   │   │       ├── service/        # Serviços
│   │   │       └── security/       # Configurações de segurança
│   │   ├── resources/
│   │   │   ├── META-INF/
│   │   │   │   └── persistence.xml # Configuração JPA
│   │   │   └── templates/          # Templates de email
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml         # Configuração web
│   │       └── *.xhtml             # Páginas JSF
├── docker/
│   ├── mysql/                      # Configuração MySQL
│   └── wildfly/                    # Configuração WildFly
├── docker-compose-wildfly.yml      # Orquestração containers
└── pom.xml                         # Dependências Maven
```

## Configurações Técnicas

### Banco de Dados
- **SGBD**: MySQL 8.0
- **Database**: pedidovenda
- **Usuário**: app_user
- **Senha**: app_password
- **Porta**: 3306

### Servidor de Aplicação
- **Servidor**: WildFly 30
- **Porta HTTP**: 8080
- **Porta Admin**: 9990
- **Java**: 21

### Tecnologias
- **Jakarta EE**: 10.0.0
- **JSF**: 4.0.0
- **PrimeFaces**: 12.0.0
- **Hibernate**: 6.4.4.Final
- **MySQL Connector**: 8.0.33

## Solução de Problemas

### Container não inicia
```bash
docker-compose -f docker-compose-wildfly.yml down
docker-compose -f docker-compose-wildfly.yml up -d
```

### Verificar logs
```bash
docker logs pedidovenda-wildfly
docker logs pedidovenda-mysql
```

### Problemas de conexão com banco
1. Verifique se o MySQL está rodando
2. Verifique as configurações no `standalone.xml`
3. Teste a conexão: `docker exec pedidovenda-mysql mysql -u app_user -papp_password`

### Problemas de deploy
1. Verifique se o WAR foi gerado: `target/PedidoVenda-1.0.0-SNAPSHOT.war`
2. Verifique os logs do WildFly
3. Acesse o console admin para verificar o status do deploy

## Comandos Úteis

### Docker
```bash
# Ver containers rodando
docker ps

# Ver logs em tempo real
docker logs -f pedidovenda-wildfly

# Parar containers
docker-compose -f docker-compose-wildfly.yml down

# Remover volumes (cuidado: apaga dados)
docker-compose -f docker-compose-wildfly.yml down -v
```

### Maven
```bash
# Compilar
mvn clean package

# Executar testes
mvn test

# Limpar
mvn clean
```

## Desenvolvimento

### Estrutura de Módulos
- **Controller**: Beans JSF para controle das páginas
- **Model**: Entidades JPA para persistência
- **Repository**: Camada de acesso a dados
- **Service**: Lógica de negócio
- **Security**: Configurações de autenticação e autorização

### Padrões Utilizados
- **CDI**: Injeção de dependência
- **JPA/Hibernate**: Persistência
- **JSF**: Interface web
- **PrimeFaces**: Componentes UI
- **Bean Validation**: Validação de dados

## Suporte

Para problemas ou dúvidas:
1. Verifique os logs com `check-logs.bat`
2. Consulte a documentação do WildFly
3. Verifique as configurações nos arquivos XML 