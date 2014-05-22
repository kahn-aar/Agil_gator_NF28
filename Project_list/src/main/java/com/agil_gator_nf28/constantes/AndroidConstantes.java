package com.agil_gator_nf28.constantes;

/**
 * Created by Nicolas on 28/04/14.
 */
public class AndroidConstantes {

    public static String EXTRA_TITLE_NEW_PROJECT = "titre_nouveau_projet";
    public static String EXTRA_SUB_NEW_PROJECT = "sub_nouveau_projet";
    public static String EXTRA_DESCRIPTION_NEW_PROJECT = "description_nouveau_projet";

    //BDD
    public static final String NOM_BDD = "agilgator2.db";
    public static final int VERSION_BDD = 5;

    //Table Projet
    public static final String TABLE_PROJET = "table_projet";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_SUBTITLE = "SubTitle";
    public static final String COL_DESC = "description";

    //Table Sprint
    public static final String TABLE_SPRINT = "table_sprint";
    public static final String COL_SPRINT_ID = "ID";
    public static final String COL_SPRINT_NUMBER = "numero";
    public static final String COL_SPRINT_PROJET = "projet";

    //Table Tache
    public static final String TABLE_TACHE = "table_tache";
    public static final String COL_TACHE_ID = "ID";
    public static final String COL_TACHE_NAME = "Name";
    public static final String COL_TACHE_PRIORITE = "Priorite";
    public static final String COL_TACHE_DIFFICULTE = "Difficulte";
    public static final String COL_TACHE_SPRINT = "sprint";

    //Table SousTache
    public static final String TABLE_SS_TACHE = "table_sous_tache";
    public static final String COL_SS_TACHE_ID = "ID";
    public static final String COL_SS_TACHE_NAME = "Name";
    public static final String COL_SS_TACHE_ETAT = "Etat";
    public static final String COL_SS_TACHE_TACHE = "Tache";

}
