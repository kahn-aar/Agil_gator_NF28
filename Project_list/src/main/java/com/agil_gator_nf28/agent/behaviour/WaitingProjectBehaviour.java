package com.agil_gator_nf28.agent.behaviour;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
import com.agil_gator_nf28.agent.Message.DataMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.Date;

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
    String conversationId;
    int step = 0;

    public WaitingProjectBehaviour(DeviceInfoTypes demande, User user, Projet projet){
        this.demande = demande;
        this.user = user;
        this.projet = projet;
        this.conversationId  = generateConversationId();
    }

    private String generateConversationId() {
        Date date = new Date();
        return new Timestamp(date.getTime()).toString();
    }

    @Override
    public void action() {
        // construction du data message
        DataMessage dm = new DataMessage();
        dm.setDemande(demande);
        dm.setUser(user);
        dm.setProjet(projet);
        // construction de l'acl message
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(getLiaisonAID());
        message.setConversationId(conversationId);
        ObjectMapper omap = new ObjectMapper();
        try {
            String content = omap.writeValueAsString(dm);
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
        if (step == 1 ) {
            return true;
        }
        return false;

    }
}
