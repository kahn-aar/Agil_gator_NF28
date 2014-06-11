package com.agil_gator_nf28.Listeners;

import android.content.ClipData;
import android.view.View;

import com.agil_gator_nf28.SousTaches.SousTache;

/**
 * Created by Nicolas on 22/05/14.
 */
public class SousTacheListener implements View.OnLongClickListener {

    private SousTache sousTache;

    public SousTacheListener(SousTache sousTache) {
        this.sousTache = sousTache;
    }

    @Override
    public boolean onLongClick(View v) {
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData data = ClipData.newPlainText("id", String.valueOf(sousTache.getId()));
        data.addItem(new ClipData.Item( sousTache.getEtat().name()));
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, v, 0);
        v.setVisibility(View.INVISIBLE);

        return true;
    }
}
