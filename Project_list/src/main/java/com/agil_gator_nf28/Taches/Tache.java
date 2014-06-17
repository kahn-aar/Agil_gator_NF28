package com.agil_gator_nf28.Taches;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO de la Tache
 *
 * Created by Nicolas on 15/05/14.
 */
public class Tache {

    private List<SousTache> sousTaches = new ArrayList<SousTache>();

    private int id;
    private String name;
    private String description;
    private int priorite;
    private int difficulte;
    private int notifications;
    private int sprint;

    public Tache() {
    }

    public Tache(List<SousTache> sousTaches, int priorite, int difficulte, int notifications) {
        this.sousTaches = sousTaches;
        this.priorite = priorite;
        this.difficulte = difficulte;
        this.notifications = notifications;
    }

    public Tache(String nom, int priorite, int difficulte) {
        this.name = nom;
        this.priorite = priorite;
        this.difficulte = difficulte;
    }

    public Tache(String nom, String description, int priorite, int difficulte) {
        this.name = nom;
        this.description = description;
        this.priorite = priorite;
        this.difficulte = difficulte;
    }

    public Tache(String nom, String description, int difficulte, int priorite, int notifications) {
        this.name = nom;
        this.description = description;
        this.difficulte = difficulte;
        this.priorite = priorite;
        this.notifications = notifications;
    }

    //Getters and setters
    public List<SousTache> getSousTaches() {
        return sousTaches;
    }

    public void setSousTaches(List<SousTache> sousTaches) {
        this.sousTaches = sousTaches;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSprint() {
        return sprint;
    }

    public void setSprint(int sprint) {
        this.sprint = sprint;
    }

    // Opérations sur les sousTaches
    public void addNewSousTache(SousTache tache) {
        sousTaches.add(tache);
    }

    public List<SousTache> sousTachesAFaire() {
        List<SousTache> aFaire = new ArrayList<SousTache>();
        for(SousTache tache : sousTaches) {
            if (SousTacheEtat.AFAIRE.equals(tache.getEtat())) {
                aFaire.add(tache);
            }
        }
        return aFaire;
    }

    public List<SousTache> sousTachesEnCours() {
        List<SousTache> enCours = new ArrayList<SousTache>();
        for(SousTache tache : sousTaches) {
            if (SousTacheEtat.ENCOURS.equals(tache.getEtat())) {
                enCours.add(tache);
            }
        }
        return enCours;
    }

    public List<SousTache> sousTachesARelire() {
        List<SousTache> aRelire = new ArrayList<SousTache>();
        for(SousTache tache : sousTaches) {
            if (SousTacheEtat.ARELIRE.equals(tache.getEtat())) {
                aRelire.add(tache);
            }
        }
        return aRelire;
    }

    public List<SousTache> sousTachesDone() {
        List<SousTache> done = new ArrayList<SousTache>();
        for(SousTache tache : sousTaches) {
            if (SousTacheEtat.VALIDE.equals(tache.getEtat())) {
                done.add(tache);
            }
        }
        return done;
    }
}
