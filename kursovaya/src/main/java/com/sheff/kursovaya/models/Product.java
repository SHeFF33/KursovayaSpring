package com.sheff.kursovaya.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Производитель не может быть пустым")
    private String brand;

    @NotBlank(message = "Модель не может быть пустой")
    @Size(max = 25, message = "Модель не может содержать больше 3 символов")
    @Pattern(regexp = "^[A-Za-z0-9]{8,}$", message = "Модель должна содержать только буквы латинского алфавита и цифры")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    @Column(length = 1000)
    private String description;
    @NotBlank(message = "Цена не может быть пустой")
    private Double price;

    private Double magprice;
    @NotBlank(message = "Категория не может быть пустой")
    private String category;
    private String status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() { dateOfCreated = LocalDateTime.now(); }

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

    public Product(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMagprice() {
        return magprice;
    }

    public void setMagprice(Double magprice) {
        this.magprice = magprice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public Product(Long id, String brand, String title, String description, Double price, Double magprice, String category, String status, List<Image> images, User user, Long previewImageId, LocalDateTime dateOfCreated) {
        this.id = id;
        this.brand = brand;
        this.title = title;
        this.description = description;
        this.price = price;
        this.magprice = magprice;
        this.category = category;
        this.status = status;
        this.images = images;
        this.user = user;
        this.previewImageId = previewImageId;
        this.dateOfCreated = dateOfCreated;
    }
}