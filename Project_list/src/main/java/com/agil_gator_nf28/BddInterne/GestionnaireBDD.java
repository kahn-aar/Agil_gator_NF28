package com.agil_gator_nf28.BddInterne;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nicolas on 17/05/14.
 */
public interface GestionnaireBDD {

    public void open();
    public void close();
    public SQLiteDatabase getBDD();

}
