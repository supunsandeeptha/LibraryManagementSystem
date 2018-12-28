package models;

import java.util.ArrayList;
import java.util.Date;


public class DVD extends LibraryItem {   //inheriting from LibraryManagement.LibraryItem Class as it's a LibraryManagement.LibraryItem

    private String availableLanguages;
    private String availablesubs;
    private String producer;
    private String actors;



    public DVD(String CurrentReader, String title, String sector, String availableLanguages, String availablesubs, String producer, String actors, String pubDate, Date borrowedDate,Date returnDate, int isbn, String publisher,double fee,long dateDifference) {
        super(title, sector, pubDate, borrowedDate,returnDate, isbn, publisher,CurrentReader,fee,dateDifference);
        this.availableLanguages = availableLanguages;
        this.availablesubs = availablesubs;
        this.producer = producer;
        this.actors = actors;

    }

    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public String getAvailablesubs() {
        return availablesubs;
    }

    public void setAvailablesubs(String availablesubs) {
        this.availablesubs = availablesubs;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }


}
