package help.baremetal.service.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/populate")
    public ResponseEntity<Integer> getPopulate() {
        return ResponseEntity.ok().body(bookService.populate(1000));
    }
}
