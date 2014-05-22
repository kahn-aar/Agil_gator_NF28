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
public class SousTacheListener implements View.OnDragListener {

    private SousTache sousTache;

    public SousTacheListener(SousTache sousTache) {
        this.sousTache = sousTache;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                //sousTache.getProjet();
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }
        return true;
    }

}
