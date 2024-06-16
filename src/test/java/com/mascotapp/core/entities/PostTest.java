package com.mascotapp.core.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    void testGetContent() {
        Post post = new Post("Hello, world!", "https://example.com");
        assertEquals("Hello, world!", post.getContent());
    }

    @Test
    void testGetUrl() {
        Post post = new Post("Hello, world!", "https://example.com");
        assertEquals("https://example.com", post.getUrl());
    }
    
    @Test
    void testGetSource() {
        Post post = new Post("Hello, world!", "https://example.com", "Example");
        assertEquals("Example", post.getSource());
        Post post2 = new Post("Hello, world!", "https://example.com");
        post2.setSource("Example2");
        assertEquals("Example2", post2.getSource());
    }
    
    @Test
    void testGetDomain() {
        Post post = new Post("Hello, world!", "https://example.com", "Example");
        assertEquals("example.com", post.getDomain());
        
        Post post2 = new Post("Hello, world!", "https://www.example.com", "Example");
        assertEquals("example.com", post2.getDomain());
        
        Post post3 = new Post("Hello, world!", "www.example.com", "Example");
        assertEquals("example.com", post3.getDomain());
    }

    @Test
    void testEquals() {
        Post post1 = new Post("Hello, world!", "https://example.com");
        Post post2 = new Post("Hello, world!", "https://example.com");
        Post post3 = new Post("Goodbye, world!", "https://example.com");

        assertEquals(post1, post2);
        assertNotEquals(post1, post3);
    }

    @Test
    void testHashCode() {
        Post post1 = new Post("Hello, world!", "https://example.com");
        Post post2 = new Post("Hello, world!", "https://example.com");
        Post post3 = new Post("Goodbye, world!", "https://example.com");

        assertEquals(post1.hashCode(), post2.hashCode());
        assertNotEquals(post1.hashCode(), post3.hashCode());
    }
}
