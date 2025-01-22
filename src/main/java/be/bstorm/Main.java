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

        Book book = new Book("10987654321","Update",null, LocalDate.now(),1);

        bookRepository.save(book);

        RappelGenerique<Book> rg = new RappelGenerique<>();

        RappelGenerique<Author> rg2 = new RappelGenerique<>();

        List<Integer> ints = new ArrayList<>();
    }
}
