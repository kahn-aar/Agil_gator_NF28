package com.agil_gator_nf28.agil_gator;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.agil_gator_nf28.agent.manager.AgentManager;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Task extends ActionBarActivity {

    private Projet project;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__task);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);

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
            System.out.println("actualSprint = " + acutalSprint.getNumber());
            TextView titreProjet = (TextView) findViewById(R.id.add_task_project_name);
            titreProjet.setText(project.getTitle());

            final EditText titre = (EditText) findViewById(R.id.taskName);
            final EditText prio = (EditText) findViewById(R.id.taskPrio);
            final EditText diff = (EditText) findViewById(R.id.taskHard);
            final EditText description = (EditText) findViewById(R.id.description_task);

            //Gestion du boutton d'ajout add_button
            Button ajout = (Button) findViewById(R.id.add_button);
            ajout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gestion des erreurs
                    final String nomText = titre.getText().toString();
                    final int prioValue = Integer.valueOf(prio.getText().toString());
                    final int hardValue = Integer.valueOf(diff.getText().toString());
                    final String descriptionText = description.getText().toString();
                    // On déclare le pattern que l’on doit vérifier
                    Pattern p = Pattern.compile(".+");
                    // On déclare un matcher, qui comparera le pattern avec la
                    // string passée en argument
                    Matcher m = p.matcher(nomText);
                    Matcher m2 = p.matcher(descriptionText);
                    // Si l’adresse mail saisie ne correspond au format d’une
                    // adresse mail on un affiche un message à l'utilisateur
                    if (!m.matches()) {
                        Toast.makeText(Add_Task.this, R.string.error_name_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!m2.matches()) {
                        Toast.makeText(Add_Task.this, R.string.error_desc_task, Toast.LENGTH_SHORT).show();
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
                    Tache tache = new Tache(nomText, descriptionText, prioValue, hardValue);
                    tache.setSprint(acutalSprint.getId());
                    AgentManager.getInstance().createTache(tache);

                    //Création d'une instance de ma classe TacheBDD
                    TacheBDD tacheBDD = new TacheBDD(Add_Task.this);

                    //On ouvre la base de données pour écrire dedans
                    tacheBDD.open();
                    //On insère la tâche que l'on vient de créer
                    tacheBDD.insertTache(tache, acutalSprint);
                    tacheBDD.close();

                    //On retourne sur la page d'accueil
                    Intent intent = new Intent(Add_Task.this, Page_projet.class);
                    intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(project.getId()));
                    startActivity(intent);
                    Add_Task.this.finish();

                }
            });
        }
    }


}
