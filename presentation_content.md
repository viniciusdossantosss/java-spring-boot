# Stock Control Application - Conteúdo da Apresentação

## Slide 1: Título e Introdução

**Título Principal**: Stock Control Application

**Subtítulo**: Aplicação Web de Controle de Estoque em Java Spring Boot

**Informações**:
- Disciplina: Programação Orientada a Objetos em Java
- Instituição: Wyden | UniRuy
- Período: Final de Semestre 2025
- Desenvolvedor: [Seu Nome]

**Conteúdo do Slide**:
Bem-vindo à apresentação da **Stock Control Application**, uma solução completa para gerenciamento de estoque desenvolvida em Java Spring Boot. Este projeto demonstra a aplicação prática de conceitos de Programação Orientada a Objetos, arquitetura de software e desenvolvimento web moderno.

---

## Slide 2: Problema e Contexto

**Título**: O Desafio do Controle de Estoque

**Problema Identificado**:
Lojistas e gerentes de estoque enfrentam desafios significativos no controle manual de produtos. A falta de um sistema automatizado resulta em:

- Erros de contagem e perda de produtos
- Dificuldade em identificar produtos com baixo estoque
- Impossibilidade de gerar relatórios de movimentação
- Falta de rastreabilidade das operações
- Perda de tempo em tarefas administrativas

**Contexto**:
Segundo pesquisas, empresas que implementam sistemas de gestão de estoque reduzem custos operacionais em até 30% e melhoram a satisfação do cliente.

**Solução Proposta**:
A Stock Control Application oferece uma plataforma web intuitiva que automatiza o controle de estoque, fornecendo relatórios em tempo real e alertas de baixo estoque.

---

## Slide 3: Funcionalidades Principais

**Título**: Funcionalidades Implementadas

**Funcionalidade 1 - Cadastro de Produtos**:
Os usuários podem cadastrar novos produtos com informações detalhadas como nome, descrição, categoria, preço e quantidade mínima. O sistema valida automaticamente que os preços sejam positivos e que as quantidades sejam não-negativas.

**Funcionalidade 2 - Controle de Movimentações**:
Registre entradas (compras) e saídas (vendas) de produtos. O sistema atualiza automaticamente o estoque e valida se há quantidade suficiente para saídas, prevenindo operações inválidas.

**Funcionalidade 3 - Alertas de Baixo Estoque**:
O sistema identifica automaticamente produtos com quantidade inferior à quantidade mínima configurada e exibe alertas no dashboard para ação imediata.

**Funcionalidade 4 - Histórico de Movimentações**:
Cada movimentação é registrada com data, hora, tipo (entrada/saída), quantidade e motivo, criando um histórico completo e rastreável de todas as operações.

**Funcionalidade 5 - Relatórios e Dashboard**:
Visualize resumos de estoque, gráficos de movimentação mensal, valor total em estoque e produtos com baixo estoque em um dashboard intuitivo.

---

## Slide 4: Arquitetura Técnica

**Título**: Arquitetura e Tecnologias Utilizadas

**Stack Tecnológico**:

| Camada | Tecnologia | Versão |
| :--- | :--- | :--- |
| **Linguagem** | Java | 17 |
| **Framework Web** | Spring Boot | 3.1.5 |
| **Banco de Dados** | MySQL | 5.7+ |
| **ORM** | Spring Data JPA | 3.1.5 |
| **Build Tool** | Maven | 3.6.0+ |
| **Template Engine** | Thymeleaf | 3.1.1 |

**Padrão Arquitetural - MVC (Model-View-Controller)**:

A aplicação segue o padrão MVC com camadas bem definidas:

- **Model**: Entidades JPA que representam os dados do negócio
- **View**: Templates Thymeleaf que renderizam as páginas HTML
- **Controller**: Controladores Spring que processam requisições e orquestram a lógica

**Camadas Adicionais**:

- **Service Layer**: Encapsula a lógica de negócio e validações
- **Repository Layer**: Abstrai o acesso aos dados com Spring Data JPA
- **Validation Layer**: Valida dados em múltiplos níveis

---

## Slide 5: Modelo de Dados

**Título**: Estrutura do Banco de Dados

**Entidade User (Usuários)**:
Representa os usuários do sistema com autenticação e controle de acesso. Cada usuário possui um identificador único, credenciais de login, email e um papel (ADMIN ou USER) que determina suas permissões no sistema.

