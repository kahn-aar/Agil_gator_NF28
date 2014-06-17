package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.agent.manager.AgentManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText email = (EditText) findViewById(R.id.createAccoutMail);
        final EditText nom = (EditText) findViewById(R.id.CreateAccountNom);
        final EditText prenom = (EditText) findViewById(R.id.createAccountPrenom);
        final EditText password = (EditText) findViewById(R.id.CreateAccountPassword);

        Button create = (Button) findViewById(R.id.create_account_btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                final String nomText = nom.getText().toString();
                final String prenomText = prenom.getText().toString();
                final String passwordText = password.getText().toString();

                //On teste les valeurs
                //Pattern général
                Pattern p = Pattern.compile(".+");

                //Pattern email
                Pattern pe = Pattern.compile(".+@.+..+");

                // Matchers
                Matcher m = pe.matcher(emailText);
                Matcher m2 = p.matcher(nomText);
                Matcher m3 = p.matcher(prenomText);
                Matcher m4 = p.matcher(passwordText);
                // Si l’adresse mail saisie ne correspond au format d’une
                // adresse mail on un affiche un message à l'utilisateur
                if (!m.matches()) {
                    Toast.makeText(CreateAccount.this, R.string.error_account_email, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m2.matches()) {
                    Toast.makeText(CreateAccount.this, R.string.error_account_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m3.matches()) {
                    Toast.makeText(CreateAccount.this, R.string.error_account_prenom, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m4.matches()) {
                    Toast.makeText(CreateAccount.this, R.string.error_account_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(emailText, nomText, prenomText);
                user.setPassword(passwordText);
                user.setSalt1("oksaltwaiting");

                //On va lancer la création
                AgentManager.getInstance().createAccount(user, CreateAccount.this);

                /*
                UserBDD userBDD = new UserBDD(CreateAccount.this);
                userBDD.open();
                userBDD.insertProjet(user, passwordText);
                userBDD.close();*/

                //On retourne sur la page d'accueil
                Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(intent);
                CreateAccount.this.finish();
            }

        });

    }

}
