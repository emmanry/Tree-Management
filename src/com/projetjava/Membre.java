package com.projetjava;

import java.util.*;

public class Membre extends Personne{

    private MyDate dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listeArbresVotes = new ArrayList<>();
    private int nbDefraiement;
    private final HashMap<Integer,Double> cotisations;
    private int id;
    private static int indexId = 0;

    public Membre(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre){
        super(nom, prenom, anneeNaissance, moisNaissance, jourNaissance);
        this.association = assoc;
        this.dateDerniereInscription = new MyDate(anneeInscription, moisInscription, jourInscription);
        this.adresse = adresseMembre;
        this.association.addListeMembres(this);
        cotisations = new HashMap<>();
        this.nbDefraiement = 0;
        id = indexId;
        indexId++;
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

    public void seDesinscrire(){
        this.association.removeMembre(this);
    }

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

    public void cotiser(int year) {
        if(cotisations.get(year) == null){
            association.cotisationRecette();
            cotisations.put(year,association.getPrixCotisation());
        }
    }

    public boolean hasCotiser(int year){
        return (cotisations.get(year) != null);
    }

    public int getNbDefraiement() {
        return nbDefraiement;
    }

    public int getId() {
        return id;
    }


    // todo exception pour la cohérence des dates
    /**
     *  || Dernière étape d'une visite : la visite ||
     *  S'en suit la création d'un compte rendu daté et d'un défraiement
     */
    public void visiteArbre(Arbre arbre, int annee, int mois, int jour, String contenu){
        // On vérifie que la demande a été programmée et acceptée
        if(this.association.getDicoVisitesEnAttente().get(arbre) == this){
            if(this.association.defraiement(this, arbre)) {
                nbDefraiement++;
                this.association.getDicoVisitesEnAttente().remove(arbre);
                // Si l'arbre a déjà été visité, on met à jour son état
                if (Arbre.getDicoArbre().get(arbre.getIdArbre()) instanceof ArbreVisite) {
                    ArbreVisite arbreVisite = (ArbreVisite) Arbre.getDicoArbre().get(arbre.getIdArbre());
                    MyDate dateDerniereVisite = new MyDate(annee, mois, jour);
                    ArbreVisite.getDicoArbresVisites().put(arbreVisite, dateDerniereVisite);
                    arbreVisite.ecrireCompteRendu(contenu, annee, mois, jour, this.association, this);
                }
                // Sinon on le rend visité
                else {
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

    public HashMap<Integer, Double> getCotisations() {
        return cotisations;
    }

    public Association getAssociation() {
        return association;
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
                "[ Nom.........................."   + this.getNom()                + " ] \n" +
                "[ Prénom......................."   + this.getPrenom()             + " ] \n" +
                "[ Date de Naissance............"   + this.getDateDeNaissance()         + " ] \n" +
                "[ Date de dernière inscription."   + this.dateDerniereInscription + " ] \n" +
                "[ Adresse......................"   + this.adresse                 + " ] \n" +
                idArbresVotes.toString() +
                "[ Cotisation..................."   + this.cotisations             + " ] \n" +
                "[ Nombre de Défraiements......."   + this.nbDefraiement  + " ] \n} "
                ;
    }
}
