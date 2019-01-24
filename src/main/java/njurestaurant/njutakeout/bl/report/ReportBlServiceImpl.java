package njurestaurant.njutakeout.bl.report;

import javafx.application.Platform;
import njurestaurant.njutakeout.blservice.report.ReportBlService;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import njurestaurant.njutakeout.response.report.AgentReportResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import njurestaurant.njutakeout.response.report.PlatformAnalyse;
import njurestaurant.njutakeout.util.FormatDateTime;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportBlServiceImpl implements ReportBlService {
    private final PlatformOrderDataService platformOrderDataService;
    private final WithdrewOrderDataService withdrewOrderDataService;
    private final UserDataService userDataService;
    private final SupplierDataService supplierDataService;
    private final MerchantDataService merchantDataService;
    private final AgentDataService agentDataService;
    private final TeamDataService teamDataService;

    @Autowired
    public ReportBlServiceImpl(PlatformOrderDataService platformOrderDataService, WithdrewOrderDataService withdrewOrderDataService, UserDataService userDataService, SupplierDataService supplierDataService, MerchantDataService merchantDataService, AgentDataService agentDataService, TeamDataService teamDataService) {
        this.platformOrderDataService = platformOrderDataService;
        this.withdrewOrderDataService = withdrewOrderDataService;
        this.userDataService = userDataService;
        this.supplierDataService = supplierDataService;
        this.merchantDataService = merchantDataService;
        this.agentDataService = agentDataService;
        this.teamDataService = teamDataService;
    }

    /**
     * 商户报表
     * 系统编号(商户内部编号)，日期，商户号(商户在平台的账号)，存款(名下所有成功订单的总金额)，
     * 实际存款(名下所有成功订单的总金额-平台已经收取的手续费总额)，提款(已经提现成功的总金额，有多少笔提现成功的算多少)，
     * 冻结余额(正在提现的或者被管理员冻结的)，可用余额，代理分润r(如果他不是公司直属是有代理的，代理已经通过这个商户分红多少)，
     * 公司分润r(平台已经收取这个商户的手续费总额)，量分析r(存款详细规划，支付宝/微信/云闪付各成功多少)，成功率(根据总订单和成功订单计算出成功率)
     *
     * @return
     */
    @Override
    public List<MerchantReportResponse> getReportOfMerchant(Date date) {
        if (date == null) date = new Date();
        // 获取date当天的订单信息
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        c1.set(Calendar.MILLISECOND, 999);
//        List<PlatformOrder> platformOrders = platformOrderDataService.findPlatformByDate(c1.getTime(), c2.getTime());
        // 全部的成功的订单记录和提现记录
        List<PlatformOrder> allPlatformOrders = platformOrderDataService.findAll();
        List<WithdrewOrder> successWithdrewOrders = withdrewOrderDataService.findByState(WithdrewState.SUCCESS);
        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        List<Agent> agentList = agentDataService.getAllAgent();
        List<User> userList = userDataService.getAll();
        // 每个商户的报表<userid, 返回值>
        Map<Integer, MerchantReportResponse> merchantReportResponseMap = new HashMap<>();
        // 商家列表<userid, 商家信息>
        Map<Integer, Merchant> merchantMap = new HashMap<>();
        // 代理商列表<userid, 代理信息>
        Map<Integer, Agent> agentMap = new HashMap<>();

        List<MerchantReportResponse> result = new ArrayList<>();

        // 处理数据
        if (merchantList.size() > 0) {
            for (Merchant merchant : merchantList) {
                String number = "Sh" + String.format("%08d", merchant.getUser().getId());
                List<PlatformAnalyse> list = new ArrayList<>();
                list.add(new PlatformAnalyse("支付宝", 0));
                list.add(new PlatformAnalyse("微信", 0));
                list.add(new PlatformAnalyse("云闪付", 0));
                MerchantReportResponse merchantReportResponse = new MerchantReportResponse(number, date, merchant.getUser().getUsername(), merchant.getName(), 0.0, 0.0, 0.0, merchant.getWithdrewMoney(), merchant.getBalance(), 0.0, 0.0, list, 0, 0);
                merchantReportResponseMap.put(merchant.getUser().getId(), merchantReportResponse);
                merchantMap.put(merchant.getUser().getId(), merchant);
            }
            if (agentList.size() > 0) {
                for (Agent agent : agentList) {
                    agentMap.put(agent.getUser().getId(), agent);
                }
            }

            if (allPlatformOrders.size() > 0) {
                for (PlatformOrder platformOrder : allPlatformOrders) {
                    if (merchantReportResponseMap.containsKey(platformOrder.getUid())) {
                        MerchantReportResponse merchantReportResponse = merchantReportResponseMap.get(platformOrder.getUid());
                        merchantReportResponse.setTotalOrders(merchantReportResponse.getTotalOrders() + 1);
                        if (platformOrder.getState() == OrderState.PAID) { // 成功的订单
                            merchantReportResponse.setSuccessOrder(merchantReportResponse.getSuccessOrder() + 1);
                            merchantReportResponse.setDeposit(platformOrder.getPayMoney() + merchantReportResponse.getDeposit());   // 存款
                            Merchant merchant = merchantMap.get(platformOrder.getUid());
                            switch (platformOrder.getType()) {
                                case "alipay":
                                    merchantReportResponse.setAvailiableDeposit(merchantReportResponse.getAvailiableDeposit() + platformOrder.getPayMoney() * (1 - merchant.getAlipay() / 100)); //
                                    break;
                                case "wechat":
                                    merchantReportResponse.setAvailiableDeposit(merchantReportResponse.getAvailiableDeposit() + platformOrder.getPayMoney() * (1 - merchant.getWechat() / 100));
                                    break;
                            }
                            if (platformOrder.getTime() != null && DateUtils.isSameDay(date, platformOrder.getTime())) { // 和查询日期同一天
                                if (agentMap.containsKey(merchant.getApplyId())) {
                                    Agent agent = agentMap.get(merchant.getApplyId());
                                    List<PlatformAnalyse> platformAnalyses = null;
                                    PlatformAnalyse temp = null;
                                    switch (platformOrder.getType()) {
                                        case "alipay":  // 支付宝
                                            if (agent != null)
                                                merchantReportResponse.setAgentProfit(merchantReportResponse.getAgentProfit() + platformOrder.getPayMoney() * agent.getAlipay() / 100);    // 代理分润
                                            platformAnalyses = dailyAnalyse(merchantReportResponse.getPlatformAnalyseList(), "支付宝", platformOrder.getPayMoney());
                                            merchantReportResponse.setPlatformAnalyseList(platformAnalyses);    // 每日量分析
                                            merchantReportResponse.setCompanyProfit((merchant.getAlipay() - agent.getAlipay()) / 100 * platformOrder.getPayMoney() + merchantReportResponse.getCompanyProfit());    // 公司分润
                                            break;
                                        case "wechat":  // 微信
                                            if (agent != null)
                                                merchantReportResponse.setAgentProfit(merchantReportResponse.getAgentProfit() + platformOrder.getPayMoney() * agent.getWechat() / 100);    // 代理分润
                                            platformAnalyses = dailyAnalyse(merchantReportResponse.getPlatformAnalyseList(), "微信", platformOrder.getPayMoney());
                                            merchantReportResponse.setPlatformAnalyseList(platformAnalyses);
                                            merchantReportResponse.setCompanyProfit((merchant.getWechat() - agent.getWechat()) / 100 * platformOrder.getPayMoney() + merchantReportResponse.getCompanyProfit());
                                            break;
                                        case "cloudpay":    // 云闪付
//                                        platformAnalyses = dailyAnalyse(merchantReportResponse.getPlatformAnalyseList(), "云闪付", platformOrder.getMoney());
//                                        merchantReportResponse.setPlatformAnalyseList(platformAnalyses);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                        }
                        merchantReportResponseMap.put(platformOrder.getUid(), merchantReportResponse);
                    }
                }
            }

            if (successWithdrewOrders.size() > 0) {
                for (WithdrewOrder withdrewOrder : successWithdrewOrders) {
                    if (merchantReportResponseMap.containsKey(withdrewOrder.getApplicantId())) {
                        MerchantReportResponse merchantReportResponse = merchantReportResponseMap.get(withdrewOrder.getApplicantId());
                        merchantReportResponse.setWithdrewed(withdrewOrder.getMoney() + merchantReportResponse.getWithdrewed());//提款
                        merchantReportResponseMap.put(withdrewOrder.getApplicantId(), merchantReportResponse);
                    }
                }
            }

            for (Map.Entry<Integer, MerchantReportResponse> entry : merchantReportResponseMap.entrySet()) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * 代理商报表
     * 系统编号， 日期，代理账户
     * 分润点位（详细的分红点位，支付宝/微信），存款r（名下所有商户，所有成功订单总金额。细分支付宝/微信）
     * 分润r(名下所有商户，所有成功订单总金额*分润点位，分支付宝/微信)，提款r（名下所有商户，提现成功的总金额，显示实现段内）
     * 余额（名下所有商户未提现的总金额）
     *
     * @param startDate 时间段的开始时间
     * @param endDate   时间段的结束时间
     * @return
     */
    @Override
    public List<AgentReportResponse> getReportOfAgent(Date startDate, Date endDate) throws WrongInputException {
        // 判断输入的时间格式是否错误
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            if (DateUtils.isSameDay(startDate, endDate)) date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }

        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        List<Agent> agentList = agentDataService.getAllAgent();
        List<PlatformOrder> platformOrders = platformOrderDataService.findPlatformByDate(startDate, endDate);
        List<WithdrewOrder> withdrewOrders = withdrewOrderDataService.findByDateRange(startDate, endDate);

        Map<Integer, Merchant> merchantMap = new HashMap<>();
        Map<Integer, Agent> agentMap = new HashMap<>();
        Map<Integer, AgentReportResponse> agentReportResponseMap = new HashMap<>();

        List<AgentReportResponse> result = new ArrayList<>();

        // 数据处理
        if (agentList.size() > 0) {
            for (Agent agent : agentList) {
                String number = "Dl" + String.format("%08d", agent.getUser().getId());
                agentMap.put(agent.getUser().getId(), agent);
                List<PlatformAnalyse> dlist = new ArrayList<>();
                dlist.add(new PlatformAnalyse("支付宝", 0));
                dlist.add(new PlatformAnalyse("微信", 0));
                dlist.add(new PlatformAnalyse("云闪付", 0));
                List<PlatformAnalyse> plist = new ArrayList<>();
                plist.add(new PlatformAnalyse("支付宝", 0));
                plist.add(new PlatformAnalyse("微信", 0));
                plist.add(new PlatformAnalyse("云闪付", 0));
                agentReportResponseMap.put(agent.getUser().getId(), new AgentReportResponse(number, date, agent.getUser().getUsername(), agent.getAgentName(), agent.getAlipay(), agent.getWechat(), dlist, plist, 0.0, 0.0));
            }
        }
        if (merchantList.size() > 0) {
            for (Merchant merchant : merchantList) {
                if (agentReportResponseMap.containsKey(merchant.getApplyId())) { // 该用户上级是代理商
                    merchantMap.put(merchant.getUser().getId(), merchant);
                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(merchant.getApplyId());
                    agentReportResponse.setBalance(merchant.getBalance() + agentReportResponse.getBalance());
                    agentReportResponseMap.put(merchant.getApplyId(), agentReportResponse);
                }
            }
        }

        // 报表生成
        if (platformOrders.size() > 0) {
            for (PlatformOrder platformOrder : platformOrders) {
                if (platformOrder.getState() != OrderState.PAID) continue;   // 过滤掉未成功的订单
                Merchant merchant = merchantMap.get(platformOrder.getUid());
                if (merchant != null) {
                    Agent agent = agentMap.get(merchant.getApplyId());
                    if (agent == null) continue;  // 该条记录的商户不属于某个代理
                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(agent.getUser().getId());
                    List<PlatformAnalyse> dlist, plist;
                    switch (platformOrder.getType()) {
                        case "alipay":
                            dlist = dailyAnalyse(agentReportResponse.getDepositList(), "支付宝", platformOrder.getPayMoney());
                            plist = dailyAnalyse(agentReportResponse.getProfitList(), "支付宝", platformOrder.getPayMoney() * agentReportResponse.getAlipay() / 100);
                            agentReportResponse.setDepositList(dlist);
                            agentReportResponse.setProfitList(plist);
                            agentReportResponseMap.put(merchant.getApplyId(), agentReportResponse);
                            break;
                        case "wechat":
                            dlist = dailyAnalyse(agentReportResponse.getDepositList(), "微信", platformOrder.getPayMoney());
                            plist = dailyAnalyse(agentReportResponse.getProfitList(), "微信", platformOrder.getPayMoney() * agentReportResponse.getWechat() / 100);
                            agentReportResponse.setDepositList(dlist);
                            agentReportResponse.setProfitList(plist);
                            agentReportResponseMap.put(merchant.getApplyId(), agentReportResponse);
                            break;
                        case "cloudpay":
//                            dlist = dailyAnalyse(agentReportResponse.getDepositList(), "云闪付", platformOrder.getPayMoney());
//                            plist = dailyAnalyse(agentReportResponse.getProfitList(), "云闪付", platformOrder.getPayMoney() * agentReportResponse.getAlipay());
//                            agentReportResponse.setDepositList(dlist);
//                            agentReportResponse.setProfitList(plist);
                            break;
                    }
                }
            }
        }
        if (withdrewOrders.size() > 0) { // 提款
            for (WithdrewOrder withdrewOrder : withdrewOrders) {
                if (withdrewOrder.getState() != WithdrewState.SUCCESS) continue; // 过滤未成功的提款
                Agent agent = agentMap.get(withdrewOrder.getApplicantId());
                if (agent != null) {
                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(agent.getUser().getId());
                    if (agentReportResponse != null) {
                        agentReportResponse.setWithdrewed(withdrewOrder.getMoney() + agentReportResponse.getWithdrewed());
                        agentReportResponseMap.put(agent.getUser().getId(), agentReportResponse);
                    }
                }
            }
        }

        for (Map.Entry<Integer, AgentReportResponse> entry : agentReportResponseMap.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * 量分析，当天的支付宝/微信/云闪付成功订单多少
     *
     * @param platformAnalyses 量分析数组
     * @param type             订单类型
     * @param money            订单金额
     * @return
     */
    private List<PlatformAnalyse> dailyAnalyse(List<PlatformAnalyse> platformAnalyses, String type, double money) {
        PlatformAnalyse temp = null;
        if (platformAnalyses.size() > 0) {
            for (PlatformAnalyse p : platformAnalyses) {
                if (type.equals(p.getType())) {
                    temp = p;
                    platformAnalyses.remove(p);
                    break;
                }
            }
        }
        if (temp == null) {
            temp = new PlatformAnalyse("支付宝", money);
        } else {
            temp.setMoney(temp.getMoney() + money);
        }
        platformAnalyses.add(temp);
        return platformAnalyses;
    }
}
