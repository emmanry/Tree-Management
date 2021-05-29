package com.projetjava.console;

import com.projetjava.*;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.finance.Facture;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AssociationConsole {

    private Association association;

    private BufferedReader br;

    public AssociationConsole(Association association, BufferedReader br){
        this.association = association;
        this.br = br;
    }

    public Membre associationMenu() throws IOException {
        boolean state = false;
        ArrayList<Membre> membres = association.getListeMembres();
        Membre membre = null;

        System.out.println(association.getNom());
        while(!state) {
            displayMembre();
            System.out.println("'ad' pour ajouter un membre, 'supr' pour supprimer un membre,\n" +
                    " 'not' lire notification, 'don' ajouter donateur, 'donSupr' supprimer un donateur,\n" +
                    " 'fin' fin exercice budgétaire, 'paid' paiement facture");
            String s = br.readLine();
            try{
                //add membre
                switch (s) {
                    case "ad":
                        //on affiche une liste de potentiel membre
                        addMembre(association, br);
                        break;
                    case "supr":
                        System.out.println("\nEntrer l'id du membre à désinscrire");
                        s = br.readLine();
                        association.removeMembreById(Integer.parseInt(s));
                        System.out.println("Membre numéro " + s + " abattue");
                        break;
                    case "don":
                        //Ajoute donnateur
                        System.out.println("Donateur suggéré");
                        ArrayList<Donateur> donateurs = generateDonateur();
                        for (int i = 0; i < donateurs.size(); i++) {
                            System.out.println(i + " : " + donateurs.get(i).getNom());
                        }
                        s = br.readLine();
                        association.addDonateur(donateurs.get(Integer.parseInt(s)));

                        break;
                    case "donSupr":
                        System.out.println("Entrée l'id pour supprimer un ");
                        for (int i = 0; i < association.getDonateur().size(); i++) {
                            System.out.println(i + " : " + association.getDonateur().get(i).getNom());
                        }
                        s = br.readLine();
                        association.removeDonateur(association.getDonateur().get(Integer.parseInt(s)));
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
                }
            }catch (NumberFormatException e){
                System.out.println("Veuillez rentrer un nombre");
            }
        }
        return membre;
    }

    private void displayMembre(){
        ArrayList<Membre> membres = association.getListeMembres();
        for (int i = 0; i < membres.size(); i++) {
            System.out.println(String.format("id : %d, nom : %s ",membres.get(i).getId(),membres.get(i).getNom()));
        }
    }

    private ArrayList<Donateur> generateDonateur(){
        ArrayList<Donateur> donateur = new ArrayList<>();

        donateur.add(new Personne("p","p", 2000, 11, 27));
        donateur.add(new ServiceMairie("Service des ordures"));
        donateur.add(new Personne("v","v", 1999, 7, 10));
        return donateur;
    }

    private void addMembre(Association association, BufferedReader br) throws IOException{
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
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        new Membre(nom, prenom,association, annee, mois, jour,
                cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH), adresse);
    }
}
