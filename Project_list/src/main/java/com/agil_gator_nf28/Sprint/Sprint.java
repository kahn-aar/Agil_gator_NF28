package com.agil_gator_nf28.Sprint;

import com.agil_gator_nf28.Taches.Tache;

import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class Sprint {

    private int id;
    private int number;
    private String start_date;
    private String end_date;
    private int project;
    private List<Tache> taches;

    public Sprint() {
    }

    public Sprint(List<Tache> taches, int number) {
        this.taches = taches;
        this.number = number;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
