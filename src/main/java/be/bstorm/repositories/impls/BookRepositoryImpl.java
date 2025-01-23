package be.bstorm.repositories.impls;

import be.bstorm.entities.Author;
import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;
import be.bstorm.utils.ConnectionUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book,Integer> implements BookRepository {

    public BookRepositoryImpl() {
        super("book","id");
    }

    @Override
    public List<Book> findAllWithAuthor() {

        try(Connection conn = ConnectionUtils.getConnection()) {

            String query = "SELECT * FROM book LEFT JOIN author ON book.author_id=author.id";

            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            List<Book> books = new ArrayList<>();

            while (rs.next()) {
                books.add(buildFullBook(rs));
            }

            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Book buildFullBook(ResultSet rs) throws SQLException {
        Book book = buildEntity(rs);
        book.setAuthor(buildAuthor(rs));
        return book;
    }

    private Author buildAuthor(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("author_id");
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
        return new Author(id,firstName,lastName,birthdate);
    }
}
