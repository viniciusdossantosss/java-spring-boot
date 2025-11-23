package com.stockcontrol.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum MovementType {
        ENTRY("Entrada"),
        EXIT("Saída");

        private final String label;

        MovementType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
