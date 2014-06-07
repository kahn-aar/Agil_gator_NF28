package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mathieu on 26/05/14.
 */
public class Modif_task extends ActionBarActivity {

    private Projet project;
    private Tache tache;
    private int ID;
    private String from;
    private int sprintIdFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_task);

        Intent intent = getIntent();

        ID = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);

        from = intent.getStringExtra(AndroidConstantes.TASK_DESC_FROM);
        if(AndroidConstantes.TASK_DESC_FROM_ARCHIVE.equals(from)) {
            sprintIdFrom = intent.getIntExtra(AndroidConstantes.SPRINT_ID,-1);
        }

       if (intent != null) {
           final Modif_task modif_task = this;

                   TacheBDD tacheBDD = new TacheBDD(Modif_task.this);
                   tacheBDD.open();
                   tache = tacheBDD.getTacheWithId(ID);
                   tacheBDD.close();


            final EditText ediNomTache = (EditText) findViewById(R.id.editTextNomTache);
            ediNomTache.setText(tache.getNom());

            final EditText editDifficulty = (EditText) findViewById(R.id.editTextDifficulty);
            editDifficulty.setText(String.valueOf(tache.getDifficulte()));

            final EditText editPriority = (EditText) findViewById(R.id.editTextPriority);
            editPriority.setText(String.valueOf(tache.getPriorite()));

           final EditText editDescription = (EditText) findViewById(R.id.editTextDescription);
           editDescription.setText(tache.getDescription());

            Button edit = (Button) findViewById(R.id.modif_button);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                   public void onClick(View v) {

                    final String NomTache = ediNomTache.getText().toString();
                    final String Difficulty = editDifficulty.getText().toString();
                    final String Priority = editPriority.getText().toString();
                    final String Description = editDescription.getText().toString();
                    // On déclare le pattern que l’on doit vérifier
                    Pattern p = Pattern.compile(".+");
                    // On déclare un matcher, qui comparera le pattern avec la
                    // string passée en argument
                    Matcher m = p.matcher(NomTache);

                    if (!m.matches()) {
                        Toast.makeText(Modif_task.this, R.string.error_name_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((Integer.valueOf(Priority)<0)||(Integer.valueOf(Priority)>1000)) {
                        Toast.makeText(Modif_task.this,  R.string.error_prio_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((Integer.valueOf(Difficulty)<0)||(Integer.valueOf(Difficulty)>20)) {
                        Toast.makeText(Modif_task.this, R.string.error_hard_task, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TacheBDD tacheBDD = new TacheBDD(Modif_task.this);
                    tacheBDD.open();
                    tacheBDD.updateTache(ID,new Tache(NomTache,Description,Integer.valueOf(Priority),Integer.valueOf(Difficulty)));
                    tacheBDD.close();

                    Intent intent1 = getSupportParentActivityIntent();
                    modif_task.startActivity(intent1);
                }

                 });



       }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = null;
        if (AndroidConstantes.TASK_DESC_FROM_LIST.equals(from)) {
            intent = new Intent(this, Page_projet.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(ID));
        }
        else if (AndroidConstantes.TASK_DESC_FROM_ARCHIVE.equals(from)) {
            intent = new Intent(this, PageSprint.class);
            intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(ID));
            intent.putExtra(AndroidConstantes.SPRINT_ID, String.valueOf(sprintIdFrom));
        }
        return intent;
    }
}
