package com.agil_gator_nf28.Projet;

/**
 * Created by Nicolas on 30/04/14.
 */
public class Projet {

    private int id;
    private String name;
    private String subTitle;
    private String description;

    public Projet() {}
    public Projet(String name, String subTitle) {
        this.name = name;
        this.subTitle = subTitle;
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
}
