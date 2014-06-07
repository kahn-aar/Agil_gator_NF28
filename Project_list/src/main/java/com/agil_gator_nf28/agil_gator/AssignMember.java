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

            int projectId = intent.getIntExtra(AndroidConstantes.PROJECT_ID,-1);
            int tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);

            TacheBDD tacheBDD = new TacheBDD(this);
            tacheBDD.open();
            Tache tache = tacheBDD.getTacheWithId(tacheId);

            tacheBDD.close();

            SousTacheBDD sousTacheBDD = new SousTacheBDD(this);
            sousTacheBDD.open();
            List<SousTache> sousTaches = sousTacheBDD.getSousTaches(tache);
            sousTacheBDD.close();

            tache.setSousTaches(sousTaches);

            ArrayAdapter<SousTache> adapter = new ArrayAdapter<SousTache>(this, R.id.contenu, tache.getSousTachesAFaire());

            contenu.setAdapter(adapter);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assign_member, menu);
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

}
