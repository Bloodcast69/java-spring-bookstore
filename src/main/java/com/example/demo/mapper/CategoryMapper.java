package com.example.demo.mapper;

import com.example.demo.dto.BookBaseGetDto;
import com.example.demo.dto.CategoryBaseGetDto;
import com.example.demo.dto.CategoryGetDto;
import com.example.demo.repository.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMapper {
    private final BookMapper bookMapper;

    public CategoryMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

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

    public List<CategoryGetDto> categoryListToCategoryGetDtoList(List<Category> categories) {
        return categories
                .stream()
                .map(category -> new CategoryGetDto(
                        category.getId(),
                        category.getName(),
                        category.getBooks()
                                .stream()
                                .map(book -> bookMapper.bookToBookBaseGetDto(book))
                                .toList()))
                .toList();
    }
}
