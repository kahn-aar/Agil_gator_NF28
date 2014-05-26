package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Projet.ProjetAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

public class Project_List extends ActionBarActivity {

    final String EXTRA_LOGIN = "user_login";
    private static final int  MENU_EDIT = Menu.FIRST;
    private static final int  MENU_DELETE = Menu.FIRST + 1;
    ProjetAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project__list);
        ListView contenuDeLaPage = (ListView)findViewById(R.id.contenuDeLaPage);

        //Création d'une instance de ma classe LivresBDD
        ProjetBDD projetBdd = new ProjetBDD(this);

        //On ouvre la base de données pour écrire dedans
        projetBdd.open();

       // Projet projet = new Projet("test projet","ceci est un test de projet", "lalalallal lilalou",55);
        //projetBdd.insertProjet(projet);

        List<Projet> projets = projetBdd.getProjets();

        projetBdd.close();

        adapter = new ProjetAdapter(Project_List.this, getApplicationContext(), projets);
        // On dit à la ListView de se remplir via cet adapter
        contenuDeLaPage.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, R.string.context_menu_proj_edit);
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, R.string.context_menu_proj_suppr);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Projet selectedProject = (Projet) adapter.getItem(item.getItemId());
        switch (item.getItemId()) {
            case MENU_EDIT:
                Intent intent = new Intent(Project_List.this, EditProjet.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(selectedProject.getId()));
                intent.putExtra(AndroidConstantes.PROJECT_EDIT_FROM, String.valueOf(AndroidConstantes.PROJECT_EDIT_FROM_LIST));
                startActivity(intent);
                return true;

            case MENU_DELETE:
                return true;
        }
        return super.onContextItemSelected(item);
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
        return super.onOptionsItemSelected(item);
    }

}
