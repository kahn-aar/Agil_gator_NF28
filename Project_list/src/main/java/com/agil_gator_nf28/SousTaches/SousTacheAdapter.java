package com.agil_gator_nf28.SousTaches;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agil_gator.R;

import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class SousTacheAdapter extends BaseAdapter{

    private List<SousTache> taches;
    private LayoutInflater inflater;

    public SousTacheAdapter(Context context, List<SousTache> taches) {
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

    public void addSousTache(SousTache tache) {
        taches.add(tache);
    }

    public void removeSousTache(int position) {
        taches.remove(position);
    }

    private class ViewHolder {
        TextView nom;

    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int pos = position;
        if(convertView == null) {
            holder = new ViewHolder();
            // On lie les éléments au fichier ligne_de_la_listview.xml
            convertView = inflater.inflate(R.layout.post_it_layout, null);
            // On lie les deux TextView déclarés précédemment à ceux du xml
            holder.nom = (TextView)convertView.findViewById(R.id.postitTitre);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // On défini ici le texte que doit contenir chacun des TextView
        // Le premier affichera le numéro de l'élément (numéro de ligne)
        holder.nom.setText(taches.get(position).getTitre());


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View postit = inflater.inflate(R.layout.post_it_layout_big, null);
                ViewHolder holderBig = new ViewHolder();
                holderBig.nom = (TextView)postit.findViewById(R.id.postitGrosTitre);
                holderBig.nom.setText(taches.get(pos).getTitre());
                postit.setTag(holderBig);
                System.out.println("Ok long click");
                postit.setVisibility(View.VISIBLE);

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return convertView;
    }
}
