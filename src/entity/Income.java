package entity;

public class Income {

    private int id;
    private String name;
    private double income;
    private int monthId;

    public Income() {
    }

    public Income( int id, String name, double income, int monthId ) {
        this.id = id;
        this.name = name;
        this.income = income;
        this.monthId = monthId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }

    public int getMonthId() {
        return monthId;
    }

    @Override
    public String toString() {
        return "Income{" + "id=" + id + ", name=" + name + ", income=" + income
                + ", monthId=" + monthId + '}';
    }

}
