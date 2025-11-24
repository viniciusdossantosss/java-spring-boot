package com.stockcontrol.app.service;

import com.stockcontrol.app.model.User;
import com.stockcontrol.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public User authenticate(String username, String password) {
        log.info("Tentativa de login para usuário: {}", username);
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            log.error("Usuário não encontrado: {}", username);
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }
        
        User user = userOpt.get();
        log.info("Usuário encontrado: {} (ID: {})", user.getUsername(), user.getId());
        
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        log.info("Senha corresponde? {}", passwordMatches);
        
        if (!passwordMatches) {
            log.error("Senha incorreta para usuário: {}", username);
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }
        
        log.info("Login bem-sucedido para usuário: {}", username);
        return user;
    }
    
    public User createDefaultAdmin() {
        log.info("Verificando/criando usuário admin padrão...");
        Optional<User> existingAdmin = userRepository.findByUsername("admin");
        
        if (existingAdmin.isEmpty()) {
            log.info("Criando novo usuário admin...");
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@stockcontrol.com");
            String encodedPassword = passwordEncoder.encode("admin123");
            admin.setPassword(encodedPassword);
            admin.setRole(User.UserRole.ADMIN);
            
            User savedAdmin = userRepository.save(admin);
            log.info("Usuário admin criado com sucesso: {} (ID: {})", savedAdmin.getUsername(), savedAdmin.getId());
            return savedAdmin;
        } else {
            log.info("Usuário admin já existe: {} (ID: {})", existingAdmin.get().getUsername(), existingAdmin.get().getId());
            return existingAdmin.get();
        }
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User save(User user) {
        if (user.getId() == null && user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
}
