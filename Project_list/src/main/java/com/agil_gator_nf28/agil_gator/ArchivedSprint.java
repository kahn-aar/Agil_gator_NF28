package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Projet.ProjetAdapter;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.archived_sprint, menu);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, Page_projet.class);
        intent.putExtra("user_login", String.valueOf(project.getId()));
        return intent;
    }

}
