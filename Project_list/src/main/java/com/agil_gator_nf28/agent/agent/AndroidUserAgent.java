package com.agil_gator_nf28.agent.agent;

import android.content.Context;


import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agent.behaviour.SendNotificationToLiaison;
import com.agil_gator_nf28.Projet.Projet;
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

    public void createSubTask(SousTache sousTache) {
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.CREE_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), null, null, sousTache));

    }

    public void createTache(Tache tache) {
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.CREE_TACHE, ConnectedUser.getInstance().getConnectedUser(), null,tache,null));
    }

    public void createProjet(DeviceInfoTypes creeProjet, User connectedUser, Projet projet) {
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.CREE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet, null, null));
    }

    public void modifProjet(Projet projet){
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.MODIFIE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet, null, null));
    }

    public void modifTask(Tache tache){
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.MODIFIE_TACHE, ConnectedUser.getInstance().getConnectedUser(), null, tache, null));
    }

    public void suppProjet(Projet projet){
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.EFFACE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet, null, null));
    }

    public void suppTache(Tache tache){
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.SUPPRIMER_TACHE, ConnectedUser.getInstance().getConnectedUser(), null, tache, null));
    }

    public void suppSousTache(SousTache sousTache){
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.SUPPRIMER_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), null, null, sousTache));
    }


    public void createAccount(User user) {
        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.CREE_COMPTE, user, null, null, null));
    }

   /* public void askForConnexion(DeviceInfoTypes connexion, User user) {

        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CONNEXION, user));
    }*/
}
