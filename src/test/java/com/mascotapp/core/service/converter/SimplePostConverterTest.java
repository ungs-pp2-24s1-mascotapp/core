package com.mascotapp.core.service.converter;

import com.mascotapp.core.entities.Pet;
import com.mascotapp.core.entities.Post;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimplePostConverterTest {

    @Test
    void testConvertToPet() {
        // Arrange
        SimplePostConverter converter = new SimplePostConverter();
        String content = "Mi perro Fido";
        Post post = new Post(content, "https://example.com");

        // Act
        Pet pet = converter.convertToPet(post);

        // Assert
        assertNotNull(pet);
        assertEquals("Fido", pet.getName());
        assertEquals("perro", pet.getAnimalType());
        assertEquals(post, pet.getPost());
    }
}
