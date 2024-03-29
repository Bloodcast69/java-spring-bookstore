package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository repository, CategoryRepository categoryRepository, BookMapper mapper, CategoryService categoryService) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public BookGetDto getBookById(long id) {
        Book entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist."));

        return mapper.bookToBookGetDto(entity);
    }

    @Transactional
    public List<BookBaseGetDto> getBooksByCategoryId(long id) {
        return repository.findForCategory(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist."));
    }

    @Transactional
    public BookGetDto createBook(BookCreateDto body) {
        Category existingCategory = categoryService.getFullCategoryById(body.getCategoryId());

        Book book = new Book(body.getName(), existingCategory);
        existingCategory.getBooks().add(book);
        System.out.println(existingCategory.getId());

        Book response = repository.save(book);

        return mapper.bookToBookGetDto(response);
    }

    @Transactional
    public BookGetDto updateBook(BookUpdateDto body) {
        Category existingCategory = categoryService.getFullCategoryById(body.getCategoryId());

        Book book = repository.findById(body.getId()).orElseThrow(() -> new EntityNotFoundException("Book with id " + body.getId() + " does not exist."));

        book.setCategory(existingCategory);
        book.setName(body.getName());
        existingCategory.getBooks().add(book);

        Book response = repository.save(book);

        return mapper.bookToBookGetDto(response);
    }

    @Transactional
    public BookGetDto deleteBook(long id) {
        Book existingBook = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist."));

        Category existingCategory = categoryService.getFullCategoryById(existingBook.getCategory().getId());

        existingCategory.getBooks().remove(existingBook);
        categoryRepository.save(existingCategory);

        existingBook.setCategory(null);
        repository.delete(existingBook);

        return mapper.bookToBookGetDto(existingBook);
    }
}
