package com.sheff.kursovaya.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "saleuser_id", nullable = false)
    private User saleuser_id;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "magprice", nullable = false)
    private Double magprice;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSaleuser_id() {
        return saleuser_id;
    }

    public void setSaleuser_id(User saleuser_id) {
        this.saleuser_id = saleuser_id;
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

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
