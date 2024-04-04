package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.Category;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, BookMapper bookMapper, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public BookGetDto getBookById(long id) {
        logger.info("Called getBookById with id = {}.", id);
        Book response = bookRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist."));

        logger.info("getBookById response = {}.", response);
        return bookMapper.bookToBookGetDto(response);
    }

    @Transactional
    public List<BookBaseGetDto> getBooksByCategoryId(long id) {
        logger.info("Called getBooksByCategoryId with id = {}.", id);
        List<BookBaseGetDto> response = bookRepository
                .findForCategory(id)
                .stream().map(book -> bookMapper.bookToBookBaseGetDto(book))
                .toList();

        logger.info("getBooksByCategoryId response = {}.", response);
        return response;
    }

    @Transactional
    public BookGetDto createBook(BookCreateDto body) {
        logger.info("Called createBook with body = {}.", body);
        Category existingCategory = categoryService.getFullCategoryById(body.getCategoryId());

        Book book = new Book(body.getName(), existingCategory);
        existingCategory.getBooks().add(book);

        Book response = bookRepository.save(book);

        logger.info("createBook response = {}.", response);
        return bookMapper.bookToBookGetDto(response);
    }

    @Transactional
    public BookGetDto updateBook(BookUpdateDto body) {
        logger.info("Called updateBook with body = {}.", body);
        Category existingCategory = categoryService.getFullCategoryById(body.getCategoryId());

        Book book = bookRepository.findById(body.getId()).orElseThrow(() -> new EntityNotFoundException("Book with id " + body.getId() + " does not exist."));

        book.setCategory(existingCategory);
        book.setName(body.getName());
        existingCategory.getBooks().add(book);

        Book response = bookRepository.save(book);

        logger.info("updateBook response = {}.", response);
        return bookMapper.bookToBookGetDto(response);
    }

    @Transactional
    public BookGetDto deleteBook(long id) {
        logger.info("Called deleteBook with id = {}.", id);
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist."));

        Category existingCategory = categoryService.getFullCategoryById(existingBook.getCategory().getId());

        existingCategory.getBooks().remove(existingBook);
        categoryRepository.save(existingCategory);

        existingBook.setCategory(null);
        bookRepository.delete(existingBook);

        BookGetDto response = bookMapper.bookToBookGetDto(existingBook);

        logger.info("deleteBook response = {}.", response);
        return response;
    }
}
