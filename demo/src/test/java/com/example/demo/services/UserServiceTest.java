package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource("classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void verify_createUser() {
        // Verify that a user with the username="testUser" and password="testPassword" does not exit in the users table
        User newUser = userService.getUserByUsername("testUser");
        assertNull(newUser);

        // Add a record to the users table with the username="testUser" and password="testPassword"
        newUser = new User("testUser", "testPassword");
        userService.createUser(newUser);

        // Verifies that userService.createUser method has added the record to the users table
        User retrievedUser = userService.getUserByUsername("testUser");
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("testPassword", retrievedUser.getPassword());
    }

    @Test
    @Order(2)
    public void verify_deleteUser() {
        // Verifies that there exists a record in the users table with a username="testUser"
        User testUser = userService.getUserByUsername("testUser");
        assertEquals("testUser", testUser.getUsername());

        // Deletes the record in the users table with a username="testUser"
        userService.deleteUserByUsername("testUser");

        // Verifies that the users table does not include a record with a username="testUser"
        User nullUser = userService.getUserByUsername("testUser");
        assertNull(nullUser);
    }

    @Test
    @Order(3)
    public void verify_getUserById() {
        // Creates a user
        User user = new User("testUser", "testPassword");
        userService.createUser(user);

        // Retrieves a user from the DB with the max id
        int maxId = userRepository.findMaxId();
        User retrievedUser = userService.getUserById(maxId);

        assertEquals(maxId, retrievedUser.getId());
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("testPassword", retrievedUser.getPassword());

        // deletes the user
        userService.deleteUserByUsername("testUser");
    }
}
