# Guia de Deploy no Heroku

## 🚀 Passo a Passo Completo

### 1. Criar Conta no Heroku
- Acesse https://www.heroku.com
- Clique em "Sign Up"
- Confirme seu email

### 2. Instalar Heroku CLI

**Windows:**
```bash
# Baixe em: https://devcenter.heroku.com/articles/heroku-cli
# Ou use Chocolatey:
choco install heroku-cli
```

**Mac:**
```bash
brew tap heroku/brew && brew install heroku
```

**Linux:**
```bash
curl https://cli-assets.heroku.com/install.sh | sh
```

### 3. Fazer Login
```bash
heroku login
```

### 4. Compilar Projeto
```bash
cd /home/ubuntu/stock_control_app
mvn clean package
```

### 5. Inicializar Git
```bash
git init
git add .
git commit -m "Initial commit - Stock Control App"
```

### 6. Criar App no Heroku
```bash
heroku create seu-app-nome-unico
```

**Exemplo:**
```bash
heroku create stock-control-app-2024
```

### 7. Adicionar MySQL
```bash
heroku addons:create cleardb:ignite --app seu-app-nome-unico
```

### 8. Ver Configuração do Banco
```bash
heroku config --app seu-app-nome-unico
```

Você verá algo como:
```
CLEARDB_DATABASE_URL: mysql://user:password@host/database
```

### 9. Fazer Deploy
```bash
git push heroku main
```

Se der erro sobre branch:
```bash
git push heroku master
```

### 10. Acessar Aplicação
```
https://seu-app-nome-unico.herokuapp.com
```

---

## 🔍 Troubleshooting

### Ver Logs
```bash
heroku logs --tail --app seu-app-nome-unico
```

### Erro de Conexão com Banco
Se der erro de conexão com MySQL, verifique:
```bash
# Ver variáveis de ambiente
heroku config --app seu-app-nome-unico

# Atualizar URL do banco
heroku config:set SPRING_DATASOURCE_URL="sua-url" --app seu-app-nome-unico
```

### Erro de Porta
Heroku fornece a porta via variável `$PORT`. O arquivo `Procfile` já está configurado para isso.

### Aplicação Não Inicia
```bash
# Ver logs completos
heroku logs --app seu-app-nome-unico

# Reiniciar aplicação
heroku restart --app seu-app-nome-unico
```

---

## ✅ Checklist Final

- [ ] Conta Heroku criada
- [ ] Heroku CLI instalado
- [ ] Projeto compilado
- [ ] Git inicializado
- [ ] App criado no Heroku
- [ ] MySQL adicionado
- [ ] Deploy feito
- [ ] Aplicação acessível online

---

## 📝 Comandos Úteis

```bash
# Ver status
heroku status --app seu-app-nome-unico

# Reiniciar
heroku restart --app seu-app-nome-unico

# Ver logs em tempo real
heroku logs --tail --app seu-app-nome-unico

# Abrir app no navegador
heroku open --app seu-app-nome-unico

# Fazer novo deploy
git push heroku main
```

---

## 🎉 Pronto!

Sua aplicação está online e pronta para apresentar! 🚀
