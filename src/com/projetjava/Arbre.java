package com.projetjava;

import java.awt.geom.Point2D;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Arbre {

    private String genre, espece, nomFrancais, stadeDeveloppement, adresseAcces;
    private double circonferenceEnCm, hauteurEnM;
    private int idArbre;
    private boolean remarquable;
    private Point2D coordonnees;
    private static Map<Integer, Arbre> dicoArbre = new Hashtable<Integer,Arbre>();
    private Date dateRemarquable;

    public Arbre(int idBase, String idAdresse, String nomFr, String genreA, String especeA, double circonference, double hauteur, String stadeDev, boolean remarquableA, Date dateRemarquable, double x, double y){
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

    public double getCirconferenceEnCm(){
        return this.circonferenceEnCm;
    }

    public static void createArbre(String path, Municipalite municipalite) {
        String idEmplacement, libelleFrancais, genre, espece, stadeDeDeveloppement;
        double circonference = 0, hauteur = 0;
        int idbase = 0;
        Point2D geoPoint2D = null;

        String csvFilePath = path;
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath), StandardCharsets.ISO_8859_1)) {

            // Lire la première ligne
            String line = bufferedReader.readLine();

            // Récupère les données de la ligne
            String[] data = line.split(";", -1);

            if (data.length != 17) {
                System.out.println("[FileReader] Le fichier à " + csvFilePath + " ne contient pas le bon nombre de colonnes.");
            }

            // Créer un Arbre pour chaque ligne du fichier
            while ((line = bufferedReader.readLine()) != null) {

                // Récupère les données de la ligne
                data = line.split(";", -1);

                try {
                    idbase = Integer.parseInt(data[0]);
                }
                catch(NumberFormatException e){
                    System.out.println(data[0] + " n'est pas un entier");
                }

                idEmplacement = data[7];

                libelleFrancais = data[8];

                genre = data[9];

                espece = data[10];

                try {
                    circonference = Double.parseDouble(data[12]);
                }
                catch(NumberFormatException e){
                    System.out.println(data[12] + " n'est pas un double");
                }

                try {
                    hauteur = Double.parseDouble(data[13]);
                }
                catch(NumberFormatException e){
                    System.out.println(data[13] + " n'est pas un double");
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
                    System.out.println(dataCoord[0] = " ou " + dataCoord[1] + " n'est pas un double");
                }

                Arbre arbre = new Arbre(idbase, idEmplacement, libelleFrancais, genre, espece, circonference, hauteur, stadeDeDeveloppement, remarquable, null, geoPoint2D.getX(), geoPoint2D.getY());

                municipalite.addArbre(arbre);

            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public String toString(){
        StringBuilder dateConnue = new StringBuilder();
        if (this.remarquable){
            dateConnue.append("[ Date remarquable...........");
            if (this.dateRemarquable == null){
                dateConnue.append("Pas connue");
            }
            else {
                dateConnue.append(this.dateRemarquable.toString());
            }
            dateConnue.append(" ] \n");
        }
        return "\n" +
               "{ Arbre d'ID unique.........."   + this.idArbre            + "\n" +
               "[ ID d'emplacement..........."   + this.adresseAcces       + " ] \n" +
               "[ Nom en français............"   + this.nomFrancais        + " ] \n" +
               "[ Genre......................"   + this.genre              + " ] \n" +
               "[ Espèce....................."   + this.espece             + " ] \n" +
               "[ Circonférence en cm........"   + this.circonferenceEnCm  + " ] \n" +
               "[ Hauteur en m..............."   + this.hauteurEnM         + " ] \n" +
               "[ Stade de développement....."   + this.stadeDeveloppement + " ] \n" +
               "[ Remarquable................"   + (this.remarquable ? "Oui" : "Non" ) + " ] \n" +
               dateConnue.toString() +
               "[ Coordonnées................( " + this.coordonnees.getX() + ", " + this.coordonnees.getY() + " ) ] \n}"
                ;
    }

    public static void main (String[] args){
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        createArbre("C:\\Users\\emman\\OneDrive\\Bureau\\S6\\Java\\Projet\\les-arbres.csv", paris);
        System.out.println(dicoArbre.values());
        System.out.println(dicoArbre.get(102837));
        //System.out.println(paris.getListeArbre().size());
        System.out.println(paris.toString());
    }

}
