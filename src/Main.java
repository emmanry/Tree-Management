import com.projetjava.*;
import com.projetjava.finance.Facture;

import static com.projetjava.Arbre.createArbre;

public class Main {
    public static void main(String[] args) {
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        Arbre.createArbre("data/les-arbres.csv", paris);

        Personne p = new Personne("q","p");
        serviceParis.addNotifier(p);

        paris.removeArbreById(282008);

        Association assoc = new Association("nom");
        //test cotisation
        Membre membre = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        membre.cotiser();
        Membre membre2 = new Membre("v", "v", assoc, 1999, 1, 22, 2018, 2, 22, "add");

        System.out.println(assoc.getCompteBancaire().getSolde());

        //Test facture
        Facture facture = new Facture("nom","ok",100);
        assoc.paiement(facture);
        System.out.println(assoc.getCompteBancaire().getSolde());
        Facture facture2 = new Facture("nom","ok",2000);
        if(!assoc.paiement(facture2)){
            System.out.println("Pas assez de solde");
        }

        //test don
        assoc.addDonateur(p);
        assoc.demandeDon("demande");
        System.out.println(assoc.getCompteBancaire().getSolde());

        //test exercice budgétaire
        for(Membre m:assoc.getListeMembres()){
            System.out.println(m);
        }
        assoc.getExerciceBudgetaire().fin("bonjour");

        for(Membre m:assoc.getListeMembres()){
            System.out.println(m);
        }

        System.out.println(assoc.getLastRapportActivite());
    }
}
