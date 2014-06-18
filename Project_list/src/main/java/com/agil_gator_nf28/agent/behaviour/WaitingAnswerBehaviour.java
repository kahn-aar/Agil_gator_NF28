package com.agil_gator_nf28.agent.behaviour;

import android.content.Context;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.Message.DataMessage;
import com.agil_gator_nf28.agent.manager.AgentManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingAnswerBehaviour extends Behaviour {

    private Object objectWaitingId;
    private String conversationId;
    private Context context;
    private int step = 0;
    DataMessage dataMessage;

    public WaitingAnswerBehaviour(Object o, String conversationId, Context context)
    {
        this.objectWaitingId = o;
        this.conversationId = conversationId;
        this.context = context;
    }

    @Override
    public void action() {
        ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationId)));
        if (message != null) {
            System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());

            try{
            ObjectMapper omap = new ObjectMapper();
            dataMessage = omap.readValue(message.getContent(),DataMessage.class);
            }catch(IOException e){}
            DeviceInfoTypes demande = null;
            int id = -1;
            demande = DeviceInfoTypes.CREE_PROJET;
                id = Integer.valueOf(message.getContent());

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
                        ProjetBDD projetBDD = new ProjetBDD(context);
                        projetBDD.open();
                        Projet projet = dataMessage.getProjet();
                        projet.setChef(dataMessage.getUser());
                        projetBDD.insertProjet(projet);
                        //projetBDD.insertProjetWithId((Projet) this.objectWaitingId);
                        projetBDD.close();
                        Sprint sprint = new Sprint();
                        sprint.setProject(dataMessage.getProjet().getId());
                        AgentManager.getInstance().createSprint(sprint,context);

                        break;
                    case CREE_COMPTE:
                        ((User)this.objectWaitingId).setId(id);
                        break;
                    case CREE_SOUS_TACHE:
                        SousTacheBDD sousTacheBDD = new SousTacheBDD(context);
                        sousTacheBDD.open();
                        sousTacheBDD.insertSousTache(dataMessage.getSousTache(),dataMessage.getTache());
                        sousTacheBDD.close();
                        //((SousTache) this.objectWaitingId).setId(id);
                        break;
                    case CREE_SPRINT:
                        SprintBDD sprintBDD = new SprintBDD(context);
                        sprintBDD.open();
                        sprintBDD.insertSprint(dataMessage.getSprint(), dataMessage.getSprint().getProject());
                        sprintBDD.close();
                        ((Sprint)this.objectWaitingId).setId(id);
                        break;
                    case CREE_TACHE:
                        TacheBDD tacheBDD = new TacheBDD(context);
                        tacheBDD.open();
                        tacheBDD.insertTache(dataMessage.getTache(),dataMessage.getTache().getSprint());
                        tacheBDD.close();
                        //((Tache)this.objectWaitingId).setId(id);
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
