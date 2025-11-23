# Guia de Deployment e Apresentação - Stock Control Application

## Parte 1: Como Colocar a Aplicação no Ar

### Pré-requisitos

Certifique-se de que você tem instalado:

- **Java 17 ou superior**: `java -version`
- **Maven 3.6.0 ou superior**: `mvn -version`
- **MySQL 5.7 ou superior**: `mysql --version`
- **Git**: `git --version`

### Passo 1: Preparar o Banco de Dados

1. Abra o terminal e acesse o MySQL:
   ```bash
   mysql -u root -p
   ```

2. Crie o banco de dados:
   ```sql
   CREATE DATABASE stock_control_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE stock_control_db;
   ```

3. Saia do MySQL:
   ```sql
   EXIT;
   ```

### Passo 2: Configurar a Aplicação

1. Navegue até o diretório do projeto:
   ```bash
   cd /home/ubuntu/stock_control_app
   ```

2. Abra o arquivo `src/main/resources/application.properties` e verifique as configurações do banco de dados:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/stock_control_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   ```

   Atualize `username` e `password` se necessário.

### Passo 3: Compilar a Aplicação

1. Execute o comando Maven para compilar:
   ```bash
   mvn clean install
   ```

   Isso vai:
   - Baixar todas as dependências
   - Compilar o código Java
   - Executar os testes
   - Criar o arquivo JAR executável

### Passo 4: Executar a Aplicação

1. Inicie a aplicação:
   ```bash
   mvn spring-boot:run
   ```

   Ou, se preferir usar o JAR diretamente:
   ```bash
   java -jar target/stock-control-app-1.0.0.jar
   ```

2. Aguarde a mensagem:
   ```
   Tomcat started on port(s): 8080 (http)
   ```

3. Abra o navegador e acesse:
   ```
   http://localhost:8080
   ```

### Passo 5: Verificar o Banco de Dados

As tabelas serão criadas automaticamente pelo Hibernate na primeira execução. Para verificar:

```bash
mysql -u root -p stock_control_db
```

```sql
SHOW TABLES;
DESCRIBE users;
DESCRIBE products;
DESCRIBE stock_movements;
```

---

## Parte 2: Como Apresentar o Trabalho

### Estrutura da Apresentação (8-10 minutos)

#### **Slide 1: Introdução (1 minuto)**
- Título: "Stock Control Application"
- Objetivo: Aplicação web de controle de estoque
- Contexto: Facilitar a gestão de produtos em lojas

#### **Slide 2: Problema e Solução (1 minuto)**
- **Problema**: Lojistas precisam controlar estoque manualmente
- **Solução**: Sistema automatizado com relatórios em tempo real

#### **Slide 3: Funcionalidades Principais (2 minutos)**
- Cadastro de produtos
- Controle de entradas e saídas
- Histórico de movimentações
- Alertas de baixo estoque
- Dashboard com resumo

#### **Slide 4: Arquitetura Técnica (1 minuto)**
- Backend: Java Spring Boot
- Banco de Dados: MySQL
- Padrão: MVC com camadas de Serviço
- Diagrama de arquitetura

#### **Slide 5: Modelo de Dados (1 minuto)**
- Entidade User (id, username, email, role)
- Entidade Product (id, name, category, price, quantity)
- Entidade StockMovement (id, product, type, quantity)
- Relacionamentos e validações

#### **Slide 6: Demonstração ao Vivo (2-3 minutos)**
- Abrir a aplicação em `http://localhost:8080`
- Mostrar as principais funcionalidades
- Navegar pelos menus
- Criar um produto de exemplo
- Registrar uma movimentação

#### **Slide 7: Regras de Negócio (1 minuto)**
- Preço não pode ser negativo
- Quantidade não pode ser negativa
- Saída não pode exceder quantidade disponível
- Alertas de baixo estoque

#### **Slide 8: Tecnologias Utilizadas (1 minuto)**
- Java 17
- Spring Boot 3.1.5
- MySQL 8.0
- Maven
- Thymeleaf

#### **Slide 9: Conclusão (1 minuto)**
- Resumo das funcionalidades
- Possíveis melhorias futuras
- Agradecimentos

---

## Parte 3: Histórias de Usuário

