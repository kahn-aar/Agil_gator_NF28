package com.agil_gator_nf28.agent.agent;

import android.content.Context;

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

   /* public void createSubTask(DeviceInfoTypes creeSousTache, User connectedUser, SousTache sousTache) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CREE_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), sousTache));

    }

    public void createTache(DeviceInfoTypes creeTache, User connectedUser, Tache tache) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CREE_TACHE, ConnectedUser.getInstance().getConnectedUser(), tache));

    }*/

    public void createProjet(DeviceInfoTypes creeProjet, User connectedUser, Projet projet) {

        this.addBehaviour(new WaitingProjectBehaviour(DeviceInfoTypes.CREE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet));
    }

    /*public void askForConnexion(DeviceInfoTypes connexion, User user) {

        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CONNEXION, user));
    }

    public void createAccount(DeviceInfoTypes creeCompte, User user) {

        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CREE_COMPTE, user));
    }*/
}
