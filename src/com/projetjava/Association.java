package com.projetjava;

import com.projetjava.notification.Notifiable;

import java.util.*;

public class Association implements Notifiable {

    private Municipalite municipalite;
    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();
    private List<Classification> listeClassifications = new ArrayList<>();
    private HashMap<Arbre, Membre> dicoVisitesEnAttente = new HashMap<>();

    public Association(Municipalite municipalite){
        this.municipalite = municipalite;
    }

    public ArrayList<Membre> getListeMembres(){
        return this.listeMembres;
    }

    public List<Classification> getListeClassifications(){
        return this.listeClassifications;
    }

    public HashMap<Arbre, Membre> getDicoVisitesEnAttente(){
        return this.dicoVisitesEnAttente;
    }

    public void addVisitesEnAttente(Arbre arbre, Membre membre){
        this.dicoVisitesEnAttente.put(arbre, membre);
    }

    public void addListeMembres(Membre membre){
        if(this.listeMembres.isEmpty()){
            if(membre instanceof President){
                this.listeMembres.add(membre);
            }
            else {
                System.err.println("Le premier membre doit être le président");
            }
        }
        else {
            if(membre instanceof President){
                System.err.println("L'association a déjà un président");
            }
            else if(membre instanceof Membre){
                this.listeMembres.add(membre);
            }
        }
    }

    public void addListeClassifications(Classification classification){
        this.listeClassifications.add(classification);
    }

    public void envoyerListArbresNomines(int annee){
        Classification classification = new Classification(this, annee);
        this.addListeClassifications(classification);
        classification.nomination();
        municipalite.recevoirListArbresNomines(classification.getAnnee(), classification.getListArbresNomines());
    }

    public boolean verificationVisite(Arbre arbre){
        return (!(this.dicoVisitesEnAttente.containsKey(arbre)) && arbre.getRemarquable());
    }

    public String getHistoriqueArbresVisites(){
        StringBuilder sb = new StringBuilder();
        String s;
        sb.append("Enumération des arbres remarquables par ancienneté de leur dernière visite : \n");

        // Faire une copie profonde du dicoArbresVisites
        HashMap<ArbreVisite, MyDate> dicoArbresVisitesCopie = new HashMap<>();
        for (ArbreVisite arbreVisite : ArbreVisite.getDicoArbresVisites().keySet()){
            dicoArbresVisitesCopie.put(arbreVisite, ArbreVisite.getDicoArbresVisites().get(arbreVisite));
        }

        while(!dicoArbresVisitesCopie.isEmpty()){
            Calendar dateAujourdhui = Calendar.getInstance();
            MyDate dateMin = new MyDate(dateAujourdhui.get(Calendar.YEAR), dateAujourdhui.get(Calendar.MONTH), dateAujourdhui.get(Calendar.DATE));
            ArbreVisite arbreVisiteMin = null;
            for (ArbreVisite arbreVisite : dicoArbresVisitesCopie.keySet()) {
                if(arbreVisite.getDateDerniereVisite().before(dateMin)){
                    dateMin = arbreVisite.getDateDerniereVisite();
                    arbreVisiteMin = arbreVisite;
                }
            }

            sb.append(arbreVisiteMin.getDateDerniereVisite() + " : " + " Arbre n° " + arbreVisiteMin.getIdArbre() + " visité par " +
                    arbreVisiteMin.getMembreVisite().getNom() + " " + arbreVisiteMin.getMembreVisite().getPrenom() + "\n");
            dicoArbresVisitesCopie.remove(arbreVisiteMin);

        }
        s = sb.toString();
        return s;
    }


    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        //todo faire quelque chose
        System.out.println(action.toString() + arbre);
        switch (action){
            case ABATTAGE:
                break;
            case PLANTATION:
                break;
            case CLASSIFICATION:
                break;
        }
    }
}
/*(Service Mairie,ExerciceBudgetaire,Listes de classification (1 par année),liste des membres
        ,président,listes des ArbresVisité (et arbre prévu),liste rapport d'activité,liste des donateurs,montant du compte bancaire)*/