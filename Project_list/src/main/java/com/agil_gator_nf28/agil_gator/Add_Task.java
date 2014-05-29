package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            final Sprint acutalSprint = sprintBDD.getLastSprintOfProject(project);
            sprintBDD.close();

            TextView titreProjet = (TextView) findViewById(R.id.add_task_project_name);
            titreProjet.setText(project.getName());

            final EditText titre = (EditText) findViewById(R.id.taskName);
            final EditText prio = (EditText) findViewById(R.id.taskPrio);
            final EditText diff = (EditText) findViewById(R.id.taskHard);

            //Gestion du boutton d'ajout add_button
            Button ajout = (Button) findViewById(R.id.add_button);
            ajout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gestion des erreurs
                    final String nomText = titre.getText().toString();
                    final int prioValue = Integer.valueOf(prio.getText().toString());
                    final int hardValue = Integer.valueOf(diff.getText().toString());
                    // On déclare le pattern que l’on doit vérifier
                    Pattern p = Pattern.compile(".+");
                    // On déclare un matcher, qui comparera le pattern avec la
                    // string passée en argument
                    Matcher m = p.matcher(nomText);
                    // Si l’adresse mail saisie ne correspond au format d’une
                    // adresse mail on un affiche un message à l'utilisateur
                    if (!m.matches()) {
                        Toast.makeText(Add_Task.this, R.string.error_name_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (prioValue <= 0 || prioValue > 1000) {
                        Toast.makeText(Add_Task.this, R.string.error_prio_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hardValue <= 0 || hardValue > 20) {
                        Toast.makeText(Add_Task.this, R.string.error_hard_task, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Ajout de la tâche
                    Tache tache = new Tache(nomText, prioValue, hardValue);
                    //Création d'une instance de ma classe TacheBDD
                    TacheBDD tacheBDD = new TacheBDD(Add_Task.this);

                    //On ouvre la base de données pour écrire dedans
                    tacheBDD.open();
                    //On insère la tâche que l'on vient de créer
                    tacheBDD.insertTache(tache, acutalSprint);
                    tacheBDD.close();

                    //On retourne sur la page d'accueil
                    Intent intent = new Intent(Add_Task.this, Page_projet.class);
                    intent.putExtra("user_login", String.valueOf(project.getId()));
                    startActivity(intent);

                }
            });
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

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, Page_projet.class);
        intent.putExtra("user_login", String.valueOf(project.getId()));
        return intent;
    }

}
