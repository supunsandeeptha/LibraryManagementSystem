package models;

public class Creator extends Person {

    private String CreatorId;


    public Creator(String CreatorId, String name, String ID, String email, String address) {
        super(name, ID, email, address);
        this.CreatorId = CreatorId;
    }
}

