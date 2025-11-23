# Arquitetura e Diagramas - Stock Control Application

## Diagrama de Classes (UML)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                            USER ENTITY                                  │
├─────────────────────────────────────────────────────────────────────────┤
│ - id: Long (PK)                                                         │
│ - username: String (UNIQUE, NOT NULL)                                   │
│ - email: String (UNIQUE, NOT NULL)                                      │
│ - password: String (NOT NULL)                                           │
│ - role: UserRole {ADMIN, USER}                                          │
│ - createdAt: LocalDateTime                                              │
│ - updatedAt: LocalDateTime                                              │
├─────────────────────────────────────────────────────────────────────────┤
│ + createProduct(product): Product                                       │
│ + updateProfile(email, password): void                                  │
│ + getRole(): UserRole                                                   │
└─────────────────────────────────────────────────────────────────────────┘
                                  │
                                  │ 1..*
                                  │
                    ┌─────────────┴──────────────┐
                    │                            │
                    ▼                            ▼
        ┌──────────────────────────┐  ┌──────────────────────────┐
        │   PRODUCT ENTITY         │  │ STOCK_MOVEMENT ENTITY    │
        ├──────────────────────────┤  ├──────────────────────────┤
        │ - id: Long (PK)          │  │ - id: Long (PK)          │
        │ - name: String (NN)      │  │ - product: Product (FK)  │
        │ - description: Text      │  │ - user: User (FK)        │
        │ - category: String (NN)  │  │ - type: MovementType     │
        │ - price: BigDecimal (NN) │  │ - quantity: Integer (NN) │
        │ - quantity: Integer (NN) │  │ - reason: String         │
        │ - minQuantity: Integer   │  │ - createdAt: LocalDateTime
        │ - user: User (FK)        │  └──────────────────────────┘
        │ - createdAt: LocalDateTime
        │ - updatedAt: LocalDateTime
        ├──────────────────────────┤
        │ + isLowStock(): boolean  │
        │ + getTotalValue(): BigDecimal
        │ + updateQuantity(qty): void
        └──────────────────────────┘
```

## Diagrama de Entidade-Relacionamento (DER)

```
┌──────────────┐                    ┌──────────────┐
│    USERS     │                    │   PRODUCTS   │
├──────────────┤                    ├──────────────┤
│ id (PK)      │◄──────────┐        │ id (PK)      │
│ username (U) │           │        │ name         │
│ email (U)    │           └────────│ user_id (FK) │
│ password     │                    │ category     │
│ role         │                    │ price        │
│ createdAt    │                    │ quantity     │
│ updatedAt    │                    │ minQuantity  │
└──────────────┘                    │ createdAt    │
       ▲                            │ updatedAt    │
       │                            └──────────────┘
       │                                   ▲
       │                                   │
       │                                   │ 1..*
       │                            ┌──────┴──────────┐
       │                            │                 │
       │                    ┌───────────────────────────────┐
       │                    │  STOCK_MOVEMENTS              │
       │                    ├───────────────────────────────┤
       │                    │ id (PK)                       │
       │                    │ product_id (FK) ─────┐       │
       │                    │ user_id (FK) ────┐   │       │
       │                    │ type (ENTRY/EXIT)│   │       │
       │                    │ quantity          │   │       │
       │                    │ reason            │   │       │
       │                    │ createdAt         │   │       │
       │                    └───────────────────┼───┼───────┘
       │                                        │   │
       └────────────────────────────────────────┘   │
                                                    │
                                            ┌───────┘
                                            │
                                    ┌───────▼──────┐
                                    │   PRODUCTS   │
                                    └──────────────┘
