package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.parameters.user.MerchantAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MerchantController {
    private final MerchantBlService merchantBlService;

    @Autowired
    public MerchantController(MerchantBlService merchantBlService) {
        this.merchantBlService = merchantBlService;
    }

    @ApiOperation(value = "新增商家", notes = "代理/管理员新增商家")
    @RequestMapping(value = "merchant/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addAgent(@RequestBody MerchantAddParameters merchantAddParameters) throws UsernameIsExistentException {
        Merchant merchant = new Merchant(merchantAddParameters.getUsername(), "123", merchantAddParameters.getAlipay(), merchantAddParameters.getWechat(), merchantAddParameters.getBalance(), merchantAddParameters.getStatus(), merchantAddParameters.getCode(), merchantAddParameters.getTime());
        MerchantAddResponse merchantAddResponse= merchantBlService.addMerchant(merchant);
        return new ResponseEntity<>(merchantAddResponse, HttpStatus.OK);
    }



}
