package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.UserBDD;
import com.agil_gator_nf28.agent.manager.AgentManager;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailText = (EditText) findViewById(R.id.user_email);
        final EditText passwordText = (EditText) findViewById(R.id.user_password);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        final Button loginButton = (Button) findViewById(R.id.connect);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = emailText.getText().toString();
                final String password = passwordText.getText().toString();

                //Mise en place de la récupération des datas.
                UserBDD userBDD = new UserBDD(LoginActivity.this);
                userBDD.open();
                boolean isOkConnect = userBDD.connexion(email, password);
                userBDD.close();

                if (isOkConnect) {
                    AgentManager manager = AgentManager.getInstance();
                    manager.doConnect(LoginActivity.this, "172.25.27.53", "myAgent");
                    Intent intent = new Intent(LoginActivity.this, Project_List.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button InsciprionButton = (Button) findViewById(R.id.create_account);
        InsciprionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AgentManager manager = AgentManager.getInstance();
                manager.doConnect(LoginActivity.this, "83.156.25.95", "myAgent");
                Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }

}
