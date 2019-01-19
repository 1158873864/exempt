package njurestaurant.njutakeout.parameters.user;

public class MerchantUpdateParameters {
    private String password;
    private String name;

    public MerchantUpdateParameters(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
