package com.agil_gator_nf28.Projet;

import com.agil_gator_nf28.User.User;

/**
 * POJO gérant les projets
 *
 * Created by Nicolas on 30/04/14.
 */
public class Projet {

    private int id;
    private String title;
    private String subTitle;
    private String description;
    private String creation_date;
    private String last_update;
    private User chef;

    public Projet(String title, String subTitle, String description, User chef) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.chef = chef;
    }

    public Projet() {}

    public Projet(String title, String subTitle, String description) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
