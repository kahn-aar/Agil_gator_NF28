package com.agil_gator_nf28.agent.agent;

import android.content.Context;

import com.agil_gator_nf28.agent.behaviour.ReceptionistBehaviour;
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
}