### História 1: Gerenciar Produtos
**Como** um gerente de loja,
**Quero** cadastrar, editar e deletar produtos,
**Para que** eu possa manter o catálogo atualizado.

**Critérios de Aceitação:**
- Posso criar um novo produto com nome, descrição, categoria e preço
- Posso editar os dados de um produto existente
- Posso deletar um produto (apenas admin)
- Recebo mensagens de erro se os dados forem inválidos

### História 2: Controlar Estoque
**Como** um operador de estoque,
**Quero** registrar entradas e saídas de produtos,
**Para que** eu possa manter o estoque atualizado.

**Critérios de Aceitação:**
- Posso registrar uma entrada de produtos
- Posso registrar uma saída de produtos
- O sistema valida se há quantidade suficiente para saída
- Vejo o histórico de todas as movimentações

### História 3: Receber Alertas
**Como** um gerente,
**Quero** receber alertas quando um produto está com baixo estoque,
**Para que** eu possa repor no tempo certo.

**Critérios de Aceitação:**
- O sistema identifica produtos com quantidade <= minQuantity
- Exibe um alerta visual no dashboard
- Posso configurar a quantidade mínima por produto

### História 4: Gerar Relatórios
**Como** um gerente,
**Quero** gerar relatórios de movimentação mensal,
**Para que** eu possa analisar o desempenho de vendas.

**Critérios de Aceitação:**
- Posso gerar relatório por período
- Vejo tabelas com entradas e saídas
- Posso exportar em PDF ou CSV
- Vejo gráficos de movimentação

---

## Parte 4: Dicas para a Apresentação

### Antes da Apresentação
1. **Teste tudo**: Compile e execute a aplicação antes
2. **Prepare exemplos**: Crie alguns produtos de teste
3. **Tenha backup**: Salve a apresentação em PDF também
4. **Pratique o timing**: Ensaie a apresentação inteira

### Durante a Apresentação
1. **Fale com clareza**: Explique cada conceito de forma simples
2. **Mostre o código**: Abra o IDE e mostre as classes principais
3. **Demonstre ao vivo**: Interaja com a aplicação durante a apresentação
4. **Responda perguntas**: Esteja preparado para questões sobre a arquitetura

### Pontos Importantes a Mencionar
- **Programação Orientada a Objetos**: Explique como as classes (User, Product, StockMovement) representam entidades do mundo real
- **Padrão MVC**: Mostre como o código está organizado em Model (entidades), View (templates) e Controller (controladores)
- **Banco de Dados**: Explique os relacionamentos entre as tabelas
- **Validações**: Mencione as regras de negócio implementadas
- **Escalabilidade**: Fale sobre como a arquitetura permite crescimento futuro

---

## Parte 5: Possíveis Melhorias Futuras

1. **Autenticação com Spring Security**: Implementar login seguro
2. **Gráficos Avançados**: Usar bibliotecas como Chart.js
3. **Exportação de Relatórios**: Gerar PDF e CSV
4. **API REST**: Criar endpoints para integração com outros sistemas
5. **Testes Unitários**: Implementar testes com JUnit e Mockito
6. **Deploy em Cloud**: Publicar em AWS, Azure ou Heroku
7. **Notificações**: Enviar alertas por email
8. **Auditoria**: Rastrear quem fez cada alteração

---

## Troubleshooting

### Problema: "Port 8080 is already in use"
**Solução**: Mude a porta em `application.properties`:
```properties
server.port=8081
```

### Problema: "Cannot connect to database"
**Solução**: Verifique se MySQL está rodando:
```bash
sudo systemctl start mysql
```

### Problema: "Maven compilation error"
**Solução**: Limpe o cache e recompile:
```bash
mvn clean install -U
```

### Problema: "Hibernate table not created"
**Solução**: Verifique se `spring.jpa.hibernate.ddl-auto=update` está em `application.properties`

---

## Contato e Suporte

Para dúvidas sobre a implementação, consulte:
- Documentação Spring Boot: https://spring.io/projects/spring-boot
- Documentação JPA: https://www.oracle.com/java/technologies/persistence-jsp.html
- Documentação MySQL: https://dev.mysql.com/doc/

---

**Boa sorte na apresentação!** 🎓
