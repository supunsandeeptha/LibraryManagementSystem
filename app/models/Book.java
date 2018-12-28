package models;



import java.util.ArrayList;
import java.util.Date;

public class Book extends LibraryItem { //inheriting from LibraryManagement.LibraryItem Class as it's a LibraryManagement.LibraryItem



    private String currentReader;
    private String authors;
    private int noOfPages;




    public Book(String title, String sector, String currentReader, String authors, int noOfPages, String pubDate, Date borrowedDate, Date returnDate, int isbn, String publisher,double fee,long dateDifference) {

        super(title, sector, pubDate, borrowedDate,returnDate, isbn,publisher,currentReader,fee,dateDifference); //calling the super constructor
        this.authors = authors;
        this.noOfPages = noOfPages;

    }


    public String getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(String currentReader) {
        this.currentReader = currentReader;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }


    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }


}



