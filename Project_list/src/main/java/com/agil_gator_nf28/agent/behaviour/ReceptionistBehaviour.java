package com.agil_gator_nf28.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behaviour attendant les messages venant du serveur
 *
 * Created by Nicolas on 29/05/14.
 */
public class ReceptionistBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage sms = new ACLMessage(ACLMessage.REQUEST);
        sms.setContent("\n" +
                "{\n" +
                "\"demande\" : \"CREE_PROJET\",\n" +
                "\"user\" : {\"pseudo\" : \"nico\", \"password\" : \"nicoco67\", \"email\" : \"nicolas.martinr@gmail.com\"},\n" +
                "\"projet\" : {\"title\" : \"Banana\", \"subtitle\" : \"Mon projet\", \"description\" : \"Blablabla\"},\n" +
                "\"tache\" : null,\n" +
                "\"sousTache\" : null,\n" +
                "\"sprint\" : null\n" +
                "}");
        sms.addReceiver(getAIDLiaison());

        myAgent.send(sms);
        /*
        ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (message != null) {
            //On gère ici les messages
        }*/
    }

    private AID getAIDLiaison() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Liaison");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            return result[0].getName();
        }
        catch (FIPAException e) {
            e.printStackTrace();
        }
        return null;
    }
}
