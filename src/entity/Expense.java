package entity;

public class Expense {

    private int id;
    private int monthId;
    private String name;
    private double ammount;

    public Expense() {
    }

    public Expense( int id, int monthId, String name, double ammount ) {
        this.id = id;
        this.monthId = monthId;
        this.name = name;
        this.ammount = ammount;
    }

    public int getId() {
        return id;
    }

    public int getMonthId() {
        return monthId;
    }

    public String getName() {
        return name;
    }

    public double getAmmount() {
        return ammount;
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id + ", monthId=" + monthId + ", name="
                + name + ", ammount=" + ammount + '}';
    }

}
