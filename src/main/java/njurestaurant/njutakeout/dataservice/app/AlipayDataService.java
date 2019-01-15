package njurestaurant.njutakeout.dataservice.app;

import njurestaurant.njutakeout.entity.app.Alipay;

public interface AlipayDataService {

    Alipay findById(int id);

    Alipay saveAlipay(Alipay alipay);
}
