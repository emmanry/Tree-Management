package com.projetjava;

import com.projetjava.exception.DateException;
import com.projetjava.exception.SoldeNegatifException;

import java.util.*;

/**
 * Membre d'une application
 * @see Personne
 */
public class Membre extends Personne{

    /**
     * Date de l'inscription (date d'aujourd'hui)
     */
    private MyDate dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listeArbresVotes = new ArrayList<>();
    private int nbDefraiement;
    private final HashMap<Integer,Double> cotisations;
    private int id;
    private static int indexId = 0;

    public Membre(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre) throws DateException {
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
     * @param arbre
     * @return Si le vote à réussi
     */
    public boolean vote(Arbre...arbre){

        for (Arbre a : arbre) {
            if (!this.listeArbresVotes.contains(a) && !a.getRemarquable()){
                this.listeArbresVotes.add(a);
            }
            else{
                return false;
            }
        }
        // S'il y a plus de 5 arbres votés, on prend en compte seulement les 5 derniers
        if(this.listeArbresVotes.size() > 5){
            this.listeArbresVotes = this.listeArbresVotes.subList(this.listeArbresVotes.size() - 5, this.listeArbresVotes.size());
        }
        return true;
    }

    /**
     * || Première étape d'une visite : demande l'autorisation ||
     * On fait appel à une vérification auprès de l'Association
     * @param arbre
     * @return Si la demande est conforme
     */
    public boolean demandeVisiteArbre(Arbre arbre){
        if(!this.association.verificationVisite(arbre, this)){
            return false;
        }
        else{
            this.association.addVisitesEnAttente(arbre, this);
            return true;
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

    /**
     *  || Dernière étape d'une visite : la visite ||
     *  S'en suit la création d'un compte rendu daté et d'un défraiement
     * @param arbre
     * @param annee
     * @param mois
     * @param jour
     * @param contenu
     * @return Si la visite à une autorisation
     */
    public boolean visiteArbre(Arbre arbre, int annee, int mois, int jour, String contenu) throws SoldeNegatifException, DateException {
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
            } else {
                throw new SoldeNegatifException(association);
            }
        }else{
            return false;
        }
        return true;
    }

    public HashMap<Integer, Double> getCotisations() {
        return cotisations;
    }

    public Association getAssociation() {
        return association;
    }

    /**
     * Redéfinition de toString de Personne
     * @return String
     */
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
