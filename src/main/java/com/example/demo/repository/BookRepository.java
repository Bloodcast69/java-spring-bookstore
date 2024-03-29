package com.example.demo.repository;

import com.example.demo.dto.BookBaseGetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
@Query(nativeQuery = true)
    Optional<List<BookBaseGetDto>> findForCategory(@Param("id") long id);
}
