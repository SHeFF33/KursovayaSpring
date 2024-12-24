package com.sheff.kursovaya.services;

import com.sheff.kursovaya.models.Image;
import com.sheff.kursovaya.models.Product;
import com.sheff.kursovaya.models.User;
import com.sheff.kursovaya.models.enums.Role;
import com.sheff.kursovaya.repositories.ImageRepository;
import com.sheff.kursovaya.repositories.ProductRepository;
import com.sheff.kursovaya.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private Role role;

    public List<Product> list() {
        return productRepository.findAll();
    }

    public void purchaseProduct(Product product) {
        product.setStatus("Продан");
        productRepository.save(product);
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void updateProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Product existingProduct = getProductById(product.getId());
        if (existingProduct != null) {
            existingProduct.setBrand(product.getBrand());
            existingProduct.setTitle(product.getTitle());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setMagprice(product.getPrice() * 1.5);
            existingProduct.setStatus(product.getStatus());

            if (file1.getSize() != 0) {
                Image image1 = toImageEntity(file1);
                image1.setPreviewImage(true);
                existingProduct.addImageToProduct(image1);
            }
            if (file2.getSize() != 0) {
                Image image2 = toImageEntity(file2);
                existingProduct.addImageToProduct(image2);
            }
            if (file3.getSize() != 0) {
                Image image3 = toImageEntity(file3);
                existingProduct.addImageToProduct(image3);
            }

            productRepository.save(existingProduct);
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchAvailableProducts(String searchWord, String category) {
        if ((searchWord == null || searchWord.isBlank()) && (category == null || category.isBlank())) {
            return productRepository.findByStatus("В продаже");
        }
        return productRepository.findByStatusAndFilters("В продаже", searchWord, category);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
                productRepository.delete(product);
        }
    }
}

