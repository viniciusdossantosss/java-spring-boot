package com.stockcontrol.app.controller;

import com.stockcontrol.app.model.Product;
import com.stockcontrol.app.model.Category;
import com.stockcontrol.app.service.ProductService;
import com.stockcontrol.app.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        return "products/list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product,
                               RedirectAttributes redirectAttributes) {
        try {
            productService.save(product);
            redirectAttributes.addFlashAttribute("message", "Produto criado com sucesso!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "products/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                               @Valid @ModelAttribute Product product,
                               RedirectAttributes redirectAttributes) {
        try {
            productService.update(id, product);
            redirectAttributes.addFlashAttribute("message", "Produto atualizado com sucesso!");
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Produto excluído com sucesso!");
            return "redirect:/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir produto: " + e.getMessage());
            return "redirect:/products";
        }
    }
}
