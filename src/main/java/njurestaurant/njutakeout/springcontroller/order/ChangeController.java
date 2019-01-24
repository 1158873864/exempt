package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.ChangeBlService;
import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.QRcodeChangeParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewDealParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.transaction.FailedToLoadCodeResponse;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ChangeController {

    private final ChangeBlService changeBlService;

    @Autowired
    public ChangeController(ChangeBlService changeBlService) {
        this.changeBlService = changeBlService;
    }

    @ApiOperation(value = "内部码账变", notes = "发起内部码（支付宝）余额提现到卡申请")
    @RequestMapping(value = "internalaccountchange/qrcode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> QRcodeChangeOrder(@RequestBody QRcodeChangeParameters QRcodeChangeParameters) {
        try {
            changeBlService.addQRcodeChangeOrder(QRcodeChangeParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出提现成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该提现操作。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "提现金额大于该用户现有的余额。")), HttpStatus.OK);
        }catch (PersonalCardDoesNotExistException e){
            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
        } catch (AlipayNotExistException e) {
            return new ResponseEntity<>(new JSONResponse(1001, new WrongResponse(1001, "支付宝账号不存在。")), HttpStatus.OK);
        }
    }
    
    @ApiOperation(value = "内部卡账变(个人卡转入公司卡)", notes = "发起内部卡转账申请(个人卡转入公司卡)")
    @RequestMapping(value = "internalaccountchange/P2Ccard", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> P2CCardChangeOrder(@RequestBody CardChangeParameters CardChangeParameters) {
        try {
            changeBlService.addP2CCardChangeOrder(CardChangeParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出转账成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该转账操作。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "提现金额大于该用户现有的余额。")), HttpStatus.OK);
        }catch (PersonalCardDoesNotExistException e){
            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
        }catch (CompanyCardDoesNotExistException e){
            return new ResponseEntity<>(new JSONResponse(10412, new WrongResponse(10412, "公司银行卡不存在。")), HttpStatus.OK);
        }
    }
    @ApiOperation(value = "内部卡账变(公司卡转入个人卡)", notes = "发起内部卡转账申请(公司卡转入个人卡)")
    @RequestMapping(value = "internalaccountchange/C2Pcard", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> C2PCardChangeOrder(@RequestBody CardChangeParameters CardChangeParameters) {
        try {
            changeBlService.addC2PCardChangeOrder(CardChangeParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出转账成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该转账操作。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "提现金额大于该用户现有的余额。")), HttpStatus.OK);
        }catch (PersonalCardDoesNotExistException e){
            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
        }catch (CompanyCardDoesNotExistException e){
            return new ResponseEntity<>(new JSONResponse(10412, new WrongResponse(10412, "公司银行卡不存在。")), HttpStatus.OK);
        }
    }
}
