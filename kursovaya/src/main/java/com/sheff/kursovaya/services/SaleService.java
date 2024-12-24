package com.sheff.kursovaya.services;

import com.sheff.kursovaya.models.Product;
import com.sheff.kursovaya.models.Sale;
import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.repositories.ProductRepository;
import com.sheff.kursovaya.repositories.SaleRepository;
import com.sheff.kursovaya.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    public List<Sale> getUserPurchases(Principal principal) {
        return saleRepository.findByUserEmail(principal.getName());
    }

    public List<Sale> list() {
        return saleRepository.findAll();
    }

    public SaleService(ProductRepository productRepository, UserRepository userRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
    }

    @Transactional
    public void completeSale(Long productId, String Email) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
        User buyer = userRepository.findByEmail(Email);
        User seller = product.getUser();
        if ("Продан".equals(product.getStatus())) {throw new IllegalStateException("Этот товар уже продан.");}
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setUser(buyer);
        sale.setSaleuser_id(seller);
        sale.setPrice(product.getPrice());
        sale.setMagprice(product.getMagprice());
        sale.setSaleDate(LocalDateTime.now());

        saleRepository.save(sale);
    }
}

