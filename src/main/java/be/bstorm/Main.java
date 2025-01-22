package be.bstorm;

import be.bstorm.entities.Book;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/demo_jdbc";
        String user = "postgres";
        String password = "postgres";

        try (Connection conn = DriverManager.getConnection(url,user,password)){

            String searchIsbn = "12345678910";

//            String query = "select * from book where isbn = '" + searchIsbn + "'";
            String query = "select * from book where isbn = ?";

//            Statement stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,searchIsbn);

//            ResultSet rs = stmt.executeQuery(query);

            ResultSet rs = ps.executeQuery();

            List<Book> books = new ArrayList<>();

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime releaseDate = rs.getTimestamp("release_date").toLocalDateTime();
                Long authorId = rs.getLong("author_id");

                Book book = new Book(isbn,title,description,releaseDate,authorId);

                books.add(book);
            }

            books.forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
