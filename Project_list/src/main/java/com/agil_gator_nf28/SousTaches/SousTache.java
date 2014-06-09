package com.agil_gator_nf28.SousTaches;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;

/**
 * Classe gérant les sous taches
 *
 * Created by Nicolas on 15/05/14.
 */
public class SousTache {

    private int id;
    private String titre;
    private String description;
    private SousTacheEtat etat;
    private User effecteur;

    public SousTache() {
    }

    public SousTache(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public SousTacheEtat getEtat() {
        return etat;
    }

    public void setEtat(SousTacheEtat etat) {
        this.etat = etat;
    }

    public Tache getTache() {
        return null;
    }

    public User getEffecteur() {
        return effecteur;
    }

    public void setEffecteur(User effecteur) {
        this.effecteur = effecteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
