package com.stockcontrol.app.service;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.User;
import com.stockcontrol.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        if (product.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByIdAndUser(Long id, User user) {
        return productRepository.findByIdAndUser(id, user);
    }

    public List<Product> getProductsByUser(User user) {
        return productRepository.findByUser(user);
    }

    public List<Product> getProductsByCategory(User user, String category) {
        return productRepository.findByUserAndCategory(user, category);
    }

    public List<Product> getLowStockProducts(User user) {
        return productRepository.findLowStockProducts(user);
    }

    public List<Product> searchProductsByName(User user, String name) {
        return productRepository.searchByName(user, name);
    }

    public List<String> getCategories(User user) {
        return productRepository.findDistinctCategories(user);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean isLowStock(Product product) {
        return product.isLowStock();
    }
}
