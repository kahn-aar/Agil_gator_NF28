package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_member_projet, menu);
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
