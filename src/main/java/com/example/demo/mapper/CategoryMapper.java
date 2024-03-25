package com.example.demo.mapper;

import com.example.demo.dto.CategoryGetDto;
import com.example.demo.repository.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryGetDto categoryToCategoryGetDto(Category category) {
        return new CategoryGetDto(category.getId(), category.getName());
    }
}
