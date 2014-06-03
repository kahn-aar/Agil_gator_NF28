package com.agil_gator_nf28.BddInterne;

import android.content.Context;

/**
 * Classe gérant la table des utilisateurs
 *
 * Created by Nicolas on 03/06/14.
 */
public class UserBDD extends GestionnaireBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_EMAIL = 1;
    private static final int NUM_COL_NAME = 2;
    private static final int NUM_COL_FIRST_NAME = 3;
    private static final int NUM_COL_PASSWORD = 4;

    public UserBDD(Context context) {
        super(context);
    }


}
