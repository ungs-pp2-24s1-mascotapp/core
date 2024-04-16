package com.mascotapp.core.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    void testGetName() {
        Pet pet = new Pet("Fido", "Dog", null);
        assertEquals("Fido", pet.getName());
    }

    @Test
    void testGetAnimalType() {
        Pet pet = new Pet("Fido", "Dog", null);
        assertEquals("Dog", pet.getAnimalType());
    }

    @Test
    void testEquals() {
        Pet pet1 = new Pet("Fido", "Dog", null);
        Pet pet2 = new Pet("Fido", "Dog", null);
        Pet pet3 = new Pet("Rex", "Dog", null);

        assertEquals(pet1, pet2);
        assertNotEquals(pet1, pet3);
        
        
    }

    @Test
    void testHashCode() {
        Pet pet1 = new Pet("Fido", "Dog", null);
        Pet pet2 = new Pet("Fido", "Dog", null);
        Pet pet3 = new Pet("Rex", "Dog", null);

        assertEquals(pet1.hashCode(), pet2.hashCode());
        assertNotEquals(pet1.hashCode(), pet3.hashCode());
    }
}
