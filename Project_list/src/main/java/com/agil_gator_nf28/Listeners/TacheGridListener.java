package com.agil_gator_nf28.Listeners;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.agil_gator.R;

import java.util.List;

/**
 * Listener pour le drag and drop des sous tâches
 *
 * Created by Nicolas on 22/05/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TacheGridListener implements View.OnDragListener {

    private Context context;

    Drawable enterShape;
    Drawable normalShape;
    SousTacheEtat clicked;
    List<SousTache> taches;
    View vueTotale;
    Tache linkedTask;

    public TacheGridListener(Context context, List<SousTache> taches, Tache tache, SousTacheEtat etat, View vue) {
        this.context = context;
        this.clicked = etat;
        this.taches = taches;
        this.enterShape = context.getResources().getDrawable(R.drawable.shape_droptarget);
        this.normalShape = context.getResources().getDrawable(R.drawable.shape);
        this.vueTotale = vue;
        this.linkedTask = tache;
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
                    //v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    HorizontalScrollView mainScrollView = (HorizontalScrollView) vueTotale.findViewById(R.id.main_scroll_view);

                    int topOfDropZone = v.getRight();
                    int bottomOfDropZone = v.getLeft();

                    int scrollX = mainScrollView.getScrollX();
                    int getScrollViewWidth = mainScrollView.getMeasuredWidth();


                    if (topOfDropZone > (scrollX + getScrollViewWidth - 300))
                        mainScrollView.smoothScrollBy(30, 0);

                    if (bottomOfDropZone < (scrollX + 300))
                        mainScrollView.smoothScrollBy(-30, 0);

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    //On récupère la sous tâche qui a été déplacée
                    int id = Integer.valueOf(event.getClipData().getItemAt(0).getText().toString());
                    SousTacheEtat old = SousTacheEtat.valueOf(event.getClipData().getItemAt(1).getText().toString());
                    View view = (View) event.getLocalState();

                    SousTacheBDD sousTacheBDD = new SousTacheBDD(context);
                    sousTacheBDD.open();
                    //Mise a jour en base de données
                    SousTache selected = sousTacheBDD.getSousTacheFromId(id);
                    sousTacheBDD.close();

                    if (! clicked.equals(old) || selected.getTache().getId() != linkedTask.getId()) {
                        selected.setEtat(clicked);
                        sousTacheBDD.open();
                        //Mise a jour en base de données
                        sousTacheBDD.updateSousTacheEtat(id, clicked);
                        sousTacheBDD.close();

                        //Récupération des différentes vues

                        GridView owner = (GridView) view.getParent();
                        GridView container = (GridView) v;

                        if(SousTacheEtat.AFAIRE.equals(old) && SousTacheEtat.ENCOURS.equals(clicked)) {
                            selected.setEffecteur(ConnectedUser.getInstance().getConnectedUser());
                            sousTacheBDD.open();
                            //Mise a jour en base de données
                            sousTacheBDD.updateSousTacheEffecteur(selected);
                            sousTacheBDD.close();
                            System.out.println("Ok bdd");
                        }

                        if (selected.getTache().getId() != linkedTask.getId()) {
                            selected.setTache(linkedTask);
                            sousTacheBDD.open();
                            //Mise a jour en base de données
                            sousTacheBDD.updateSousTacheTache(linkedTask, selected);
                            sousTacheBDD.close();
                            System.out.println("Ok bdd2 wtf");
                        }
                        System.out.println("maj");

                        //Mise a jour de la vue
                        ((SousTacheAdapter )container.getAdapter()).addSousTache(selected);
                        ((SousTacheAdapter )container.getAdapter()).notifyDataSetChanged();
                        ((SousTacheAdapter )owner.getAdapter()).removeSousTacheId(id);
                        ((SousTacheAdapter )owner.getAdapter()).notifyDataSetChanged();
                        System.out.println("end maj");
                    }

                    //view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
                }
            return true;
            }

}
