package be.bstorm;

import be.bstorm.entities.Author;
import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;
import be.bstorm.repositories.impls.BookRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepositoryImpl();

        bookRepository.findAllWithAuthor().forEach(System.out::println);
    }
}
