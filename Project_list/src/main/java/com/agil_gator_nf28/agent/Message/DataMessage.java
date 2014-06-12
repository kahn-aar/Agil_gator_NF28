package com.agil_gator_nf28.agent.Message;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;

/**
 * Created by Nicolas on 12/06/14.
 */
public class DataMessage {
    private DeviceInfoTypes demande;
    // Utilisateur, sera le manager dans le cas d'une création de projet
    private User user;
    private Projet projet;
    private Tache tache;
    private SousTache sousTache;
    private Sprint sprint;
    // Membre que l'on veut ajouter au projet ou supprimer du projet
    private User member;

    public DeviceInfoTypes getDemande() {
        return demande;
    }

    public void setDemande(DeviceInfoTypes demande) {
        this.demande = demande;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public SousTache getSousTache() {
        return sousTache;
    }

    public void setSousTache(SousTache sousTache) {
        this.sousTache = sousTache;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

}
