package com.agil_gator_nf28.Listeners;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheAdapter;
import com.agil_gator_nf28.agil_gator.R;

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

    public TacheGridListener(Context context, SousTacheAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        this.enterShape = context.getResources().getDrawable(R.drawable.shape_droptarget);
        this.normalShape = context.getResources().getDrawable(R.drawable.shape);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    GridView container = (GridView) v;
                    long lg = container.getSelectedItemId();
                    System.out.println(lg + " = selectedID");
                    System.out.println(container.getSelectedItem());
                    adapter.removeSousTache(0);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    GridView owner = (GridView) view.getParent();
                    container = (GridView) v;
                    adapter.addSousTache((SousTache )adapter.getItem(0));

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
