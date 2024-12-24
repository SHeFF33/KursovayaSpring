package com.sheff.kursovaya.repositories;

import com.sheff.kursovaya.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitle(String title);

    List<Product> findByStatusAndTitleContaining(String status, String title);

    List<Product> findByStatus(String status);

    @Query("SELECT p FROM Product p WHERE p.status = :status " +
            "AND ((:searchWord IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchWord, '%'))) " +
            "OR (:searchWord IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :searchWord, '%')))) " +
            "AND (:category = '' OR LOWER(p.category) = LOWER(:category))")
    List<Product> findByStatusAndFilters(@Param("status") String status,
                                         @Param("searchWord") String searchWord,
                                         @Param("category") String category);

}