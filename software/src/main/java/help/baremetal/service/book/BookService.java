package help.baremetal.service.book;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String getTitle() {
        return DataSources.randomTitle();
    }

    public String getAuthor() {
        return DataSources.randomName();
    }

    public String getIsbn() {
        return DataSources.randomIsbn();
    }

    private Book randomBook(final int n) {
        return new Book(getAuthor(), getTitle(), getIsbn());
    }

    public int populate(final int n) {
        final var books = IntStream.range(0, n)
                .mapToObj(this::randomBook)
                .toList()

        bookRepository.saveAll(books);

        return n;
    }
}
