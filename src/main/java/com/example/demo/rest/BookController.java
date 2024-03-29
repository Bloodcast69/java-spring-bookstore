package com.example.demo.rest;

import com.example.demo.dto.BookBaseGetDto;
import com.example.demo.dto.BookCreateDto;
import com.example.demo.dto.BookGetDto;
import com.example.demo.dto.BookUpdateDto;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookGetDto> getBookById(@PathVariable(name = "id") long id) {
        logger.info("Called getBookById with id = {}.", id);
        BookGetDto response = bookService.getBookById(id);

        logger.info("getBookById response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/{categoryId}")
    public ResponseEntity<List<BookBaseGetDto>> getBooksByCategoryId(@PathVariable(name = "categoryId") long categoryId) {
        logger.info("Called getBooksByCategoryId with id = {}.", categoryId);
        List<BookBaseGetDto> response = bookService.getBooksByCategoryId(categoryId);

        logger.info("getBooksByCategoryId response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/book")
    public ResponseEntity<BookGetDto> createBook(@RequestBody BookCreateDto body) {
        logger.info("Called createBook with body = {}.", body);
        BookGetDto response = bookService.createBook(body);

        logger.info("createBook response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookGetDto> updateBook(@RequestBody BookUpdateDto body) {
        logger.info("Called updateBook with body = {}.", body);
        BookGetDto response = bookService.updateBook(body);

        logger.info("updateBook response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<BookGetDto> deleteBook(@PathVariable(name = "id") long id) {
        logger.info("Called deleteBook with id = {}.", id);
        BookGetDto response = bookService.deleteBook(id);

        logger.info("deleteBook response = {}.", response);
        return ResponseEntity.ok(response);
    }
}
