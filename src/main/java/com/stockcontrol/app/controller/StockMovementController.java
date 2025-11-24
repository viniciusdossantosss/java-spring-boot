package com.stockcontrol.app.controller;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.StockMovement;
import com.stockcontrol.app.model.StockMovement.MovementType;
import com.stockcontrol.app.service.ProductService;
import com.stockcontrol.app.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService stockMovementService;
    private final ProductService productService;

    @GetMapping
    public String listMovements(Model model) {
        List<StockMovement> movements = stockMovementService.findAll();
        model.addAttribute("movements", movements);
        return "movements/list";
    }

    @GetMapping("/entry/{productId}")
    public String entryForm(@PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(MovementType.ENTRY);
        model.addAttribute("movement", movement);
        model.addAttribute("title", "Entrada de Estoque");
        return "movements/form";
    }

    @GetMapping("/exit/{productId}")
    public String exitForm(@PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(MovementType.EXIT);
        model.addAttribute("movement", movement);
        model.addAttribute("title", "Saída de Estoque");
        return "movements/form";
    }

    @PostMapping("/entry")
    public String createEntry(@Valid @ModelAttribute StockMovement movement,
                             RedirectAttributes redirectAttributes) {
        try {
            movement.setType(MovementType.ENTRY);
            stockMovementService.createMovement(movement);
            redirectAttributes.addFlashAttribute("message", "Entrada de estoque registrada com sucesso!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movements/entry/" + movement.getProduct().getId();
        }
    }

    @PostMapping("/exit")
    public String createExit(@Valid @ModelAttribute StockMovement movement,
                            RedirectAttributes redirectAttributes) {
        try {
            movement.setType(MovementType.EXIT);
            stockMovementService.createMovement(movement);
            redirectAttributes.addFlashAttribute("message", "Saída de estoque registrada com sucesso!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movements/exit/" + movement.getProduct().getId();
        }
    }

    @GetMapping("/product/{productId}")
    public String movementsByProduct(@PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        List<StockMovement> movements = stockMovementService.findByProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("movements", movements);
        return "movements/by-product";
    }
}
