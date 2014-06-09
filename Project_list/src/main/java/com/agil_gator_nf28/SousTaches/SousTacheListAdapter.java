package com.agil_gator_nf28.SousTaches;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.agil_gator.AssignMember;
import com.agil_gator_nf28.agil_gator.AssignMemberToTask;
import com.agil_gator_nf28.agil_gator.DescriptifTaskActivity;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.List;

/**
 * Created by Nicolas on 02/06/14.
 */
public class SousTacheListAdapter extends BaseAdapter {

    private List<SousTache> taches;
    private AssignMember assignMember;
    private Context context;
    private LayoutInflater inflater;
    private int projectId;

    public SousTacheListAdapter(AssignMember assignMember, Context context, List<SousTache> taches, int projectId) {
        this.taches = taches;
        this.assignMember = assignMember;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.projectId = projectId;
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
        TextView desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final int pos = position;


        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.sous_tache_element, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.nom = (TextView)convertView.findViewById(R.id.titre_sous_tache);
            //holder.desc = (TextView)convertView.findViewById(R.id.prio);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.nom.setText(taches.get(position).getTitre());
        //holder.desc.setText(String.valueOf(taches.get(position).get));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(assignMember, AssignMemberToTask.class);
                intent.putExtra(AndroidConstantes.SOUS_TACHE_ID, String.valueOf(taches.get(pos).getId()));
                intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projectId));
                assignMember.startActivity(intent);
                assignMember.finish();
            }
        });

        return convertView;
    }
}
