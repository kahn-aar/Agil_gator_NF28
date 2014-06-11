package com.agil_gator_nf28.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;


/**
 * Adapteur pour la liste des user pour leur assigner une tâche
 *
 * Created by Nicolas on 05/06/14.
 */
public class UserAssignAdapter extends ArrayAdapter<User> {

    private List<User> users;
    private LayoutInflater inflater;
    private SousTache sousTache;
    private int projectId;

    public UserAssignAdapter(Context context, int resource, List<User> users, SousTache sousTache, int projectId) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.users = users;
        this.sousTache = sousTache;
        this.projectId = projectId;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
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
        holder.nom.setText(users.get(position).getName());
        holder.prenom.setText(users.get(position).getFirstname());
        holder.email.setText(users.get(position).getEmail());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sousTache.setEffecteur(users.get(pos));
                sousTache.setEtat(SousTacheEtat.ENCOURS);

                SousTacheBDD sousTacheBDD = new SousTacheBDD(getContext());
                sousTacheBDD.open();
                sousTacheBDD.updateSousTacheEffecteur(sousTache);
                sousTacheBDD.close();

                Intent intent = new Intent(getContext(), Page_projet.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projectId));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
