package com.stockcontrol.app.repository;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.StockMovement;
import com.stockcontrol.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProduct(Product product);
    List<StockMovement> findByUser(User user);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.product = :product AND sm.createdAt BETWEEN :startDate AND :endDate ORDER BY sm.createdAt DESC")
    List<StockMovement> findByProductAndDateRange(@Param("product") Product product, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.user = :user AND sm.createdAt BETWEEN :startDate AND :endDate ORDER BY sm.createdAt DESC")
    List<StockMovement> findByUserAndDateRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    List<StockMovement> findByProductAndType(Product product, StockMovement.MovementType type);
    
    @Query("SELECT COALESCE(SUM(sm.quantity), 0) FROM StockMovement sm WHERE sm.product = :product AND sm.type = 'ENTRY'")
    Integer sumEntriesByProduct(@Param("product") Product product);
    
    @Query("SELECT COALESCE(SUM(sm.quantity), 0) FROM StockMovement sm WHERE sm.product = :product AND sm.type = 'EXIT'")
    Integer sumExitsByProduct(@Param("product") Product product);
}
