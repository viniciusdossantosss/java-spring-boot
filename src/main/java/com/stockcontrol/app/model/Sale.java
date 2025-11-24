package com.stockcontrol.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Cliente é obrigatório")
    @Column(nullable = false, length = 255)
    private String customerName;

    @Column(length = 20)
    private String customerPhone;

    @Column(length = 255)
    private String customerEmail;

    @NotNull(message = "Total da venda é obrigatório")
    @Positive(message = "Total deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SaleStatus status = SaleStatus.COMPLETED;

    @Column(length = 500)
    private String observations;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum SaleStatus {
        PENDING("Pendente"),
        COMPLETED("Concluída"),
        CANCELLED("Cancelada");

        private final String label;

        SaleStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(Product product, Integer quantity, BigDecimal unitPrice) {
        SaleItem item = new SaleItem();
        item.setSale(this);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
        items.add(item);
    }

    public void calculateTotal() {
        totalAmount = items.stream()
                .map(SaleItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
