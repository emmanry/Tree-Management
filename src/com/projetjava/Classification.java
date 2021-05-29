package com.projetjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classification {

    private Association association;
    private int annee;
    private HashMap<Arbre, Integer> dicoVotes = new HashMap<>();
    private List<Arbre> listeArbresNomines = new ArrayList<>();

    public Classification(Association assoc, int annee){
        this.association = assoc;
        this.annee = annee;
    }

    public Association getAssociation(){
        return this.association;
    }

    public int getAnnee(){
        return this.annee;
    }

    public List<Arbre> getListeArbresNomines(){
        return this.listeArbresNomines;
    }

    public HashMap<Arbre, Integer> getDicoVotes(){
        return this.dicoVotes;
    }

    /**
     * Remplir la listeArbresNomines des 5 Arbre les plus votés
     * en cas d'égalité on priviligie les plus grandes circonférences puis les plus hauts
     */
    public void nomination(){
        List<Arbre> listeVote;
        int nbVotes;
        for (Membre membre : association.getListeMembres()) {
            listeVote = membre.getListArbresVotes();

            for (Arbre arbre : listeVote) {
                this.dicoVotes.putIfAbsent(arbre, 0); // si un arbre n'a jamais été voté alors on l'ajoute dans le dico
                nbVotes = this.dicoVotes.get(arbre);
                this.dicoVotes.put(arbre, nbVotes + 1);
            }
        }

        // Faire une copie profonde du dicoVotes
        HashMap<Arbre, Integer> dicoVotesCopie = new HashMap<>();
        for (Arbre arbre : this.dicoVotes.keySet()){
            dicoVotesCopie.put(arbre, this.dicoVotes.get(arbre));
        }

        while (this.listeArbresNomines.size() < 5 && !dicoVotesCopie.isEmpty()) {
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
                this.listeArbresNomines.add(arbre);
                dicoVotesCopie.remove(arbre);
            }
        }
        if(this.listeArbresNomines.size() > 5){
            this.listeArbresNomines = this.listeArbresNomines.subList(0, 5);
        }
    }


}
