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

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductService productService;

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

    public List<StockMovement> getMovementsByProduct(Product product) {
        return stockMovementRepository.findByProduct(product);
    }

    public List<StockMovement> getMovementsByUser(User user) {
        return stockMovementRepository.findByUser(user);
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
}
