package be.bstorm.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Book {

    private Integer id;
    private String isbn;
    private String title;
    private String description;
    private LocalDateTime releaseDate;
    private Integer authorId;

    public Book() {
    }

    public Book(String isbn, String title, String description, LocalDateTime releaseDate, Integer authorId) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.authorId = authorId;
    }

    public Book(Integer id, String isbn, String title, String description, LocalDateTime releaseDate, Integer authorId) {
        this(isbn, title, description, releaseDate, authorId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(description, book.description) && Objects.equals(releaseDate, book.releaseDate) && Objects.equals(authorId, book.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, description, releaseDate, authorId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", authorId=" + authorId +
                '}';
    }
}
