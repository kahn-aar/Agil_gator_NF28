package com.agil_gator_nf28.agil_gator;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.agil_gator_nf28.SousTaches.SousTache;

/**
 * Fragment d'affichage du postit
 *
 * Created by Nicolas on 09/06/14.
 */
public class SousTacheFragment  extends DialogFragment {

    private class ViewHolder {
        TextView nom;
        TextView description;

    }
    private SousTache sousTache;

    public SousTacheFragment(SousTache sousTache) {
        this.sousTache = sousTache;
    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View postit = inflater.inflate(R.layout.post_it_layout_big, null);
        ViewHolder holderBig = new ViewHolder();
        holderBig.nom = (TextView)postit.findViewById(R.id.postitGrosTitre);
        holderBig.nom.setText(sousTache.getTitre());
        holderBig.description = (TextView)postit.findViewById(R.id.postitContenu);
        holderBig.description.setText(sousTache.getDescription());

        postit.setTag(holderBig);
        return postit;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
