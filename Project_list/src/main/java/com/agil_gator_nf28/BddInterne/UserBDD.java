package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.constantes.AndroidConstantes;

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

    public long insertProjet(User user, String password){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        System.out.println("insert user");

        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(AndroidConstantes.COL_USER_EMAIL, user.getEmail());
        values.put(AndroidConstantes.COL_USER_NAME, user.getName());
        values.put(AndroidConstantes.COL_USER_PRENOM, user.getFirstname());
        values.put(AndroidConstantes.COL_USER_PASSWORD, password);

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(AndroidConstantes.TABLE_USER, null, values);
    }


    public boolean connexion(String email, String password) {
        // Si le user email existe
        String query = "SELECT * FROM " + AndroidConstantes.TABLE_USER
                    + " WHERE " + AndroidConstantes.COL_USER_EMAIL + " LIKE '" + email + "'"
                    + " AND " + AndroidConstantes.COL_USER_PASSWORD + " LIKE '" + password + "';";
        Cursor c = bdd.rawQuery(query, null);
        User user = cursorToUser(c);

        if (user != null) {
            ConnectedUser.getInstance().setConnectedUser(user);
            return true;
        }
        return false;
    }

    private User cursorToUser(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un user
        User user = new User();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        user.setId(c.getInt(NUM_COL_ID));
        user.setEmail(c.getString(NUM_COL_EMAIL));
        user.setName(c.getString(NUM_COL_NAME));
        user.setFirstname(c.getString(NUM_COL_FIRST_NAME));
        //On ferme le cursor
        c.close();

        //On retourne le user
        return user;
    }

}
