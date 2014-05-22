package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Taches.Tache;

public class DescriptifProjectActivity extends ActionBarActivity {

    private TextView title_project, text_description, text_membres, text_advanced;
    private RelativeLayout layout = null;
    private int ID = 0;
    private Tache tache;

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

        ID = intent.getIntExtra("ID_TACHE",-1);

        if(ID != -1){

        TacheBDD tacheBDD = new TacheBDD(this);
        //On ouvre la base de données pour écrire dedans
        tacheBDD.open();

        System.out.println("identifiant de la tache !!!!!!!!!!!!!!!!!!!!!!!!!!! : "+ID);

        tache = tacheBDD.getTacheWithId(ID);

        tacheBDD.close();
        /** on remplit les champs title, description, priorité et l'avancee **/
        title_project.setText(tache.getNom());
        text_description.setText(Integer.toString(tache.getDifficulte()));
        text_advanced.setText(Integer.toString(tache.getId()));
        }

        setContentView(layout);

    }



}
