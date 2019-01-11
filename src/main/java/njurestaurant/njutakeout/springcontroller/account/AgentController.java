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

    @Autowired
    public AgentController(AgentBlService agentBlService) {
        this.agentBlService = agentBlService;
    }

}
