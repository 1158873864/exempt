package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;

@Entity
@Table(name = "personal_card")
public class PersonalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "cardNumber")
    private String cardNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "bank")
    private String bank;
    @Column(name = "accountWithBank")
    private String accountWithBank;
    @Column(name = "bin")
    private String bin;
    @Column(name = "status")
    private String status;

    public PersonalCard(String cardNumber, String name, String bank, String accountWithBank, String bin, String status) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.bank = bank;
        this.accountWithBank = accountWithBank;
        this.bin = bin;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getAccountWithBank() {
        return accountWithBank;
    }

    public void setAccountWithBank(String accountWithBank) {
        this.accountWithBank = accountWithBank;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}