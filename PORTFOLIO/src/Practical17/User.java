package Practical17;

/**
 * Represents a user in the system.
 */
public class User {

    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Human-friendly representation.
     */
    @Override
    public String toString() {
        return "User ID: " + id + ", Name: " + name + ", Email: " + email;
    }

    /**
     * Equality based on user id.
     * Required so User works correctly as a HashMap key.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
