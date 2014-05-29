package com.agil_gator_nf28.agent.manager;


import android.content.Context;
import android.widget.Toast;

import com.agil_gator_nf28.agent.agent.AndroidUserAgent;

import jade.android.MicroRuntimeService;
import jade.android.RuntimeCallback;


/**
 * Classe qui manage l'agent du device
 *
 * Created by Nicolas on 29/05/14.
 */
public class AgentManager {

    public static final String RESULT_CONNECTED = "connected";
    public static final String RESULT_DISONNECTED = "disconnected";

    private boolean isConnected = false;
    private MicroRuntimeService mr;
    private String participant;
    private String agentName;
    private AndroidUserAgent agent;
    private Context context;

    public synchronized void setAgent(AndroidUserAgent agent) {
        this.agent = agent;
    }

    public synchronized AndroidUserAgent getAgent() {
        return this.agent;
    }

    public synchronized void clean() {
        clean(false);
    }

    public synchronized void clean(boolean keepContext) {
        if (mr != null) {
            mr.stopAgentContainer(new RuntimeCallback<Void>() {

                public void onSuccess(Void arg0) {

                }

                public void onFailure(Throwable arg0) {

                }

            });

            mr = null;

        }
    }

    public synchronized void doConnect (Context context, String ip, String name) {
        if (isConnected) {
            throw new IllegalStateException("Already connected");
        }
        mr = new MicroRuntimeService();
        this.participant = name;
        this.agentName = name + "-agent";
        this.context = context;

        mr.startAgentContainer(ip, 1099, new RuntimeCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startAgent();
            }

            @Override
            public void onFailure(Throwable throwable) {
                sendErrorToast(throwable);
            }
        });
    }


    private void startAgent() {
        mr.startAgent(agentName, AndroidUserAgent.class.getName(), new Object[]{context, this}, new RuntimeCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isConnected = true;
                sendSuccesToast();
            }

            @Override
            public void onFailure(Throwable throwable) {
                sendErrorToast(throwable);
                clean();
            }
        });
    }

    public synchronized void doDisconnect() {
        if (!isConnected) {
            return;
        }
        if(agent != null) {
            agent.doDelete();
        }
        mr.stopAgentContainer(new RuntimeCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendSuccesToast();
                mr = null;
            }

            @Override
            public void onFailure(Throwable throwable) {
                sendErrorToast(throwable);
            }
        });
        clean();
        isConnected = false;
    }

    private void sendSuccesToast() {
        Toast.makeText(context, "Succes connected", Toast.LENGTH_SHORT).show();
    }


    private void sendErrorToast(Throwable throwable) {
        Toast.makeText(context, "looser connected", Toast.LENGTH_SHORT).show();
    }

}
