package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant la table des projets
 *
 * Created by Nicolas on 30/04/14.
 */
public class ProjetBDD extends GestionnaireBDD{

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ISBN = 1;
    private static final int NUM_COL_TITRE = 2;
    private static final int NUM_COL_DESCRIPTION = 3;
    private static final int NUM_COL_CHEF = 4;

    public ProjetBDD(Context context) {
        super(context);
    }


    public long insertProjet(Projet projet){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_NAME, projet.getTitle());
        values.put(AndroidConstantes.COL_SUBTITLE, projet.getSubTitle());
        values.put(AndroidConstantes.COL_DESC, projet.getDescription());
        values.put(AndroidConstantes.COL_CHEF, projet.getChef().getId());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_PROJET, null, values);
    }

    public int updateProjet(int id, Projet projet){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(AndroidConstantes.COL_NAME, projet.getTitle());
        values.put(AndroidConstantes.COL_SUBTITLE, projet.getSubTitle());
        values.put(AndroidConstantes.COL_DESC, projet.getDescription());
        values.put(AndroidConstantes.COL_CHEF, projet.getChef().getId());
        return bdd.update(AndroidConstantes.TABLE_PROJET, values, AndroidConstantes.COL_ID + " = " +id, null);
    }

    public int removeProjetWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(AndroidConstantes.TABLE_PROJET, AndroidConstantes.COL_ID + " = " +id, null);
    }

    public Projet getProjectWithId(int id){
        //selectionne project avec ID
       // Cursor c = bdd.query(AndroidConstantes.TABLE_PROJET, new String[]{AndroidConstantes.COL_ID, AndroidConstantes.COL_NAME, AndroidConstantes.COL_SUBTITLE}, AndroidConstantes.COL_ID + " = " + id, null, null, null, null);
        Cursor c = bdd.rawQuery("SELECT * FROM "+AndroidConstantes.TABLE_PROJET+" WHERE "+AndroidConstantes.COL_ID+" = '"+id+"';",null);

        return cursorToProjet(c);
    }

    public Projet getProjetById(int id) {
        String query = "SELECT * FROM " + AndroidConstantes.TABLE_PROJET + " WHERE " + AndroidConstantes.COL_ID + "=" + id + ";";
        Cursor c = bdd.rawQuery(query, null);
        return cursorToProjet(c);
    }

    public List<Projet> getProjets() {
        //Cursor c = bdd.query(AndroidConstantes.TABLE_PROJET, new String[] {AndroidConstantes.COL_ID, AndroidConstantes.COL_NAME, AndroidConstantes.COL_SUBTITLE}, null, null, null, null, null);
        Cursor c = bdd.rawQuery("SELECT * FROM "+AndroidConstantes.TABLE_PROJET+";",null);
        return cursorToProjets(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private List<Projet> cursorToProjets(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return new ArrayList<Projet>();

        List<Projet> projets = new ArrayList<Projet>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un projet
        while(!c.isAfterLast()) {
            Projet projet = new Projet();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            projet.setId(c.getInt(NUM_COL_ID));
            projet.setTitle(c.getString(NUM_COL_ISBN));
            projet.setSubTitle(c.getString(NUM_COL_TITRE));
            projet.setDescription(c.getString(NUM_COL_DESCRIPTION));

            projets.add(projet);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        //On retourne le projet
        return projets;
    }

    //Cette méthode permet de convertir un cursor en un projet
    private Projet cursorToProjet(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un projet
        Projet projet = new Projet();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        projet.setId(c.getInt(NUM_COL_ID));
        projet.setTitle(c.getString(NUM_COL_ISBN));
        projet.setSubTitle(c.getString(NUM_COL_TITRE));
        projet.setDescription(c.getString(NUM_COL_DESCRIPTION));

        int chefID = c.getInt(NUM_COL_CHEF);
        UserBDD userBDD = new UserBDD(context);
        userBDD.open();
        projet.setChef(userBDD.getUserById(chefID));
        userBDD.close();


        //On ferme le cursor
        c.close();

        //On retourne le projet
        return projet;
    }

    public List<Projet> getProjetsFromUser(User connectedUser) {
        return null;
    }
}
