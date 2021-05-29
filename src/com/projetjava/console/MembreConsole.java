package com.projetjava.console;

import com.projetjava.Arbre;
import com.projetjava.Membre;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MembreConsole {

    private Membre membre;

    private BufferedReader br;

    public MembreConsole(Membre membre, BufferedReader br){
        this.membre = membre;
        this.br = br;
    }
    
    public void membreMenu() throws IOException {
        boolean state = false;

        System.out.println(membre);

        System.out.println("Action possible : \n'p' payer la cotisation, 'd' demande visite, 'vis' visite d'arbre remarquable,\n" +
                "'vote' voter pour un arbre remarquable, 'not' lire notification, 'r' return \n" +
                "'cot' historique cotisation ");
        while(!state){
            String s = br.readLine();
            try{

                switch (s) {
                    //payer cotisation
                    case "p":
                        membre.cotiser(membre.getAssociation().getExerciceBudgetaire().getAnnee());
                        System.out.println("Cotisé!");
                        break;
                    case "d":
                        System.out.println(membre.getAssociation().getHistoriqueArbresVisites());
                        System.out.println("Taper l'id de l'arbre à visité : ");
                        s = br.readLine();
                        Arbre a = Arbre.getDicoArbre().get(Integer.parseInt(s));
                        membre.demandeVisiteArbre(a);
                        break;
                    case "vis":
                        System.out.println("Taper l'id de l'arbre à visité : ");
                        s = br.readLine();
                        a = Arbre.getDicoArbre().get(Integer.parseInt(s));
                        String contenu;
                        int annee;
                        int jour;
                        int mois;
                        System.out.println("Compte rendu écrit : ");
                        contenu = br.readLine();
                        System.out.println("Année visite : ");
                        annee = Integer.parseInt(br.readLine());
                        System.out.println("Mois visite : ");
                        mois = Integer.parseInt(br.readLine());
                        System.out.println("Jour visite : ");
                        jour = Integer.parseInt(br.readLine());
                        membre.visiteArbre(a, annee, mois, jour, contenu);
                        break;
                    case "vote":
                        System.out.println("Taper l'id de l'arbre pour le voter : ");
                        s = br.readLine();
                        a = Arbre.getDicoArbre().get(Integer.parseInt(s));
                        membre.vote(a);
                        System.out.println("voté !");
                        break;
                    case "not":
                        // Affiche Notification
                        List<String> membres = membre.alertNotifications();
                        if (membres.size() == 0){
                            System.out.println("Aucune Notification");
                        }
                        for (String notif : membres) {
                            System.out.println(notif);
                        }
                        break;
                    case "cot":
                        for(Map.Entry<Integer,Double> cotisation :membre.getCotisations().entrySet()){
                            System.out.println("annee " + cotisation.getKey() + " montant " + cotisation.getValue());
                        }
                        break;
                    case "r":
                        state = true;
                        break;
                }
                System.out.println();
            }catch (NumberFormatException e){
                System.out.println("Veuillez rentrer un nombre");
            }
        }
    }
}
