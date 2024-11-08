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
import br.org.fiergs.cosmos.infrastructure.jpa.repository.JpaAuthorRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaAuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
    }

    private String readJson(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testCreateAuthor() throws Exception {
        String createAuthorJson = readJson("src/test/resources/json/create_author.json");

        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAuthorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Author Name"))
                .andExpect(jsonPath("$.version").value(0));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testGetAuthorById() throws Exception {
        AuthorEntity savedAuthor = authorRepository.save(new AuthorEntity(null, "Author Name", 1L));

        mockMvc.perform(get("/api/v1/authors/{id}", savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Author Name"))
                .andExpect(jsonPath("$.version").value(1));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testGetAuthorByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/authors/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateAuthor() throws Exception {
        AuthorEntity savedAuthor = authorRepository.save(new AuthorEntity(null, "Author Name", 1L));

        String updateAuthorJson = readJson("src/test/resources/json/update_author.json");

        mockMvc.perform(put("/api/v1/authors/{id}", savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAuthorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.version").value(2));
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateAuthorVersionConflict() throws Exception {
        AuthorEntity savedAuthor = authorRepository.save(new AuthorEntity(null, "Author Name", 1L));

        String updateAuthorJson = readJson("src/test/resources/json/update_author_conflict.json");

        mockMvc.perform(put("/api/v1/authors/{id}", savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAuthorJson))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testUpdateAuthorNotFound() throws Exception {
        String updateAuthorJson = readJson("src/test/resources/json/update_author.json");

        mockMvc.perform(put("/api/v1/authors/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateAuthorJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testDeleteAuthor() throws Exception {
        AuthorEntity savedAuthor = authorRepository.save(new AuthorEntity(null, "Author Name", 1L));

        mockMvc.perform(delete("/api/v1/authors/{id}", savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<AuthorEntity> deletedAuthor = authorRepository.findById(savedAuthor.getId());
        assertTrue(deletedAuthor.isEmpty());
    }

    @Test
    @WithMockUser(roles = "COSMOS_ADM[*]", username = "TEST")
    public void testDeleteAuthorNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAuthorUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "TEST")
    public void testDeleteAuthorForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
