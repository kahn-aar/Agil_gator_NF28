package com.agil_gator_nf28.Taches;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agil_gator_nf28.Listeners.TacheGridListener;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.agil_gator.AddSubTask;
import com.agil_gator_nf28.agil_gator.DescriptifTaskActivity;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapteur pour les taches, dans la page du projet
 *
 * Created by Nicolas on 15/05/14.
 */
public class TacheAdapter extends ArrayAdapter<Tache> {

    private List<Tache> taches;
    private LayoutInflater inflater;
    private Context context;
    private Page_projet page_project;
    private int Position;


    public TacheAdapter(Page_projet page_project, int view, Context context, List<Tache> taches) {
        super(context,view,taches);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.taches = taches == null ? new ArrayList<Tache>() : taches;
        this.page_project = page_project;
    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @Override
    public Tache getItem(int position) {
        return taches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getposition (){
        return Position;
    }

    private class ViewHolder {
        TextView nom;
        TextView priorite;
        TextView difficulte;
        TextView notifs;

        GridView aFaireGrid;

        LinearLayout tacheLayout;

        GridView enCoursGrid;
        GridView aRelireGrid;
        GridView doneGrid;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Position = position;
        final int pos = position;

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
        holder.difficulte.setText("difficulté = " + taches.get(position).getDifficulte());
        holder.notifs.setText("" + taches.get(position).getNotifications());

        // On va créer les sous taches liés à la tache
        holder.aFaireGrid = (GridView)convertView.findViewById(R.id.gridAFaire);

        holder.tacheLayout = (LinearLayout)convertView.findViewById(R.id.tache);

        //on lance la page de description lorsque click sur tache

        holder.tacheLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_project, DescriptifTaskActivity.class);
                intent.putExtra(AndroidConstantes.TACHE_ID, taches.get(pos).getId());
                intent.putExtra(AndroidConstantes.TASK_DESC_FROM, AndroidConstantes.TASK_DESC_FROM_LIST);
                page_project.startActivity(intent);
            }
        }));

        SousTacheAdapter adapter1 = new SousTacheAdapter(context, R.id.gridAFaire, taches.get(position).getSousTachesAFaire());
        holder.aFaireGrid.setOnDragListener(new TacheGridListener(context, taches.get(position).getSousTachesAFaire(), adapter1, SousTacheEtat.AFAIRE));
        // On dit à la ListView de se remplir via cet adapter
        holder.aFaireGrid.setAdapter(adapter1);

        holder.enCoursGrid = (GridView)convertView.findViewById(R.id.gridenCours);
        SousTacheAdapter adapter2 = new SousTacheAdapter(context, R.id.gridenCours, taches.get(position).getSousTachesEnCours());
        holder.enCoursGrid.setOnDragListener(new TacheGridListener(context, taches.get(position).getSousTachesEnCours(), adapter2, SousTacheEtat.ENCOURS));

        // On dit à la ListView de se remplir via cet adapter
        holder.enCoursGrid.setAdapter(adapter2);

        holder.aRelireGrid = (GridView)convertView.findViewById(R.id.gridaRelire);
        SousTacheAdapter adapter3 = new SousTacheAdapter(context, R.id.gridaRelire, taches.get(position).getSousTachesARelire());
        holder.aRelireGrid.setOnDragListener(new TacheGridListener(context, taches.get(position).getSousTachesARelire(), adapter3, SousTacheEtat.ARELIRE));
        // On dit à la ListView de se remplir via cet adapter
        holder.aRelireGrid.setAdapter(adapter3);

        holder.doneGrid = (GridView)convertView.findViewById(R.id.griddone);
        SousTacheAdapter adapter4 = new SousTacheAdapter(context, R.id.griddone, taches.get(position).getSousTachesDone());
        holder.doneGrid.setOnDragListener(new TacheGridListener(context, taches.get(position).getSousTachesDone(), adapter4, SousTacheEtat.VALIDE));
        // On dit à la ListView de se remplir via cet adapter
        holder.doneGrid.setAdapter(adapter4);

        return convertView;
    }
}
