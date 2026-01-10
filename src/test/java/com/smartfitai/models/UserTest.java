package com.smartfitai.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }
}
