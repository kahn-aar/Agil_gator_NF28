package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.ArchivedTacheAdapter;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class PageSprint extends ActionBarActivity {

    private Projet project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_sprint);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        ListView contenuDeLaPage = (ListView)findViewById(R.id.contenuDeLaPage);

        Intent intent = getIntent();
        if (intent != null) {
            int sprintId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.SPRINT_ID));

            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            int projectId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));
            project = projetBDD.getProjetById(projectId);
            projetBDD.close();

            SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            Sprint sprint = sprintBDD.getSprintFromId(sprintId);
            sprintBDD.close();

            TacheBDD tacheBDD = new TacheBDD(this);
            tacheBDD.open();
            List<Tache> taches = tacheBDD.getTaches(sprint);
            tacheBDD.close();

            TextView titre = (TextView) findViewById(R.id.SprintNumber);
            titre.setText(project.getName() + " sprint n°" + sprint.getNumber());


            ArchivedTacheAdapter adapter = new ArchivedTacheAdapter(PageSprint.this, getApplicationContext(), taches, projectId);
            // On dit à la ListView de se remplir via cet adapter
            contenuDeLaPage.setAdapter(adapter);
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(PageSprint.this, ArchivedSprint.class);
        intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
        return intent;
    }

}