```

## Diagrama de Camadas (Arquitetura MVC)

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                         │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Thymeleaf Templates (HTML/CSS)                         │   │
│  │  - dashboard.html                                       │   │
│  │  - products/list.html                                   │   │
│  │  - products/form.html                                   │   │
│  │  - movements/list.html                                  │   │
│  │  - reports/index.html                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                    CONTROLLER LAYER                             │
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │HomeController│  │ProductControl│  │ReportControl │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│                                                                 │
│  Responsabilidades:                                             │
│  - Receber requisições HTTP                                    │
│  - Chamar serviços                                             │
│  - Retornar respostas (HTML/JSON)                              │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                    SERVICE LAYER                                │
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ProductService│  │StockMovement │  │ReportService │          │
│  │              │  │Service       │  │              │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│                                                                 │
│  Responsabilidades:                                             │
│  - Lógica de negócio                                           │
│  - Validações                                                  │
│  - Transações                                                  │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                 REPOSITORY LAYER                                │
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │UserRepository│  │ProductRepository  │StockMovement│          │
│  │              │  │              │  │Repository    │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│                                                                 │
│  Responsabilidades:                                             │
│  - Acesso a dados                                              │
│  - Consultas customizadas                                      │
│  - Persistência                                                │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                   MODEL LAYER                                   │
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │User Entity   │  │Product Entity │  │StockMovement │          │
│  │              │  │              │  │Entity        │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│                                                                 │
│  Responsabilidades:                                             │
│  - Representar dados                                           │
│  - Validações de entidade                                      │
│  - Cálculos de domínio                                         │
└────────────────────┬────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                  DATABASE LAYER                                 │
│                                                                 │
│  MySQL Database                                                │
│  - users table                                                 │
│  - products table                                              │
│  - stock_movements table                                       │
└─────────────────────────────────────────────────────────────────┘
```

## Fluxo de Dados - Exemplo: Criar Produto

```
1. Usuário acessa /products/new
   │
   ├─► HomeController.newProductForm()
   │   └─► Retorna template: products/form.html
   │
2. Usuário preenche formulário e submete
   │
   ├─► ProductController.createProduct(product)
   │   │
   │   ├─► Valida dados (@Valid)
   │   │
   │   ├─► ProductService.createProduct(product)
   │   │   │
   │   │   ├─► Valida regras de negócio
   │   │   │   - Preço > 0?
   │   │   │   - Quantidade >= 0?
   │   │   │
   │   │   ├─► ProductRepository.save(product)
   │   │   │   │
   │   │   │   └─► INSERT INTO products (...)
   │   │   │       VALUES (...)
   │   │   │
   │   │   └─► Retorna produto salvo
   │   │
   │   ├─► Redireciona para /products
   │   │
   │   └─► Exibe mensagem de sucesso
   │
3. Página de listagem é exibida
   │
   └─► ProductService.getProductsByUser(user)
       └─► SELECT * FROM products WHERE user_id = ?
```

## Padrões de Design Utilizados

### 1. **MVC (Model-View-Controller)**
- **Model**: Entidades JPA (User, Product, StockMovement)
- **View**: Templates Thymeleaf
- **Controller**: Controladores Spring

### 2. **Repository Pattern**
- Abstração do acesso a dados
- Consultas customizadas com @Query
- Facilita testes e manutenção

### 3. **Service Layer Pattern**
- Encapsula lógica de negócio
- Transações com @Transactional
- Reutilização de código

### 4. **Dependency Injection**
- Uso de @Autowired e @RequiredArgsConstructor
- Facilita testes e desacoplamento

### 5. **Validation Pattern**
- Validações com @Valid e anotações (NotNull, Positive, etc)
- Regras de negócio nas entidades

## Fluxo de Autenticação (Futuro)

```
1. Usuário acessa /login
   │
   ├─► AuthController.showLoginForm()
   │
2. Usuário submete credenciais
   │
   ├─► AuthController.login(username, password)
   │   │
   │   ├─► UserService.authenticate(username, password)
   │   │   │
   │   │   ├─► UserRepository.findByUsername(username)
   │   │   │
   │   │   ├─► PasswordEncoder.matches(password, hash)
   │   │   │
   │   │   └─► Retorna usuário se válido
   │   │
   │   ├─► Cria sessão/JWT
   │   │
   │   └─► Redireciona para /dashboard
   │
3. Acesso protegido com @PreAuthorize
   │
   └─► Verifica role (ADMIN, USER)
```

## Considerações de Segurança

1. **SQL Injection**: Prevenido com Prepared Statements (JPA)
2. **XSS**: Prevenido com escaping automático do Thymeleaf
3. **CSRF**: Será implementado com Spring Security
4. **Autenticação**: Será implementado com Spring Security
5. **Autorização**: Role-based access control (RBAC)
6. **Validação**: Validação em cliente e servidor

---

**Diagrama atualizado em**: Novembro de 2025
