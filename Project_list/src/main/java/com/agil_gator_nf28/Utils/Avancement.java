package com.agil_gator_nf28.Utils;

/**
 * POJO d'avancement des tâches
 *
 * Created by Nicolas on 07/06/14.
 */
public class Avancement {
    private int nombre_total;
    private int nombre_fini;
    private int nombre_en_cours;

    public Avancement(int nombre_total, int nombre_fini, int nombre_en_cours) {
        this.nombre_total = nombre_total;
        this.nombre_fini = nombre_fini;
        this.nombre_en_cours = nombre_en_cours;
    }

    public int getNombre_total() {
        return nombre_total;
    }

    public void setNombre_total(int nombre_total) {
        this.nombre_total = nombre_total;
    }

    public int getNombre_fini() {
        return nombre_fini;
    }

    public void setNombre_fini(int nombre_fini) {
        this.nombre_fini = nombre_fini;
    }

    public int getNombre_en_cours() {
        return nombre_en_cours;
    }

    public void setNombre_en_cours(int nombre_en_cours) {
        this.nombre_en_cours = nombre_en_cours;
    }
}
