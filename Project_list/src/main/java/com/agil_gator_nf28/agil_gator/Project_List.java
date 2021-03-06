package com.agil_gator_nf28.agil_gator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.UserProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Projet.ProjetAdapter;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.agent.manager.AgentManager;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class Project_List extends ActionBarActivity {

    final String EXTRA_LOGIN = "user_login";
    private static final int  MENU_DESCRIPTION = Menu.FIRST;
    private static final int  MENU_EDIT = Menu.FIRST+1;
    private static final int  MENU_DELETE = Menu.FIRST + 2;
    ProjetAdapter adapter = null;
    Projet selectedProject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project__list);
        final ListView contenuDeLaPage = (ListView)findViewById(R.id.contenuDeLaPage);

        //Création d'une instance de ma classe LivresBDD
        UserProjetBDD projetBdd = new UserProjetBDD(this);

        //On ouvre la base de données pour écrire dedans
        projetBdd.open();

        List<Projet> projets = projetBdd.getProjetsFromUser(ConnectedUser.getInstance().getConnectedUser());

        projetBdd.close();

        adapter = new ProjetAdapter(getApplicationContext(), R.id.contenuDeLaPage,  projets, Project_List.this);
        // On dit à la ListView de se remplir via cet adapter
        Thread thread = new Thread()
        {
            @Override
            public void run() {
        contenuDeLaPage.setAdapter(adapter);
    }};
        thread.start();

        this.registerForContextMenu(contenuDeLaPage);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        ListView lv = (ListView) vue;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedProject = (Projet) lv.getItemAtPosition(acmi.position);


        menu.add(Menu.NONE, MENU_DESCRIPTION, Menu.NONE, R.string.context_menu_proj_desc);
        if (ConnectedUser.getInstance().getConnectedUser().isChef(selectedProject)) {
            menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, R.string.context_menu_proj_edit);
            menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.context_menu_proj_suppr);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        System.out.println("id de la tache selectionne "+ selectedProject.getId());
        Intent intent;

        switch (item.getItemId()) {
            case MENU_DESCRIPTION:
                intent = new Intent(Project_List.this,DescriptifProjectActivity.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(selectedProject.getId()));
                startActivity(intent);
                return true;
            case MENU_EDIT:
                intent = new Intent(Project_List.this, EditProjet.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(selectedProject.getId()));
                intent.putExtra(AndroidConstantes.PROJECT_EDIT_FROM, String.valueOf(AndroidConstantes.PROJECT_EDIT_FROM_LIST));
                startActivity(intent);
                return true;
            case MENU_DELETE:
                //on affiche un popup de confirmation pour la suppression
                new AlertDialog.Builder(this)
                        .setTitle("Suppression")
                        .setMessage("Etes vous sure de vouloir supprimer ce projet?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ProjetBDD projetBDD = new ProjetBDD(Project_List.this);
                                projetBDD.open();
                                projetBDD.removeProjetWithID(selectedProject.getId());

                                AgentManager.getInstance().suppProject(selectedProject,Project_List.this);

                                projetBDD.close();
                                UserProjetBDD userProjectBDD = new UserProjetBDD(Project_List.this);
                                userProjectBDD.open();
                                userProjectBDD.removeProjetUserWithID(selectedProject.getId());
                                userProjectBDD.close();
                                adapter.removeProjectId(selectedProject.getId());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project__list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.addProject) {
            Intent intent = new Intent(this, AddProject.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.refresh) {
            Intent intent = new Intent(this, Project_List.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
