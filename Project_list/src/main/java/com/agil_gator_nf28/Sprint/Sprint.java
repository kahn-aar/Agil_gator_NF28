package com.agil_gator_nf28.Sprint;

import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Taches.Tache;

import java.util.List;

/**
 * Created by Nicolas on 15/05/14.
 */
public class Sprint {

    private List<Tache> taches;

    public Sprint(List<Tache> taches) {
        this.taches = taches;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }
}
