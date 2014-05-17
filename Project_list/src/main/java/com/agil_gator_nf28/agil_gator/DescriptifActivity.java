package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.Projet.Projet;

import java.util.List;

public class DescriptifActivity extends ActionBarActivity {

    private TextView title_project, text_description, text_membres, text_advanced;
    private RelativeLayout layout = null;
    private int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UTILISER burndown chart pour stats !!!
        super.onCreate(savedInstanceState);

        /** permet de deserializer xml -> java **/
        layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_descritpif, null);

        title_project = (TextView)layout.findViewById(R.id.title_project);
        text_description = (TextView)layout.findViewById(R.id.descriptionContent);
        text_membres = (TextView)layout.findViewById(R.id.membreContent);
        text_advanced = (TextView)layout.findViewById(R.id.avancedContent);

        Intent intent = getIntent();

        intent.getIntExtra("id",ID);

        //Création d'une instance de ma classe ProjetBDD
        ProjetBDD projetBdd = new ProjetBDD(this);

        //On ouvre la base de données pour écrire dedans
        projetBdd.open();

        Projet projet = projetBdd.getProjectWithId(ID);

        projetBdd.close();

        /** on remplit les champs title, description, priorité et l'avancee **/
        title_project.setText(projet.getName());
        text_description.setText(projet.getDescription());
        text_advanced.setText(projet.getAdvanced());

        setContentView(layout);

    }



}
