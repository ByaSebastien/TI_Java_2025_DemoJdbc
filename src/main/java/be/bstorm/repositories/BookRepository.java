package be.bstorm.repositories;

import be.bstorm.entities.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();
    Book findById(int id);
    Integer save(Book book);
    boolean update(Integer id, Book book);
    boolean delete(Integer id);
}
