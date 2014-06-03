package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

public class DescriptifProjectActivity extends ActionBarActivity {

    private TextView title_project, text_description, text_membres, text_advanced;
    private RelativeLayout layout = null;
    private int ID = 0;
    private Projet projet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UTILISER burndown chart pour stats !!!
        super.onCreate(savedInstanceState);

        /** permet de deserializer xml -> java **/
        layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_descriptifproject, null);

        title_project = (TextView)layout.findViewById(R.id.title_project);
        text_description = (TextView)layout.findViewById(R.id.descriptionContent);
        text_membres = (TextView)layout.findViewById(R.id.membreContent);
        text_advanced = (TextView)layout.findViewById(R.id.avancedContent);

        Intent intent = getIntent();

        ID = intent.getIntExtra(AndroidConstantes.PROJECT_ID,-1);

        if(ID != -1){

        ProjetBDD projetBDD = new ProjetBDD(this);
        //On ouvre la base de données pour écrire dedans
        projetBDD.open();

        projet = projetBDD.getProjectWithId(ID);

        projetBDD.close();
        /** on remplit les champs title, description, priorité et l'avancee **/
        title_project.setText(projet.getName());
        text_description.setText(projet.getDescription());
        text_advanced.setText(Integer.toString(projet.getAdvanced()));
        }

        setContentView(layout);

    }



}