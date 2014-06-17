package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.UserProjetBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.agent.manager.AgentManager;

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

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);

        final Button loginButton = (Button) findViewById(R.id.add_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String nomText = nom.getText().toString();
                final String subText = sub.getText().toString();
                final String descText = desc.getText().toString();
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
                Projet projet = new Projet(nomText, subText, descText, ConnectedUser.getInstance().getConnectedUser());

                AgentManager.getInstance().createProject(projet);

                ProjetBDD projetBdd = new ProjetBDD(AddProject.this);
                projetBdd.open();
                long projectId = projetBdd.insertProjet(projet);
                projetBdd.close();

                projet.setId((int) projectId);
                Sprint sprint = new Sprint();
                sprint.setNumber(1);
                sprint.setProject((int) projectId);
                SprintBDD sprintBDD = new SprintBDD(AddProject.this);
                sprintBDD.open();
                sprintBDD.insertSprint(sprint, projet);
                sprintBDD.close();
                AgentManager.getInstance().createSprint(sprint);

                UserProjetBDD userProjetBDD = new UserProjetBDD(AddProject.this);
                userProjetBDD.open();
                userProjetBDD.insertProjet(ConnectedUser.getInstance().getConnectedUser(), projectId);
                userProjetBDD.close();
                AgentManager.getInstance().createUserProject(ConnectedUser.getInstance().getConnectedUser(), projet);

                //On retourne sur la page d'accueil
                Intent intent = new Intent(AddProject.this, Project_List.class);
                startActivity(intent);
                AddProject.this.finish();

            }
        });
    }

}
