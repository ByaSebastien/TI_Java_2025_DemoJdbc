package be.bstorm;

import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;
import be.bstorm.repositories.impls.BookRepositoryImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepositoryImpl();

        List<Book> books = bookRepository.findAll();

        books.forEach(System.out::println);
    }
}
