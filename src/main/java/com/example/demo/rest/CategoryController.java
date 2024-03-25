package com.example.demo.rest;

import com.example.demo.dto.CategoryGetDto;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CategoryController {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    public CategoryController(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryGetDto> getCategoryById(@PathVariable(name = "id") long id) {
        Optional<Category> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CategoryGetDto response = mapper.categoryToCategoryGetDto(entity.get());

        return ResponseEntity.ok(response);
    }
}
