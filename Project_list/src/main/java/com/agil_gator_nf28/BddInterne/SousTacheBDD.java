package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.SousTaches.SousTacheEtat;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant la table des sous taches.
 *
 * Created by Nicolas on 17/05/14.
 */
public class SousTacheBDD extends GestionnaireBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_TITRE = 1;
    private static final int NUM_COL_ETAT = 2;
    private static final int NUM_COL_DESCRIPTION = 3;
    private static final int NUM_COL_TACHE = 5;
    private static final int NUM_COL_EFFECTEUR = 4;

    public SousTacheBDD(Context context){
        super(context);
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

    public long insertSousTache(SousTache sousTache, Tache tache) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        System.out.println("insert sous Tache");

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_SS_TACHE_NAME, sousTache.getTitre());
        values.put(AndroidConstantes.COL_SS_TACHE_ETAT, sousTache.getEtat().toString());
        values.put(AndroidConstantes.COL_SS_TACHE_TACHE, tache.getId());
        values.put(AndroidConstantes.COL_SS_TACHE_DESCRIPTION, sousTache.getDescription());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_SS_TACHE, null, values);
    }

    public List<SousTache> getSousTaches(Tache tache) {
        String query = "SELECT " + AndroidConstantes.COL_SS_TACHE_ID + ", " + AndroidConstantes.COL_SS_TACHE_NAME + ", " + AndroidConstantes.COL_SS_TACHE_ETAT + ", " + AndroidConstantes.COL_SS_TACHE_DESCRIPTION + ", " + AndroidConstantes.COL_SS_TACHE_USER
                + " FROM " + AndroidConstantes.TABLE_SS_TACHE
                 + " WHERE " + AndroidConstantes.COL_SS_TACHE_TACHE + "=" + tache.getId() + ";";
        Cursor c = bdd.rawQuery(query, null);


        return toList(c);
    }

    private List<SousTache> toList(Cursor c) {

        List<SousTache> sousTaches = new ArrayList<SousTache>();
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return sousTaches;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //On créé un SousTache
        while(!c.isAfterLast()) {
            SousTache sousTache = new SousTache();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            sousTache.setId(c.getInt(NUM_COL_ID));
            sousTache.setTitre(c.getString(NUM_COL_TITRE));
            sousTache.setEtat(SousTacheEtat.valueOf(c.getString(NUM_COL_ETAT)));
            sousTache.setDescription(c.getString(NUM_COL_DESCRIPTION));

            UserBDD userBDD = new UserBDD(context);
            userBDD.open();
            sousTache.setEffecteur(userBDD.getUserById(c.getInt(NUM_COL_EFFECTEUR)));
            userBDD.close();

            sousTaches.add(sousTache);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        return sousTaches;
    }

    public SousTache getSousTacheFromId(int id) {
        String query = "SELECT " + AndroidConstantes.COL_SS_TACHE_ID + ", " + AndroidConstantes.COL_SS_TACHE_NAME + ", " + AndroidConstantes.COL_SS_TACHE_DESCRIPTION + ", " + AndroidConstantes.COL_SS_TACHE_ETAT
                + " FROM " + AndroidConstantes.TABLE_SS_TACHE
                + " WHERE " + AndroidConstantes.COL_SS_TACHE_ID + "=" + id + ";";
        Cursor c = bdd.rawQuery(query, null);


        return toUnqiue(c);

    }

    private SousTache toUnqiue(Cursor c) {
        SousTache sousTache = new SousTache();
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return sousTache;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        sousTache.setId(c.getInt(NUM_COL_ID));
        sousTache.setTitre(c.getString(NUM_COL_TITRE));
        sousTache.setEtat(SousTacheEtat.valueOf(c.getString(NUM_COL_ETAT)));
        sousTache.setDescription(c.getString(NUM_COL_DESCRIPTION));


        //On ferme le cursor
        c.close();

        return sousTache;

    }

    public void updateSousTacheEtat(int id, SousTacheEtat clicked) {
        ContentValues values = new ContentValues();
        values.put(AndroidConstantes.COL_SS_TACHE_ETAT, clicked.name());
        bdd.update(AndroidConstantes.TABLE_SS_TACHE, values, AndroidConstantes.COL_SS_TACHE_ID + " = " +id, null);
    }

    public void updateSousTacheEffecteur(SousTache clicked) {
        ContentValues values = new ContentValues();
        values.put(AndroidConstantes.COL_SS_TACHE_USER, clicked.getEffecteur().getId());
        values.put(AndroidConstantes.COL_SS_TACHE_ETAT, clicked.getEtat().name());
        bdd.update(AndroidConstantes.TABLE_SS_TACHE, values, AndroidConstantes.COL_SS_TACHE_ID + " = " + clicked.getId(), null);
        System.out.println("User update = " + clicked.getEffecteur().getEmail());
    }
}
