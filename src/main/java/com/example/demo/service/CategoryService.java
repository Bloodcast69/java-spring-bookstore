package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository repository;
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
        Category existingCategory = repository.findByName(body.getName());

        if (existingCategory != null) {
            return null;
        }

        Category entity = repository.save(new Category(body.getName()));

        return mapper.categoryToCategoryBaseGetDto(entity);

    }

    @Transactional
    public CategoryBaseGetDto updateCategory(long id, CategoryUpdateDto body) {
        Optional<Category> existingCategory = repository.findById(id);

        if (existingCategory.isEmpty()) {
            return null;
        }

        existingCategory.get().setName(body.getName());

        Category entity = repository.save(existingCategory.get());


        return mapper.categoryToCategoryBaseGetDto(entity);

    }

    @Transactional
    public CategoryBaseGetDto deleteCategory(long id, CategoryDeleteDto body) {
        Optional<Category> existingCategory = repository.findById(id);

        if (existingCategory.isEmpty()) {
            return null;
        }

        List<Book> categoryBooks = existingCategory.get().getBooks();

        if (!categoryBooks.isEmpty() && body.getForce()) {
            // remove books from category
            categoryBooks.forEach(book -> book.clearCategory());
            existingCategory.get().setBooks(categoryBooks);
            repository.save(existingCategory.get());
            // remove category
            repository.deleteById(id);
        } else if (categoryBooks.isEmpty()) {
            repository.delete(existingCategory.get());
        }

        return mapper.categoryToCategoryBaseGetDto(existingCategory.get());
    }
}
