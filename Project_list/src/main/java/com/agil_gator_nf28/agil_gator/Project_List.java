package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Projet.ProjetAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

public class Project_List extends ActionBarActivity {

    final String EXTRA_LOGIN = "user_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project__list);
        ListView contenuDeLaPage = (ListView)findViewById(R.id.contenuDeLaPage);

        //Création d'une instance de ma classe LivresBDD
        ProjetBDD projetBdd = new ProjetBDD(this);

        //On ouvre la base de données pour écrire dedans
        projetBdd.open();
        List<Projet> projets = projetBdd.getProjets();
        //List<Projet> projets = new ArrayList<Projet>();
        //projets.add(livre);
        projetBdd.close();

        ProjetAdapter adapter = new ProjetAdapter(Project_List.this, getApplicationContext(), projets);
        // On dit à la ListView de se remplir via cet adapter
        contenuDeLaPage.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project__list, menu);
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
        if (id == R.id.addProject) {
            Intent intent = new Intent(this, AddProject.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
