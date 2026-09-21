package com.campus.lostfound;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final boolean admin;

    public User(String id, String name, String email, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return admin; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
