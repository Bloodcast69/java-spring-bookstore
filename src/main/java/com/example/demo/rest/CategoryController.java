package com.example.demo.rest;

import com.example.demo.dto.*;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryGetDto> getCategoryById(@PathVariable(name = "id") long id) {
        CategoryGetDto response = categoryService.getCategoryById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryGetDto>> getAllCategories() {
        List<CategoryGetDto> response = categoryService.getAllCategories();

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryBaseGetDto> createCategory(@RequestBody @Valid CategoryCreateDto body) {
        CategoryBaseGetDto response = categoryService.createCategory(body);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<CategoryBaseGetDto>(response, HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryBaseGetDto> updateCategory(@PathVariable(name = "id") long id, @RequestBody @Valid CategoryUpdateDto body) {
        CategoryBaseGetDto response = categoryService.updateCategory(id, body);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<CategoryBaseGetDto>(response, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryBaseGetDto> deleteCategory(@PathVariable(name = "id") long id, @RequestBody @Valid CategoryDeleteDto body) {
        CategoryBaseGetDto response = categoryService.deleteCategory(id, body);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<CategoryBaseGetDto>(response, HttpStatus.OK);
    }
}
