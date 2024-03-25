package com.example.demo.mapper;

import com.example.demo.dto.BookBaseGetDto;
import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.dto.CategoryGetDto;
import com.example.demo.repository.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMapper {
    public CategoryGetDto categoryToCategoryGetDto(Category category) {
        final List<BookBaseGetDto> books = category
                .getBooks()
                .stream()
                .map(book -> new BookBaseGetDto(
                                book.getId(),
                                book.getName()
                        )
                )
                .toList();

        return new CategoryGetDto(category.getId(), category.getName(), books);
    }

    public CategoryBaseGetDto categoryToCategoryBaseGetDto(Category category) {
        return new CategoryBaseGetDto(category.getId(), category.getName());
    }
}
