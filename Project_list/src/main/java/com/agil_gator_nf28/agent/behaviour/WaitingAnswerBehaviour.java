package com.agil_gator_nf28.agent.behaviour;

import android.content.Context;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.BddInterne.UserProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.Message.DataMessage;
import com.agil_gator_nf28.agent.manager.AgentManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingAnswerBehaviour extends CyclicBehaviour {

    private Context context;
    DataMessage dataMessage;

    public WaitingAnswerBehaviour(Context context)
    {
        this.context = context;
    }

    @Override
    public void action() {
        ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (message != null) {
            System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());

            try{
                ObjectMapper omap = new ObjectMapper();
                dataMessage = omap.readValue(message.getContent(),DataMessage.class);
            }catch(IOException e){e.printStackTrace();}
            DeviceInfoTypes demande = null;
            demande = dataMessage.getDemande();

            if(demande!=null){
                switch(demande){
                    //Attend un boolean ou rien
                    case CONNEXION:
                        //Send OK to connexion function
                        break;
                    case EFFACE_PROJET:
                        break;
                    case AJOUT_MEMBRE:
                        if (dataMessage.getProjet().getChef().getId() != ConnectedUser.getInstance().getConnectedUser().getId()) {
                            ProjetBDD projetBDD = new ProjetBDD(context);
                            projetBDD.open();
                            Projet projet = dataMessage.getProjet();
                            projetBDD.insertProjetWithId(projet);
                            projetBDD.close();
                            UserProjetBDD userProjetBDD = new UserProjetBDD(context);
                            userProjetBDD.open();
                            userProjetBDD.insertProjet(ConnectedUser.getInstance().getConnectedUser(), projet.getId());
                            userProjetBDD.close();
                            AgentManager.getInstance().selectLastSprint(projet, context);
                        }
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
                    case IS_USER:
                        break;
                    case CREE_PROJET:
                        if (dataMessage.getProjet().getChef().getId() == ConnectedUser.getInstance().getConnectedUser().getId()) {
                            ProjetBDD projetBDD = new ProjetBDD(context);
                            projetBDD.open();
                            Projet projet = dataMessage.getProjet();
                            projetBDD.insertProjetWithId(projet);
                            projetBDD.close();
                            UserProjetBDD userProjetBDD = new UserProjetBDD(context);
                            userProjetBDD.open();
                            userProjetBDD.insertProjet(projet.getChef(), projet.getId());
                            userProjetBDD.close();
                            Sprint sprint = new Sprint();
                            sprint.setProject(dataMessage.getProjet().getId());
                            AgentManager.getInstance().createSprint(sprint,context);
                            AgentManager.getInstance().createUserProject(projet.getChef(), projet, context);
                        }

                        break;
                    case CREE_COMPTE:
                        break;
                    case CREE_SOUS_TACHE:
                        SousTacheBDD sousTacheBDD = new SousTacheBDD(context);
                        sousTacheBDD.open();
                        sousTacheBDD.insertSousTache(dataMessage.getSousTache(),dataMessage.getSousTache().getTask());
                        sousTacheBDD.close();
                        break;
                    case CREE_SPRINT:
                        SprintBDD sprintBDD = new SprintBDD(context);
                        sprintBDD.open();
                        sprintBDD.insertSprintWithId(dataMessage.getSprint(), dataMessage.getSprint().getProject());
                        sprintBDD.close();
                        break;
                    case CREE_TACHE:
                        TacheBDD tacheBDD = new TacheBDD(context);
                        tacheBDD.open();
                        tacheBDD.insertTacheWithId(dataMessage.getTache(),dataMessage.getTache().getSprint());
                        tacheBDD.close();
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
                    case SELECT_LAST_SPRINT:
                        SprintBDD sprintBDD1 = new SprintBDD(context);
                        sprintBDD1.open();
                        sprintBDD1.insertSprintWithId(dataMessage.getSprint(), dataMessage.getSprint().getProject());
                        sprintBDD1.close();
                        break;
                    default:
                        break;

                }
            }
        }

    }

}
