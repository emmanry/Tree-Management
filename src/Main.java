import com.projetjava.*;

import static com.projetjava.Arbre.createArbre;

public class Main {
    public static void main(String[] args) {
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        Arbre.createArbre("data/les-arbres.csv", paris);



        Personne p = new Personne("q","p");
        serviceParis.addNotifier(p);

        paris.removeArbreById(282008);
    }
}
