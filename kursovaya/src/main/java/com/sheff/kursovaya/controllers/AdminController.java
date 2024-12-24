package com.sheff.kursovaya.controllers;

import com.sheff.kursovaya.models.Product;
import com.sheff.kursovaya.models.Sale;
import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.models.enums.Role;
import com.sheff.kursovaya.services.ProductService;
import com.sheff.kursovaya.services.SaleService;
import com.sheff.kursovaya.services.UserService;
import com.sheff.kursovaya.utils.DateUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final SaleService saleService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("sales", saleService.list());
        model.addAttribute("users", userService.list());
        model.addAttribute("products", productService.list());
        model.addAttribute("dateUtils", new DateUtils());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@ModelAttribute @RequestParam("userId") User user, @RequestParam Map<String, String> form, Model model) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "admin-user-info";
    }

    @GetMapping("/admin/product/{id}")
    public String adminProductInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        model.addAttribute("dateUtils", new DateUtils());
        return "admin-product-info";
    }

    @GetMapping("/admin/product/add")
    public String addProductPage(Model model, Principal principal) {
        model.addAttribute("product", new Product());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin-product-add";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@RequestParam("file1") MultipartFile file1,  @RequestParam("file2") MultipartFile file2, @RequestParam("file3")  MultipartFile file3,@Valid Product product, Principal principal, Model model, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
        }
        if(product.getPrice() != null){
            product.setMagprice(product.getPrice()* 1.5);
        }
        if(product.getStatus() == null) {
            product.setStatus("В продаже");
        }
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/admin";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String productEdit(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "product-edit";
    }

    @PostMapping("/admin/product/edit")
    public String productEdit(Product product, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля");
            return "product-edit";
        }

        productService.updateProduct(product, file1, file2, file3);
        return "redirect:/admin";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null && "В продаже".equals(product.getStatus())) {
            productService.deleteProduct(id);
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/exportExcel")
    public void exportToExcel(HttpServletResponse response, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) throws IOException {
        List<Sale> sales = saleService.list();
        sales = sales.stream()
                .filter(sale -> sale.getSaleDate().isAfter(startDate) && sale.getSaleDate().isBefore(endDate))
                .collect(Collectors.toList());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Данные товара");
        headerRow.createCell(1).setCellValue("Покупатель");
        headerRow.createCell(2).setCellValue("Продавец (администратор)");
        headerRow.createCell(3).setCellValue("Цена продавца (закупочная)");
        headerRow.createCell(4).setCellValue("Итоговая цена");
        headerRow.createCell(5).setCellValue("Дата продажи");
        headerRow.createCell(6).setCellValue("Выручка");
        int rowNum = 1;
        double totalProfit = 0.0;
        for (Sale sale : sales) {
            Row row = sheet.createRow(rowNum++);
            double profit = sale.getMagprice() - sale.getPrice();
            totalProfit += profit;

            row.createCell(0).setCellValue(sale.getProduct().getBrand() + " " + sale.getProduct().getTitle());
            row.createCell(1).setCellValue(sale.getUser().getEmail());
            row.createCell(2).setCellValue(sale.getSaleuser_id().getEmail());
            row.createCell(3).setCellValue(sale.getPrice());
            row.createCell(4).setCellValue(sale.getMagprice());
            row.createCell(5).setCellValue(DateUtils.format(sale.getSaleDate()));
            row.createCell(6).setCellValue(profit);
        }
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(5).setCellValue("Общая выручка:");
        totalRow.createCell(6).setCellValue(totalProfit);
        for (int i = 0; i <= 6; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=sales.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
