package com.agil_gator_nf28.Projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.agil_gator.R;

import java.util.List;

/**
 * Created by Nicolas on 05/05/14.
 */
public class ProjetAdapter extends BaseAdapter {

    private List<Projet> projets;
    private LayoutInflater inflater;

    public ProjetAdapter(Context context, List<Projet> projets) {
        inflater = LayoutInflater.from(context);
        this.projets = projets;
    }

    @Override
    public int getCount() {
        return projets.size();
    }

    @Override
    public Object getItem(int position) {
        return projets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        return convertView;
    }
}
