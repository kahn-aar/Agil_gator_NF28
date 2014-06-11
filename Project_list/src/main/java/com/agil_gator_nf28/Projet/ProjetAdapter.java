package com.agil_gator_nf28.Projet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.Project_List;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 05/05/14.
 */
public class ProjetAdapter extends ArrayAdapter<Projet> {

    private List<Projet> projets;
    private LayoutInflater inflater;
    private Context context;
    private Project_List project_list;
    private int Position;

    final String EXTRA_ID = "user_login";


    public ProjetAdapter(Context context, int view, List<Projet> projets, Project_List project_list) {
        super(context, view, projets);
        this.project_list = project_list;
        inflater = LayoutInflater.from(context);
        this.projets = projets ==null ? new ArrayList<Projet>() : projets;
    }


    @Override
    public int getCount() {
        return projets.size();
    }

    @Override
    public Projet getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeProjectId(int supp_projet_id){
        Projet supp_projet = null;
        for(Projet projet : projets) {
            if (projet.getId() == supp_projet_id) {
                supp_projet = projet;
            }
        }
        projets.remove(supp_projet);

    }

    public int getposition (){
        return Position;
    }

    /**
     * Classe dans laquelle vous déclarez les éléments
     * qui vont être présents sur une ligne;
     * (ici, éléments du fichier ligne_de_la_listview.xml)
     */
    private class ViewHolder {
        TextView titre;
        TextView sub;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final int pos = position;
        Position = position;

        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.project_view, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.titre = (TextView)convertView.findViewById(R.id.title_project_list);
            holder.sub = (TextView)convertView.findViewById(R.id.sub_project_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.titre.setText(projets.get(position).getName());
        // Le second affichera l'élément
        holder.sub.setText(projets.get(position).getSubTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(project_list, Page_projet.class);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projets.get(pos).getId()));
                project_list.startActivity(intent);
            }
        });

        convertView.setLongClickable(true);

        return convertView;
    }
}
