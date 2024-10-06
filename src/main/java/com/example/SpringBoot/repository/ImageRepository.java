package com.example.SpringBoot.repository;

import com.example.SpringBoot.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
