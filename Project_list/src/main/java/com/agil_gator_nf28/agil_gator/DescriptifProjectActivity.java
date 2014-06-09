package com.agil_gator_nf28.agil_gator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.BddInterne.UserBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.User.UserAdapter;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import org.w3c.dom.Text;

import java.util.List;

public class DescriptifProjectActivity extends ActionBarActivity {

    private TextView title_project, text_description, text_membres, text_advanced;
    private RelativeLayout layout = null;
    private int ID = 0;
    private Projet projet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UTILISER burndown chart pour stats !!!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptifproject);
        /** permet de deserializer xml -> java **/
        layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_descriptifproject, null);
        LinearLayout listeView = (LinearLayout)layout.findViewById(R.id.listeMembres);

        title_project = (TextView)layout.findViewById(R.id.title_project);
        text_description = (TextView)layout.findViewById(R.id.descriptionContent);
        text_advanced = (TextView)layout.findViewById(R.id.avancedContent);

        ImageButton add_user_button = (ImageButton) layout.findViewById(R.id.add_user_button);
        ImageButton change_powerbutton = (ImageButton) layout.findViewById(R.id.change_pouvoirbutton);
        ImageButton statsbutton = (ImageButton) layout.findViewById(R.id.statsbutton);
        ImageButton suppbutton = (ImageButton) layout.findViewById(R.id.suppbutton);

        Intent intent = getIntent();

        ID = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));

        if(ID != -1){

        ProjetBDD projetBDD = new ProjetBDD(this);
        //On ouvre la base de données pour écrire dedans
        projetBDD.open();

        projet = projetBDD.getProjectWithId(ID);

        projetBDD.close();
        /** on remplit les champs title, description, priorité et l'avancee **/
        title_project.setText(projet.getName());
        text_description.setText(projet.getDescription());
        text_advanced.setText(Integer.toString(projet.getAdvanced()));
        }

        UserBDD userBDD = new UserBDD(DescriptifProjectActivity.this);
        userBDD.open();
        List<User> users = userBDD.getListUserOfAProject(ID);
        userBDD.close();
        for (User user : users) {
            TextView vue = new TextView(this);
            vue.setText(user.getFirstname() + " " + user.getName());
            listeView.addView(vue);
        }


        add_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DescriptifProjectActivity.this, AddMemberProjet.class);
                intent1.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
                DescriptifProjectActivity.this.startActivity(intent1);
            }
        });

        change_powerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DescriptifProjectActivity.this, TransferChief.class);
                intent1.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
                DescriptifProjectActivity.this.startActivity(intent1);
            }
        });

        statsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DescriptifProjectActivity.this, Statistic_sprint.class);
                intent1.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
                DescriptifProjectActivity.this.startActivity(intent1);
            }
        });

        suppbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DescriptifProjectActivity.this)
                        .setTitle("Suppression")
                        .setMessage("Etes vous sûr de vouloir supprimer ce projet?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                TacheBDD tacheBDD = new TacheBDD(DescriptifProjectActivity.this);
                                tacheBDD.open();
                                tacheBDD.deleteTacheWithId(projet.getId());
                                tacheBDD.close();
                                Intent intent1 = new Intent(DescriptifProjectActivity.this, Project_List.class);
                                DescriptifProjectActivity.this.startActivity(intent1);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        if (! ConnectedUser.getInstance().getConnectedUser().isChef(projet)) {
            add_user_button.setEnabled(false);
            change_powerbutton.setEnabled(false);
            suppbutton.setEnabled(false);
            System.out.println("disabled move");
        }

        setContentView(layout);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, Page_projet.class);
        intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
        return intent;
    }

}
