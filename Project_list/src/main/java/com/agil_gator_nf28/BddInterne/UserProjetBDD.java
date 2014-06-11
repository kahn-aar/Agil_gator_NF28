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

    public long insertProjet(User user, long projetId){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_USER_PROJET_USER, user.getId());
        values.put(AndroidConstantes.COL_USER_PROJET_PROJET, projetId);

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_USER_PROJET, null, values);
    }

    public int removeProjetUserWithID(int project_id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(AndroidConstantes.TABLE_USER_PROJET, AndroidConstantes.COL_USER_PROJET_PROJET + " = " +project_id, null);
    }

    public List<Projet> getProjetsFromUser(User connectedUser) {

        String query = "SELECT " + AndroidConstantes.COL_USER_PROJET_PROJET
                    +  " FROM " + AndroidConstantes.TABLE_USER_PROJET
                    + " WHERE " + AndroidConstantes.COL_USER_PROJET_USER + "=" + connectedUser.getId() + ";";
        Cursor c = bdd.rawQuery(query, null);
        return cursorToProjets(c);
    }

    private List<Projet> cursorToProjets(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return new ArrayList<Projet>();

        List<Projet> projets = new ArrayList<Projet>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un projet
        while(!c.isAfterLast()) {
            int id = c.getInt(0);

            ProjetBDD projetBDD = new ProjetBDD(this.context);
            projetBDD.open();
            Projet projet = projetBDD.getProjetById(id);
            projetBDD.close();

            projets.add(projet);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        //On retourne le projet
        return projets;
    }
}
