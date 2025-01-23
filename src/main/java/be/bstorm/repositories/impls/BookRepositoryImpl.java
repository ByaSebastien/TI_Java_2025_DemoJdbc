package be.bstorm.repositories.impls;

import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;
import be.bstorm.utils.ConnectionUtils;

import java.sql.*;
import java.time.LocalDate;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book,Integer> implements BookRepository {

    public BookRepositoryImpl() {
        super("book","id");
    }

    @Override
    protected Book buildEntity(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer authorId = rs.getInt("author_id");
        return new Book(id, isbn, title, description, releaseDate, authorId);
    }
}
