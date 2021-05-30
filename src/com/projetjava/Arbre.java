package com.projetjava;

import java.awt.geom.Point2D;
import java.io.FileReader;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Arbre {

    private String genre, espece, nomFrancais, stadeDeveloppement, adresseAcces;
    private double circonferenceEnCm, hauteurEnM;
    private final int idArbre;
    private boolean remarquable;
    private final Point2D coordonnees;
    private static final Map<Integer, Arbre> dicoArbre = new Hashtable<Integer,Arbre>();
    private MyDate dateRemarquable;

    /**
     * dicoArbre permet de récuperer un arbre précis grâce à son idArbre qui est unique
     */
    public Arbre(int idBase, String idAdresse, String nomFr, String genreA, String especeA, double circonference, double hauteur, String stadeDev, boolean remarquableA, MyDate dateRemarquable, double x, double y){
        this.idArbre = idBase;
        this.genre = genreA;
        this.espece = especeA;
        this.nomFrancais = nomFr;
        this.stadeDeveloppement = stadeDev;
        this.circonferenceEnCm = circonference;
        this.hauteurEnM = hauteur;
        this.adresseAcces = idAdresse;
        this.remarquable = remarquableA;
        this.coordonnees = new Point2D.Double(x,y);
        this.dateRemarquable = dateRemarquable;
        dicoArbre.put(idBase, this);
    }

    public static Map<Integer, Arbre> getDicoArbre(){
        return dicoArbre;
    }

    public int getIdArbre() {
        return this.idArbre;
    }

    public String getAdresseAcces(){
        return this.adresseAcces;
    }

    public String getNomFrancais(){
        return this.nomFrancais;
    }

    public String getGenre(){
        return this.genre;
    }

    public String getEspece(){
        return this.espece;
    }

    public String getStadeDeveloppement(){
        return this.stadeDeveloppement;
    }

    public double getCirconferenceEnCm(){
        return this.circonferenceEnCm;
    }

    public double getHauteurEnM(){
        return this.hauteurEnM;
    }

    public boolean getRemarquable(){
        return this.remarquable;
    }

    public MyDate getDateRemarquable(){
        return this.dateRemarquable;
    }

    public Point2D getCoordonnees(){
        return this.coordonnees;
    }

    public void setDateRemarquable(int jour, int mois, int annee){
        this.dateRemarquable = new MyDate(annee, mois, jour);
    }

    public void setRemarquable(boolean bool){
        this.remarquable = bool;
    }

    /**
     * A partir du path, cette méthode createArbre crée tous les arbres du fichier csv
     * @param path
     * @return Liste des arbres créés
     */
    public static List<Arbre> createArbre(String path) {
        String idEmplacement, libelleFrancais, genre, espece, stadeDeDeveloppement;
        double circonference = 0, hauteur = 0;
        int idbase = 0;
        Point2D geoPoint2D = null;
        List<Arbre> listeArbres = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            // Lire la première ligne
            String line = bufferedReader.readLine();

            // Récupère les données de la ligne
            String[] data = line.split(";", -1);

            if (data.length != 17) {
                System.out.println("[FileReader] Le fichier à " + path + " ne contient pas le bon nombre de colonnes.");
                return null;
            }

            // Créer un Arbre pour chaque ligne du fichier
            while ((line = bufferedReader.readLine()) != null) {

                // Récupère les données de la ligne
                data = line.split(";", -1);

                // On vérifie que la ligne soit valide
                if(data.length != 17){
                    continue;
                }

                try {
                    idbase = Integer.parseInt(data[0]);
                }
                catch(NumberFormatException e){
                    //System.out.println(data[0] + " n'est pas un entier");
                    continue;
                }

                idEmplacement = data[7];

                libelleFrancais = data[8];

                genre = data[9];

                espece = data[10];

                try {
                    circonference = Double.parseDouble(data[12]);
                }
                catch(NumberFormatException e){
                    //System.out.println(data[12] + " n'est pas un double");
                    continue;
                }

                try {
                    hauteur = Double.parseDouble(data[13]);
                }
                catch(NumberFormatException e){
                    //System.out.println(data[13] + " n'est pas un double");
                    continue;
                }

                stadeDeDeveloppement = data[14];

                boolean remarquable;
                if (data[15].equals("NON") || data[15].equals("")) {
                    remarquable = false;
                } else {
                    remarquable = true;
                }

                String[] dataCoord = data[16].split(",", -1);
                try {
                    geoPoint2D = new Point2D.Double(Double.parseDouble(dataCoord[0]), Double.parseDouble(dataCoord[1]));
                }
                catch(NumberFormatException e){
                    //System.out.println(dataCoord[0] = " ou " + dataCoord[1] + " n'est pas un double");
                    continue;
                }

                Arbre arbre = new Arbre(idbase, idEmplacement, libelleFrancais, genre, espece, circonference, hauteur, stadeDeDeveloppement, remarquable, null, geoPoint2D.getX(), geoPoint2D.getY());

                listeArbres.add(arbre);

            }
        }
        catch(IOException e){
            System.out.println(e);
            return null;
        }
        return listeArbres;
    }

    /**
     * Méthode toString
     * @return String
     */
    public String toString() {
        StringBuilder dateConnue = new StringBuilder();
        if (this.remarquable) {
            dateConnue.append("[ Date remarquable...........");
            if (this.dateRemarquable == null) {
                dateConnue.append("Pas connue");
            } else {
                dateConnue.append(this.dateRemarquable.toString());
            }
            dateConnue.append(" ] \n");
        }
        return "\n" +
                "{ Arbre d'ID unique.........." + this.idArbre + "\n" +
                "[ ID d'emplacement..........." + this.adresseAcces + " ] \n" +
                "[ Nom en français............" + this.nomFrancais + " ] \n" +
                "[ Genre......................" + this.genre + " ] \n" +
                "[ Espèce....................." + this.espece + " ] \n" +
                "[ Circonférence en cm........" + this.circonferenceEnCm + " ] \n" +
                "[ Hauteur en m..............." + this.hauteurEnM + " ] \n" +
                "[ Stade de développement....." + this.stadeDeveloppement + " ] \n" +
                "[ Remarquable................" + (this.remarquable ? "Oui" : "Non") + " ] \n" +
                dateConnue.toString() +
                "[ Coordonnées................( " + this.coordonnees.getX() + ", " + this.coordonnees.getY() + " ) ] \n}"
                ;
    }

}
