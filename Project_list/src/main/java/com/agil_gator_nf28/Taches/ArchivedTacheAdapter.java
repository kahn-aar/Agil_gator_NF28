package com.agil_gator_nf28.Taches;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.agil_gator.DescriptifTaskActivity;
import com.agil_gator_nf28.agil_gator.PageSprint;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

/**
 * Created by Nicolas on 02/06/14.
 */
public class ArchivedTacheAdapter extends BaseAdapter {

    private List<Tache> taches;
    private PageSprint pageSprint;
    private Context context;
    private LayoutInflater inflater;
    private int projetId;

    public ArchivedTacheAdapter(PageSprint pageSprint, Context context, List<Tache> taches, int id) {
        this.taches = taches;
        this.pageSprint = pageSprint;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.projetId = id;
    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @Override
    public Object getItem(int position) {
        return taches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView nom;
        TextView priorite;
        TextView difficulte;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final int pos = position;


        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.task_view_no_notif, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.nom = (TextView)convertView.findViewById(R.id.nom_tache);
            holder.priorite = (TextView)convertView.findViewById(R.id.prio);
            holder.difficulte = (TextView)convertView.findViewById(R.id.diff);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.nom.setText(taches.get(position).getNom());
        holder.priorite.setText("Priorité : " + String.valueOf(taches.get(position).getPriorite()));
        holder.difficulte.setText("Difficulté : " +String.valueOf(taches.get(position).getDifficulte()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pageSprint, DescriptifTaskActivity.class);
                intent.putExtra(AndroidConstantes.TACHE_ID, String.valueOf(taches.get(pos).getId()));
                intent.putExtra(AndroidConstantes.TASK_DESC_FROM, AndroidConstantes.TASK_DESC_FROM_ARCHIVE);
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projetId));
                pageSprint.startActivity(intent);
            }
        });

        return convertView;
    }
}
