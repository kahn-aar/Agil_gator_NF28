package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.constantes.AndroidConstantes;

public class Add_Task extends ActionBarActivity {

    private Projet project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__task);
        Intent intent = getIntent();
        if (intent != null) {
            int projectId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));
            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            project = projetBDD.getProjetById(projectId);
            projetBDD.close();

            TextView titreProjet = (TextView) findViewById(R.id.add_task_project_name);
            titreProjet.setText(project.getName());

           // TacheBDD tacheBDD = new TacheBDD(this);
           // tacheBDD.open();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add__task, menu);
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

}
