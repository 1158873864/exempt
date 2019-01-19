package njurestaurant.njutakeout.blservice.report;

import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.response.report.AgentReportResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;

import java.util.Date;
import java.util.List;

public interface ReportBlService {
    List<MerchantReportResponse> getReportOfMerchant(Date date);

    List<AgentReportResponse> getReportOfAgent(Date startDate, Date endDate) throws WrongInputException;
}
