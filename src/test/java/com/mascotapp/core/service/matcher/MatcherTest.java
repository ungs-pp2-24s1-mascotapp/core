package com.mascotapp.core.service.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.exception.NullArgumentException;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;

public class MatcherTest {

    @Test
    public void testGetMatchs() {
        Set<Post> founds = new HashSet<>();
        Set<Post> losts = new HashSet<>();
        
        // Crear algunos posts para pruebas
        Post foundPost1 = new Post("Found post 1 content", "www.post.com");
        Post foundPost2 = new Post("Found post 2 content", "www.post.com");
        Post lostPost1 = new Post("Lost post 1 content", "www.post.com");
        
        // Agregar los posts a los sets correspondientes
        founds.add(foundPost1);
        founds.add(foundPost2);
        losts.add(lostPost1);
        
        try {
            Set<Match> matches = Matcher.getMatchs(founds, losts);
            assertNotNull(matches);
            assertEquals(2, matches.size()); // No debería haber coincidencias
        } catch (NullArgumentException e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }
}
