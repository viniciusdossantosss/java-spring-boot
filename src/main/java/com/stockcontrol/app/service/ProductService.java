package com.stockcontrol.app.service;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.User;
import com.stockcontrol.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public Product createProduct(Product product) {
        if (product.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return productRepository.save(product);
    }

    public Product save(Product product) {
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

    public List<Product> findLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
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

    // Métodos para relatórios
    public Long countAll() {
        return productRepository.count();
    }

    public Long countLowStock() {
        return productRepository.countLowStock();
    }

    public BigDecimal getTotalStockValue() {
        return productRepository.findAll().stream()
                .map(Product::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Long> getProductsCountByCategory() {
        return productRepository.findAll().stream()
                .filter(p -> p.getCategory() != null)
                .collect(Collectors.groupingBy(
                    p -> p.getCategory().getName(),
                    Collectors.counting()
                ));
    }
}
