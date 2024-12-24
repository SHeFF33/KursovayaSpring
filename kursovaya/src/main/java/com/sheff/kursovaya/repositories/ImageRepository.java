package com.sheff.kursovaya.repositories;

import com.sheff.kursovaya.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
