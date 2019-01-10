package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.account.PersonalCardBlService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.parameters.user.PersonalCardAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonalCardController {
    private final PersonalCardBlService personalCardBlService;

    @Autowired
    public PersonalCardController(PersonalCardBlService personalCardBlService) {
        this.personalCardBlService = personalCardBlService;
    }

    @ApiOperation(value = "新增银行卡", notes = "用户个人银行卡增加")
    @RequestMapping(value = "usr/card/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPersonalCard(@RequestBody PersonalCardAddParameters personalCardAddParameters) throws UsernameIsExistentException {
        PersonalCard personalCard = new PersonalCard(personalCardAddParameters.getNumber(), personalCardAddParameters.getName(), personalCardAddParameters.getBank(), personalCardAddParameters.getAccountOfBank(), personalCardAddParameters.getBin(), personalCardAddParameters.getStatus());
        PersonalCardAddResponse personalCardAddResponse= personalCardBlService.addPersonalCard(personalCard);
        return new ResponseEntity<>(personalCardAddResponse, HttpStatus.OK);
    }
}
