package org.example;

public class User {
    private int userId;
    private String nume;
    private String email;

    public User(int userId, java.lang.String nume, java.lang.String email) {
        this.userId = userId;
        this.nume = nume;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public java.lang.String getNume() {
        return nume;
    }

    public void setNume(java.lang.String nume) {
        this.nume = nume;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }
}
