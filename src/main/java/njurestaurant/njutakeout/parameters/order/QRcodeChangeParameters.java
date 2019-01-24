package njurestaurant.njutakeout.parameters.order;

public class QRcodeChangeParameters {
    //支付宝账号
    private String loginId;
    //提现金额
    private double money;
    //卡号
    private String cardNumber;
    //操作人username
    private String operateUsername;

    public QRcodeChangeParameters() {

    }

    public QRcodeChangeParameters(String loginId, double money, String cardNumber, String operateUsername) {
        this.loginId = loginId;
        this.money = money;
        this.cardNumber = cardNumber;
        this.operateUsername = operateUsername;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername;
    }
}
