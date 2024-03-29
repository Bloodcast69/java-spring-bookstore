package com.example.demo.repository;

import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.dto.CategoryGetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    CategoryGetDto findCategoryById(long id);


}
