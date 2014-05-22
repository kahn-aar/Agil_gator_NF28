package com.agil_gator_nf28.Taches;

import android.annotation.TargetApi;
import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
=======
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.DragEvent;
>>>>>>> baf3b9e3622333380bd8bac4e116ffd7585dc5e5
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agil_gator_nf28.Listeners.TacheGridListener;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.agil_gator.DescriptifProjectActivity;
import com.agil_gator_nf28.agil_gator.DescriptifTaskActivity;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;

import java.security.acl.Group;
import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class TacheAdapter extends BaseAdapter {

    private List<Tache> taches;
    private LayoutInflater inflater;
    private Context context;
    private Page_projet page_project;

<<<<<<< HEAD
    public TacheAdapter(Page_projet page_projet, Context context, List<Tache> taches) {
=======

    public TacheAdapter(Context context, List<Tache> taches) {
>>>>>>> baf3b9e3622333380bd8bac4e116ffd7585dc5e5
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.taches = taches;
        this.page_project = page_projet;
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
<<<<<<< HEAD
        LinearLayout tacheLayout;

=======
        GridView enCoursGrid;
        GridView aRelireGrid;
        GridView doneGrid;
>>>>>>> baf3b9e3622333380bd8bac4e116ffd7585dc5e5
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

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
<<<<<<< HEAD
        holder.tacheLayout = (LinearLayout)convertView.findViewById(R.id.tache);

        SousTacheAdapter adapter = new SousTacheAdapter(context, taches.get(position).getSousTachesAFaire());

        holder.tacheLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page_project, DescriptifTaskActivity.class);
                intent.putExtra("ID_TACHE", taches.get(pos).getId());
                page_project.startActivity(intent);
            }
        }));

=======
        SousTacheAdapter adapter1 = new SousTacheAdapter(context, taches.get(position).getSousTachesAFaire());
        holder.aFaireGrid.setOnDragListener(new TacheGridListener(context, adapter1));
        // On dit à la ListView de se remplir via cet adapter
        holder.aFaireGrid.setAdapter(adapter1);

        holder.enCoursGrid = (GridView)convertView.findViewById(R.id.gridenCours);
        SousTacheAdapter adapter2 = new SousTacheAdapter(context, taches.get(position).getSousTachesEnCours());
        holder.enCoursGrid.setOnDragListener(new TacheGridListener(context, adapter2));
>>>>>>> baf3b9e3622333380bd8bac4e116ffd7585dc5e5
        // On dit à la ListView de se remplir via cet adapter
        holder.enCoursGrid.setAdapter(adapter2);

        holder.aRelireGrid = (GridView)convertView.findViewById(R.id.gridaRelire);
        SousTacheAdapter adapter3 = new SousTacheAdapter(context, taches.get(position).getSousTachesARelire());
        holder.aRelireGrid.setOnDragListener(new TacheGridListener(context, adapter3));
        // On dit à la ListView de se remplir via cet adapter
        holder.aRelireGrid.setAdapter(adapter3);

        holder.doneGrid = (GridView)convertView.findViewById(R.id.griddone);
        SousTacheAdapter adapter4 = new SousTacheAdapter(context, taches.get(position).getSousTachesDone());
        holder.doneGrid.setOnDragListener(new TacheGridListener(context, adapter4));
        // On dit à la ListView de se remplir via cet adapter
        holder.doneGrid.setAdapter(adapter4);

        return convertView;
    }
}
