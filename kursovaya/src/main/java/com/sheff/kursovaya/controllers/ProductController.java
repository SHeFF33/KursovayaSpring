package com.sheff.kursovaya.controllers;

import com.sheff.kursovaya.models.Product;
import com.sheff.kursovaya.models.Sale;
import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.services.ProductService;
import com.sheff.kursovaya.services.SaleService;
import com.sheff.kursovaya.utils.DateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SaleService saleService;

    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String searchWord, @RequestParam(name = "category", required = false) String category, Principal principal, Model model) {
        model.addAttribute("products", productService.searchAvailableProducts(searchWord, category));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("category", category);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/product/buy/{id}")
    public String confirmPurchase(@PathVariable Long id, Principal principal, Model model) {
        Product product = productService.getProductById(id);
        saleService.completeSale(id, principal.getName());
        if (!"В продаже".equals(product.getStatus())) {
            product.setStatus("Продан");
        }

        productService.purchaseProduct(product);

        return "redirect:/";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3, @Valid Product product, BindingResult bindingResult, Principal principal, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Некорректно заполнены поля формы");
        }
        if (product.getPrice() != null) {
            product.setMagprice(product.getPrice() * 1.3);
        }
        if (product.getStatus() == null) {
            product.setStatus("В продаже");
        }
        productService.saveProduct(principal, product, file1, file2, file3);

        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }

    @GetMapping("/myproduct/{id}")
    public String myProductInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        model.addAttribute("dateUtils", new DateUtils());
        return "my-product-info";
    }
    @GetMapping("/my/purchases")
    public String userPurchases(Principal principal, Model model) {
        List<Sale> purchases = saleService.getUserPurchases(principal);
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("purchases", purchases);
        return "my-purchases";
    }
}
