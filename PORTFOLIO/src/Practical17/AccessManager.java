package Practical17;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages access permissions using a Map within a Map:
 * User -> (System Name -> Permission)
 */
public class AccessManager {

    private final Map<User, Map<String, String>> accessMap = new HashMap<>();

    /**
     * Adds a new user with an initial system permission.
     *
     * Time Complexity:
     * - containsKey / put → O(1) average, O(n) worst case (hash collisions)
     */
    public boolean addUser(User user, String systemName, String permission) {
        if (accessMap.containsKey(user)) {
            System.out.println("User already exists: " + user.getName());
            return false;
        }

        Map<String, String> systemMap = new HashMap<>();
        systemMap.put(systemName, permission);
        accessMap.put(user, systemMap);
        return true;
    }

    /**
     * Deletes an existing user.
     *
     * Time Complexity:
     * - containsKey / remove → O(1) average
     */
    public boolean deleteUser(User user) {
        if (!accessMap.containsKey(user)) {
            System.out.println("User not found: " + user.getName());
            return false;
        }

        accessMap.remove(user);
        return true;
    }

    /**
     * Updates or adds a permission for a system.
     *
     * Time Complexity:
     * - get / put → O(1) average
     *
     * Sub-optimal:
     * - Repeated containsKey lookups; could be optimised by single get().
     */
    public boolean updateUser(User user, String systemName, String permission) {
        if (!accessMap.containsKey(user)) {
            System.out.println("User not found: " + user.getName());
            return false;
        }

        Map<String, String> systemMap = accessMap.get(user);
        systemMap.put(systemName, permission);
        return true;
    }

    /**
     * Prints access details for a given user.
     *
     * Time Complexity:
     * - Lookup → O(1) average
     * - Printing permissions → O(m) where m = number of systems
     */
    public void printUserAccess(User user) {
        if (!accessMap.containsKey(user)) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Access details for " + user);
        Map<String, String> systems = accessMap.get(user);

        for (String system : systems.keySet()) {
            System.out.println(system + " : " + systems.get(system));
        }
    }

    // Helper for testing
    public boolean containsUser(User user) {
        return accessMap.containsKey(user);
    }
}
