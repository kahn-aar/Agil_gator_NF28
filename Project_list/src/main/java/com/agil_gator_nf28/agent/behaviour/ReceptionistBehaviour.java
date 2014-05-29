package com.agil_gator_nf28.agent.behaviour;

import jade.core.behaviours.CyclicBehaviour;
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
        ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (message != null) {
            //On gère ici les messages
        }
    }
}
