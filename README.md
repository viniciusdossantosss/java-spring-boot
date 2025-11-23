# Stock Control Application

**Aplicação Web de Controle de Estoque em Java Spring Boot**

## Descrição

A **Stock Control Application** é uma aplicação web desenvolvida em **Java Spring Boot** para gerenciar o estoque de produtos em uma loja. A aplicação permite a gestão eficiente de itens, controle de entradas e saídas, geração de relatórios de vendas e alertas de baixo estoque.

## Funcionalidades Principais

### Autenticação e Segurança
- Login e logout de usuários
- Controle de acesso por papel (Admin e User)
- Proteção de rotas com Spring Security

### Cadastro e Gerenciamento de Produtos
- Cadastro de produtos com nome, descrição, categoria e preço
- Edição de informações do produto
- Exclusão de produtos
- Listagem de produtos com busca e filtros
- Visualização de detalhes do produto

### Controle de Estoque
- Registro de entrada de produtos (aumentar quantidade)
- Registro de saída de produtos (diminuir quantidade)
- Histórico de movimentações de estoque
- Alertas de baixo estoque (quantidade mínima configurável)
- Controle de estoque em tempo real

### Relatórios
- Relatório de movimentação mensal (tabela)
- Relatório de movimentação mensal (gráficos)
- Relatório de produtos com baixo estoque
- Relatório de valor total em estoque

### Dashboard
- Resumo do estoque
- Gráficos de movimentação
- Indicadores principais (KPIs)

## Tecnologias Utilizadas

- **Backend**: Java 11, Spring Boot 3.1.5
- **Banco de Dados**: MySQL
- **ORM**: Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf (Templates HTML)
- **Build**: Maven

## Estrutura do Projeto

```
stock_control_app/
├── src/
│   ├── main/
│   │   ├── java/com/stockcontrol/app/
│   │   │   ├── model/              # Entidades JPA
│   │   │   ├── repository/         # Repositórios JPA
│   │   │   ├── service/            # Serviços de negócio
│   │   │   ├── controller/         # Controladores
│   │   │   └── StockControlApplication.java
│   │   └── resources/
│   │       ├── templates/          # Templates Thymeleaf
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Entidades Principais

### User
- Representa um usuário do sistema
- Atributos: id, username, email, password, role, createdAt, updatedAt

### Product
- Representa um produto no estoque
- Atributos: id, name, description, category, price, quantity, minQuantity, user, createdAt, updatedAt

### StockMovement
- Representa uma movimentação de estoque (entrada ou saída)
- Atributos: id, product, user, type, quantity, reason, createdAt

## Regras de Negócio

1. O preço de um produto não pode ser negativo
2. A quantidade de um produto em estoque não pode ser negativa
3. Uma movimentação de saída não pode exceder a quantidade disponível
4. Alerta de baixo estoque quando quantidade <= minQuantity
5. Apenas admin pode deletar produtos
6. Histórico de movimentações é imutável

## Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6.0 ou superior
- MySQL 5.7 ou superior

### Passos

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd stock_control_app
   ```

2. **Configure o banco de dados**
   - Crie um banco de dados MySQL chamado `stock_control_db`
   - Atualize as credenciais em `src/main/resources/application.properties`

3. **Compile o projeto**
   ```bash
   mvn clean install
   ```

4. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse a aplicação**
   - Abra o navegador e vá para `http://localhost:8080`

## Testes

Para executar os testes unitários:

```bash
mvn test
```

## Autores

- Desenvolvido como trabalho de final de semestre
- Disciplina: Programação Orientada a Objetos em Java
- Instituição: Wyden | UniRuy

## Licença

Este projeto é fornecido como está, para fins educacionais.

---

**Última atualização**: Novembro de 2025
