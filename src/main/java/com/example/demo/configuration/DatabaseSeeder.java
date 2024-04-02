package com.example.demo.configuration;

import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DatabaseSeeder {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

        seedDatabase();
    }

    @Transactional
    private void seedDatabase() {
        Category catOne = categoryRepository.save(new Category("Kryminały"));
        Category catTwo = categoryRepository.save(new Category("Romanse"));
        Category catThree = categoryRepository.save(new Category("Obyczajowe"));
        Category catFour = categoryRepository.save(new Category("Inne"));

        Book bookOne = new Book("Ojciec Chrzestny", catOne);
        catOne.getBooks().add(bookOne);

        Book bookTwo = new Book("Romans Brygidy", catTwo);
        catTwo.getBooks().add(bookTwo);

        Book bookThree = new Book("O czymś", catThree);
        catThree.getBooks().add(bookThree);

        Book bookFour = new Book("Zabójstwo", catOne);
        catOne.getBooks().add(bookFour);

        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        bookRepository.save(bookThree);
        bookRepository.save(bookFour);

        User userFirst = userRepository.save(new User("John", "Doe", "john.doe@example.com", "password", true));


    }
}
