package com.example.demo.mapper;

import com.example.demo.dto.BookGetDto;
import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.repository.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public BookGetDto bookToBookGetDto(Book book) {
        return new BookGetDto(
                book.getId(),
                book.getName(),
                new CategoryBaseGetDto(
                        // TU JEST BŁĄD, próba pobrania kategorii z pustym id
                        book.getCategory().getId(),
                        book.getCategory().getName()
                )
        );
    }
}
