package com.agil_gator_nf28.agent.agent;

import android.content.Context;


import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agent.behaviour.SendNotificationToLiaison;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.behaviour.WaitingProjectBehaviour;
import com.agil_gator_nf28.agent.manager.AgentManager;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by Nicolas on 29/05/14.
 */
public class AndroidUserAgent extends Agent {

    Context context;
    AgentManager myManager;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        context = (Context) args[0];
        myManager = (AgentManager) args[1];
        myManager.setAgent(this);
        super.setup();

        //addBehaviour(new ReceptionistBehaviour());
        //addBehaviour(new SendNotificationToLiaison());

        //Enregistrement de l'agent auprès du DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("user");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    public void createProjet(DeviceInfoTypes creeProjet, User connectedUser, Projet projet) {
        this.addBehaviour(new WaitingProjectBehaviour(creeProjet, connectedUser, projet, null, null, null));
    }

    public void createAccount(DeviceInfoTypes creeCompte, User user) {
        this.addBehaviour(new WaitingProjectBehaviour(creeCompte, user, null, null, null, null));
    }

    public void createSprint(DeviceInfoTypes creeSprint, User connectedUser, Sprint sprint) {
        this.addBehaviour(new WaitingProjectBehaviour(creeSprint, connectedUser, null, sprint, null, null));
    }

    public void createUserProjet(DeviceInfoTypes ajoutManager, User connectedUser, Projet project) {
        this.addBehaviour(new WaitingProjectBehaviour(ajoutManager, connectedUser, project, null, null, null));
    }

    public void createTache(DeviceInfoTypes creeTache, User connectedUser, Tache tache) {
        this.addBehaviour(new WaitingProjectBehaviour(creeTache, connectedUser, null, null, tache, null));
    }

    public void createSubTask(DeviceInfoTypes creeSousTache, User connectedUser, SousTache sousTache) {
        this.addBehaviour(new WaitingProjectBehaviour(creeSousTache, connectedUser, null, null, null, sousTache));
    }

    public void editProjet(DeviceInfoTypes modifieProjet, User connectedUser, Projet projet) {
        this.addBehaviour(new WaitingProjectBehaviour(modifieProjet, connectedUser, projet, null, null, null));
    }

    public void modifTask(DeviceInfoTypes modifieTache, User connectedUser, Tache tache){
        this.addBehaviour(new WaitingProjectBehaviour(modifieTache, connectedUser, null, null, tache,null));
    }

    public void modifSoustache(DeviceInfoTypes modifieSubTache, User connectedUser, SousTache sousTache){
        this.addBehaviour(new WaitingProjectBehaviour(modifieSubTache, connectedUser, null, null, null, sousTache));
    }

    public void archiveSprint(DeviceInfoTypes archivesprint, User connectedUser, Sprint sprint){
        this.addBehaviour(new WaitingProjectBehaviour(archivesprint, connectedUser, null, sprint, null, null));
    }

    public void suppProjet(DeviceInfoTypes suppProjet, User connectedUser, Projet projet){
        this.addBehaviour(new WaitingProjectBehaviour(suppProjet, connectedUser, projet, null, null,null));
    }

    public void suppTache(DeviceInfoTypes suppTache, User connectedUser, Tache tache){
        this.addBehaviour(new WaitingProjectBehaviour(suppTache, connectedUser, null, null, tache, null));
    }

    public void suppSousTache(DeviceInfoTypes suppSubTache, User connectedUser, SousTache sousTache){
        this.addBehaviour(new WaitingProjectBehaviour(suppSubTache, connectedUser, null, null, null, sousTache));
    }

    public void ajoutUser(DeviceInfoTypes ajoutUser, User connectedUser, SousTache sousTache){
        this.addBehaviour(new WaitingProjectBehaviour(ajoutUser, connectedUser, null, null, null, sousTache));
    }


   /* public void askForConnexion(DeviceInfoTypes connexion, User user) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CONNEXION, user));
    }*/
}
