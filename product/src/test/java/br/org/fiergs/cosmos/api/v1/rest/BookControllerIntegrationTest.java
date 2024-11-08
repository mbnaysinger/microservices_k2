package br.org.fiergs.cosmos.api.v1.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.org.fiergs.cosmos.infrastructure.jpa.entity.AuthorEntity;
import br.org.fiergs.cosmos.infrastructure.jpa.entity.BookEntity;
import br.org.fiergs.cosmos.infrastructure.jpa.repository.JpaAuthorRepository;
import br.org.fiergs.cosmos.infrastructure.jpa.repository.JpaBookRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaBookRepository bookRepository;

    @Autowired
    private JpaAuthorRepository authorRepository;

    @Autowired
    private SaveAuthorTestService saveAuthorTestService;

    private AuthorEntity savedAuthor;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        persistAuthorWithId();
    }

    public void persistAuthorWithId() {
        savedAuthor = saveAuthorTestService.saveAuthorWithId(1L, "Author Name", 1L);
    }

    private String readJson(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testCreateBook() throws Exception {
        String createBookJson = readJson("src/test/resources/json/create_book.json");

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.isbn").value("123-4567890123"))
                .andExpect(jsonPath("$.published_date").value("2024-01-01"))
                .andExpect(jsonPath("$.author_id").value(savedAuthor.getId()))
                .andExpect(jsonPath("$.version").value(0));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testGetBookById() throws Exception {
        BookEntity savedBook = bookRepository
                .save(new BookEntity(null, "Book Title", "123-4567890123", LocalDate.of(2020, Month.APRIL, 10),
                        savedAuthor, 1L));

        mockMvc.perform(get("/api/v1/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.isbn").value("123-4567890123"))
                .andExpect(jsonPath("$.published_date").value("2020-04-10"))
                .andExpect(jsonPath("$.author_id").value(savedAuthor.getId()))
                .andExpect(jsonPath("$.version").value(1));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testGetBookByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateBook() throws Exception {
        BookEntity savedBook = bookRepository
                .save(new BookEntity(null, "Book Title", "123-4567890123", LocalDate.of(2020, Month.APRIL, 10),
                        savedAuthor, 1L));

        String updateBookJson = readJson("src/test/resources/json/update_book.json");

        mockMvc.perform(put("/api/v1/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateBookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.isbn").value("123-4567890123"))
                .andExpect(jsonPath("$.published_date").value("2024-01-01"))
                .andExpect(jsonPath("$.author_id").value(savedAuthor.getId()))
                .andExpect(jsonPath("$.version").value(2));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateBookVersionConflict() throws Exception {
        BookEntity savedBook = bookRepository
                .save(new BookEntity(null, "Book Title", "123-4567890123", LocalDate.now(), savedAuthor, 1L));

        String updateBookJson = readJson("src/test/resources/json/update_book_conflict.json");

        mockMvc.perform(put("/api/v1/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateBookJson))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateBookNotFound() throws Exception {
        String updateBookJson = readJson("src/test/resources/json/update_book.json");

        mockMvc.perform(put("/api/v1/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateBookJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testDeleteBook() throws Exception {
        BookEntity savedBook = bookRepository
                .save(new BookEntity(null, "Book Title", "123-4567890123", LocalDate.now(), savedAuthor, 1L));

        mockMvc.perform(delete("/api/v1/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<BookEntity> deletedBook = bookRepository.findById(savedBook.getId());
        assertTrue(deletedBook.isEmpty());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testDeleteBookNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBookUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "TEST")
    public void testDeleteBookForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
