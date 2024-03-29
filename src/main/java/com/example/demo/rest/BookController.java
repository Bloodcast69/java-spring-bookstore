package com.example.demo.rest;

import com.example.demo.dto.BookBaseGetDto;
import com.example.demo.dto.BookCreateDto;
import com.example.demo.dto.BookGetDto;
import com.example.demo.dto.BookUpdateDto;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {

        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookGetDto> getBookById(@PathVariable(name = "id") long id) {
        BookGetDto response = bookService.getBookById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/{categoryId}")
    public ResponseEntity<List<BookBaseGetDto>> getBooksByCategoryId(@PathVariable(name = "categoryId") long categoryId) {
        List<BookBaseGetDto> response = bookService.getBooksByCategoryId(categoryId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/book")
    public ResponseEntity<BookGetDto> createBook(@RequestBody BookCreateDto body) {
        BookGetDto response = bookService.createBook(body);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookGetDto> updateBook(@RequestBody BookUpdateDto body) {
        BookGetDto response = bookService.updateBook(body);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<BookGetDto> deleteBook(@PathVariable(name = "id") long id) {
        BookGetDto response = bookService.deleteBook(id);

        return ResponseEntity.ok(response);
    }
}
