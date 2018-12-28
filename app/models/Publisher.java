package models;


public class Publisher extends Person {

    private String PublisherId;


    public Publisher(String PublisherId, String name, String ID, String email, String address) {
        super(name, ID, email, address);
        this.PublisherId = PublisherId;
    }


    public String getPublisherId() {
        return PublisherId;
    }

    public void setPublisherId(String publisherId) {
        PublisherId = publisherId;
    }
}

