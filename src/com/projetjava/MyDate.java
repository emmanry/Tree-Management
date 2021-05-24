package com.projetjava;

import java.util.Calendar;

public class MyDate {

    Calendar date;

    public MyDate(int annee, int mois, int jour){
        this.date = Calendar.getInstance();
        this.date.set(annee, mois, jour);
    }

    public Calendar getDate(){
        return this.date;
    }

    public boolean before(MyDate autreDate){
        return (this.getDate().before(autreDate.getDate()));
    }

    public String toString(){
        return this.date.get(Calendar.DATE) + " / " + this.date.get(Calendar.MONTH) + " / " + this.date.get(Calendar.YEAR);
    }

}
