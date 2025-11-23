# Stock Control Application - TODO

## Requisitos Funcionais (RF)

### Autenticação e Usuários
- [x] RF1: Estrutura de entidade User com roles (ADMIN, USER)
- [ ] RF2: Sistema de login com autenticação
- [ ] RF3: Logout e gerenciamento de sessão
- [ ] RF4: Perfil de usuário

### Cadastro e Gerenciamento de Produtos
- [x] RF5: Entidade Product com atributos completos
- [x] RF6: Repositório ProductRepository com consultas
- [x] RF7: Serviço ProductService com lógica de negócio
- [ ] RF8: Controlador ProductController (CRUD completo)
- [ ] RF9: Telas de cadastro, edição e exclusão de produtos
- [ ] RF10: Listagem de produtos com busca e filtros
- [ ] RF11: Visualização de detalhes do produto

### Controle de Estoque
- [x] RF12: Entidade StockMovement para rastrear movimentações
- [x] RF13: Repositório StockMovementRepository com consultas
- [x] RF14: Serviço StockMovementService com métodos de entrada/saída
- [ ] RF15: Controlador para registrar movimentações
- [ ] RF16: Histórico de movimentações de estoque
- [ ] RF17: Alertas de baixo estoque
- [ ] RF18: Controle de estoque em tempo real

### Relatórios
- [ ] RF19: Relatório de movimentação mensal (tabela)
- [ ] RF20: Relatório de movimentação mensal (gráficos)
- [ ] RF21: Relatório de produtos com baixo estoque
- [ ] RF22: Relatório de valor total em estoque
- [ ] RF23: Exportação de relatórios (PDF/CSV)

### Dashboard
- [ ] RF24: Dashboard com resumo do estoque
- [ ] RF25: Gráficos de movimentação
- [ ] RF26: Indicadores principais (KPIs)

## Requisitos Não Funcionais (RNF)

### Performance e Responsividade
- [x] RNF1: Arquitetura responsiva com Spring Boot
- [ ] RNF2: Tempo de resposta < 2 segundos
- [ ] RNF3: Interface intuitiva e fácil de usar
- [ ] RNF4: Design responsivo (desktop, tablet, mobile)

### Segurança
- [ ] RNF5: Autenticação obrigatória para acesso
- [ ] RNF6: Validação de entrada de dados
- [ ] RNF7: Proteção contra SQL Injection
- [ ] RNF8: Controle de acesso por papel (admin/user)

### Banco de Dados
- [x] RNF9: Persistência com MySQL e JPA/Hibernate
- [x] RNF10: Integridade referencial com relacionamentos
- [ ] RNF11: Backup e recuperação

## Implementação Técnica - Backend

### Banco de Dados
- [x] Criar tabela `users` com atributos completos
- [x] Criar tabela `products` com relacionamento com users
- [x] Criar tabela `stock_movements` com relacionamento com products e users
- [x] Criar índices para otimização
- [ ] Executar migrations com Spring Data JPA

### Entidades JPA
- [x] Entidade User com validações
- [x] Entidade Product com cálculos de estoque
- [x] Entidade StockMovement com tipos de movimentação
- [ ] Adicionar mais validações e regras de negócio

### Repositórios
- [x] UserRepository com consultas customizadas
- [x] ProductRepository com filtros e buscas
- [x] StockMovementRepository com análises de período
- [ ] Implementar paginação

### Serviços
- [x] ProductService com CRUD e operações de estoque
- [x] StockMovementService com entrada/saída e relatórios
- [ ] UserService para autenticação
- [ ] ReportService para gerar relatórios

### Controladores
- [x] HomeController para dashboard
- [x] ProductController para CRUD de produtos
- [ ] StockMovementController para registrar movimentações
- [ ] ReportController para gerar relatórios
- [ ] AuthController para autenticação

## Implementação Técnica - Frontend

### Templates Thymeleaf
- [ ] Layout base com navegação
- [ ] Página de login
- [ ] Dashboard com resumo
- [ ] Listagem de produtos
- [ ] Formulário de cadastro/edição de produtos
- [ ] Página de movimentações
- [ ] Página de relatórios

### Estilos e UI
- [ ] Integrar Bootstrap 5
- [ ] Design responsivo
- [ ] Componentes reutilizáveis
- [ ] Validação no cliente

## Documentação

### Documentação Técnica
- [ ] Diagrama de Classes (UML)
- [ ] Diagrama de Entidade-Relacionamento (DER)
- [ ] Dicionário de Dados
- [ ] Especificação de APIs

### Documentação do Projeto
- [x] README.md com instruções
- [ ] Relatório Técnico (ABNT)
- [ ] Histórias de Usuário
- [ ] Guia de Instalação

### Apresentação
- [ ] Slides da apresentação (8-10 minutos)
- [ ] Demonstração ao vivo da aplicação
- [ ] Explicação das funcionalidades
- [ ] Discussão sobre arquitetura e design

## Regras de Negócio

- [x] RN1: Preço de produto não pode ser negativo
- [x] RN2: Quantidade em estoque não pode ser negativa
- [x] RN3: Quantidade mínima deve ser >= 0
- [x] RN4: Movimentação de saída não pode exceder quantidade disponível
- [ ] RN5: Alerta de baixo estoque quando quantidade <= minQuantity
- [ ] RN6: Apenas admin pode deletar produtos
- [ ] RN7: Apenas admin pode alterar preços
- [ ] RN8: Usuários podem visualizar relatórios
- [ ] RN9: Histórico de movimentações é imutável

## Status de Desenvolvimento

- **Fase 1**: ✅ Projeto inicializado com Spring Boot
- **Fase 2**: ✅ Entidades, Repositórios e Serviços implementados
- **Fase 3**: ⏳ Controladores e Telas (em progresso)
- **Fase 4**: ⏳ Autenticação e Segurança
- **Fase 5**: ⏳ Relatórios e Gráficos
- **Fase 6**: ⏳ Testes e Deploy
- **Fase 7**: ⏳ Documentação e Apresentação

## Próximos Passos

1. Implementar controladores completos com lógica de negócio
2. Criar templates Thymeleaf para as telas
3. Integrar Bootstrap para UI responsiva
4. Implementar autenticação com Spring Security
5. Criar serviço de relatórios com gráficos
6. Escrever testes unitários
7. Documentar o projeto
8. Preparar apresentação
