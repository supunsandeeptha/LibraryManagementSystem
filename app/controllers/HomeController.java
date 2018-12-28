package controllers;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import models.Book;
import models.DVD;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.LibraryItem;
import models.LibraryManager;
import org.bson.Document;
//import play.api.mvc.Filters;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.reflect.internal.Trees;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;



public class HomeController extends Controller implements LibraryManager {


    private ArrayList<Book> bookList = new ArrayList<Book>(); //Arraylist to store the Books
    private ArrayList<DVD> dvdList = new ArrayList<DVD>(); //ArrayList to store Dvds
    //linking the online Mongodb Database
    MongoClientURI uri = new MongoClientURI("mongodb://Supun:12345@cluster0-shard-00-00-qqaal.mongodb.net:27017,cluster0-shard-00-01-qqaal.mongodb.net:27017,cluster0-shard-00-02-qqaal.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");
    MongoClient mongo = new MongoClient(uri);
    MongoDatabase database = mongo.getDatabase("WestminsterLIbraryManager");   //Getting the database (database name is WestMinsterLibraryManager)
    MongoCollection<Document> bookCollection = database.getCollection("book"); //assigning the database collection to a local variable
    MongoCollection<Document> dvdCollection = database.getCollection("dvd"); //assigning the database collection to a local variable


    public Result index() {
        return ok();
    }

    //addbook method

    public Result addbook()  {
        JsonNode body = request().body().asJson(); //Json body to request and send data

        if (bookList.size() <= 100) {  //Book will be created only if the Space is available (max space = 100 Books)


            String Title = body.get("Title").asText();  //getting data from the frontend and assigning to a local variable
            String sector = body.get("sector").asText();
            String pubdate = body.get("pubDate").asText();
            int isbn = body.get("isbn").asInt();
            String publisher = body.get("publisher").asText();
            String currentReader = null; // null value will be assigned as there's no curent reader at the point of Adding the book
            String authors = body.get("authors").asText();
            int noOfPages = body.get("noOfPages").asInt();
            Date borrowedDate = null; //null value will be assigned as there's no borrowedDate when adding the book
            Date returnDate = null; //null value will be assigned as there's no borrowedDate when adding the book
            double fee = 0; // value will be assigned to zero as there's no penalty fee when adding the book
            long dateDifference = 0;  //there' no date difference when adding the book


            Document book = new Document() //creating a new book document to store details about the book

                    .append("Title", Title) //adding data to the document
                    .append("sector", sector)
                    .append("pubDate", pubdate)
                    .append("isbn", isbn)
                    .append("publisher", publisher)
                    .append("currentReader", currentReader)
                    .append("authors", authors)
                    .append("noOfPages", noOfPages)
                    .append("borrowedDate", borrowedDate)
                    .append("returnDate", returnDate)
                    .append("Penalty Fee",fee)
                    .append("dateDifference",dateDifference);



            bookCollection.insertOne(book); //inserting the book to the mongo database
            //Creating a new Book Object for the local reference
            Book bookObj = new Book(Title, sector, currentReader, authors, noOfPages, pubdate, borrowedDate, returnDate, isbn, publisher,fee,dateDifference);
            bookList.add(bookObj); //adding the book to the arraylist
            return ok(Json.toJson("Congratulations !!! Book has been added to the Database")); //parsing message to frontend

        } else {

            return ok(Json.toJson("Maximum Number of Books are Stored")); //If the arraylist is full this message will be shown
        }
    }

