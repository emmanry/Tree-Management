package com.projetjava.console;

import com.projetjava.*;
import com.projetjava.don.Donateur;
import com.projetjava.exception.DateException;
import com.projetjava.finance.Facture;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe qui affiche sur la console l'interface pour gérer une association
 */
public class AssociationConsole {

    /**
     * Association
     */
    private final Association association;

    /**
     * Buffer qui lit sur la console
     */
    private BufferedReader br;

    /**
     *
     * @param association les actions agirons sur cette association
     * @param br reader sur la console
     */
    public AssociationConsole(Association association, BufferedReader br){
        this.association = association;
        this.br = br;
    }

    /**
     * Menu de l'association
     * @return
     * @throws IOException
     */
    public Membre associationMenu() throws IOException {
        boolean state = false;
        ArrayList<Membre> membres = association.getListeMembres();
        Membre membre = null;

        System.out.println(association.getNom());
        while(!state) {
            displayMembre();
            System.out.println("'ad' pour ajouter un membre, 'supr' pour supprimer un membre,\n" +
                    " 'not' lire notification, 'don' ajouter donateur, 'donSupr' supprimer un donateur,\n" +
                    " 'fin' fin exercice budgétaire, 'paid' paiement facture, 'his' historique visites \n" +
                    " 's' solde compte, 'r' return");
            String s = br.readLine();
            try{
                //add membre
                switch (s) {
                    case "ad":
                        //on affiche une liste de potentiels membres
                        addMembre(association, br);
                        break;
                    case "s":
                        System.out.println("Solde : " + association.getSolde());
                        break;
                    case "supr":
                        System.out.println("\nEntrer l'id du membre à désinscrire");
                        s = br.readLine();
                        association.removeMembreById(Integer.parseInt(s));
                        System.out.println("Membre numéro " + s + " désinscrit");
                        break;
                    case "don":
                        //Ajoute donnateur
                        System.out.println("Donateur suggéré (entrer l'index) :");
                        ArrayList<Donateur> donateurs = generateDonateur();
                        for (int i = 0; i < donateurs.size(); i++) {
                            System.out.println(i + " : " + donateurs.get(i).getNom());
                        }
                        s = br.readLine();
                        association.addDonateur(donateurs.get(Integer.parseInt(s)));

                        break;
                    case "donSupr":
                        System.out.println("Entrer l'id pour supprimer un donateur");
                        for (int i = 0; i < association.getDonateur().size(); i++) {
                            System.out.println(i + " : " + association.getDonateur().get(i).getNom());
                        }
                        s = br.readLine();
                        association.removeDonateur(association.getDonateur().get(Integer.parseInt(s)));
                        break;
                    case "his":
                        System.out.println(association.getHistoriqueArbresVisites());
                        break;
                    case "fin":
                        RapportActivite activite = association.getExerciceBudgetaire().fin("Bonjour don svp");
                        System.out.println(activite + "\n");
                        break;
                    case "paid":
                        if (association.paiement(new Facture("Edf", "facture", 150.f))) {
                            System.out.println("Paiement réussi");
                        } else {
                            System.out.println("Paiement refusé");
                        }
                        break;
                    case "not":
                        // Affiche Notification
                        for (String notif : association.alertNotifications()) {
                            System.out.println(notif);
                        }
                        break;
                    default:
                        int index = Integer.parseInt(s);
                        if (index >= membres.size()) {
                            System.out.println("Ce numéro n'existe pas");
                            continue;
                        }
                        membre = membres.get(index);
                        state = true;
                        break;
                    case "r":
                        state = true;
                        break;
                }
            }catch (NumberFormatException e){
                System.err.println("Veuillez rentrer un nombre");
            } catch (DateException e) {
                System.err.println(e.getMessageErreur());
            }
        }
        return membre;
    }

    private void displayMembre(){
        ArrayList<Membre> membres = association.getListeMembres();
        for (int i = 0; i < membres.size(); i++) {
            System.out.println(String.format("index : %d, nom : %s ",i,membres.get(i).getNom()));
        }
    }

    private ArrayList<Donateur> generateDonateur() throws DateException {
        ArrayList<Donateur> donateur = new ArrayList<>();

        donateur.add(new Personne("p","p", 2000, 11, 27));
        donateur.add(new ServiceMairie("Service des ordures"));
        donateur.add(new Personne("v","v", 1999, 7, 10));
        return donateur;
    }

    private void addMembre(Association association, BufferedReader br) throws IOException, DateException {
        String nom;
        String prenom;
        String adresse;
        int annee;
        int jour;
        int mois;

        System.out.println("Nom : ");
        nom = br.readLine();
        System.out.println("Prénom : ");
        prenom = br.readLine();
        System.out.println("Année Naissance : ");
        annee = Integer.parseInt(br.readLine());
        System.out.println("Mois Naissance : ");
        mois = Integer.parseInt(br.readLine());
        System.out.println("Jour Naissance : ");
        jour = Integer.parseInt(br.readLine());
        System.out.println("Adresse : ");
        adresse = br.readLine();
        Calendar cal = Calendar.getInstance();

        new Membre(nom, prenom,association, annee, mois, jour,
                cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), adresse);
    }
}
