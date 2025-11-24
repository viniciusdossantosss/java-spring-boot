package com.stockcontrol.app.service;

import com.stockcontrol.app.model.Category;
import com.stockcontrol.app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category categoryDetails) {
        Category category = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
        
        // Verificar se o nome já existe em outra categoria
        if (!category.getName().equals(categoryDetails.getName()) && 
            categoryRepository.existsByName(categoryDetails.getName())) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome: " + categoryDetails.getName());
        }
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
        
        // Verificar se existem produtos vinculados
        if (!category.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir categoria com produtos vinculados");
        }
        
        categoryRepository.delete(category);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
