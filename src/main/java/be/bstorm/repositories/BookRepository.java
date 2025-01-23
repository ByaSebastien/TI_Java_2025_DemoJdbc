package be.bstorm.repositories;

import be.bstorm.entities.Book;

import java.util.List;

public interface BookRepository extends BaseRepository<Book,Integer> {

    List<Book> findAllWithAuthor();
}
