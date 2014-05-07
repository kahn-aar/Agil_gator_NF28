package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 30/04/14.
 */
public class ProjetBDD {

    private static final int VERSION_BDD = 3;

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ISBN = 1;
    private static final int NUM_COL_TITRE = 2;

    private SQLiteDatabase bdd;

    private MaBaseProjet maBaseSQLite;

    public ProjetBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseProjet(context, AndroidConstantes.NOM_BDD, null, VERSION_BDD);
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

    public long insertProjet(Projet projet){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        System.out.println("insert projet");

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_NAME, projet.getName());
        values.put(AndroidConstantes.COL_SUBTITLE, projet.getSubTitle());
        values.put(AndroidConstantes.COL_DESC, projet.getDescription());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_PROJET, null, values);
    }

    public int updateProjet(int id, Projet projet){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(AndroidConstantes.COL_NAME, projet.getName());
        values.put(AndroidConstantes.COL_SUBTITLE, projet.getSubTitle());
        return bdd.update(AndroidConstantes.TABLE_PROJET, values, AndroidConstantes.COL_ID + " = " +id, null);
    }

    public int removeLivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(AndroidConstantes.TABLE_PROJET, AndroidConstantes.COL_ID + " = " +id, null);
    }

    public Projet getLivreWithTitre(String titre){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(AndroidConstantes.TABLE_PROJET, new String[] {AndroidConstantes.COL_ID, AndroidConstantes.COL_NAME, AndroidConstantes.COL_SUBTITLE}, AndroidConstantes.COL_NAME + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToLivre(c);
    }

    public List<Projet> getProjets() {
        Cursor c = bdd.query(AndroidConstantes.TABLE_PROJET, new String[] {AndroidConstantes.COL_ID, AndroidConstantes.COL_NAME, AndroidConstantes.COL_SUBTITLE}, null, null, null, null, null);
        return cursorToLivres(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private List<Projet> cursorToLivres(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return new ArrayList<Projet>();

        List<Projet> projets = new ArrayList<Projet>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        while(!c.isAfterLast()) {
            Projet projet = new Projet();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            projet.setId(c.getInt(NUM_COL_ID));
            projet.setName(c.getString(NUM_COL_ISBN));
            projet.setSubTitle(c.getString(NUM_COL_TITRE));

            projets.add(projet);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return projets;
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Projet cursorToLivre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Projet projet = new Projet();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        projet.setId(c.getInt(NUM_COL_ID));
        projet.setName(c.getString(NUM_COL_ISBN));
        projet.setSubTitle(c.getString(NUM_COL_TITRE));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return projet;
    }
}
