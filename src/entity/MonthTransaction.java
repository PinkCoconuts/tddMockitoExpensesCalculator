package entity;

public class MonthTransaction {

    private int id;
    private String name;
    private String type;
    private int monthId;
    private int categoryId;
    private double amount;

    public MonthTransaction() {
    }

    public MonthTransaction( int id, String name, String type, int monthId,
            int categoryId, double amount ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.monthId = monthId;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public void setMonthId( int monthId ) {
        this.monthId = monthId;
    }

    public void setCategoryId( int categoryId ) {
        this.categoryId = categoryId;
    }

    public void setAmount( double amount ) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MonthTransaction{" + "id=" + id + ", name=" + name + ", type=" + type
                + ", monthId=" + monthId + ", categoryId=" + categoryId
                + ", amount=" + amount + '}';
    }

}
