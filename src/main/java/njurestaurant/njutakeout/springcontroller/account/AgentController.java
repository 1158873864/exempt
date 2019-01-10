package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.account.AgentBlService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.parameters.company.CompanyCardAddParameters;
import njurestaurant.njutakeout.parameters.user.AgentAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.CompanyCardAddResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgentController {
    private final AgentBlService agentBlService;

    @ApiOperation(value = "新增代理商", notes = "管理员新增代理")
    @RequestMapping(value = "usr/agent/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addAgent(@RequestBody AgentAddParameters agentAddParameters) throws UsernameIsExistentException {
        Agent agent = new Agent(agentAddParameters.getUsername(), agentAddParameters.getFlow(), agentAddParameters.getStatus(), agentAddParameters.getCode(), agentAddParameters.getBrokerage());
        AgentAddResponse agentAddResponse = agentBlService.addAgent(agent);
        return new ResponseEntity<>(agentAddResponse, HttpStatus.OK);
    }

    @Autowired
    public AgentController(AgentBlService agentBlService) {
        this.agentBlService = agentBlService;
    }
}
