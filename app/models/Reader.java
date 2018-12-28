package models;



public class Reader {

    private int id;
    private String name;
    private int mobNumber;
    private String email;


    public Reader(int id, String name, int mobNumber, String email) {
        this.id = id;
        this.name = name;
        this.mobNumber = mobNumber;
        this.email = email;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(int mobNumber) {
        this.mobNumber = mobNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
