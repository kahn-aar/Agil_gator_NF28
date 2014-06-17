package com.agil_gator_nf28.agent.Message;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;

import java.util.List;

public class BDDAnswerMessage {

    private List<User> mesUsers;
    private List<Projet> myProjects;
    private List<Sprint> mySprints;
    private List<Tache> myTasks;
    private List<SousTache> mySubTasks;
    private User user;
    private String table;
    private DeviceInfoTypes demande;
    private int id;

    public List<Sprint> getMySprints() {
        return mySprints;
    }

    public void setMySprints(List<Sprint> mySprints) {
        this.mySprints = mySprints;
    }

    public List<Tache> getMyTasks() {
        return myTasks;
    }

    public void setMyTasks(List<Tache> myTasks) {
        this.myTasks = myTasks;
    }

    public List<SousTache> getMySubTasks() {
        return mySubTasks;
    }

    public void setMySubTasks(List<SousTache> mySubTasks) {
        this.mySubTasks = mySubTasks;
    }

    public List<Projet> getMyProjects() {
        return myProjects;
    }

    public void setMyProjects(List<Projet> myProjects) {
        this.myProjects = myProjects;
    }

    public List<User> getMesUsers() {
        return mesUsers;
    }

    public void setMesUsers(List<User> mesUsers) {
        this.mesUsers = mesUsers;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}