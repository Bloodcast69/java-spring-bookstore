package com.example.demo.rest;

import com.example.demo.dto.*;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryGetDto> getCategoryById(@PathVariable(name = "id") long id) {
        logger.info("Called getCategoryById with id = {}.", id);
        CategoryGetDto response = categoryService.getCategoryById(id);

        logger.info("getCategoryById response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryGetDto>> getAllCategories() {
        logger.info("Called getAllCategories.");
        List<CategoryGetDto> response = categoryService.getAllCategories();

        logger.info("getAllCategories response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryBaseGetDto> createCategory(@Valid @RequestBody CategoryCreateDto body) {
        logger.info("Called createCategory with body = {}.", body);
        CategoryBaseGetDto response = categoryService.createCategory(body);

        logger.info("createCategory response = {}.", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryBaseGetDto> updateCategory(@PathVariable(name = "id") long id, @RequestBody @Valid CategoryUpdateDto body) {
        logger.info("Called updateCategory with id = {} and body = {}.", id, body);
        CategoryBaseGetDto response = categoryService.updateCategory(id, body);

        logger.info("updateCategory response = {}.", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryBaseGetDto> deleteCategory(@PathVariable(name = "id") long id, @RequestBody @Valid CategoryDeleteDto body) {
        logger.info("Called updateCategory with id = {} and body = {}.", id, body);
        CategoryBaseGetDto response = categoryService.deleteCategory(id, body);

        logger.info("deleteCategory response = {}.", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
