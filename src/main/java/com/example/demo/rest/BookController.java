package com.example.demo.rest;

import com.example.demo.dto.BookGetDto;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<BookGetDto>> getBooksByCategoryId(@PathVariable(name = "categoryId") long categoryId) {
        List<BookGetDto> response = bookService.getBooksByCategoryId(categoryId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
