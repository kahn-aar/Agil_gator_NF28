package com.agil_gator_nf28.agent.behaviour;

import android.content.Context;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.Message.DataMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 * Created by Nicolas on 12/06/14.
 */
public class WaitingProjectBehaviour extends Behaviour {

    private static final long serialVersionUID = 1L;
    DeviceInfoTypes demande;
    User user;
    Projet projet;
    Tache tache;
    String conversationId;
    Sprint sprint;
    SousTache sousTache;
    private User member = null;
    private Context context;
    int step = 0;


    public WaitingProjectBehaviour(DeviceInfoTypes demande, String conversationId, User user, Projet projet, Sprint sprint, Tache tache, SousTache sousTache, Context context){
        this.demande = demande;
        this.user = user;
        this.projet = projet;
        this.conversationId  = conversationId;
        this.sprint = sprint;
        this.tache = tache;
        this.sousTache = sousTache;
        this.context = context;
    }

    public WaitingProjectBehaviour(DeviceInfoTypes ajoutMembre, String conversationId, User connectedUser, User user, Projet projet, Sprint sprint, Tache tache, SousTache sousTache, Context context) {
        this.demande = ajoutMembre;
        this.user = connectedUser;
        this.projet = projet;
        this.conversationId  = conversationId;
        this.sprint = sprint;
        this.tache = tache;
        this.sousTache = sousTache;
        this.context = context;
        this.member = user;
    }


    @Override
    public void action() {
        // construction du data message
        DataMessage dm = new DataMessage();
        dm.setDemande(demande);
        dm.setUser(user);
        System.out.println(user.getEmail());
        dm.setProjet(projet);
        dm.setSprint(sprint);
        dm.setTache(tache);
        dm.setSousTache(sousTache);
        dm.setMember(member);

        // construction de l'acl message
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(getLiaisonAID());
        message.setConversationId(conversationId);
        ObjectMapper omap = new ObjectMapper();
        try {
            String content = omap.writeValueAsString(dm);
            System.out.println(content);
            message.setContent(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        myAgent.send(message);

        step = 1;
    }

    private AID getLiaisonAID() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Liaison");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            return result[0].getName();
        } catch(FIPAException fe) {
            fe.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean done() {

        return step == 1;

    }
}
