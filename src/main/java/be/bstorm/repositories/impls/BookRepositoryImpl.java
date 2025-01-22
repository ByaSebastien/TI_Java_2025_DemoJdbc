package be.bstorm.repositories.impls;

import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/demo_jdbc";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public List<Book> findAll() {
        try(Connection conn = this.getConnection()){

            String query = "SELECT * FROM book";
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            List<Book> books = new ArrayList<>();

            while(rs.next()){
                books.add(this.buildBook(rs));
            }

            return books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(int id) {

        try(Connection conn = this.getConnection()){

            String query = "SELECT * FROM book WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new RuntimeException("Book with id " + id + " not found");
            }
            return this.buildBook(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer save(Book book) {

        try(Connection conn = this.getConnection()) {

            String query = "INSERT INTO book ( isbn, " +
                                              "title, " +
                                              "description, " +
                                              "release_date, " +
                                              "author_id ) " +
                           "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getDescription());
            ps.setDate(4, Date.valueOf(book.getReleaseDate()));
            ps.setInt(5, book.getAuthorId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(!rs.next()){
                throw new RuntimeException("Cannot insert new book");
            }

            return rs.getInt("id");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Integer id, Book book) {

        try(Connection conn = this.getConnection()){

            String query = "UPDATE book " +
                           "SET isbn = ?, " +
                               "title = ?, " +
                               "description = ?, " +
                               "release_date = ?, " +
                               "author_id = ? " +
                           "WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getDescription());
            ps.setDate(4, Date.valueOf(book.getReleaseDate()));
            ps.setInt(5, book.getAuthorId());
            ps.setInt(6, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {

        try(Connection conn = this.getConnection()){

            String query = "DELETE FROM book WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    private Book buildBook(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();

        Integer authorId = rs.getInt("author_id");
        return new Book(id, isbn, title, description, releaseDate, authorId);
    }
}
