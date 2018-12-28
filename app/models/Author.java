package models;

public class Author extends Person {


    private String AuthorId;

    public Author(String Authorid, String name, String ID, String email, String address) {
        super(name, ID, email, address);
        this.AuthorId = Authorid;
    }
}