    //add Dvd method
    public Result addDvd()  {
        JsonNode body = request().body().asJson(); //Json body to request and send data

        if (dvdList.size() <= 50) { //limiting the size of the arraylist to 50

            String title = body.get("title").asText(); //getting data from the frontend and assigning to a local variable
            String sector = body.get("sector").asText();
            String pubDate = body.get("pubDate").asText();
            int isbn = body.get("isbn").asInt();
            String publisher = body.get("publisher").asText();
            String currentReader = null; //Null value as there's no current reader in the adding point
            String availableLanguages = body.get("availableLanguages").asText();
            String availableSubtitles = body.get("availableSubstitles").asText();
            String producer = body.get("producer").asText();
            String actors = body.get("actors").asText();
            double fee = 0; //initialized to zero as there's no penalty
            long dateDifference = 0; //intialized to zero as there's no date difference
            Date borrowedDate = null; //No borrowed date when adding the dvd
            Date returnDate = null; //No return date when adding the dvd

            //creating the dvd obj
            DVD DVDobj = new DVD(currentReader, title, sector, availableLanguages, availableSubtitles, producer, actors, pubDate, borrowedDate, returnDate, isbn, publisher,fee,dateDifference);

            Document dvd = new Document() //creating a new Dvd document to be stored in the database

                    .append("title", title) //getting data from the frontend and assigning to a local variable
                    .append("sector", sector)
                    .append("pubDate", pubDate)
                    .append("borrowedDate", borrowedDate)
                    .append("returnDate", returnDate)
                    .append("isbn", isbn)
                    .append("publisher", publisher)
                    .append("currentReader", currentReader)
                    .append("availableLanguages", availableLanguages)
                    .append("availableSubstitles", availableSubtitles)
                    .append("producer", producer)
                    .append("actors", actors)
                    .append("Penalty Fee",fee)
                    .append("dateDifference",dateDifference);

            dvdList.add(DVDobj); //adding the dvd obj to the arrayList
            dvdCollection.insertOne(dvd); //adding the document to the database collection


            //parsing the message if the book is created
            return ok(Json.toJson("Congratulations !!! Dvd has been added to the Database"));


        } else {

            //if the arrayList is full this message will be shown
            return ok(Json.toJson("Maximum Number of Dvds are Stored"));

        }
    }


    //delete book method
    public Result deleteBook() {
        JsonNode body = request().body().asJson(); //Json body to request and send data


        int bookIsbn = body.get("bookIsbn").asInt(); //getting data from the frontend and assigning to a local variable

        for (Book RemoveBook : bookList) {  //using the enhanced forloop, iterate through the arraylist(bookList)
            if (RemoveBook.getIsbn() == bookIsbn) { //if the isbn is equal to any available books' isbns.
                bookList.remove(RemoveBook); //book will be removed
                bookCollection.deleteOne(Filters.eq("isbn", bookIsbn)); //book will be removed from cloud database

                //if the attempt is successfull this message will be shown
                return ok(Json.toJson(bookIsbn + " ISBN Numbered Book Has Been Deleted   /" + (100 - bookList.size()) + "Spaces are left to store Books"));

            } else {

            }

        }
        //if the isbn is wrong this message will be shown
        return ok(Json.toJson("Book not Found"));



    }

    public Result deleteDvd()  {
        JsonNode body = request().body().asJson(); //Json body to request and send data


        int dvdIsbn = body.get("dvdIsbn").asInt(); //getting data from the frontend and assigning to a local variable

        for (DVD RemoveDVD : dvdList) {  //using the enhanced forloop, iterate through the arraylist(dvdList)
            if (RemoveDVD.getIsbn() == dvdIsbn) { //if the isbn is equal to any available books' isbns.
                dvdList.remove(RemoveDVD); //dvd will be removed
                dvdCollection.deleteOne(Filters.eq("isbn", dvdIsbn)); //dvd will be removed from cloud database

                //if the attempt is successfull this message will be shown
                return ok(Json.toJson(dvdIsbn + " ISBN Numbered DVD Has Been Deleted   /" + (50 - dvdList.size()) + " Spaces are left to store Dvds"));

            } else {

            }

        }
        //if the isbn is wrong this message will be shown
        return ok(Json.toJson("DVD Not Found"));



    }

    //display Item method
    public Result displayItem() {
        JsonNode body = request().body().asJson();  //Json body to request and send data


        String itemType = body.get("itemType").asText();  //getting data from the frontend and assigning to a local variable

        if (itemType.equalsIgnoreCase("Book")) { //if the itemType is equal to the String value Book

            return ok(Json.toJson(bookList)); //bookList will be returned to the frontend and will be shown in the frontend


        } else if (itemType.equalsIgnoreCase("Dvd")) { //else if the itemtype is dvd

            return ok(Json.toJson(dvdList));  //dvdList will be returned to the frontend and will be shown in the frontend


        }
        else {

        }
        //if invalid item type is entered this message will be shown
        return ok(Json.toJson("Invalid Library Item Type"));

    }

