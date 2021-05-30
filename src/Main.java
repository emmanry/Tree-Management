import com.projetjava.*;
import com.projetjava.console.AssociationConsole;
import com.projetjava.console.MembreConsole;
import com.projetjava.console.MunicipaliteConsole;
import com.projetjava.exception.DateException;
import com.projetjava.finance.Facture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws DateException {
        System.out.println(args[0]);
        ArrayList<Arbre> arbres = (ArrayList<Arbre>) Arbre.createArbre(args[0]);
        if (arbres == null) {
            System.out.println("Impossible de charger les données relancer le programme");
            return;
        }

        // Création de données initiales
        Municipalite[] municipalite = new Municipalite[1];
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        municipalite[0] = new Municipalite("Paris", serviceParis, arbres);
        Association association = new Association("Association des arbres", municipalite[0], 1000, 50, 5, 30);
        serviceParis.addNotifiable(association);
        President president = new President("NOURRY", "Emma", association, 2000, 11, 28, 2020, 3, 18, "Paris sud, 91000");
        Membre membre = new Membre("SENECAT", "Loïc", association, 2000, 10, 4, 2020, 5, 22, "Paris Centre, 75000");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //Municipalité choisi
        Municipalite current = null;
        Association currentAssos = null;
        Membre currentMembre = null;
        boolean state = false;
        boolean stateMunicipalite = false;

        while (!state) {
            try {
                System.out.println("Choississez la municipalité en tapant le numéro de la ville en question: ");
                while (!stateMunicipalite) {
                    for (int j = 0; j < municipalite.length; j++) {
                        System.out.println(String.format("%d. %s : %s", j, municipalite[j].getNom(), serviceParis.getNom()));
                    }
                    String s = br.readLine();
                    try {
                        int index = Integer.parseInt(s);
                        if (index >= municipalite.length) {
                            System.out.println("Ce numéro n'existe pas");
                            continue;
                        }else{
                            stateMunicipalite = true;
                        }
                        current = municipalite[index];
                    } catch (NumberFormatException exception) {
                        System.out.println("Veuillez rentrer un nombre");
                    }
                }

                MunicipaliteConsole municipaliteConsole = new MunicipaliteConsole(current, br);

                currentAssos = municipaliteConsole.chooseAssociation();

                AssociationConsole associationConsole = new AssociationConsole(currentAssos, br);
                currentMembre = associationConsole.associationMenu();

                MembreConsole membreConsole = new MembreConsole(currentMembre, br);

                membreConsole.membreMenu();
            } catch(IOException exception){
                System.err.println("Erreur lecture console");
            }
        }
    }
}
