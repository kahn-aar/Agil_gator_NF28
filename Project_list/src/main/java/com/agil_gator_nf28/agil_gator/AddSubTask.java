package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agent.manager.AgentManager;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSubTask extends ActionBarActivity {

    private Tache tache;
    private Projet projet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_task);
        //add_sub_task_task_name
        Intent intent = getIntent();

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        if (intent != null) {
            int projectId = intent.getIntExtra(AndroidConstantes.PROJECT_ID, -1);
            int tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID, -1);

            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            projet = projetBDD.getProjetById(projectId);
            projetBDD.close();

            TacheBDD tacheBDD = new TacheBDD(this);
            tacheBDD.open();
            tache = tacheBDD.getTacheWithId(tacheId);
            tacheBDD.close();

            TextView titreTache = (TextView) findViewById(R.id.add_sub_task_task_name);
            titreTache.setText(tache.getName());

            final EditText titre = (EditText) findViewById(R.id.taskName);
            final EditText description = (EditText) findViewById(R.id.description_task);

            //Gestion du boutton d'ajout add_button
            Button ajout = (Button) findViewById(R.id.add_button);
            ajout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gestion des erreurs
                    final String nomText = titre.getText().toString();
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
                        Toast.makeText(AddSubTask.this, R.string.error_name_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!m2.matches()) {
                        Toast.makeText(AddSubTask.this, R.string.error_desc_task, Toast.LENGTH_SHORT).show();
                        return;
                    }


                    // Ajout de la sous tâche
                    SousTache sousTache = new SousTache();
                    sousTache.setTitre(nomText);
                    sousTache.setEtat(SousTacheEtat.AFAIRE);
                    sousTache.setDescription(descriptionText);
                    sousTache.setTask(tache);
                    //Création d'une instance de ma classe TacheBDD

                    AgentManager.getInstance().createSubTask(sousTache, AddSubTask.this);

                    //On retourne sur la page d'accueil
                    Intent intent = new Intent(AddSubTask.this, Page_projet.class);
                    intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
                    startActivity(intent);
                    AddSubTask.this.finish();
                }
            });
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, Page_projet.class);
        intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
        return intent;
    }

}
