package com.stockcontrol.app.repository;

import com.stockcontrol.app.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    List<SaleItem> findBySaleId(Long saleId);
    List<SaleItem> findByProductId(Long productId);
    
    @Query("SELECT SUM(si.quantity) FROM SaleItem si WHERE si.product.id = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);
    
    @Query("SELECT si FROM SaleItem si WHERE si.sale.id = :saleId")
    List<SaleItem> findItemsBySaleId(@Param("saleId") Long saleId);
}
