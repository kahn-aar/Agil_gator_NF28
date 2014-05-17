package com.agil_gator_nf28.Taches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.agil_gator.R;

import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class TacheAdapter extends BaseAdapter {

    private List<Tache> taches;
    private LayoutInflater inflater;
    private Context context;

    public TacheAdapter(Context context, List<Tache> taches) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.taches = taches;
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
        TextView notifs;

        GridView aFaireGrid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.task_line_view, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.nom = (TextView)convertView.findViewById(R.id.nom_tache);
            holder.priorite = (TextView)convertView.findViewById(R.id.prio);
            holder.difficulte = (TextView)convertView.findViewById(R.id.diff);
            holder.notifs = (TextView)convertView.findViewById(R.id.notifs);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.nom.setText(taches.get(position).getNom());
        holder.priorite.setText("priorité = " + taches.get(position).getPriorite());
        holder.difficulte.setText("diffuculté = " + taches.get(position).getDifficulte());
        holder.notifs.setText("" + taches.get(position).getNotifications());
        // On va créer les sous taches liés à la tache
        holder.aFaireGrid = (GridView)convertView.findViewById(R.id.gridAFaire);
        SousTacheAdapter adapter = new SousTacheAdapter(context, taches.get(position).getSousTachesAFaire());

        // On dit à la ListView de se remplir via cet adapter
        holder.aFaireGrid.setAdapter(adapter);
        return convertView;
    }
}
