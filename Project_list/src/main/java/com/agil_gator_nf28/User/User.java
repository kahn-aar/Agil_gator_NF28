package com.agil_gator_nf28.User;

import com.agil_gator_nf28.Projet.Projet;

/**
 * Classe gérant les utilisateurs
 *
 * Created by Mathieu on 15/05/14.
 */
public class User {

    private int id;
    private String email;
    private String name;
    private String firstname;
    private String password;
    private String salt1;

    public User() {

    }

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

    public String getTag() {
        String tag = firstname.substring(0,1) + name.substring(0,2);
        return tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt1() {
        return salt1;
    }

    public void setSalt1(String salt1) {
        this.salt1 = salt1;
    }

    public boolean isChef(Projet project) {
        return id == project.getChef().getId();
    }
}
