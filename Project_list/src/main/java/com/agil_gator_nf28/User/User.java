package com.agil_gator_nf28.User;

/**
 * Created by Mathieu on 15/05/14.
 */
public class User {

    private String email;
    private String name;
    private String firstname;

    public User(String email, String name, String firstname) {
        this.email = email;
        this.name = name;
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
