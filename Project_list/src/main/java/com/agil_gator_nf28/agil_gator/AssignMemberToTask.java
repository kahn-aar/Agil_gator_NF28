package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.UserBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.User.UserAdapter;
import com.agil_gator_nf28.User.UserAssignAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class AssignMemberToTask extends ActionBarActivity {

    private int id_project;
    private int id_sous_tache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_projet);
        final ListView listeView = (ListView)findViewById(R.id.listeMembres);
        TextView title = (TextView) findViewById(R.id.title_add_member);
        title.setText("Assigner cette sous tâche au membre");

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        Intent intent = getIntent();
        if (intent != null) {
            id_sous_tache = Integer.valueOf(intent.getStringExtra(AndroidConstantes.SOUS_TACHE_ID));
            id_project = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));

            SousTacheBDD sousTacheBDD = new SousTacheBDD(this);
            sousTacheBDD.open();
            SousTache sousTache = sousTacheBDD.getSousTacheFromId(id_sous_tache);
            sousTacheBDD.close();

            UserBDD userBDD = new UserBDD(AssignMemberToTask.this);
            userBDD.open();
            List<User> users = userBDD.getListUserOfAProject(id_project);
            userBDD.close();

            UserAssignAdapter adapter = new UserAssignAdapter(AssignMemberToTask.this, R.id.listeMembres , users, sousTache, id_project);

            listeView.setAdapter(adapter);
        }
    }

}
