package njurestaurant.njutakeout.publicdatas.app;

public enum CodeType {
    TSOLID, //转账固码
    TPASS,  //转账通码
    RSOLID, //收款固码
    RPASSOFF,  //收款通码离线
    RPASSQR,  //收款通码在线
    NONE
}
