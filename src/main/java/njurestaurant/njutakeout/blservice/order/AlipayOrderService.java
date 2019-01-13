package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.parameters.order.OrderMsgParameters;
import njurestaurant.njutakeout.response.order.AlipayOrderAddResponse;

public interface AlipayOrderService {
    AlipayOrderAddResponse saveAlipayOrder(OrderMsgParameters orderMsgParameters);
}
