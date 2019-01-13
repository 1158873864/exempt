package njurestaurant.njutakeout.parameters.user;

public class SupplierAddParameters {
    private String username;
    private String password;
    private String aplipayloginId;
    private int level;

    public SupplierAddParameters(String username, String password, String aplipayloginId, int level) {
        this.username = username;
        this.password = password;
        this.aplipayloginId = aplipayloginId;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAplipayloginId() {
        return aplipayloginId;
    }

    public void setAplipayloginId(String aplipayloginId) {
        this.aplipayloginId = aplipayloginId;
    }
}

