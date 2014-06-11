package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.agil_gator_nf28.BddInterne.UserBDD;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.User.UserAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

public class AddMemberProjet extends ActionBarActivity {

    private int id_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_projet);
        final ListView listeView = (ListView)findViewById(R.id.listeMembres);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);


        Intent intent = getIntent();
        if (intent != null) {
            id_project = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));

            UserBDD userBDD = new UserBDD(AddMemberProjet.this);
            userBDD.open();
            List<User> users = userBDD.getListUserOfNotAProject(id_project);
            userBDD.close();

            UserAdapter adapter = new UserAdapter(AddMemberProjet.this, R.id.listeMembres , users, id_project);

            listeView.setAdapter(adapter);
        }
    }

}
