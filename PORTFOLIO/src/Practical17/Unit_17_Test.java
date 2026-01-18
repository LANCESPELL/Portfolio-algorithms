package Practical17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Unit_17_Test {

    private AccessManager manager;
    private User alice;
    private User bob;

    @BeforeEach
    void setup() {
        manager = new AccessManager();
        alice = new User(1, "Alice", "alice@gmail.com");
        bob = new User(2, "Bob", "bob@gmail.com");
    }

    // -------------------------
    // User tests
    // -------------------------

    @Test
    void user_toString_containsDetails() {
        String s = alice.toString();
        assertTrue(s.contains("Alice"));
        assertTrue(s.contains("alice@gmail.com"));
        assertTrue(s.contains("1"));
    }

    // -------------------------
    // Add user tests
    // -------------------------

    @Test
    void addUser_successful() {
        assertTrue(manager.addUser(alice, "SystemA", "READ"));
        assertTrue(manager.containsUser(alice));
    }

    @Test
    void addUser_duplicateFails() {
        manager.addUser(alice, "SystemA", "READ");
        assertFalse(manager.addUser(alice, "SystemB", "WRITE"));
    }

    // -------------------------
    // Delete user tests
    // -------------------------

    @Test
    void deleteUser_successful() {
        manager.addUser(alice, "SystemA", "READ");
        assertTrue(manager.deleteUser(alice));
        assertFalse(manager.containsUser(alice));
    }

    @Test
    void deleteUser_nonExistentFails() {
        assertFalse(manager.deleteUser(alice));
    }

    // -------------------------
    // Update user tests
    // -------------------------

    @Test
    void updateUser_existingSystem() {
        manager.addUser(alice, "SystemA", "READ");
        assertTrue(manager.updateUser(alice, "SystemA", "WRITE"));
    }

    @Test
    void updateUser_addsNewSystem() {
        manager.addUser(alice, "SystemA", "READ");
        assertTrue(manager.updateUser(alice, "SystemB", "ADMIN"));
    }

    @Test
    void updateUser_nonExistentFails() {
        assertFalse(manager.updateUser(alice, "SystemA", "READ"));
    }

    // -------------------------
    // Multiple users test
    // -------------------------

    @Test
    void multipleUsersHandledIndependently() {
        manager.addUser(alice, "SystemA", "READ");
        manager.addUser(bob, "SystemA", "WRITE");

        assertTrue(manager.updateUser(alice, "SystemA", "ADMIN"));
        assertTrue(manager.updateUser(bob, "SystemA", "READ"));
    }
}
