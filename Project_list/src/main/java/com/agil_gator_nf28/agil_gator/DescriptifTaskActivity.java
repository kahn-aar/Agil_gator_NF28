package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.Utils.Avancement;
import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * Created by Mathieu on 22/05/14.
 */
public class DescriptifTaskActivity extends ActionBarActivity {
    private TextView title_project, text_description, text_advanced, text_priority, text_doing;
    private RelativeLayout layout = null;
    private int tacheId = 0;
    private int projetId = 0;

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

        ImageButton assign = (ImageButton) layout.findViewById(R.id.assign_task_button);
        ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.progressBar);

        Intent intent = getIntent();

        tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);
        projetId = intent.getIntExtra(AndroidConstantes.PROJECT_ID,-1);
        if(tacheId != -1){

            TacheBDD tacheBDD = new TacheBDD(this);

            tacheBDD.open();

            tache = tacheBDD.getTacheWithId(tacheId);

            tacheBDD.close();

            //Récupération des Sous taches
            SousTacheBDD sousTacheBDD = new SousTacheBDD(this);
            sousTacheBDD.open();
            tache.setSousTaches(sousTacheBDD.getSousTaches(tache));
            sousTacheBDD.close();

            Avancement avancement = new Avancement(tache.getSousTaches().size(), tache.getSousTachesDone().size(), tache.getSousTachesEnCours().size() + tache.getSousTachesARelire().size());


            /** on remplit les champs de la description d'une tache **/
            title_project.setText(tache.getNom());
            text_description.setText(tache.getDescription());
            text_priority.setText(Integer.toString(tache.getPriorite()));
            text_advanced.setText("Fait/En cours/total : " + avancement.getNombre_fini() + "/" + avancement.getNombre_en_cours() + "/" + avancement.getNombre_total());
            progressBar.setMax(avancement.getNombre_total());
            progressBar.setProgress(avancement.getNombre_fini());
            progressBar.setSecondaryProgress(avancement.getNombre_fini() + avancement.getNombre_en_cours());
        }

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DescriptifTaskActivity.this, AssignMember.class);
                intent1.putExtra(AndroidConstantes.PROJECT_ID, projetId);
                intent1.putExtra(AndroidConstantes.TACHE_ID, tacheId);
                DescriptifTaskActivity.this.startActivity(intent1);
            }
        });

        setContentView(layout);

    }

}
