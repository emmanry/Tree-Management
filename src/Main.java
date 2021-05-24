import com.projetjava.*;


public class Main {
    public static void main(String[] args) {
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        Association assoc = new Association(paris);
        Arbre.createArbre("data/les-arbres.csv", paris);

        President membre = new President("NOURRY", "Emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        Membre membre2 = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");

        Personne p = new Personne("q","p");
        serviceParis.addNotifier(p);

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


        assoc.envoyerListArbresNomines(2020);
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


    }
}
