package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * Created by Mathieu on 22/05/14.
 */
public class DescriptifTaskActivity extends ActionBarActivity {
    private TextView title_project, text_description, text_advanced, text_priority, text_doing;
    private RelativeLayout layout = null;
    private int ID = 0;
    private Tache tache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UTILISER burndown chart pour stats !!!
        super.onCreate(savedInstanceState);

        /** permet de deserializer xml -> java **/
        layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_descriptiftask, null);

        title_project = (TextView)layout.findViewById(R.id.title_project);
        text_description = (TextView)layout.findViewById(R.id.descriptionContent);
        text_priority = (TextView)layout.findViewById(R.id.prioritycontent);
        text_advanced = (TextView)layout.findViewById(R.id.avancedContent);
        text_doing = (TextView)layout.findViewById(R.id.doingContent);

        Intent intent = getIntent();

        ID = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);

        if(ID != -1){

            TacheBDD tacheBDD = new TacheBDD(this);

            tacheBDD.open();

            tache = tacheBDD.getTacheWithId(ID);

            tacheBDD.close();
            /** on remplit les champs de la description d'une tache **/
            title_project.setText(tache.getNom());
            text_description.setText(tache.getDescription());
            text_priority.setText(Integer.toString(tache.getPriorite()));
            text_advanced.setText(Integer.toString(tache.getId()));
        }

        setContentView(layout);

    }

}
