package com.stockcontrol.app.controller;

import com.stockcontrol.app.service.ProductService;
import com.stockcontrol.app.service.StockMovementService;
import com.stockcontrol.app.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ProductService productService;
    private final StockMovementService stockMovementService;
    private final SaleService saleService;

    @GetMapping
    public String reportsHome(Model model) {
        // Estatísticas gerais
        model.addAttribute("totalProducts", productService.countAll());
        model.addAttribute("lowStockProducts", productService.countLowStock());
        model.addAttribute("totalValue", productService.getTotalStockValue());
        
        return "reports/index";
    }

    @GetMapping("/inventory")
    public String inventoryReport(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("totalProducts", productService.countAll());
        model.addAttribute("totalValue", productService.getTotalStockValue());
        model.addAttribute("lowStockProducts", productService.findLowStockProducts());
        
        return "reports/inventory";
    }

    @GetMapping("/movements")
    public String movementReport(@RequestParam(required = false) String startDate,
                                @RequestParam(required = false) String endDate,
                                Model model) {
        
        LocalDateTime start = startDate != null ? 
            LocalDateTime.parse(startDate + "T00:00:00") : 
            LocalDateTime.now().minusDays(30);
        LocalDateTime end = endDate != null ? 
            LocalDateTime.parse(endDate + "T23:59:59") : 
            LocalDateTime.now();
        
        model.addAttribute("movements", stockMovementService.findByDateRange(start, end));
        model.addAttribute("startDate", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("endDate", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        return "reports/movements";
    }

    @GetMapping("/sales")
    public String salesReport(@RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             Model model) {
        
        LocalDateTime start = startDate != null ? 
            LocalDateTime.parse(startDate + "T00:00:00") : 
            LocalDateTime.now().minusDays(30);
        LocalDateTime end = endDate != null ? 
            LocalDateTime.parse(endDate + "T23:59:59") : 
            LocalDateTime.now();
        
        model.addAttribute("sales", saleService.findByDateRange(start, end));
        model.addAttribute("totalSales", saleService.countByDateRange(start, end));
        model.addAttribute("totalRevenue", saleService.getTotalRevenueByDateRange(start, end));
        model.addAttribute("startDate", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("endDate", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        return "reports/sales";
    }

    @GetMapping("/dashboard")
    public String dashboardReport(Model model) {
        // Dados para gráficos
        Map<String, Object> chartData = new HashMap<>();
        
        // Produtos por categoria
        chartData.put("productsByCategory", productService.getProductsCountByCategory());
        
        // Movimentações dos últimos 7 dias
        chartData.put("movementsLast7Days", stockMovementService.getMovementsByLastDays(7));
        
        // Vendas dos últimos 30 dias
        chartData.put("salesLast30Days", saleService.getSalesByLastDays(30));
        
        model.addAttribute("chartData", chartData);
        model.addAttribute("totalProducts", productService.countAll());
        model.addAttribute("lowStockCount", productService.countLowStock());
        model.addAttribute("totalStockValue", productService.getTotalStockValue());
        model.addAttribute("todayMovements", stockMovementService.countTodayMovements());
        model.addAttribute("todaySales", saleService.countTodaySales());
        
        return "reports/dashboard";
    }
}
