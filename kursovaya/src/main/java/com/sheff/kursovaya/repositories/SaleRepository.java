package com.sheff.kursovaya.repositories;

import com.sheff.kursovaya.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserEmail(String email);

    @Query("SELECT s FROM Sale s WHERE s.user.email = :email")
    List<Sale> findByUserEmailCustom(@Param("email") String email);
}

