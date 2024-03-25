package com.example.demo.repository;

import com.example.demo.dto.CategoryBaseGetDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    CategoryBaseGetDto findByName(String name);
}
