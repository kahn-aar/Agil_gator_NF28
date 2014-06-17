package com.agil_gator_nf28.agent.behaviour;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.Message.BDDAnswerMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingAnswerBehaviour extends Behaviour {

    private Object objectWaitingId;
    private String conversationId;
    private int step = 0;

    public WaitingAnswerBehaviour(Object o, String conversationId)
    {
        this.objectWaitingId = o;
        this.conversationId = conversationId;
    }

    @Override
    public void action() {
        ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId(conversationId)));
        if (message != null) {
            System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
            ObjectMapper omap = new ObjectMapper();
            DeviceInfoTypes demande = null;
            int id = -1;

            try {
                BDDAnswerMessage msg = omap.readValue(message.getContent(),BDDAnswerMessage.class);
                demande = msg.getDemande();
                id = msg.getId();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(demande!=null){
                switch(demande){
                    //Attend un boolean ou rien
                    case CONNEXION:
                        //Send OK to connexion function
                        break;
                    case EFFACE_PROJET:
                        break;
                    case AJOUT_MEMBRE:
                        break;
                    case ARCHIVER_SPRINT:
                        break;
                    case EFFACE_SPRINT:
                        break;
                    case MODIFIER_SOUS_TACHE:
                        break;
                    case MODIFIE_PROJET:
                        break;
                    case MODIFIE_TACHE:
                        break;
                    case SUPPRIMER_SOUS_TACHE:
                        break;
                    case SUPPRIMER_TACHE:
                        break;
                    case RETRAIT_MEMBRE:
                        break;
                    case AJOUT_MANAGER:
                        break;
                    case DECONNEXION:
                        break;

                    //Attend un ID
                    case CREE_PROJET:
                        ((Projet)this.objectWaitingId).setId(id);
                        break;
                    case CREE_COMPTE:
                        ((User)this.objectWaitingId).setId(id);
                        break;
                    case CREE_SOUS_TACHE:
                        ((SousTache)this.objectWaitingId).setId(id);
                        break;
                    case CREE_SPRINT:
                        ((Sprint)this.objectWaitingId).setId(id);
                        break;
                    case CREE_TACHE:
                        ((Tache)this.objectWaitingId).setId(id);
                        break;

                    //Attend un contenu
                    case MEMBRES_DU_PROJET:
                        break;
                    case ALL_USERS:
                        break;
                    case SYNCHRONIZE_DOWN:
                        break;
                    case SYNCHRONIZE_UP:
                        break;
                    default:
                        break;

                }
            }
            step++;
        }

    }

    @Override
    public boolean done() {
        return step == 1;
    }
}
