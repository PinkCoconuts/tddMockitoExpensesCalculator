package entity;

public class Month {

    private int id;
    private String name;

    public Month() {
    }

    public Month( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