**Entidade Product (Produtos)**:
Representa os produtos no estoque com informações comerciais como nome, descrição, categoria e preço. Inclui também controle de quantidade atual e quantidade mínima para alertas. Cada produto está associado a um usuário (proprietário).

**Entidade StockMovement (Movimentações)**:
Registra cada operação de entrada ou saída de estoque. Cada movimentação rastreia qual produto foi movimentado, quem realizou a operação, o tipo (ENTRY ou EXIT), a quantidade e o motivo da operação.

**Relacionamentos**:
- Um usuário pode ter múltiplos produtos (1:N)
- Um usuário pode realizar múltiplas movimentações (1:N)
- Um produto pode ter múltiplas movimentações (1:N)

---

## Slide 6: Demonstração Ao Vivo

**Título**: Demonstração Prática da Aplicação

**Roteiro de Demonstração**:

1. **Acesso à Aplicação**: Abrir http://localhost:8080 no navegador
2. **Dashboard**: Mostrar a página inicial com resumo do estoque
3. **Cadastro de Produto**: Criar um novo produto demonstrando as validações
4. **Listagem**: Mostrar a lista de produtos com filtros e busca
5. **Movimentação**: Registrar uma entrada de estoque
6. **Histórico**: Visualizar o histórico de movimentações
7. **Alertas**: Mostrar produtos com baixo estoque
8. **Relatórios**: Exibir gráficos de movimentação mensal

**Pontos a Destacar**:
- Validações automáticas (preço positivo, quantidade válida)
- Atualização em tempo real do estoque
- Interface intuitiva e responsiva
- Mensagens de feedback ao usuário

---

## Slide 7: Regras de Negócio

**Título**: Regras de Negócio Implementadas

**Validações de Produto**:
- O preço de um produto não pode ser negativo
- A quantidade em estoque não pode ser negativa
- A quantidade mínima deve ser maior ou igual a zero
- O nome do produto é obrigatório
- A categoria do produto é obrigatória

**Validações de Movimentação**:
- A quantidade de uma movimentação deve ser positiva
- Uma saída não pode exceder a quantidade disponível em estoque
- Cada movimentação deve estar associada a um produto válido
- O motivo da movimentação é opcional mas recomendado

**Alertas e Notificações**:
- Um alerta é disparado quando a quantidade de um produto é menor ou igual à quantidade mínima
- O alerta é exibido no dashboard para ação imediata do gerente

**Integridade de Dados**:
- Cada movimentação é imutável após criação (não pode ser editada)
- O histórico de movimentações é mantido para auditoria
- Relacionamentos entre tabelas são mantidos com integridade referencial

---

## Slide 8: Histórias de Usuário

**Título**: Histórias de Usuário e Casos de Uso

**História 1 - Gerenciar Produtos**:
Como um gerente de loja, quero cadastrar, editar e deletar produtos, para que eu possa manter o catálogo atualizado. Critérios de aceitação: Posso criar um novo produto com todos os atributos, editar dados existentes, deletar produtos (apenas admin), e recebo mensagens de erro para dados inválidos.

**História 2 - Controlar Estoque**:
Como um operador de estoque, quero registrar entradas e saídas de produtos, para que eu possa manter o estoque atualizado. Critérios de aceitação: Posso registrar entradas e saídas, o sistema valida quantidade suficiente, vejo o histórico completo de movimentações.

**História 3 - Receber Alertas**:
Como um gerente, quero receber alertas quando um produto está com baixo estoque, para que eu possa repor no tempo certo. Critérios de aceitação: O sistema identifica produtos com quantidade <= minQuantity, exibe alertas visuais no dashboard, posso configurar a quantidade mínima.

**História 4 - Gerar Relatórios**:
Como um gerente, quero gerar relatórios de movimentação mensal, para que eu possa analisar o desempenho de vendas. Critérios de aceitação: Posso gerar relatório por período, vejo tabelas com entradas e saídas, posso exportar em PDF ou CSV, vejo gráficos de movimentação.

---

## Slide 9: Conceitos de POO Aplicados

**Título**: Programação Orientada a Objetos na Prática

**Encapsulamento**:
As entidades encapsulam seus dados com getters e setters, controlando o acesso aos atributos. Por exemplo, a classe Product encapsula a lógica de cálculo de valor total do estoque.

