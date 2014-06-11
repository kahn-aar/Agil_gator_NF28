package com.agil_gator_nf28.agil_gator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.Utils.Avancement;
import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * Created by Mathieu on 22/05/14.
 */
public class DescriptifTaskActivity extends ActionBarActivity {
    private TextView title_project, text_description, text_advanced, text_priority, text_doing;
    private RelativeLayout layout = null;
    //private int tacheId = 0;
   // private int projetId = 0;

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

        ImageButton assign = (ImageButton) layout.findViewById(R.id.assign_task_button);
        ImageButton statsbutton = (ImageButton) layout.findViewById(R.id.statbutton);
        ImageButton suppbutton = (ImageButton) layout.findViewById(R.id.suppbutton);

        ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.progressBar);

        Intent intent = getIntent();

        LinearLayout en_cours = (LinearLayout) layout.findViewById(R.id.doingContent);
        final int tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);
        final int projetId = intent.getIntExtra(AndroidConstantes.PROJECT_ID,-1);
        if(tacheId != -1){

            TacheBDD tacheBDD = new TacheBDD(this);

            tacheBDD.open();

            tache = tacheBDD.getTacheWithId(tacheId);
            int sprint_id = tacheBDD.getSprintIdWithTacheId(tacheId);
            tacheBDD.close();

            //Récupération des Sous taches
            SousTacheBDD sousTacheBDD = new SousTacheBDD(this);
            sousTacheBDD.open();
            tache.setSousTaches(sousTacheBDD.getSousTaches(tache));
            sousTacheBDD.close();

            Avancement avancement = new Avancement(tache.getSousTaches().size(), tache.getSousTachesDone().size(), tache.getSousTachesEnCours().size() + tache.getSousTachesARelire().size());

            /** on verifie si la tache est archive **/
           /* SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            Sprint actualsprint = sprintBDD.getLastSprintOfProjectWithId(projetId);
            sprintBDD.close();

            if(actualsprint.getId()!=sprint_id){
                assign.setClickable(false);
                suppbutton.setClickable(false);
            }*/

            /** on remplit les champs de la description d'une tache **/
            title_project.setText(tache.getNom());
            text_description.setText(tache.getDescription());
            text_priority.setText(Integer.toString(tache.getPriorite()));
            text_advanced.setText("Fait/En cours/total : " + avancement.getNombre_fini() + "/" + avancement.getNombre_en_cours() + "/" + avancement.getNombre_total());
            progressBar.setMax(avancement.getNombre_total());
            progressBar.setProgress(avancement.getNombre_fini());
            progressBar.setSecondaryProgress(avancement.getNombre_fini() + avancement.getNombre_en_cours());

            for (SousTache underTask : tache.getSousTachesEnCours()) {
                TextView vue = new TextView(DescriptifTaskActivity.this);
                vue.setText(underTask.getTitre() + " - " + underTask.getEffecteur().getFirstname() + " " + underTask.getEffecteur().getName());
                en_cours.addView(vue);
            }

            assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(DescriptifTaskActivity.this, AssignMember.class);
                    intent1.putExtra(AndroidConstantes.PROJECT_ID, projetId);
                    intent1.putExtra(AndroidConstantes.TACHE_ID, tacheId);
                    System.out.println("projet = " + projetId );
                    System.out.println("Tache = " + tacheId);
                    DescriptifTaskActivity.this.startActivity(intent1);
                    DescriptifTaskActivity.this.finish();
                }
            });



            statsbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(DescriptifTaskActivity.this, Statistic.class);
                    intent1.putExtra(AndroidConstantes.PROJECT_ID, projetId);
                    intent1.putExtra(AndroidConstantes.TACHE_ID, tacheId);
                    DescriptifTaskActivity.this.startActivity(intent1);
                    DescriptifTaskActivity.this.finish();
                }
            });

            suppbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(DescriptifTaskActivity.this)
                            .setTitle("Suppression")
                            .setMessage("Etes vous sure de vouloir supprimer cette tâche?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    TacheBDD tacheBDD = new TacheBDD(DescriptifTaskActivity.this);
                                    tacheBDD.open();
                                    tacheBDD.deleteTacheWithId(tacheId);
                                    tacheBDD.close();
                                    Intent intent1 = new Intent(DescriptifTaskActivity.this, Page_projet.class);
                                    intent1.putExtra(AndroidConstantes.PROJECT_ID, projetId);
                                    DescriptifTaskActivity.this.startActivity(intent1);
                                    DescriptifTaskActivity.this.finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });


            setContentView(layout);

        }


    }

}
