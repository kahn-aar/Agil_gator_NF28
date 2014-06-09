package com.agil_gator_nf28.Projet;

import com.agil_gator_nf28.User.User;

/**
 * Created by Nicolas on 30/04/14.
 */
public class Projet {

    private int id;
    private String name;
    private String subTitle;
    private String description;
    private int advanced;
    private User chef;

    public Projet(String name, String subTitle, String description, User chef) {
        this.name = name;
        this.subTitle = subTitle;
        this.description = description;
        this.chef = chef;
    }

    public Projet() {}

    public int getAdvanced() {
        return advanced;
    }

    public void setAdvanced(int advanced) {
        this.advanced = advanced;
    }

    public Projet(String name, String subTitle, String description) {
        this.name = name;
        this.subTitle = subTitle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }
}
