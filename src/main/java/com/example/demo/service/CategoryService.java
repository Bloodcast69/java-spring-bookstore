package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final BookRepository bookRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, BookRepository bookRepository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public CategoryGetDto getCategoryById(long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        return mapper.categoryToCategoryGetDto(entity);
    }

    @Transactional
    public Category getFullCategoryById(long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));
    }

    @Transactional
    public List<CategoryGetDto> getAllCategories() {
        return mapper.categoryListToCategoryGetDtoList(repository.findAll());
    }

    @Transactional
    public CategoryBaseGetDto createCategory(CategoryCreateDto body) {
        Category entity;

        try {
            entity = repository.save(new Category(body.getName()));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Category with name " + body.getName() + " already exists.");
        }

        return mapper.categoryToCategoryBaseGetDto(entity);
    }

    @Transactional
    public CategoryBaseGetDto updateCategory(long id, CategoryUpdateDto body) {
        Category existingCategory = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        existingCategory.setName(body.getName());

        Category entity = repository.save(existingCategory);

        return mapper.categoryToCategoryBaseGetDto(entity);

    }

    @Transactional
    public CategoryBaseGetDto deleteCategory(long id, CategoryDeleteDto body) {
        Category existingCategory = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        if (!existingCategory.getBooks().isEmpty() && body.getForce()) {
            // remove books from category
            existingCategory.getBooks().forEach(book -> {
                book.setCategory(null);
                bookRepository.save(book);
            });

            existingCategory.getBooks().clear();
        }
        repository.delete(existingCategory);

        return mapper.categoryToCategoryBaseGetDto(existingCategory);
    }
}
