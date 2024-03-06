package help.baremetal.service.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long>, CrudRepository<Book, Long> {
    Optional<Book> findByIsbn(String id);

    List<Book> findByAuthor(String name);

    List<Book> findByTitle(String name);
}
