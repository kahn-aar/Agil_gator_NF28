package com.agil_gator_nf28.BddInterne;

import android.content.Context;

/**
 * Classe gérant la table user-projet
 *
 * Created by Nicolas on 03/06/14.
 */
public class UserProjetBDD extends GestionnaireBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_USER = 1;
    private static final int NUM_COL_PROJET = 2;

    public UserProjetBDD(Context context) {
        super(context);
    }

}
