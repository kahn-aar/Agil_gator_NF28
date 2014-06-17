package com.agil_gator_nf28.agent.manager;


import android.content.Context;
import android.widget.Toast;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.Utils.DeviceInfoTypes;
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

    private static AgentManager instance = null;
    private boolean isConnected = false;
    private MicroRuntimeService mr;
    private String participant;
    private String agentName;
    private AndroidUserAgent agent;
    private Context context;

    public static AgentManager getInstance() {
        if (instance == null ) {
            instance = new AgentManager();
        }
        return instance;
    }

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
            this.clean();

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
        System.out.println("looser connected");
        //Toast.makeText(context, "looser connected", Toast.LENGTH_SHORT).show();
    }

    public void createAccount(User user) {
        agent.createAccount(DeviceInfoTypes.CREE_COMPTE, user);
    }

    public boolean askForConnexion(User user) {
        //agent.askForConnexion(DeviceInfoTypes.CONNEXION, user);
        return true;
    }

    public void createProject(Projet projet) {
        agent.createProjet(DeviceInfoTypes.CREE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet);
    }

    public void createTache(Tache tache) {
        agent.createTache(DeviceInfoTypes.CREE_TACHE, ConnectedUser.getInstance().getConnectedUser(), tache);
    }

    public void createSubTask(SousTache sousTache) {
        agent.createSubTask(DeviceInfoTypes.CREE_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), sousTache);

    }

    public void createSprint(Sprint sprint) {
        agent.createSprint(DeviceInfoTypes.CREE_SPRINT, ConnectedUser.getInstance().getConnectedUser(), sprint);
    }

    public void createUserProject(User connectedUser, Projet project) {
        agent.createUserProjet(DeviceInfoTypes.AJOUT_MANAGER, ConnectedUser.getInstance().getConnectedUser(), project);
    }

    public void editProject(Projet projet) {
        agent.editProjet(DeviceInfoTypes.MODIFIE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet);
    }

    public void modifTache(Tache tache){
        agent.modifTask(DeviceInfoTypes.MODIFIE_TACHE, ConnectedUser.getInstance().getConnectedUser(), tache);
    }

    public void modifSousTache(SousTache sousTache){
        agent.modifSoustache(DeviceInfoTypes.MODIFIER_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), sousTache);
    }

    public void suppProject(Projet projet){
        agent.suppProjet(DeviceInfoTypes.EFFACE_PROJET, ConnectedUser.getInstance().getConnectedUser(), projet);
    }

    public void suppTache(Tache tache){
        agent.suppTache(DeviceInfoTypes.SUPPRIMER_TACHE, ConnectedUser.getInstance().getConnectedUser(), tache);
    }

    public void suppSousTache(SousTache sousTache){
        agent.suppSousTache(DeviceInfoTypes.SUPPRIMER_SOUS_TACHE, ConnectedUser.getInstance().getConnectedUser(), sousTache);
    }
      
    public void archiveSprint(Sprint sprint){
        agent.archiveSprint(DeviceInfoTypes.ARCHIVER_SPRINT, ConnectedUser.getInstance().getConnectedUser(), sprint);
    }
}
