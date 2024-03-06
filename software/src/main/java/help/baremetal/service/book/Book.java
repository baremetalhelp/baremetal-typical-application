package help.baremetal.service.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entities cannot be Java records :-(
//
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String author;
    String title;
    String isbn;

    public Book() {
    }

    public Book(final String author, final String title, final String isbn) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
