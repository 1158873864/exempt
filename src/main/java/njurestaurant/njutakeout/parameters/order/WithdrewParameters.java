package njurestaurant.njutakeout.parameters.order;

public class WithdrewParameters {
    private int id;
    private String type;
    private double money;
    private int cardId;

    public WithdrewParameters(int id, String type, double money, int cardId) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.cardId = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
