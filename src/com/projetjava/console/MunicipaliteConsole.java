package com.projetjava.console;

import com.projetjava.Arbre;
import com.projetjava.Association;
import com.projetjava.Municipalite;
import com.projetjava.ServiceMairie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui affiche sur la console l'interface pour gérer une municipalité
 */
public class MunicipaliteConsole {

    /**
     * municipalite
     */
    private final Municipalite municipalite;
    /**
     * Buffer qui lit sur la console
     */
    private final BufferedReader br;

    public MunicipaliteConsole(Municipalite municipalite, BufferedReader br){
        this.municipalite = municipalite;
        this.br = br;
    }

    /**
     * Menu des municipalités
     * @throws IOException
     */
    public Association chooseAssociation() throws IOException{
        Association association = null;
        //on part du principe qu'il n'y qu'un seul service
        ArrayList<Association> associations = municipalite.getServiceMairie().getAssociation();

        boolean state = false;
        while(!state){
            System.out.println(municipalite.getServiceMairie().getNom() +
                    " Choississez l'association associé en tapant le numéro de l'association en question: ");
            for (int i = 0; i < associations.size(); i++) {
                System.out.println(String.format("%d. %s ",i,associations.get(i).getNom()));
            }
            System.out.println("'A' pour abattre un arbre");
            String s = br.readLine();
            try{
                //suppresion d'un arbre
                if(s.equals("A")){
                    System.out.println("Suggestion d'arbre à abattre : ");
                    ArrayList<Arbre> arbres = municipalite.getListeArbre();
                    for (int i = 0; i < 4; i++) {
                        System.out.print("id : " + arbres.get(i).getIdArbre() + " ");
                    }

                    System.out.println("\nEntrer l'id de l'arbre à abattre");
                    s = br.readLine();
                    municipalite.removeArbreById(Integer.parseInt(s));
                    System.out.println("Arbre numéro " + s + " abattue");
                }else{
                    int index = Integer.parseInt(s);
                    if(index >= associations.size()){
                        System.out.println("Ce numéro n'existe pas");
                        continue;
                    }
                    association = associations.get(index);
                    state = true;
                }
            }catch (NumberFormatException e){
                System.out.println("Veuillez rentrer un nombre ou une commande bien écrite");
            }
        }

        return association;
    }
}
