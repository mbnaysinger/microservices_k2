package com.naysinger.product.api.rest;

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

import com.naysinger.product.infrastructure.jpa.entity.Product;
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

import com.naysinger.product.infrastructure.jpa.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductModelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository authorRepository;

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
        Product savedAuthor = authorRepository.save(new Product(null, "Author Name", 1L));

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
        Product savedAuthor = authorRepository.save(new Product(null, "Author Name", 1L));

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
        Product savedAuthor = authorRepository.save(new Product(null, "Author Name", 1L));

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
        Product savedAuthor = authorRepository.save(new Product(null, "Author Name", 1L));

        mockMvc.perform(delete("/api/v1/authors/{id}", savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Product> deletedAuthor = authorRepository.findById(savedAuthor.getId());
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
