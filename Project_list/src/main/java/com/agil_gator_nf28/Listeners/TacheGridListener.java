package com.agil_gator_nf28.Listeners;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.agil_gator.R;

import java.util.List;

/**
 * Created by Nicolas on 22/05/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TacheGridListener implements View.OnDragListener {

    private Context context;
    private SousTacheAdapter adapter;

    Drawable enterShape;
    Drawable normalShape;
    SousTache actualDragged;
    SousTacheEtat clicked;
    List<SousTache> taches;

    public TacheGridListener(Context context, List<SousTache> taches, SousTacheAdapter adapter, SousTacheEtat etat) {
        this.context = context;
        this.adapter = adapter;
        this.clicked = etat;
        this.taches = taches;
        this.enterShape = context.getResources().getDrawable(R.drawable.shape_droptarget);
        this.normalShape = context.getResources().getDrawable(R.drawable.shape);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    //On récupère la sous tâche qui a été déplacée
                    int id = Integer.valueOf(event.getClipData().getItemAt(0).getText().toString());
                    SousTacheEtat old = SousTacheEtat.valueOf(event.getClipData().getItemAt(1).getText().toString());
                    View view = (View) event.getLocalState();

                    if (! clicked.equals(old)) {
                        SousTacheBDD sousTacheBDD = new SousTacheBDD(context);
                        sousTacheBDD.open();
                        //Mise a jour en base de données
                        sousTacheBDD.updateSousTacheEtat(id, clicked);
                        SousTache selected = sousTacheBDD.getSousTacheFromId(id);
                        sousTacheBDD.close();

                        //Récupération des différentes vues

                        GridView owner = (GridView) view.getParent();
                        GridView container = (GridView) v;

                        //Mise a jour de la vue
                        ((SousTacheAdapter )container.getAdapter()).addSousTache(selected);
                        ((SousTacheAdapter )container.getAdapter()).notifyDataSetChanged();
                        ((SousTacheAdapter )owner.getAdapter()).removeSousTacheId(id);
                        ((SousTacheAdapter )owner.getAdapter()).notifyDataSetChanged();
                    }

                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
                }
            return true;
            }

}
