package com.projetjava.console;

import com.projetjava.Arbre;
import com.projetjava.Membre;
import com.projetjava.exception.SoldeNegatifException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Classe qui affiche sur la console l'interface pour gérer un membre
 */
public class MembreConsole {
    /**
     * Membre
     */
    private final Membre membre;
    /**
     * Buffer qui lit sur la console
     */
    private final BufferedReader br;

    public MembreConsole(Membre membre, BufferedReader br){
        this.membre = membre;
        this.br = br;
    }

    /**
     * Menu des membres
     * @throws IOException
     */
    public void membreMenu() throws IOException {
        boolean state = false;


        while(!state){
            System.out.println(membre);

            System.out.println("Action possible : \n'p' payer la cotisation, 'd' demande visite, 'vis' visite d'arbre remarquable,\n" +
                    "'vote' voter pour un arbre remarquable, 'not' lire notification, 'r' return \n" +
                    "'cot' historique cotisation ");
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
                        if(a != null){
                            if(membre.demandeVisiteArbre(a)){
                                System.out.println("Demande envoyé");
                            }else{
                                System.err.println("La visite n'est pas autorisée");
                            }

                        }else{
                            System.out.println("Arbre n'existe pas");
                        }
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

                        try{
                            if(membre.visiteArbre(a, annee, mois, jour, contenu)){
                                System.out.println("Visite réussite");
                            }else{
                                System.err.println("Vous n'avez pas eu d'autorisation pour visiter cet arbre. Envoyez nous une demandeVisiteArbre.");
                            }
                        }catch (SoldeNegatifException e){
                            System.err.println("L'association ne peut pas encore financer votre visite. Attendez quelques temps.");
                        }

                        break;
                    case "vote":
                        System.out.println("Taper l'id de l'arbre pour le voter : ");
                        s = br.readLine();
                        a = Arbre.getDicoArbre().get(Integer.parseInt(s));
                        if(membre.vote(a)){
                            System.out.println("voté !");
                        }else{
                            System.err.println("Vous avez déjà voté pour " + a.toString());
                        }
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