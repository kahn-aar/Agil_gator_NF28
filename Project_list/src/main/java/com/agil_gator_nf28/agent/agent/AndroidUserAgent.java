package com.agil_gator_nf28.agent.agent;

import android.content.Context;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.behaviour.WaitingAnswerBehaviour;
import com.agil_gator_nf28.agent.behaviour.WaitingProjectBehaviour;
import com.agil_gator_nf28.agent.manager.AgentManager;

import java.sql.Timestamp;
import java.util.Date;

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

        this.addBehaviour(new WaitingAnswerBehaviour(context));


        //Enregistrement de l'agent auprès du DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Device");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }


    private String generateConversationId() {
        Date date = new Date();
        return new Timestamp(date.getTime()).toString();
    }

   /* public void createSubTask(DeviceInfoTypes creeSousTache, User connectedUser, SousTache sousTache) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CREE_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), sousTache));

    }

    public void createTache(DeviceInfoTypes creeTache, User connectedUser, Tache tache) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CREE_TACHE, ConnectedUser.getInstance().getConnectedUser(), tache));

    }*/

    public void createProjet(DeviceInfoTypes creeProjet, User connectedUser, Projet projet, Context context) {

        this.addBehaviour(new WaitingProjectBehaviour(creeProjet, generateConversationId(), ConnectedUser.getInstance().getConnectedUser(), projet, null, null, null, context));
    }

    public void createAccount(DeviceInfoTypes creeCompte, User user, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(creeCompte, generateConversationId(), user, null, null, null, null, context));
    }

    public void createSprint(DeviceInfoTypes creeSprint, User connectedUser, Sprint sprint, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(creeSprint, generateConversationId(), connectedUser, null, sprint, null, null, context));
    }

    public void createUserProjet(DeviceInfoTypes ajoutManager, User connectedUser, Projet project, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(ajoutManager, generateConversationId(), connectedUser, project, null, null, null, context));
    }


    public void createTache(DeviceInfoTypes creeTache, User connectedUser, Tache tache, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(creeTache, generateConversationId(), connectedUser, null, null, tache, null, context));
    }

    public void createSubTask(DeviceInfoTypes creeSousTache, User connectedUser, SousTache sousTache, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(creeSousTache, generateConversationId(), connectedUser, null, null, null, sousTache, context));
    }

    public void editProjet(DeviceInfoTypes modifieProjet, User connectedUser, Projet projet, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(modifieProjet, generateConversationId(), connectedUser, projet, null, null, null, context));
    }

    public void askForConnexion(DeviceInfoTypes connexion, User user, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(connexion, generateConversationId(), user, null, null, null, null, context));
    }

    public void modifTask(DeviceInfoTypes modifieTache, User connectedUser, Tache tache,  Context context){
        this.addBehaviour(new WaitingProjectBehaviour(modifieTache, generateConversationId(),connectedUser, null, null, tache,null,context));
    }

    public void modifSoustache(DeviceInfoTypes modifieSubTache, User connectedUser, SousTache sousTache, Context context){
        this.addBehaviour(new WaitingProjectBehaviour(modifieSubTache, generateConversationId(), connectedUser, null, null, null, sousTache, context));
    }

    public void archiveSprint(DeviceInfoTypes archivesprint, User connectedUser, Sprint sprint, Context context){
        this.addBehaviour(new WaitingProjectBehaviour(archivesprint, generateConversationId(), connectedUser, null, sprint, null, null,context));
    }

    public void suppProjet(DeviceInfoTypes suppProjet, User connectedUser, Projet projet,Context context){
        this.addBehaviour(new WaitingProjectBehaviour(suppProjet, generateConversationId(), connectedUser, projet, null, null,null,context));
    }

    public void suppTache(DeviceInfoTypes suppTache, User connectedUser, Tache tache,Context context){
        this.addBehaviour(new WaitingProjectBehaviour(suppTache, generateConversationId(),connectedUser, null, null, tache, null,context));
    }

    public void suppSousTache(DeviceInfoTypes suppSubTache, User connectedUser, SousTache sousTache,Context context){
        this.addBehaviour(new WaitingProjectBehaviour(suppSubTache, generateConversationId(),connectedUser, null, null, null, sousTache,context));
    }

    public void ajoutUser(DeviceInfoTypes ajoutUser, User connectedUser, SousTache sousTache,Context context){
        this.addBehaviour(new WaitingProjectBehaviour(ajoutUser, generateConversationId(),connectedUser, null, null, null, sousTache,context));
    }

    public void addUserToProject(DeviceInfoTypes ajoutMembre, Projet projet, User user, User connectedUser, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(ajoutMembre, generateConversationId(), connectedUser, user, projet, null, null, null, context));


    }

    public void selectLastSprint(DeviceInfoTypes selectLastSprint, User connectedUser, Projet projet, Context context) {
        this.addBehaviour(new WaitingProjectBehaviour(selectLastSprint, generateConversationId(), connectedUser, projet, null, null, null, context));

    }


   /* public void askForConnexion(DeviceInfoTypes connexion, User user) {
        this.addBehaviour(new machintrucbehaviour(DeviceInfoTypes.CONNEXION, user));
    }*/
}
