package com.stockcontrol.app.service;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.StockMovement;
import com.stockcontrol.app.model.User;
import com.stockcontrol.app.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductService productService;

    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    public StockMovement createMovement(StockMovement movement) {
        Product product = productService.findById(movement.getProduct().getId());
        
        if (movement.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        if (movement.getType() == StockMovement.MovementType.EXIT) {
            if (product.getQuantity() < movement.getQuantity()) {
                throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + product.getQuantity());
            }
            product.setQuantity(product.getQuantity() - movement.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() + movement.getQuantity());
        }

        productService.save(product);
        movement.setProduct(product);
        return stockMovementRepository.save(movement);
    }

    public StockMovement recordEntry(Product product, Integer quantity, String reason, User user) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setUser(user);
        movement.setType(StockMovement.MovementType.ENTRY);
        movement.setQuantity(quantity);
        movement.setReason(reason);

        product.setQuantity(product.getQuantity() + quantity);
        productService.updateProduct(product);

        return stockMovementRepository.save(movement);
    }

    public StockMovement recordExit(Product product, Integer quantity, String reason, User user) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque. Disponível: " + product.getQuantity());
        }

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setUser(user);
        movement.setType(StockMovement.MovementType.EXIT);
        movement.setQuantity(quantity);
        movement.setReason(reason);

        product.setQuantity(product.getQuantity() - quantity);
        productService.updateProduct(product);

        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> findByProductId(Long productId) {
        return stockMovementRepository.findByProductId(productId);
    }

    public List<StockMovement> getMovementsByProduct(Product product) {
        return stockMovementRepository.findByProduct(product);
    }

    public List<StockMovement> getMovementsByUser(User user) {
        return stockMovementRepository.findByUser(user);
    }

    public List<StockMovement> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return stockMovementRepository.findByDateRange(startDate, endDate);
    }

    public List<StockMovement> getMovementsByDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return stockMovementRepository.findByUserAndDateRange(user, startDate, endDate);
    }

    public List<StockMovement> getMovementsByMonth(User user, YearMonth yearMonth) {
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return getMovementsByDateRange(user, startDate, endDate);
    }

    public Integer getTotalEntries(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return getMovementsByDateRange(user, startDate, endDate).stream()
                .filter(m -> m.getType() == StockMovement.MovementType.ENTRY)
                .mapToInt(StockMovement::getQuantity)
                .sum();
    }

    public Integer getTotalExits(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return getMovementsByDateRange(user, startDate, endDate).stream()
                .filter(m -> m.getType() == StockMovement.MovementType.EXIT)
                .mapToInt(StockMovement::getQuantity)
                .sum();
    }

    public Long countTodayMovements() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return stockMovementRepository.countByDateRange(start, end);
    }

    public Map<String, Long> getMovementsByLastDays(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return findByDateRange(startDate, LocalDateTime.now()).stream()
                .collect(Collectors.groupingBy(
                    movement -> movement.getCreatedAt().toLocalDate().toString(),
                    Collectors.counting()
                ));
    }
}
