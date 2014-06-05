package com.agil_gator_nf28.BddInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.User.ConnectedUser;
import com.agil_gator_nf28.User.User;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import java.util.ArrayList;
import java.util.List;

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

    public User getUserById(int id) {
        String query = "SELECT * FROM " + AndroidConstantes.TABLE_USER
                + " WHERE " + AndroidConstantes.COL_USER_ID + " = " + id + ";";
        Cursor c = bdd.rawQuery(query, null);

        return cursorToUser(c);
    }

    public List<User> getListUserOfAProject(int projectId) {
        String query = "SELECT " + AndroidConstantes.TABLE_USER + "." + AndroidConstantes.COL_USER_ID + ", " + AndroidConstantes.COL_USER_NAME + ", " + AndroidConstantes.COL_USER_PRENOM + ", " + AndroidConstantes.COL_USER_EMAIL
                + " FROM " + AndroidConstantes.TABLE_USER + " INNER JOIN " + AndroidConstantes.TABLE_USER_PROJET + " ON " + AndroidConstantes.COL_USER_PROJET_USER + " = " + AndroidConstantes.COL_USER_ID
                + " WHERE " + AndroidConstantes.COL_USER_PROJET_PROJET + " = " + projectId + ";";
        Cursor c = bdd.rawQuery(query, null);

        return cursorToUsers(c);
    }

    private List<User> cursorToUsers(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return new ArrayList<User>();

        List<User> users = new ArrayList<User>();

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un projet
        while(!c.isAfterLast()) {
            User user = new User();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            user.setId(c.getInt(NUM_COL_ID));
            user.setName(c.getString(NUM_COL_NAME));
            user.setFirstname(c.getString(NUM_COL_FIRST_NAME));
            user.setEmail(c.getString(NUM_COL_EMAIL));

            users.add(user);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        //On retourne le projet
        return users;
    }

    public List<User> getListUserOfNotAProject(int projectId) {
        List<User> allUsers = new ArrayList<User>();
        String query = "SELECT * FROM " + AndroidConstantes.TABLE_USER + ";";
        Cursor c = bdd.rawQuery(query, null);

        allUsers = cursorToUsers(c);

        List<User> halfUsers = new ArrayList<User>();
        query = "SELECT " + AndroidConstantes.TABLE_USER + "." + AndroidConstantes.COL_USER_ID + ", " + AndroidConstantes.COL_USER_EMAIL + ", " + AndroidConstantes.COL_USER_NAME + ", " + AndroidConstantes.COL_USER_PRENOM
                + " FROM " + AndroidConstantes.TABLE_USER + " INNER JOIN " + AndroidConstantes.TABLE_USER_PROJET + " ON " + AndroidConstantes.COL_USER_PROJET_USER + " = " + AndroidConstantes.TABLE_USER + "." + AndroidConstantes.COL_USER_ID
                + " WHERE " + AndroidConstantes.COL_USER_PROJET_PROJET + " = " + projectId + ";";
        c = bdd.rawQuery(query, null);

        halfUsers = cursorToUsers(c);
        List<User> finalUsers = new ArrayList<User>();
        boolean ok = true;
        for (User user : halfUsers) {
            System.out.println(user.getEmail());
            for(User user2 : allUsers) {
                System.out.println(user2.getEmail());
                if (user.getEmail().equals(user2.getEmail())) {
                    ok = false;
                }
            }
            if (ok) {
                finalUsers.add(user);
            }
            ok = true;
        }

        return finalUsers;
    }
}
