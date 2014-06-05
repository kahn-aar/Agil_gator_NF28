package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mathieu on 03/06/14.
 */
public class Add_Sub_Task extends  ActionBarActivity {

        private Projet project;
        private Tache tache;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_sub_task);
            Intent intent = getIntent();

            /*Spinner spinner = (Spinner) findViewById(R.id.etat_spiner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.etat_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);*/

            if (intent != null) {
                int projectId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));
                ProjetBDD projetBDD = new ProjetBDD(this);
                projetBDD.open();
                project = projetBDD.getProjetById(projectId);
                projetBDD.close();

                TextView titreProjet = (TextView) findViewById(R.id.add_task_project_name);
                titreProjet.setText(project.getName());

                int tacheId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.TACHE_ID));
                TacheBDD tacheBDD = new TacheBDD(this);
                tacheBDD.open();
                tache = tacheBDD.getTacheWithId(tacheId);
                tacheBDD.close();

                final EditText titre = (EditText) findViewById(R.id.subtaskName);

                //Gestion du boutton d'ajout add_button
                Button ajout = (Button) findViewById(R.id.add_button);
                ajout.setOnClickListener(new View.OnClickListener() {

                    final String nomText = titre.getText().toString();
                    @Override
                    public void onClick(View v) {
                        Pattern p = Pattern.compile(".+");
                        Matcher m = p.matcher(nomText);

                            Toast.makeText(Add_Sub_Task.this, R.string.error_name_task, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Ajout de la sous tâche
                        SousTache subtache = new SousTache(nomText);
                        //Création d'une instance de ma classe TacheBDD
                        SousTacheBDD subtacheBDD = new SousTacheBDD(Add_Sub_Task.this);

                        //On ouvre la base de données pour écrire dedans
                        //On insère la tâche que l'on vient de créer


                        //On retourne sur la page d'accueil
                       // Intent intent = new Intent(Add_Sub_Task.this, Page_projet.class);
                        //intent.putExtra("user_login", String.valueOf(project.getId()));
                        //startActivity(intent);


                });
            }
        }
/*

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
*/
    }


