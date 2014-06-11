package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Sprint.SprintAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class ArchivedSprint extends ActionBarActivity {

    private Projet project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_sprint);
        ListView contenuDeLaPage = (ListView)findViewById(R.id.contenuDeLaPage);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        Intent intent = getIntent();
        if (intent != null) {
            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            int projectId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));

            project = projetBDD.getProjetById(projectId);
            projetBDD.close();

            TextView projectName = (TextView) findViewById(R.id.ProjectName);
            projectName.setText(project.getName());

            SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            List<Sprint> sprints = sprintBDD.getSprintsFromProject(project);
            sprintBDD.close();


            SprintAdapter adapter = new SprintAdapter(ArchivedSprint.this, getApplicationContext(), sprints, projectId);
            // On dit à la ListView de se remplir via cet adapter
            contenuDeLaPage.setAdapter(adapter);
        }


    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(ArchivedSprint.this, Page_projet.class);
        intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
        return intent;
    }

}
