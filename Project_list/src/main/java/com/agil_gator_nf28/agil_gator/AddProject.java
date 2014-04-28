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

import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProject extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        final EditText nom = (EditText) findViewById(R.id.editNomProjet);
        final EditText sub = (EditText) findViewById(R.id.editSubTitre);
        final EditText desc = (EditText) findViewById(R.id.editDescription);

        final Button loginButton = (Button) findViewById(R.id.add_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String nomText = nom.getText().toString();
                final String subText = sub.getText().toString();

                // On déclare le pattern que l’on doit vérifier
                Pattern p = Pattern.compile(".+");
                // On déclare un matcher, qui comparera le pattern avec la
                // string passée en argument
                Matcher m = p.matcher(nomText);
                Matcher m2 = p.matcher(subText);
                // Si l’adresse mail saisie ne correspond au format d’une
                // adresse mail on un affiche un message à l'utilisateur
                if (!m.matches()) {
                    Toast.makeText(AddProject.this, R.string.error_name_project, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m2.matches()) {
                    Toast.makeText(AddProject.this, R.string.error_sub_project, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddProject.this, Project_List.class);
                intent.putExtra(AndroidConstantes.EXTRA_TITLE_NEW_PROJECT, nom.getText().toString());
                intent.putExtra(AndroidConstantes.EXTRA_SUB_NEW_PROJECT, sub.getText().toString());
                intent.putExtra(AndroidConstantes.EXTRA_DESCRIPTION_NEW_PROJECT, desc.getText().toString());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_project, menu);
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
