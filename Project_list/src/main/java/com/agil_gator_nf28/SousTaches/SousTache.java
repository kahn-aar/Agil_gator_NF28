package com.agil_gator_nf28.SousTaches;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.Taches.Tache;

/**
 * Created by Nicolas on 15/05/14.
 */
public class SousTache {

    private int id;
    private String titre;
    private SousTacheEtat etat;

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
}
