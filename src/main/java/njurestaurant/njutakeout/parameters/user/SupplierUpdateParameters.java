package njurestaurant.njutakeout.parameters.user;

public class SupplierUpdateParameters {
    private String password;
    private int level;
    private String codeType;

    public SupplierUpdateParameters(String password, int level, String codeType) {
        this.password = password;
        this.level = level;
        this.codeType = codeType;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
