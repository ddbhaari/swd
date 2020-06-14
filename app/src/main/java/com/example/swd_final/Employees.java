package com.example.swd_final;

public class Employees {
    private String Username, ID;

    public Employees() {
    }

    public Employees(String username, String ID) {
        Username = username;
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
