package com.stockcontrol.app.service;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.Sale;
import com.stockcontrol.app.model.Sale.SaleStatus;
import com.stockcontrol.app.model.SaleItem;
import com.stockcontrol.app.model.StockMovement;
import com.stockcontrol.app.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final StockMovementService stockMovementService;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale save(Sale sale) {
        // Validar produtos e estoque
        for (SaleItem item : sale.getItems()) {
            Product product = productService.findById(item.getProduct().getId());
            
            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
            }
            
            item.setUnitPrice(product.getPrice());
            item.calculateTotal();
        }
        
        sale.calculateTotal();
        Sale savedSale = saleRepository.save(sale);
        
        // Atualizar estoque
        for (SaleItem item : savedSale.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
            
            // Registrar movimentação de saída
            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setQuantity(item.getQuantity());
            movement.setType(StockMovement.MovementType.EXIT);
            movement.setReason("Venda #" + savedSale.getId());
            stockMovementService.createMovement(movement);
        }
        
        return savedSale;
    }

    public void cancel(Long id) {
        Sale sale = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));
        
        if (sale.getStatus() == SaleStatus.CANCELLED) {
            throw new IllegalArgumentException("Venda já está cancelada");
        }
        
        // Devolver produtos ao estoque
        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productService.save(product);
            
            // Registrar movimentação de entrada
            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setQuantity(item.getQuantity());
            movement.setType(StockMovement.MovementType.ENTRY);
            movement.setReason("Cancelamento da venda #" + sale.getId());
            stockMovementService.createMovement(movement);
        }
        
        sale.setStatus(SaleStatus.CANCELLED);
        saleRepository.save(sale);
    }

    public List<Sale> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findByDateRange(startDate, endDate);
    }

    public Long countByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.countByStatusAndDateRange(SaleStatus.COMPLETED, startDate, endDate);
    }

    public BigDecimal getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = saleRepository.sumTotalAmountByStatusAndDateRange(SaleStatus.COMPLETED, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    public List<Sale> findByUserId(Long userId) {
        return saleRepository.findByUserId(userId);
    }

    public Long countTodaySales() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return countByDateRange(start, end);
    }

    // Métodos para relatórios
    public Map<String, Long> getSalesByLastDays(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<Sale> sales = findByDateRange(startDate, LocalDateTime.now());
        
        return sales.stream()
                .collect(Collectors.groupingBy(
                    sale -> sale.getCreatedAt().toLocalDate().toString(),
                    Collectors.counting()
                ));
    }
}
