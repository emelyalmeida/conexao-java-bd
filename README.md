# Conexão Java com Banco de Dados — Estacionamento

Projeto em Java com Maven, JDBC, MySQL e Swing para gerenciamento de proprietários e veículos.

## Tecnologias

- Java 17
- Maven
- MySQL
- JDBC (MySQL Connector/J 8.4.0)
- Java Swing

## Funcionalidades

- Cadastro, atualização, listagem e exclusão de proprietários.
- Cadastro, atualização, listagem e exclusão de veículos.
- Interface gráfica com abas para proprietários e veículos.
- Contagem de veículos por proprietário usando função SQL.
- Cadastro de proprietário usando procedure.
- Padronização de dados de veículos por trigger.
- Consultas, JOINs, UPDATE e DELETE no script SQL.

## Estrutura

```
conexao-java-bd/
├── pom.xml
├── README.md
├── .gitignore
├── sql/
│   └── banco.sql
└── src/
    └── main/
        └── java/
            └── projeto/
                ├── Conexao.java
                ├── exemploCrud.java
                ├── dao/
                │   ├── ProprietarioDAO.java
                │   └── VeiculoDAO.java
                ├── model/
                │   ├── Proprietario.java
                │   └── Veiculo.java
                └── view/
                    └── TelaCadastro.java
```

## Como executar

### 1. Pré-requisitos

Instale:

- JDK 17 ou superior.
- Maven.
- MySQL Server.
- Uma IDE Java, como IntelliJ IDEA, Eclipse ou NetBeans.

### 2. Criar o banco

Abra o MySQL Workbench ou outro cliente MySQL e execute o arquivo:

`sql/banco.sql`

> Atenção: o script começa com `drop database if exists db_estacionamento`, portanto ele recria o banco e pode apagar uma versão anterior.

### 3. Configurar a conexão

A aplicação lê as configurações pelas variáveis de ambiente:

- `DB_URL` — padrão: `jdbc:mysql://localhost:3306/db_estacionamento`
- `DB_USER` — padrão: `root`
- `DB_PASSWORD` — senha do usuário MySQL

Exemplo no Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/db_estacionamento"
$env:DB_USER="root"
$env:DB_PASSWORD="SUA_SENHA"
```

A senha do banco não fica armazenada no código-fonte.

### 4. Executar

Abra o projeto como projeto Maven na IDE e execute:

`src/main/java/projeto/exemploCrud.java`

A janela de cadastro será aberta após a conexão com o banco.

## Observação

O projeto foi organizado para estudo de integração entre Java e banco de dados, incluindo operações CRUD, DAO, procedure, function, trigger e interface gráfica Swing.
