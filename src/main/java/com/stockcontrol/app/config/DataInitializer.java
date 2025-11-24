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
        // Garantir que usuário admin exista
        User admin = userService.createDefaultAdmin();
        log.info("Usuário admin garantido no sistema: {} (ID: {})", admin.getUsername(), admin.getId());
    }
}
