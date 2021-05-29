import com.projetjava.*;
import com.projetjava.console.AssociationConsole;
import com.projetjava.console.MembreConsole;
import com.projetjava.console.MunicipaliteConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        ArrayList<Arbre> arbres =(ArrayList<Arbre>) Arbre.createArbre("data/les-arbres.csv");
        Municipalite[] municipalites = new Municipalite[1];
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        municipalites[0] = new Municipalite("Paris", serviceParis,arbres);
        Association assoc = new Association("Association des arbres",municipalites[0]);
        serviceParis.addNotifier(assoc);
        President membre = new President("test", "test", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        Membre membre2 = new Membre("azerty", "dupont", assoc, 1999, 1, 22, 2018, 2, 22, "add");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //Municipalité choisi
        Municipalite current = null;
        Association currentAssos = null;
        Membre currentMembre = null;
        boolean state = false;


        try {
            System.out.println("Choississez la municipalité en tapant le numéro de la ville en question: ");
            while(!state){
                for (int i = 0; i < municipalites.length; i++) {
                    System.out.println(String.format("%d. %s : %s",i,municipalites[i].getNom(),serviceParis.getNom()));
                }
                String s = br.readLine();
                try{
                    int index = Integer.parseInt(s);
                    if(index >= municipalites.length){
                        System.out.println("Ce numéro n'existe pas");
                        continue;
                    }
                    current = municipalites[index];
                }catch (NumberFormatException e){
                    System.out.println("Veuillez rentrer un nombre");
                }
                MunicipaliteConsole municipaliteConsole = new MunicipaliteConsole(current,br);

                currentAssos = municipaliteConsole.chooseAssociation();

                AssociationConsole associationConsole = new AssociationConsole(currentAssos,br);
                currentMembre = associationConsole.associationMenu();

                MembreConsole membreConsole = new MembreConsole(currentMembre,br);

                membreConsole.membreMenu();
            }
        }catch (IOException e){
            System.out.println("Erreur lecture console");
        }
    }
}
