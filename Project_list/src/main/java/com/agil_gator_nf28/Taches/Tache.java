package com.agil_gator_nf28.Taches;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class Tache {

    private List<SousTache> sousTaches = new ArrayList<SousTache>();
    private int id;
    private String nom;
    private int priorite;
    private int difficulte;
    private int notifications;

    public Tache() {
    }

    public Tache(List<SousTache> sousTaches, int priorite, int difficulte, int notifications) {
        this.sousTaches = sousTaches;
        this.priorite = priorite;
        this.difficulte = difficulte;
        this.notifications = notifications;
    }

    public Tache(String nom, int priorite, int difficulte) {
        this.nom = nom;
        this.priorite = priorite;
        this.difficulte = difficulte;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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


    // Opérations sur les sousTaches
    public void addNewSousTache(SousTache tache) {
        sousTaches.add(tache);
    }

    public List<SousTache> getSousTachesAFaire() {
        List<SousTache> aFaire = new ArrayList<SousTache>();
        for(SousTache tache : sousTaches) {
            if (SousTacheEtat.AFAIRE.equals(tache.getEtat())) {
                aFaire.add(tache);
            }
        }
        return aFaire;
    }
}
