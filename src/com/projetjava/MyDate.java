package com.projetjava;

import com.projetjava.exception.DateException;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * MyDate nous permet de créer des dates plus simplement
 */

public class MyDate {

    Calendar date;

    public MyDate(int annee, int mois, int jour) throws DateException{
        Calendar dateEntree = Calendar.getInstance();
        dateEntree.set(annee, mois - 1, jour);
        if(dateEntree.before(Calendar.getInstance())){
            this.date = dateEntree;
        }
        else {
            throw new DateException("Date supérieure à la date d'aujourd'hui");
        }
    }

    public Calendar getDate(){
        return this.date;
    }

    public boolean before(MyDate autreDate){
        return (this.getDate().before(autreDate.getDate()));
    }

    public String toString(){
        return this.date.get(Calendar.DATE) + " / " + (this.date.get(Calendar.MONTH) + 1) + " / " + this.date.get(Calendar.YEAR);
    }

}
