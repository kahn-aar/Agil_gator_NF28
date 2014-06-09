package com.agil_gator_nf28.Sprint;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.agil_gator.ArchivedSprint;
import com.agil_gator_nf28.agil_gator.PageSprint;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

/**
 * Created by Nicolas on 02/06/14.
 */
public class SprintAdapter extends BaseAdapter {

    private List<Sprint> sprints;
    private LayoutInflater inflater;
    private ArchivedSprint archivedSprint;
    private int projectId;

    public SprintAdapter(ArchivedSprint archivedSprint, Context context, List<Sprint> sprints, int projectId) {
        this.sprints = sprints;
        this.inflater = LayoutInflater.from(context);
        this.archivedSprint = archivedSprint;
        this.projectId = projectId;
    }

    @Override
    public int getCount() {
        return sprints.size();
    }

    @Override
    public Object getItem(int position) {
        return sprints.get(position);
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
        TextView number;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final int pos = position;


        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.project_view, null);
            holder.number = (TextView)convertView.findViewById(R.id.title_project_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.number.setText("Sprint n°" + sprints.get(position).getNumber());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(archivedSprint, PageSprint.class);
                intent.putExtra(AndroidConstantes.SPRINT_ID, String.valueOf(sprints.get(pos).getId()));
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projectId));
                archivedSprint.startActivity(intent);
                archivedSprint.finish();
            }
        });

        return convertView;
    }
}
