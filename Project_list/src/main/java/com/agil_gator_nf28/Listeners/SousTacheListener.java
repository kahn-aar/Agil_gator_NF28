package com.agil_gator_nf28.Listeners;

import android.annotation.TargetApi;
import android.content.ClipData;
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
public class SousTacheListener implements View.OnLongClickListener {

    private SousTache sousTache;

    public SousTacheListener(SousTache sousTache) {
        this.sousTache = sousTache;
    }

    @Override
    public boolean onLongClick(View v) {
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData data = ClipData.newPlainText("id", String.valueOf(sousTache.getId()));
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, v, 0);
        v.setVisibility(View.INVISIBLE);

        return true;
    }
}
