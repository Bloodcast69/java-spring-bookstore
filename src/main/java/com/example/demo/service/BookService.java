package com.example.demo.service;

import com.example.demo.dto.BookGetDto;
import com.example.demo.dto.CategoryGetDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;
    private final CategoryService categoryService;
    private final BookMapper mapper;

    public BookService(BookRepository repository, BookMapper mapper, CategoryService categoryService) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryService = categoryService;
    }

    @Transactional
    public BookGetDto getBookById(long id) {
        Optional<Book> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return null;
        }


        return mapper.bookToBookGetDto(entity.get());
    }

    @Transactional
    public List<BookGetDto> getBooksByCategoryId(long id) {
        CategoryGetDto category = categoryService.getCategoryById(id);

        if (category == null) {
            return null;
        }

        List<BookGetDto> books = repository
                .findAll()
                .stream()
                .filter(book -> book.getCategory().getId() == id)
                .map(book -> mapper.bookToBookGetDto(book))
                .toList();

        return books;

    }
}
