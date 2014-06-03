package com.agil_gator_nf28.agil_gator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.Taches.TacheAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité gérant le tableau scul d'un projet
 */
public class Page_projet extends ActionBarActivity {

    private static final int  MENU_EDIT = Menu.FIRST;
    private static final int  MENU_DELETE = Menu.FIRST + 1;
    private static final int  MENU_ADD_SUB = Menu.FIRST + 2;

    private CharSequence mTitle;
    private int id_project;

    private Projet project;
    private Sprint actualSprint;
    private TacheAdapter adapter = null;
    private ListView listTaches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String EXTRA_ID = "user_login";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_projet);
        listTaches = (ListView)findViewById(R.id.ListeTaches);
        TextView titre = (TextView)findViewById(R.id.projectPageTitle);


        Intent intent = getIntent();
        if (intent != null) {
            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            id_project = Integer.valueOf(intent.getStringExtra(EXTRA_ID));

            project = projetBDD.getProjetById(id_project);

            projetBDD.close();

            titre.setText(project.getName());

            SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            actualSprint = sprintBDD.getLastSprintOfProject(project);
            sprintBDD.close();

            TacheBDD tacheBDD = new TacheBDD(this);
            tacheBDD.open();
            List<Tache> taches = tacheBDD.getTaches(actualSprint);
            tacheBDD.close();

            adapter = new TacheAdapter(this,getApplicationContext(), taches);


            registerForContextMenu(listTaches);
            listTaches.setLongClickable(true);
            // On dit à la ListView de se remplir via cet adapter
            listTaches.setAdapter(adapter);

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_projet, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_task_menu) {
            Intent intent = new Intent(this, Add_Task.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
            startActivity(intent);
            return true;
        }
        if (id == R.id.edit_project) {
            Intent intent = new Intent(Page_projet.this, EditProjet.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
            intent.putExtra(AndroidConstantes.PROJECT_EDIT_FROM, String.valueOf(AndroidConstantes.PROJECT_EDIT_FROM_DETAIL));
            startActivity(intent);
            return true;
        }
        if (id == R.id.archives) {
            Intent intent = new Intent(Page_projet.this, ArchivedSprint.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
            startActivity(intent);
            return true;
        }
        if (id == R.id.archiver) {
            archiver();
            Intent intent = new Intent(Page_projet.this, Page_projet.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void archiver() {
        SprintBDD sprintBDD = new SprintBDD(this);
        sprintBDD.open();
        sprintBDD.archivateSprint(actualSprint, project);
        sprintBDD.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //Tache tache = (Tache) listTaches.getItemAtPosition(acmi.position);

        menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, R.string.modif_task);
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.description_task);
        menu.add(Menu.NONE, MENU_ADD_SUB, Menu.NONE, R.string.context_menu_tache_add);
        //menu.add(tache.getId());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Tache selectedTache = (Tache) adapter.getItem(item.getItemId()-1);
        System.out.println("id de la tache selectionne " + selectedTache.getId());

        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this,Modif_task.class);
                intent.putExtra("ID_TA", selectedTache.getId());
                this.startActivity(intent);
                return true;
            case R.id.description_task:
                //editNote(info.id);
                return true;
            case 3:
                Intent intent2 = new Intent(this,Modif_task.class);
                intent2.putExtra(AndroidConstantes.PROJECT_ID, project.getId());
                intent2.putExtra(AndroidConstantes.TACHE_ID, selectedTache.getId());
                this.startActivity(intent2);
                return true;
            case R.id.supp_task:
                //deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
