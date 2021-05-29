package com.projetjava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Membre extends Personne{

    private MyDate dateDeNaissance, dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listeArbresVotes = new ArrayList<>();
    private boolean cotise;
    private int nbDefraiement;
    private int id;
    private static int indexId = 0;

    public Membre(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre){
        super(nom, prenom);
        this.association = assoc;
        this.dateDeNaissance = new MyDate(anneeNaissance, moisNaissance, jourNaissance);
        this.dateDerniereInscription = new MyDate(anneeInscription, moisInscription, jourInscription);
        this.adresse = adresseMembre;

        this.association.addListeMembres(this);
        this.cotise = false;
        this.nbDefraiement = 0;
        id = indexId;
        indexId++;
    }

    public MyDate getDateDeNaissance(){
        return this.dateDeNaissance;
    }

    public MyDate getDateDerniereInscription(){
        return this.dateDerniereInscription;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public List<Arbre> getListeArbresVotes(){
        return this.listeArbresVotes;
    }

    public int getNbDefraiement() {
        return nbDefraiement;
    }

    public int getId() {
        return id;
    }

    // todo inscription et désinscription volontaire

    /**
     * || Première étape de la classification : les votes ||
     * Les membres votent pour 5 arbres maximums et tous différents
     */
    public void vote(Arbre...arbre){

        for (Arbre a : arbre) {
            if (!this.listeArbresVotes.contains(a)){
                this.listeArbresVotes.add(a);
            }
            else{
                System.err.println("Vous avez déjà voté pour " + a.toString());
            }
        }
        // S'il y a plus de 5 arbres votés, on prend en compte seulement les 5 derniers
        if(this.listeArbresVotes.size() > 5){
            this.listeArbresVotes = this.listeArbresVotes.subList(this.listeArbresVotes.size() - 5, this.listeArbresVotes.size());
        }
    }

    /**
     * || Première étape d'une visite : demande l'autorisation ||
     * On fait appel à une vérification auprès de l'Association
     */
    public void demandeVisiteArbre(Arbre arbre){
        if(!this.association.verificationVisite(arbre, this)){
            System.err.println("La visite n'est pas autorisée");
        }
        else{
            this.association.addVisitesEnAttente(arbre, this);
        }
    }

    // todo exception pour la cohérence des dates
    /**
     *  || Dernière étape d'une visite : la visite ||
     *  S'en suit la création d'un compte rendu daté et d'un défraiement
     */
    public void visiteArbre(Arbre arbre, int annee, int mois, int jour, String contenu){
        // On vérifie que la demande a été programmée et acceptée
        if(this.association.getDicoVisitesEnAttente().get(arbre) == this){
            if(this.association.demandeDefraiement(this)){
                nbDefraiement++;
                this.association.getDicoVisitesEnAttente().remove(arbre);
                // Si l'arbre a déjà été visité, on met à jour son état
                if(Arbre.getDicoArbre().get(arbre.getIdArbre()) instanceof ArbreVisite){
                    ArbreVisite arbreVisite = (ArbreVisite) Arbre.getDicoArbre().get(arbre.getIdArbre());
                    MyDate dateDerniereVisite = new MyDate(annee, mois, jour);
                    ArbreVisite.getDicoArbresVisites().put(arbreVisite, dateDerniereVisite);
                    arbreVisite.ecrireCompteRendu(contenu, annee, mois, jour, this.association, this);
                }
                // Sinon on le rend visité
                else{
                    ArbreVisite arbreVisite = new ArbreVisite(arbre.getIdArbre(), arbre.getAdresseAcces(), arbre.getNomFrancais(), arbre.getGenre(), arbre.getEspece(),
                            arbre.getCirconferenceEnCm(), arbre.getHauteurEnM(), arbre.getStadeDeveloppement(), true, arbre.getDateRemarquable(),
                            arbre.getCoordonnees().getX(), arbre.getCoordonnees().getY(), contenu, annee, mois, jour, this.association, this);
                }
            }
            else {
                System.err.println("L'association ne peut pas encore financer votre visite. Attendez quelques temps.");
            }
        }
        else{
            System.err.println("Vous n'avez pas eu d'autorisation pour visiter cet arbre. Envoyez nous une demandeVisiteArbre.");
        }
    }

    public void cotiser() {
        if(!cotise){
            cotise = true;
            association.cotisationRecette();
        }
    }

    public boolean hasCotiser(){
        return cotise;
    }

    public void defraiement(){
        if(association.demandeDefraiement(this)){
            nbDefraiement++;
        }
    }

    @Override
    public String toString() {
        StringBuilder idArbresVotes = new StringBuilder();
        if(!this.listeArbresVotes.isEmpty()){
            idArbresVotes.append("[ Liste des Arbres Votés.......");
            for (Arbre arbre : this.listeArbresVotes) {
                if(arbre == this.listeArbresVotes.get(this.listeArbresVotes.size() - 1)){
                    idArbresVotes.append(arbre.getIdArbre());
                }
                else {
                    idArbresVotes.append(arbre.getIdArbre() + " ; ");
                }
            }
            idArbresVotes.append(" ] \n");
        }
        return "\n" +
                "{ Membre......................."   + this.id                      + "\n" +
                "[ Nom.........................."   + this.nom                     + " ] \n" +
                "[ Prénom......................."   + this.prenom                  + " ] \n" +
                "[ Date de Naissance............"   + this.dateDeNaissance         + " ] \n" +
                "[ Date de dernière inscription."   + this.dateDerniereInscription + " ] \n" +
                "[ Adresse......................"   + this.adresse                 + " ] \n" +
                idArbresVotes.toString() +
                "[ Cotisation..................."   + this.cotise + " ] \n" +
                "[ Nombre de Défraiements......."   + this.nbDefraiement  + " ] \n} "
                ;
    }

}