    //borrowItem Method
    public Result borrowItem() throws ParseException{   //parse exception is thrown as date string is parsed to Date datatype

        JsonNode body = request().body().asJson(); //Json body to request and send data
        String itemType = body.get("itemType").asText(); //getting data from the frontend and assigning to a local variable

        if (itemType.equalsIgnoreCase("Book")) { //if item type is equal to book string value
            int isbn = body.get("isbn").asInt(); //values will be taken from frontend
            String borrowPerson = body.get("borrowPerson").asText();
            String borrowDate = body.get("borrowDate").asText();
            //Date object is created and the String value is parsed to it
            Date convertDate = new SimpleDateFormat("dd/mm/yyyy").parse(borrowDate);

            for (Book borrowBooks : bookList) { //iterating through the arrayList using the forloop

                if (borrowBooks.getIsbn() == isbn && borrowBooks.getCurrentReader() != null) {

                    //if the isbn is valid and current reader is available below message will be shown
                    return ok(Json.toJson("This Book is already Borrowed"));

                } else if (borrowBooks.getIsbn() == isbn && borrowBooks.getCurrentReader() == null) {
                    //if the isbn is valid and no current reader is not available below actions will be taken
                    borrowBooks.setCurrentReader(borrowPerson); //setting the current reader
                    borrowBooks.setBorrowedDate(convertDate); //assigning the borrowd date
                    //updating the database
                    bookCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("currentReader", borrowPerson)));
                    bookCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("borrowedDate", convertDate)));
                    return ok(Json.toJson(borrowPerson + " You borrowed this book on" + convertDate)); //message shown

                } else {

                }
            }
            return ok(Json.toJson("Invalid Details")); //if entered details are invalid message is shown

        } else if (itemType.equalsIgnoreCase("Dvd")) { //if the itemtype is dvd string value

            int isbn = body.get("isbn").asInt(); //values will be taken from frontend
            String borrowPerson = body.get("borrowPerson").asText();
            String borrowDate = body.get("borrowDate").asText();
            //Date object is created and the String value is parsed to it
            Date convertDate = new SimpleDateFormat("dd/mm/yyyy").parse(borrowDate);

            for (DVD borrowdvds : dvdList) { //iterating through the arrayList using the forloop
                if (borrowdvds.getIsbn() == isbn && borrowdvds.getCurrentReader() != null) {

                    //if the isbn is valid and current reader is available below message will be shown
                    return ok(Json.toJson(Json.toJson("This Dvd is already Borrowed")));

                } else if (borrowdvds.getIsbn() == isbn && borrowdvds.getCurrentReader() == null) {
                    //if the isbn is valid and no current reader is not available below actions will be taken
                    borrowdvds.setCurrentReader(borrowPerson);  //setting the current reader
                    borrowdvds.setBorrowedDate(convertDate); //assigning the borrowd date
                    dvdCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("currentReader", borrowPerson)));
                    dvdCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("borrowedDate", convertDate)));
                    return ok(Json.toJson(borrowPerson + " You have borrowed this Dvd on " + convertDate));

                } else {

                }

            }
            return ok(Json.toJson("Invalid Details")); //if entered details are invalid message is shown


        } else {

            return ok(Json.toJson("Invalid Item")); //if invalid item type is entered this message will be shown

        }


    }


    //return item method
    public Result returnItem() throws ParseException{

        JsonNode body = request().body().asJson(); //Json body to request and send data
        String itemType = body.get("itemType").asText(); //getting the data from the frontend

        if (itemType.equalsIgnoreCase("Book")) { //if the item type is book
            int isbn = body.get("isbn").asInt(); //these values will be taken
            String returnDate = body.get("returnDate").asText();
            //new date object will be created and the string will be parsed
            Date convertDate = new SimpleDateFormat("dd/mm/yyyy").parse(returnDate);

            for (Book borrowBooks : bookList) {  //iterating through the arrayList using the forloop
                if (borrowBooks.getIsbn() == isbn && borrowBooks.getCurrentReader() == null) {

                    //if the isbn is valid but if the item is not borrowed this message will  be shown
                    return ok(Json.toJson("This Book is not Borrowed to Return, Please Borrow First"));

                } else if (borrowBooks.getIsbn() == isbn && borrowBooks.getCurrentReader() != null) {
                    //if the isbn is valid and book is borrowed below actions will be taken
                    borrowBooks.setCurrentReader(null); //removing the reader, making the book available
                    borrowBooks.setReturnDate(convertDate); //setting the return date
                    //calculating the no of days taken to return to the book
                    long diffInMillies = Math.abs(borrowBooks.getReturnDate().getTime() - borrowBooks.getBorrowedDate().getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    borrowBooks.setDateDifference(diff); //setting the date difference
                    bookCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("currentReader", null)));
                    bookCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("returnDate", convertDate)));
                    bookCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("dateDifference", diff)));
                    return ok(Json.toJson( " You Have Successfully Returned this book on" + convertDate));

                } else {

                }
            }
            return ok(Json.toJson("Invalid Details")); // if invalid details were entered this message will be shown

        } else if (itemType.equalsIgnoreCase("Dvd")) {

            int isbn = body.get("isbn").asInt();
            String returnDate = body.get("returnDate").asText();
            Date convertDate = new SimpleDateFormat("dd/mm/yyyy").parse(returnDate);

            for (DVD returnDvds : dvdList) {
                if (returnDvds.getIsbn() == isbn && returnDvds.getCurrentReader() == null) {

                    return ok(Json.toJson(Json.toJson("This Book is not Borrowed to Return, Please Borrow First")));

                } else if (returnDvds.getIsbn() == isbn && returnDvds.getCurrentReader() != null) {

                    returnDvds.setCurrentReader(null);
                    returnDvds.setReturnDate(convertDate);
                    long diffInMillies = Math.abs(returnDvds.getReturnDate().getTime() - returnDvds.getBorrowedDate().getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    returnDvds.setDateDifference(diff);
                    dvdCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("currentReader", null)));
                    dvdCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("returnDate", convertDate)));
                    dvdCollection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("dateDifference", diff)));
                    return ok(Json.toJson( " You Have Successfully Returned this book on " + convertDate));

                } else {

                }

            }
            return ok(Json.toJson("Invalid Details"));


        } else {

            return ok(Json.toJson("Invalid Item"));

        }
    }

    //generate report method
    public Result generateReport(){

        JsonNode body = request().body().asJson();
        String itemType = body.get("itemType").asText();
        double fees= 0;

        if (itemType.equalsIgnoreCase("Book")){ //if the item Type is book
            for (Book generateReport: bookList){ //iterating through the arraylist
                if (generateReport.getDateDifference() > 7){ //if the book is kept more than 7 days
                    if (generateReport.getDateDifference() > 10){ //if the book is kept for 10 days
                        fees = generateReport.getDateDifference() * 12; //penalty calculation
                        generateReport.setFee(fees); //setting the penalty amount
                    }else if (generateReport.getDateDifference() > 7 && generateReport.getDateDifference() < 10){
                        //if the book is borrowed for more than 7 days but less than 10 days
                        fees = generateReport.getDateDifference() * 4.8; //penalty calcualation
                        generateReport.setFee(fees); //setting the fees amount
                    }else {

                    }
                }
            }

            return ok(Json.toJson(bookList)); //returning the booklist

        }else if (itemType.equalsIgnoreCase("Dvd")){ //if the itemtype is dvd
            for (DVD generateReport: dvdList ){ //iterating through the arraylist
                if (generateReport.getDateDifference() > 3){ //if the dvd is borrowed for more than 3 days
                    if (generateReport.getDateDifference() > 6){ //if the dvd is borrowed for more than 6 days
                        fees = generateReport.getDateDifference() * 12;
                        generateReport.setFee(fees);
                    }else if (generateReport.getDateDifference() > 3 && generateReport.getDateDifference() < 6){
                        //if the dvd is borrowed for more than 3 days but less than 6 days
                        fees = generateReport.getDateDifference() * 4.8;
                        generateReport.setFee(fees);
                    }else {

                    }
                }
            }

            return ok(Json.toJson(dvdList)); //returning the dvdlist
        }else {


            return ok(Json.toJson("Invalid Item Type!!!, Please try again!!")); //if invalid item type is entered


        }


    }


    //generate report method
    public Result searchItem()
    {

        JsonNode body = request().body().asJson();
        String itemType = body.get("itemType").asText();
        int isbn = body.get("isbn").asInt();

        if (itemType.equalsIgnoreCase("Book")){ //checking the item type

            for (Book searchBook : bookList) {
                if (searchBook.getIsbn() == isbn){ //checking the availability of book through isbn
                    return ok(Json.toJson(searchBook)); //returning the relevant object
                }else {

                }
            }

            return ok(Json.toJson("Invalid ISBN")); // if the isbn is invalid this message will be returned



        }else if (itemType.equalsIgnoreCase("Dvd")){ //checking the item type



            for (DVD searchDvd : dvdList) {
                if (searchDvd.getIsbn() == isbn){ //checking the availability of dvd using isbn
                    return ok(Json.toJson(searchDvd)); //returning the relevant object
                }else {

                }
            }

            return ok(Json.toJson("Invalid ISBN")); // if invalid isbn is entered this message will be shown
        }else {

            return ok(Json.toJson("Invalid Item Type")); //if invalid item item type is entered this message will be shown
        }


    }



}
















