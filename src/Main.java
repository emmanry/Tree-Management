import com.projetjava.*;
import com.projetjava.console.AssociationConsole;
import com.projetjava.console.MembreConsole;
import com.projetjava.console.MunicipaliteConsole;
import com.projetjava.finance.Facture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;


public class Main {
    public static void main(String[] args) {
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis, new ArrayList<>());
        Association assoc = new Association("asso", paris, 1000, 50, 5, 30);
        Arbre.createArbre("data/les-arbres.csv");

        President membre = new President("NOURRY", "Emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        Membre membre2 = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");

        Personne p = new Personne("q", "p", 1999, 1, 22);
        serviceParis.addNotifiable(p);

        paris.removeArbreById(282008);

        /**
         * Test de la classification des arbres votés
         */
        Arbre a = Arbre.getDicoArbre().get(2003166);// cir = 135
        Arbre b = Arbre.getDicoArbre().get(298184); // cir = 105
        Arbre c = Arbre.getDicoArbre().get(280753); // cir = 60 ; h = 6
        Arbre d = Arbre.getDicoArbre().get(214468); // cir = 60 ; h = 7
        Arbre e = Arbre.getDicoArbre().get(149631); // cir = 180
        Arbre f = Arbre.getDicoArbre().get(204191); // cir = 80

        membre.vote(a, b, a, a, a, b, d, b, b);
        membre2.vote(a, c, e, f);

        /**
         * Transmission de la classification de l'association à la municipalité
         */


        assoc.envoyerListeArbresNomines(2020);
        System.out.println(paris.getDicoArbresNominesParAn().toString());


        Arbre g = Arbre.getDicoArbre().get(102837);
        Arbre h = Arbre.getDicoArbre().get(120299);
        Arbre i = Arbre.getDicoArbre().get(123330);

        //System.out.println(g.toString());

        membre.demandeVisiteArbre(g);
        membre.visiteArbre(g, 2018, 11, 28, "Tres beau");
        System.out.println(assoc.getHistoriqueArbresVisites());

        membre.demandeVisiteArbre(g);
        membre.visiteArbre(g, 2019, 11, 28, "Tres beau");
        System.out.println(assoc.getHistoriqueArbresVisites());

        membre.demandeVisiteArbre(h);
        membre.visiteArbre(h, 2020, 02, 28, "Tres beau");
        System.out.println(assoc.getHistoriqueArbresVisites());


        //test cotisation

        System.out.println(assoc.getCompteBancaire().getSolde());

        //Test facture
        Facture facture = new Facture("nom", "ok", 100);
        assoc.paiement(facture);
        System.out.println(assoc.getCompteBancaire().getSolde());
        Facture facture2 = new Facture("nom", "ok", 2000);
        if (!assoc.paiement(facture2)) {
            System.out.println("Pas assez de solde");
        }

        //test don
        assoc.addDonateur(p);
        assoc.demandeDon("demande");
        System.out.println(assoc.getCompteBancaire().getSolde());

/*
        for (Membre m : assoc.getListeMembres()) {
            System.out.println(m);

            ArrayList<Arbre> arbres = (ArrayList<Arbre>) Arbre.createArbre("data/les-arbres.csv");
            Municipalite[] municipalites = new Municipalite[1];
            ServiceMairie serviceParis2 = new ServiceMairie("Service des espaces verts");
            municipalites[0] = new Municipalite("Paris", serviceParis2, arbres);
            Association assoc2 = new Association("Association des arbres", municipalites[0], 1000, 50, 5, 30);
            serviceParis.addNotifiable(assoc2);
            President president = new President("test", "test", assoc, 1999, 1, 22, 2018, 2, 22, "add");
            Membre membre1 = new Membre("azerty", "dupont", assoc, 1999, 1, 22, 2018, 2, 22, "add");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //Municipalité choisi
            Municipalite current = null;
            Association currentAssos = null;
            Membre currentMembre = null;
            boolean state = false;


            try {
                System.out.println("Choississez la municipalité en tapant le numéro de la ville en question: ");
                while (!state) {
                    for (int j = 0; j < municipalites.length; j++) {
                        System.out.println(String.format("%d. %s : %s", i, municipalites[j].getNom(), serviceParis.getNom()));
                    }
                    String s = br.readLine();
                    try {
                        int index = Integer.parseInt(s);
                        if (index >= municipalites.length) {
                            System.out.println("Ce numéro n'existe pas");
                            continue;
                        }
                        current = municipalites[index];
                    } catch (NumberFormatException exception) {
                        System.out.println("Veuillez rentrer un nombre");
                    }
                    MunicipaliteConsole municipaliteConsole = new MunicipaliteConsole(current, br);

                    currentAssos = municipaliteConsole.chooseAssociation();

                    AssociationConsole associationConsole = new AssociationConsole(currentAssos, br);
                    currentMembre = associationConsole.associationMenu();

                    MembreConsole membreConsole = new MembreConsole(currentMembre, br);

                    membreConsole.membreMenu();
                }
            } catch (IOException exception) {
                System.out.println("Erreur lecture console");
            }
        }
*/

    }
}
