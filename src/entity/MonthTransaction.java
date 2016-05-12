package entity;

public class MonthTransaction {

    private int id;
    private String name;
    private String type;
    private int monthId;
    private double amount;

    public MonthTransaction() {
    }

    public MonthTransaction( int id, String name, String type, int monthId, double amount ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.monthId = monthId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMonthId() {
        return monthId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "MonthTransaction{" + "id=" + id + ", name=" + name + ", type=" + type
                + ", monthId=" + monthId + ", amount=" + amount + '}';
    }

}
