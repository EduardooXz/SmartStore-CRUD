# 🛒 SmartStore

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Workbench-blue?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Project-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![Status](https://img.shields.io/badge/Status-Acad%C3%AAmico-yellow?style=for-the-badge)

Sistema CRUD de gerenciamento de produtos desenvolvido em Java com integração a banco de dados MySQL, como parte da disciplina de Banco de Dados.

---

## 📌 Sobre o projeto

O **SmartStore** é uma aplicação de console que permite o gerenciamento de produtos e categorias de uma loja, realizando operações completas de CRUD (Create, Read, Update e Delete).

O sistema foi desenvolvido aplicando conceitos de:

- Programação Orientada a Objetos (POO)
- Integração com banco de dados (JDBC)
- Organização em camadas
- Manipulação de dados via SQL

---

## 🎯 Objetivo

Este projeto foi desenvolvido para fins acadêmicos com o objetivo de:

- Praticar operações CRUD em banco de dados
- Integrar uma aplicação Java com MySQL
- Trabalhar com queries SQL (INSERT, SELECT, UPDATE, DELETE)
- Organizar o código de forma modular e escalável

---

## ⚙️ Tecnologias utilizadas

- Java 21
- MySQL
- MySQL Workbench
- JDBC
- Maven
- Git e GitHub

---

## 🧩 Funcionalidades

### 📦 Produtos
- Cadastrar produto
- Alterar produto
- Remover produto
- Buscar produto por ID
- Listar produtos

### 🏷️ Categorias
- Cadastro de categorias
- Associação de produtos com categorias

### 📊 Extras
- Paginação de produtos no console
- Relatórios por categoria

---

## 🗂️ Estrutura do projeto


src/
├── model/ # Entidades (Produto, Categoria)
├── service/ # Regras de negócio e acesso ao banco
├── database/ # Conexão com banco de dados
├── menu/ # Interface via console
└── dto/ # Relatórios

---

## 📐 Diagrama UML

O diagrama abaixo representa a estrutura do sistema, destacando as principais classes e suas relações.

![Diagrama UML](./docs/uml.png)

---

## ▶️ Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/EduardooXz/SmartStore-CRUD.git
cd SmartStore-CRUD
2. Configurar o banco de dados

Abra o MySQL Workbench e execute:

CREATE DATABASE smartstore;

USE smartstore;

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DOUBLE NOT NULL,
    quantidade INT NOT NULL,
    idcategoria BIGINT,
    FOREIGN KEY (idcategoria) REFERENCES categoria(id)
);
3. Configurar conexão

Renomear o arquivo database.properties.example para database.properties

No arquivo database.properties ajuste:

db.url=jdbc:mysql://localhost:3306/nome_do_banco
db.user=seu_usuario
db.password=sua_senha

4. Instalar dependências
mvn clean install
5. Executar o projeto

Execute a classe principal pela sua IDE (IntelliJ, Eclipse, etc.)

ou via terminal:

mvn exec:java

```
🗃️ Banco de dados

O sistema utiliza MySQL com duas entidades principais:

Produto

Categoria

Relacionamento:

Um produto pertence a uma categoria

📦 Dependência principal
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.0.0</version>
</dependency>

🚀 Diferenciais

Estrutura organizada em camadas

Uso de POO

Integração com banco real

Paginação no terminal

Código modular e reutilizável

💻 Exemplo de uso

====== Gerenciar Produtos ======

1. Inserir
2. Alterar
3. Pesquisar
4. Remover
5. Listar Todos
6. Exibir um
7. Sair
Selecionar opção:

🔧 Melhorias futuras

Interface gráfica (JavaFX ou Web)

API REST

Sistema de login

Filtros avançados

Melhor tratamento de exceções

👨‍💻 Integrantes

Eduardo Rodrigues Melo

João Victor Amaro

📄 Licença

Projeto desenvolvido para fins acadêmicos.
