package models;

import play.mvc.Result;

import java.text.ParseException;

public interface LibraryManager {

    public Result index();
    public Result addbook();
    public Result addDvd();
    public Result deleteBook();
    public Result deleteDvd();
    public Result displayItem();
    public Result borrowItem() throws ParseException;
    public Result returnItem()  throws ParseException;
    public Result generateReport();
    public Result searchItem();




}