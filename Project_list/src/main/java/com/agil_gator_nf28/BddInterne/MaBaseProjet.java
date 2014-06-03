package com.agil_gator_nf28.BddInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * Classe de base de la base SQLite
 * Créée la base
 *
 * Created by Nicolas on 30/04/14.
 */
public class MaBaseProjet extends SQLiteOpenHelper {


    private static final String CREATE_BDD = "CREATE TABLE " + AndroidConstantes.TABLE_PROJET + " ("
            + AndroidConstantes.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_NAME + " TEXT NOT NULL, "
            + AndroidConstantes.COL_SUBTITLE + " TEXT NOT NULL, " + AndroidConstantes.COL_DESC + " TEXT,"
            + AndroidConstantes.COL_ADVANCED+" INTEGER);";

    private static final String CREATE_USER_TABLE = " CREATE TABLE " + AndroidConstantes.TABLE_USER + "("
            + AndroidConstantes.COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_USER_EMAIL + " TEXT PRIMARY KEY,"
            + AndroidConstantes.COL_USER_NAME + " TEXT NOT NULL,"
            + AndroidConstantes.COL_USER_PRENOM + " TEXT);";

    private static final String CREATE_SPRINT_TABLE =  "CREATE TABLE " + AndroidConstantes.TABLE_SPRINT + " ("
            + AndroidConstantes.COL_SPRINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_SPRINT_NUMBER + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_SPRINT_PROJET + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + AndroidConstantes.COL_SPRINT_PROJET + ") REFERENCES " + AndroidConstantes.TABLE_PROJET + "(" + AndroidConstantes.COL_ID + "))";

    private static final String CREATE_TACHE_TABLE = "CREATE TABLE " + AndroidConstantes.TABLE_TACHE + " ("
            + AndroidConstantes.COL_TACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_TACHE_NAME + " TEXT NOT NULL, "
            + AndroidConstantes.COL_TACHE_DESCRIPTION + " TEXT NOT NULL, "
            + AndroidConstantes.COL_TACHE_PRIORITE + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_TACHE_DIFFICULTE + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_TACHE_SPRINT + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + AndroidConstantes.COL_TACHE_SPRINT + ") REFERENCES " + AndroidConstantes.TABLE_PROJET + "(" + AndroidConstantes.COL_ID + "))";

    private static final String CREATE_SOUS_TACHE_TABLE = "CREATE TABLE " + AndroidConstantes.TABLE_SS_TACHE + " ("
            + AndroidConstantes.COL_SS_TACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_SS_TACHE_NAME + " TEXT NOT NULL, "
            + AndroidConstantes.COL_SS_TACHE_ETAT + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_SS_TACHE_TACHE + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_SS_TACHE_TACHE + " INTEGER, "
            + "FOREIGN KEY(" + AndroidConstantes.COL_SS_TACHE_TACHE + ") REFERENCES " + AndroidConstantes.TABLE_TACHE + "(" + AndroidConstantes.COL_TACHE_ID + ")"
            + "FOREIGN KEY(" + AndroidConstantes.COL_SS_TACHE_USER + ") REFERENCES " + AndroidConstantes.TABLE_USER + "(" + AndroidConstantes.COL_USER_ID + "))";

    private static final String CREATE_USER_PROJET_TABLE = "CREATE TABLE " + AndroidConstantes.TABLE_USER_PROJET + " ("
            + AndroidConstantes.COL_USER_PROJET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AndroidConstantes.COL_USER_PROJET_USER + " INTEGER NOT NULL, "
            + AndroidConstantes.COL_USER_PROJET_PROJET + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + AndroidConstantes.COL_USER_PROJET_USER + ") REFERENCES " + AndroidConstantes.TABLE_USER + "(" + AndroidConstantes.COL_USER_ID + ")"
            + "FOREIGN KEY(" + AndroidConstantes.COL_USER_PROJET_PROJET + ") REFERENCES " + AndroidConstantes.TABLE_PROJET + "(" + AndroidConstantes.COL_ID + "))";

    public MaBaseProjet(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        System.out.println("création de la bdd");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SPRINT_TABLE);
        db.execSQL(CREATE_TACHE_TABLE);
        db.execSQL(CREATE_SOUS_TACHE_TABLE);
        db.execSQL(CREATE_USER_PROJET_TABLE);
        System.out.println("création de la table");

        db.execSQL("INSERT INTO table_user values (1, \"nicolas.martinr@gmail.com\", \"Nicolas\", \"Martin\");");

        db.execSQL("INSERT INTO table_projet values (1, \"agilgator\", \"le dieu des agiles\", \"application android\", 0);");

        db.execSQL("INSERT INTO table_sprint values (1, 1, 1);");

        db.execSQL("INSERT INTO table_tache values (1, \"application\", \"creer l'application\", 900, 10, 1);");

        db.execSQL("INSERT INTO table_tache values (2, \"SMA\", \"Créer le serveur\", 800, 7, 1);");

        db.execSQL("INSERT INTO table_sous_tache values (1, \"vue\", \"" + SousTacheEtat.ENCOURS + "\", 1, 1);");

        db.execSQL("INSERT INTO table_sous_tache values (2, \"agent\", \"" + SousTacheEtat.AFAIRE + "\", null, 1);");

        db.execSQL("INSERT INTO table_sous_tache values (3, \"agent\", \"" + SousTacheEtat.ENCOURS + "\", 2);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0

        db.execSQL("DROP TABLE " + AndroidConstantes.TABLE_SS_TACHE + ";");
        db.execSQL("DROP TABLE " + AndroidConstantes.TABLE_TACHE + ";");
        db.execSQL("DROP TABLE " + AndroidConstantes.TABLE_SPRINT + ";");
        db.execSQL("DROP TABLE " + AndroidConstantes.TABLE_PROJET + ";");
        db.execSQL("DROP TABLE " + AndroidConstantes.TABLE_USER + ";");
        onCreate(db);
    }

}
