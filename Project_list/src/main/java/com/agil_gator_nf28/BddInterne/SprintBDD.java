package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * Created by Nicolas on 22/05/14.
 */
public class SprintBDD implements GestionnaireBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NUMBER = 1;
    private static final int NUM_COL_PROJET = 2;

    private SQLiteDatabase bdd;

    private MaBaseProjet maBaseSQLite;

    public SprintBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseProjet(context, AndroidConstantes.NOM_BDD, null, AndroidConstantes.VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertSprint(Sprint sprint, Projet projet) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        System.out.println("insert Sprint");

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_SPRINT_NUMBER, sprint.getNumber());
        values.put(AndroidConstantes.COL_SPRINT_PROJET, projet.getId());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_SPRINT, null, values);
    }

    public Sprint getLastSprintOfProject(Projet proj) {
        String query = "SELECT " + AndroidConstantes.COL_SPRINT_ID + ", " + AndroidConstantes.COL_SPRINT_NUMBER
                + " FROM " + AndroidConstantes.TABLE_SPRINT
                + " WHERE " + AndroidConstantes.COL_SPRINT_PROJET + "=" + proj.getId()
                + " ORDER BY " + AndroidConstantes.COL_SPRINT_NUMBER + " DESC;";
        Cursor c = bdd.rawQuery(query, null);

        return getFirstSprint(c);
    }

    public Sprint getFirstSprint(Cursor c) {
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();


        Sprint sprint = new Sprint();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        sprint.setId(c.getInt(NUM_COL_ID));
        sprint.setNumber(c.getInt(NUM_COL_NUMBER));


        c.close();

        return sprint;
    }
}
