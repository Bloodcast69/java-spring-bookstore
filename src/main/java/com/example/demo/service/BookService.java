package com.example.demo.service;

import com.example.demo.dto.BookGetDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public BookGetDto getBookById(long id) {
        Optional<Book> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return null;
        }


        return mapper.bookToBookGetDto(entity.get());
    }
}