**Herança**:
As classes de serviço herdam funcionalidades comuns de uma classe base abstrata, reduzindo duplicação de código. Os repositórios herdam de JpaRepository, obtendo automaticamente operações CRUD.

**Polimorfismo**:
Os tipos de movimentação (ENTRY e EXIT) são representados como um enum, permitindo diferentes comportamentos para cada tipo. O serviço trata ambos polimorficamente.

**Abstração**:
As interfaces de repositório definem contratos para acesso a dados, permitindo múltiplas implementações. Os serviços abstraem a complexidade da lógica de negócio.

**Composição**:
A classe StockMovement é composta por referências a Product e User, demonstrando relacionamentos entre objetos.

---

## Slide 10: Tecnologias e Padrões de Design

**Título**: Padrões de Design e Boas Práticas

**Repository Pattern**:
O padrão Repository abstrai o acesso a dados, permitindo consultas customizadas com @Query. Facilita testes unitários e manutenção.

**Service Layer Pattern**:
A lógica de negócio está isolada em serviços, separada dos controladores. Cada serviço é responsável por um domínio específico (produtos, movimentações).

**Dependency Injection**:
Spring injeta automaticamente as dependências usando @Autowired e @RequiredArgsConstructor, facilitando testes e desacoplamento.

**Validation Pattern**:
Validações são feitas em múltiplos níveis: anotações (@NotNull, @Positive), métodos de serviço e regras de negócio.

**Transaction Management**:
@Transactional garante que operações complexas sejam atômicas, mantendo a integridade dos dados.

---

## Slide 11: Melhorias Futuras

**Título**: Roadmap e Possíveis Melhorias

**Curto Prazo (1-2 meses)**:
- Implementar autenticação com Spring Security
- Criar telas completas com Bootstrap 5
- Adicionar testes unitários com JUnit e Mockito
- Implementar paginação nas listagens

**Médio Prazo (2-4 meses)**:
- Gráficos avançados com Chart.js
- Exportação de relatórios em PDF e CSV
- Notificações por email para alertas
- API REST para integração com outros sistemas

**Longo Prazo (4+ meses)**:
- Deploy em cloud (AWS, Azure, Heroku)
- Aplicativo mobile com Flutter ou React Native
- Sistema de permissões granulares
- Auditoria completa com rastreamento de alterações
- Integração com sistemas de pagamento

---

## Slide 12: Conclusão

**Título**: Conclusão e Aprendizados

**Resumo do Projeto**:
A Stock Control Application demonstra a aplicação prática de conceitos fundamentais de Programação Orientada a Objetos em um projeto web real. O projeto implementa uma arquitetura robusta, escalável e mantível.

**Aprendizados Principais**:
- Aplicação de padrões de design em projetos reais
- Desenvolvimento com Spring Boot e Spring Data JPA
- Modelagem de dados relacional
- Implementação de regras de negócio
- Boas práticas de desenvolvimento

**Impacto Potencial**:
A aplicação pode ser utilizada por pequenas e médias lojas para melhorar o controle de estoque, reduzindo custos operacionais e melhorando a eficiência.

**Agradecimentos**:
Agradeço à instituição Wyden | UniRuy pela oportunidade de aprender e aplicar conceitos importantes de programação em um projeto prático e significativo.

---

## Notas Gerais para Apresentação

**Tempo Total**: 8-10 minutos

**Dicas de Apresentação**:
1. Fale com clareza e confiança
2. Mantenha contato visual com a audiência
3. Use exemplos concretos durante a demonstração
4. Esteja preparado para responder perguntas sobre a arquitetura
5. Tenha o código aberto no IDE para mostrar detalhes
6. Pratique a apresentação antes do dia

**Possíveis Perguntas**:
- Por que escolheu Spring Boot?
- Como o sistema valida dados?
- Como seria implementada a autenticação?
- Como o projeto escala?
- Quais são as limitações atuais?

**Respostas Preparadas**:
- Spring Boot é o framework mais popular para desenvolvimento web em Java
- Validações ocorrem em múltiplos níveis (anotações, serviços, banco de dados)
- Autenticação seria implementada com Spring Security
- A arquitetura permite fácil escalabilidade com cache, índices de banco de dados
- Limitações atuais: sem autenticação, sem gráficos avançados, sem API REST
