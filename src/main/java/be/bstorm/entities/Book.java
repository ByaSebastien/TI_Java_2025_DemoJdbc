package be.bstorm.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private Integer id;
    private String isbn;
    private String title;
    private String description;
    private LocalDate release_date;
    private Integer authorId;
    private Author author;

    public Book() {
    }

    public Book(String isbn, String title, String description, LocalDate releaseDate, Integer authorId) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.release_date = releaseDate;
        this.authorId = authorId;
    }

    public Book(Integer id, String isbn, String title, String description, LocalDate releaseDate, Integer authorId) {
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

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(description, book.description) && Objects.equals(release_date, book.release_date) && Objects.equals(authorId, book.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, description, release_date, authorId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", release_date=" + release_date +
                ", author=" + author +
                '}';
    }
}
