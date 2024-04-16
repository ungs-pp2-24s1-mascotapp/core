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
