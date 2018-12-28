package models;

public abstract class LibraryItem {

    private String title; //variable to store the title
    private String sector;
    private String pubDate;
    private java.util.Date borrowedDate;
    private java.util.Date returnDate;
    private String currentReader;
    private int isbn;
    private String publisher;
    private Date date;
    private double fee;
    private long dateDifference;



    public LibraryItem(String title, String sector, String pubDate, java.util.Date borrowedDate, java.util.Date returnDate, int isbn, String publisher, String currentReader, double fee, long dateDifference) {
        //Constructor

        this.title = title;
        this.sector = sector;
        this.pubDate = pubDate;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.isbn = isbn;
        this.publisher = publisher;
        this.currentReader = currentReader;
        this.fee = fee;
        this.dateDifference = dateDifference;


    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(String currentReader) {
        this.currentReader = currentReader;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }


    public long getDateDifference() {
        return dateDifference;
    }

    public void setDateDifference(long dateDifference) {
        this.dateDifference = dateDifference;
    }

    public java.util.Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(java.util.Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public java.util.Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(java.util.Date returnDate) {
        this.returnDate = returnDate;
    }
}