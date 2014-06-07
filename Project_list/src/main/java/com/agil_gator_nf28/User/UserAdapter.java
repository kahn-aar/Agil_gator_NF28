package com.agil_gator_nf28.User;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.UserProjetBDD;
import com.agil_gator_nf28.Listeners.TacheGridListener;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agil_gator.DescriptifTaskActivity;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;


/**
 * Created by Nicolas on 05/06/14.
 */
public class UserAdapter extends ArrayAdapter<User> {

    private List<User> taches;
    private LayoutInflater inflater;
    private int projectId;

    public UserAdapter(Context context, int resource, List<User> users, int projectId) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.taches = users;
        this.projectId = projectId;
    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @Override
    public User getItem(int position) {
        return taches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView nom;
        TextView prenom;
        TextView email;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final int pos = position;

        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.membre_layout, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.nom = (TextView)convertView.findViewById(R.id.nomUser);
            holder.prenom = (TextView)convertView.findViewById(R.id.prenomUser);
            holder.email = (TextView)convertView.findViewById(R.id.userEmail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.nom.setText(taches.get(position).getName());
        holder.prenom.setText(taches.get(position).getFirstname());
        holder.email.setText(taches.get(position).getEmail());

        //Onclick sur la view

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProjetBDD userProjetBDD = new UserProjetBDD(getContext());
                userProjetBDD.open();
                userProjetBDD.insertProjet(taches.get(pos), (long) projectId);
                userProjetBDD.close();

                Intent intent = new Intent(getContext(), Page_projet.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projectId));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
