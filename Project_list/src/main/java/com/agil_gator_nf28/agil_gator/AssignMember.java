package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheListAdapter;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class AssignMember extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_member);

        ListView contenu = (ListView) findViewById(R.id.contenu);

        // Récupération de la liste de tâches  faire

        Intent intent = getIntent();
        if (intent != null) {

            int projectId = intent.getIntExtra(AndroidConstantes.PROJECT_ID, -1);
            int tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID, -1);

            TacheBDD tacheBDD = new TacheBDD(this);
            tacheBDD.open();
            Tache tache = tacheBDD.getTacheWithId(tacheId);

            tacheBDD.close();

            SousTacheBDD sousTacheBDD = new SousTacheBDD(this);
            sousTacheBDD.open();
            List<SousTache> sousTaches = sousTacheBDD.getSousTaches(tache);
            sousTacheBDD.close();

            tache.setSousTaches(sousTaches);

            SousTacheListAdapter adapter = new SousTacheListAdapter(AssignMember.this, getApplicationContext(), tache.getSousTachesAFaire(), projectId);

            contenu.setAdapter(adapter);
        }


    }

}
