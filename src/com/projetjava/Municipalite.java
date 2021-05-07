package com.projetjava;

import java.util.ArrayList;

public class Municipalite {

    private ServiceMairie serviceMairie;

    public Municipalite(ServiceMairie serviceEspaceVert){
        ArrayList <Arbre> listeArbre = new ArrayList<Arbre>();
        this.serviceMairie = serviceEspaceVert;
    }

}
