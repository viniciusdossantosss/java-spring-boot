package com.stockcontrol.app.config;

import com.stockcontrol.app.model.User;
import com.stockcontrol.app.model.Category;
import com.stockcontrol.app.service.UserService;
import com.stockcontrol.app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    private final CategoryService categoryService;
    
    @Override
    public void run(String... args) throws Exception {
        // Garantir que usuário admin exista
        User admin = userService.createDefaultAdmin();
        log.info("Usuário admin garantido no sistema: {} (ID: {})", admin.getUsername(), admin.getId());
        
        // Criar categorias padrão
        createDefaultCategories();
    }
    
    private void createDefaultCategories() {
        String[] defaultCategories = {
            "Eletrônicos", "Informática", "Mobiliário", "Alimentos", 
            "Vestuário", "Ferramentas", "Limpeza", "Papelaria"
        };
        
        for (String categoryName : defaultCategories) {
            try {
                if (categoryService.findByName(categoryName).isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryName);
                    category.setDescription("Categoria padrão: " + categoryName);
                    categoryService.save(category);
                    log.info("Categoria padrão criada: {}", categoryName);
                }
            } catch (Exception e) {
                log.warn("Erro ao criar categoria {}: {}", categoryName, e.getMessage());
            }
        }
    }
}
