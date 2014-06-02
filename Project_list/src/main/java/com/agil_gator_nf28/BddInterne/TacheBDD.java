package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant la table des taches
 *
 * Created by Nicolas on 17/05/14.
 */
public class TacheBDD implements GestionnaireBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_TITRE = 1;
    private static final int NUM_COL_DESCRIPTION = 2;
    private static final int NUM_COL_PRIO = 3;
    private static final int NUM_COL_DIFF = 4;
    private static final int NUM_COL_PROJET = 5;

    private SQLiteDatabase bdd;
    private Context context;
    private MaBaseProjet maBaseSQLite;

    public TacheBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseProjet(context, AndroidConstantes.NOM_BDD, null, AndroidConstantes.VERSION_BDD);
        this.context = context;
    }

    @Override
    public void open() {
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    @Override
    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    @Override
    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertTache(Tache tache, Sprint sprint) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        System.out.println("insert Tache");

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_TACHE_NAME, tache.getNom());
        values.put(AndroidConstantes.COL_TACHE_DESCRIPTION, tache.getDescription());
        values.put(AndroidConstantes.COL_TACHE_PRIORITE, tache.getPriorite());
        values.put(AndroidConstantes.COL_TACHE_DIFFICULTE, tache.getDifficulte());
        values.put(AndroidConstantes.COL_TACHE_SPRINT, sprint.getId());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_TACHE, null, values);
    }


    public Tache getTacheWithId(int id){
        //selectionne project avec ID
       Cursor c = bdd.rawQuery("SELECT * FROM "+AndroidConstantes.TABLE_TACHE+" WHERE "+AndroidConstantes.COL_TACHE_ID+" = '"+id+"';",null);

        return cursorToTache(c);
    }

    public int updateTache(int id, Tache tache){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(AndroidConstantes.COL_TACHE_NAME, tache.getNom());
        values.put(AndroidConstantes.COL_TACHE_PRIORITE, tache.getPriorite());
        values.put(AndroidConstantes.COL_TACHE_DIFFICULTE, tache.getDifficulte());
        values.put(AndroidConstantes.COL_TACHE_DESCRIPTION, tache.getDescription());
        return bdd.update(AndroidConstantes.TABLE_TACHE, values, AndroidConstantes.COL_TACHE_ID + " = " +id, null);
    }

    public List<Tache> getTaches(Sprint sprint) {

        String query = "SELECT " + AndroidConstantes.COL_TACHE_ID + ", " + AndroidConstantes.COL_TACHE_NAME + ", " + AndroidConstantes.COL_TACHE_PRIORITE + ", " + AndroidConstantes.COL_TACHE_DIFFICULTE
                + " FROM " + AndroidConstantes.TABLE_TACHE
                + " WHERE " + AndroidConstantes.COL_TACHE_SPRINT + "=" + sprint.getId() + ";";
        Cursor c = bdd.rawQuery(query, null);


        return toList(c);
    }

    public List<Tache> getTachesWithIdSprint(int id_sprint) {
       /* String query = "SELECT " + AndroidConstantes.COL_TACHE_ID + ", " + AndroidConstantes.COL_TACHE_NAME + ", " + AndroidConstantes.COL_TACHE_PRIORITE + ", " + AndroidConstantes.COL_TACHE_DIFFICULTE
                + " FROM " + AndroidConstantes.TABLE_TACHE
                + " WHERE " + AndroidConstantes.COL_TACHE_PROJET + "=" + id_project + ";";*/
        String query = "SELECT * FROM " + AndroidConstantes.TABLE_TACHE
                + " WHERE " + AndroidConstantes.COL_TACHE_SPRINT + "=" + id_sprint + ";";
        Cursor c = bdd.rawQuery(query, null);


        return toList(c);
    }

    private Tache cursorToTache(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        Tache tache = new Tache();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        tache.setId(c.getInt(NUM_COL_ID));
        tache.setNom(c.getString(NUM_COL_TITRE));
        tache.setDescription(c.getString(NUM_COL_DESCRIPTION));
        tache.setDifficulte(c.getInt(NUM_COL_DIFF));
        tache.setPriorite(c.getInt(NUM_COL_PRIO));

        c.close();

        return tache;
    }

    private List<Tache> toList(Cursor c) {

        List<Tache> taches = new ArrayList<Tache>();
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return taches;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //On créé un SousTache
        while(!c.isAfterLast()) {
            Tache tache = new Tache();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            tache.setId(c.getInt(NUM_COL_ID));
            tache.setNom(c.getString(NUM_COL_TITRE));
            tache.setPriorite(c.getInt(NUM_COL_PRIO));
            tache.setDifficulte(c.getInt(NUM_COL_DIFF));

            tache.setSousTaches(getSousTaches(tache));

            taches.add(tache);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        return taches;
    }

    private List<SousTache> getSousTaches(Tache tache) {
        List<SousTache> sub = new ArrayList<SousTache>();
        SousTacheBDD sousTacheBDD = new SousTacheBDD(context);
        sousTacheBDD.open();
        sub = sousTacheBDD.getSousTaches(tache);
        sousTacheBDD.close();
        return sub;
    }
}

