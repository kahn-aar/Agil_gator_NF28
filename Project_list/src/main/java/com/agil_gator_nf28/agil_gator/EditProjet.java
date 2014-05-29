package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProjet extends ActionBarActivity {

    private Projet projet = null;
    private String fromPage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_projet);

        //Récupération des informations
        Intent intent = getIntent();
        if (intent != null) {
            int projectId = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));
            fromPage = intent.getStringExtra(AndroidConstantes.PROJECT_EDIT_FROM);

            //Mise en place de la récupération des datas.
            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            projet = projetBDD.getProjetById(projectId);
            projetBDD.close();

            final EditText titreProj = (EditText) findViewById(R.id.editNomProjet);
            final EditText subProj = (EditText) findViewById(R.id.editSubTitre);
            final EditText descProj = (EditText) findViewById(R.id.editDescription);

            titreProj.setText(projet.getName());
            subProj.setText(projet.getSubTitle());
            descProj.setText(projet.getDescription());

            Button edit = (Button) findViewById(R.id.edit_button);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nomText = titreProj.getText().toString();
                    final String subText = subProj.getText().toString();
                    final String descText = descProj.getText().toString();
                    // On déclare le pattern que l’on doit vérifier
                    Pattern p = Pattern.compile(".+");
                    // On déclare un matcher, qui comparera le pattern avec la
                    // string passée en argument
                    Matcher m = p.matcher(nomText);
                    Matcher m2 = p.matcher(subText);
                    // Si l’adresse mail saisie ne correspond au format d’une
                    // adresse mail on un affiche un message à l'utilisateur
                    if (!m.matches()) {
                        Toast.makeText(EditProjet.this, R.string.error_name_project, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!m2.matches()) {
                        Toast.makeText(EditProjet.this, R.string.error_sub_project, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Création d'une instance de ma classe LivresBDD
                    ProjetBDD projetBdd = new ProjetBDD(EditProjet.this);

                    //On ouvre la base de données pour écrire dedans
                    projetBdd.open();
                    //On insère le livre que l'on vient de créer
                    projetBdd.updateProjet(projet.getId(), projet);
                    projetBdd.close();

                    //On retourne sur la page d'accueil
                    Intent intent = getSupportParentActivityIntent();
                    startActivity(intent);
                }
            });
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_projet, menu);
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
        Intent intent = null;
        if (AndroidConstantes.PROJECT_EDIT_FROM_DETAIL.equals(fromPage)) {
            intent = new Intent(this, Page_projet.class);
            intent.putExtra("user_login", String.valueOf(projet.getId()));
        }
        else if (AndroidConstantes.PROJECT_EDIT_FROM_LIST.equals(fromPage)) {
            intent = new Intent(this, Project_List.class);
        }
        return intent;
    }

}
