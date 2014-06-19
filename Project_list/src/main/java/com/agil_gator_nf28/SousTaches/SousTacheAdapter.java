package com.agil_gator_nf28.SousTaches;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agil_gator_nf28.Listeners.SousTacheListener;
import com.agil_gator_nf28.agil_gator.Page_projet;
import com.agil_gator_nf28.agil_gator.R;
import com.agil_gator_nf28.agil_gator.SousTacheFragment;

import java.util.List;

/**
 * Adapteur pour les sous tâches, générés dans l'adapteur des tâches
 *
 * Created by Nicolas on 15/05/14.
 */
public class SousTacheAdapter extends ArrayAdapter<SousTache> {

    private List<SousTache> taches;
    private LayoutInflater inflater;
    private Page_projet page_projet;

    public SousTacheAdapter(Context context, int view, List<SousTache> taches, Page_projet page_projet) {
        super(context, view, taches);
        inflater = LayoutInflater.from(context);
        this.taches = taches;
        this.page_projet = page_projet;
    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @Override
    public SousTache getItem(int position) {
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

    public void removeSousTache(SousTache sousTache) {
        taches.remove(sousTache);
    }

    public void removeSousTacheId(int id) {
        SousTache tacheToRemove = null;
        for(SousTache tache : taches) {
            if (tache.getId() == id) {
                tacheToRemove = tache;
            }
        }
        taches.remove(tacheToRemove);
    }

    private class ViewHolder {
        TextView nom;

    }

    private class ViewHolderDoing {
        TextView nom;
        TextView tag;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;

        switch(taches.get(position).getEtat()) {

            case AFAIRE:
                ViewHolder holder;
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

                holder.nom.setText(taches.get(position).getTitre());
                break;
            case ENCOURS:
            case ARELIRE:
            case VALIDE:
                ViewHolderDoing holderdoing;
                if(convertView == null) {
                    holderdoing = new ViewHolderDoing();
                    // On lie les éléments au fichier ligne_de_la_listview.xml
                    convertView = inflater.inflate(R.layout.post_it_doing_layout, null);
                    // On lie les deux TextView déclarés précédemment à ceux du xml
                    holderdoing.nom = (TextView)convertView.findViewById(R.id.postitTitre);
                    holderdoing.tag = (TextView)convertView.findViewById(R.id.postitTag);

                    convertView.setTag(holderdoing);
                } else {
                    holderdoing = (ViewHolderDoing) convertView.getTag();
                }

                holderdoing.nom.setText(taches.get(position).getTitre());
                if(taches.get(position).getEffecteur() != null) {
                    holderdoing.tag.setText(taches.get(position).getEffecteur().generateTag());
                }
                break;
        }


        convertView.setOnLongClickListener(new SousTacheListener(taches.get(position)));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = page_projet.getFragmentManager();
                SousTacheFragment newFragment = new SousTacheFragment(taches.get(pos));
                // The device is using a large layout, so show the fragment as a dialog
                newFragment.show(fragmentManager, "dialog");
            }
        });


        return convertView;
    }
}
