package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class Classification {

    private Association association;
    private int annee;
    private HashMap<Arbre, Integer> dicoVotes = new HashMap<>();
    private List<Arbre> listArbresNomines = new ArrayList<>();

    public Classification(Association assoc, int annee){
        this.association = assoc;
        this.annee = annee;
    }

    public int getAnnee(){
        return this.annee;
    }

    public List<Arbre> getListArbresNomines(){
        return this.listArbresNomines;
    }

    public void nomination(){
        List<Arbre> listVote;
        int nbVotes;
        for (Membre membre : association.getListeMembres()) {
            listVote = membre.getListArbresVotes();

            for (Arbre arbre : listVote) {
                this.dicoVotes.putIfAbsent(arbre, 0); // si un arbre n'a jamais été voté alors on l'ajoute dans le dico
                nbVotes = this.dicoVotes.get(arbre);
                this.dicoVotes.put(arbre, nbVotes + 1);
            }
        }

        HashMap<Arbre, Integer> dicoVotesCopie = dicoVotes;

        while (this.listArbresNomines.size() < 5 && !dicoVotesCopie.isEmpty()) {
            int valMax = 0;
            List<Arbre> listArbresMax = new ArrayList<>();
            for (Arbre arbre : dicoVotesCopie.keySet()) {
                if(valMax == dicoVotesCopie.get(arbre)){
                    listArbresMax.add(arbre);
                }
                else if (valMax < dicoVotesCopie.get(arbre)) {
                    valMax = dicoVotesCopie.get(arbre);
                    listArbresMax.clear();
                    listArbresMax.add(arbre);
                }
            }

            if(listArbresMax.size() > 1){
                for(int i = 1; i < listArbresMax.size(); i++){
                    for(int j = 0; j < i; j++){
                        if(listArbresMax.get(i).getCirconferenceEnCm() > listArbresMax.get(j).getCirconferenceEnCm()){
                            Arbre arbreJ = listArbresMax.get(j);
                            listArbresMax.set(j, listArbresMax.get(i));
                            listArbresMax.set(i, arbreJ);
                        }
                        else if(listArbresMax.get(i).getCirconferenceEnCm() == listArbresMax.get(j).getCirconferenceEnCm()){
                            if(listArbresMax.get(i).getHauteurEnM() >= listArbresMax.get(j).getHauteurEnM()){
                                Arbre arbreI = listArbresMax.get(i);
                                for(int k = i-1; k >= j; k--){
                                    listArbresMax.set(k+1, listArbresMax.get(k));
                                }
                                listArbresMax.set(j, arbreI);
                            }
                            else{
                                int p = 1;
                                while(listArbresMax.get(i).getCirconferenceEnCm() == listArbresMax.get(j + p).getCirconferenceEnCm()){
                                    if(listArbresMax.get(i).getHauteurEnM() >= listArbresMax.get(j+p).getHauteurEnM()){
                                        Arbre arbreI = listArbresMax.get(i);
                                        for(int k = i-1; k >= j+p; k--){
                                            listArbresMax.set(k+1, listArbresMax.get(k));
                                        }
                                        listArbresMax.set(j+p, arbreI);
                                        break;
                                    }
                                    p += 1;
                                }
                            }
                        }
                    }
                }
            }
            for (Arbre arbre : listArbresMax) {
                this.listArbresNomines.add(arbre);
                dicoVotesCopie.remove(arbre);
            }
        }
        if(this.listArbresNomines.size() > 5){
            this.listArbresNomines = this.listArbresNomines.subList(0, 4);
        }
    }

    public static void main(String[] args){

        Association assoc = new Association("eazf");
        Membre membre = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        Membre membre2 = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");

        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        Arbre.createArbre("C:\\Users\\emman\\OneDrive\\Bureau\\S6\\Java\\Projet\\les-arbres.csv", paris);


        // test de la classification
        Arbre a = Arbre.getDicoArbre().get(2003166);
        Arbre b = Arbre.getDicoArbre().get(298184); // cir = 105
        Arbre c = Arbre.getDicoArbre().get(280753); // cir = 60 ; h = 6
        Arbre d = Arbre.getDicoArbre().get(214468); // cir = 60 ; h = 7

        membre.vote(a, a, a, a, a, b, b, a, a, a, a, a, b, d, b, b);
        membre2.vote(a, c);
        //membre.getListArbresVotes();
        Classification classification = new Classification(assoc, 2020);
        classification.nomination();
        //System.out.println(classification.dicoVotes.get(b));
        System.out.println(classification.listArbresNomines);
    }

}
