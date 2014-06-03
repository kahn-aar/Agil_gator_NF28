package com.agil_gator_nf28.BddInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.agil_gator_nf28.constantes.AndroidConstantes;

/**
 * SuperClasse de la base de données.
 * Est partagée par chaque classe gérant une table.
 *
 * Created by Nicolas on 17/05/14.
 */
public class GestionnaireBDD {

    protected SQLiteDatabase bdd;

    protected MaBaseProjet maBaseSQLite;

    public GestionnaireBDD(Context context){
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

}
