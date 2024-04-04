package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public CategoryGetDto getCategoryById(long id) {
        logger.info("Called getCategoryById with id = {}.", id);
        Category response = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        logger.info("getCategoryById response = {}.", response);
        return categoryMapper.categoryToCategoryGetDto(response);
    }

    @Transactional
    public Category getFullCategoryById(long id) {
        logger.info("Called getFullCategoryById with id = {}.", id);
        Category response = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        logger.info("getFullCategoryById response = {}.", response);
        return response;
    }

    @Transactional
    public List<CategoryGetDto> getAllCategories() {
        logger.info("Called getAllCategories.");
        List<CategoryGetDto> response = categoryMapper.categoryListToCategoryGetDtoList(categoryRepository.findAll());

        logger.info("getAllCategories response = {}.", response);
        return response;
    }

    @Transactional
    public CategoryBaseGetDto createCategory(CategoryCreateDto body) {
        logger.info("Called createCategory with body = {}.", body);
        Category entity;

        try {
            entity = categoryRepository.save(new Category(body.getName()));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Category with name " + body.getName() + " already exists.");
        }

        CategoryBaseGetDto response = categoryMapper.categoryToCategoryBaseGetDto(entity);

        logger.info("getAllCategories response = {}.", response);
        return response;
    }

    @Transactional
    public CategoryBaseGetDto updateCategory(long id, CategoryUpdateDto body) {
        logger.info("Called updateCategory with id = {} and body = {}.", id, body);
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        existingCategory.setName(body.getName());

        Category entity = categoryRepository.save(existingCategory);

        CategoryBaseGetDto response = categoryMapper.categoryToCategoryBaseGetDto(entity);

        logger.info("updateCategory response = {}.", response);
        return response;

    }

    @Transactional
    public CategoryBaseGetDto deleteCategory(long id, CategoryDeleteDto body) {
        logger.info("Called deleteCategory with id = {} and body = {}.", id, body);
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));

        if (!existingCategory.getBooks().isEmpty() && body.isForce()) {
            // remove books from category
            existingCategory.getBooks().forEach(book -> {
                book.setCategory(null);
                bookRepository.save(book);
            });

            existingCategory.getBooks().clear();
        }
        categoryRepository.delete(existingCategory);

        CategoryBaseGetDto response = categoryMapper.categoryToCategoryBaseGetDto(existingCategory);

        logger.info("updateCategory response = {}.", response);
        return response;
    }
}
