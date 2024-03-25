package com.example.demo.rest;

import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.dto.CategoryCreateDto;
import com.example.demo.dto.CategoryGetDto;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/category")
    public ResponseEntity<CategoryBaseGetDto> createCategory( @RequestBody CategoryCreateDto body) {
        System.out.println("Category create");
        CategoryBaseGetDto response = categoryService.createCategory(body);
        System.out.println("Category created");
        if (response == null) {
            System.out.println("Null response bad request");
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Returning");
        return new ResponseEntity<CategoryBaseGetDto>(response, HttpStatus.CREATED);
    }
}
