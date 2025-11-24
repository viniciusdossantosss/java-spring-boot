package com.stockcontrol.app.controller;

import com.stockcontrol.app.model.User;
import com.stockcontrol.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
@Slf4j
public class DebugController {
    
    private final UserService userService;
    
    @GetMapping("/check-admin")
    public ResponseEntity<Map<String, Object>> checkAdmin() {
        Map<String, Object> response = new HashMap<>();
        Optional<User> admin = userService.findByUsername("admin");
        
        if (admin.isPresent()) {
            User user = admin.get();
            response.put("exists", true);
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("id", user.getId());
            response.put("passwordHash", user.getPassword());
            response.put("createdAt", user.getCreatedAt());
            log.info("Usuário admin encontrado: {}", user.getUsername());
        } else {
            response.put("exists", false);
            log.warn("Usuário admin não encontrado!");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reset-admin")
    public ResponseEntity<Map<String, Object>> resetAdmin() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User admin = userService.createDefaultAdmin();
            response.put("success", true);
            response.put("message", "Usuário admin resetado com sucesso");
            response.put("username", admin.getUsername());
            response.put("id", admin.getId());
            log.info("Usuário admin resetado: {}", admin.getUsername());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            log.error("Erro ao resetar usuário admin", e);
        }
        
        return ResponseEntity.ok(response);
    }
}
