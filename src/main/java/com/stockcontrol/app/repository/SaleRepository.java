package com.stockcontrol.app.repository;

import com.stockcontrol.app.model.Sale;
import com.stockcontrol.app.model.Sale.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByStatus(SaleStatus status);
    List<Sale> findByUserId(Long userId);
    
    @Query("SELECT s FROM Sale s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    List<Sale> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(s) FROM Sale s WHERE s.status = :status AND s.createdAt BETWEEN :startDate AND :endDate")
    Long countByStatusAndDateRange(@Param("status") SaleStatus status,
                                  @Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.status = :status AND s.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalAmountByStatusAndDateRange(@Param("status") SaleStatus status,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);
}
