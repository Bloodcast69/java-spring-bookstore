package com.example.demo.service;

import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.dto.CategoryCreateDto;
import com.example.demo.dto.CategoryGetDto;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CategoryGetDto getCategoryById(long id) {
        Optional<Category> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return null;
        }

        return mapper.categoryToCategoryGetDto(entity.get());
    }

    @Transactional
    public CategoryBaseGetDto createCategory(CategoryCreateDto body) {
        CategoryBaseGetDto existingCategory = repository.findByName(body.getName());

        if (existingCategory != null) {
            return null;
        }
        System.out.println(new Category(body.getName()));
        Category entity = repository.save(new Category(body.getName()));
        // [demo] [nio-8080-exec-2] o.h.engine.jdbc.spi.SqlExceptionHelper   : ERROR: duplicate key value violates unique constraint "category_pkey"
        //  Detail: Key (id)=(1) already exists.

        return mapper.categoryToCategoryBaseGetDto(entity);

    }
}
