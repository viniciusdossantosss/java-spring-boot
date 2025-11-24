package com.stockcontrol.app.config;

import com.stockcontrol.app.model.User;
import com.stockcontrol.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin padrão se não existir
        User admin = userService.createDefaultAdmin();
        if (admin != null) {
            log.info("Usuário admin padrão criado: admin / admin123");
        } else {
            log.info("Usuário admin já existe no sistema");
        }
    }
}
