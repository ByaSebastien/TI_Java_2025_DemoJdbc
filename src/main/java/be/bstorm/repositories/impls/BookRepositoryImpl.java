package be.bstorm.repositories.impls;

import be.bstorm.entities.Book;
import be.bstorm.repositories.BookRepository;

import java.sql.*;
import java.time.LocalDateTime;
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
                Integer id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime releaseDate = rs.getTimestamp("release_date").toLocalDateTime();
                Integer authorId = rs.getInt("author_id");
                books.add(new Book(id, isbn, title, description, releaseDate, authorId));
            }

            return books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public Integer save(Book book) {
        return 0;
    }

    @Override
    public boolean update(Integer id, Book book) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
