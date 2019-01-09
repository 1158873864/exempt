package njurestaurant.njutakeout.parameters.company;

public class CompanyCardAddParameters {
    private String name;
    private String bank;
    private String number;
    private double balance;
    private String attribution;
    private String relation;
    private String status;

    public CompanyCardAddParameters(String name, String bank, String number, double balance, String attribution, String relation, String status) {
        this.name = name;
        this.bank = bank;
        this.number = number;
        this.balance = balance;
        this.attribution = attribution;
        this.relation = relation;
        this.status = status;
    }

    public CompanyCardAddParameters() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
