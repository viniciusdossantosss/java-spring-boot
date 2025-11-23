package com.stockcontrol.app.repository;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUser(User user);
    List<Product> findByUserAndCategory(User user, String category);
    
    @Query("SELECT p FROM Product p WHERE p.user = :user AND p.quantity <= p.minQuantity")
    List<Product> findLowStockProducts(@Param("user") User user);
    
    Optional<Product> findByIdAndUser(Long id, User user);
    
    @Query("SELECT p FROM Product p WHERE p.user = :user AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> searchByName(@Param("user") User user, @Param("name") String name);
    
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.user = :user ORDER BY p.category")
    List<String> findDistinctCategories(@Param("user") User user);
}
