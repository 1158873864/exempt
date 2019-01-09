package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.company.TeamBlService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TeamController {
    private final TeamBlService teamBlService;

    @Autowired
    public TeamController(TeamBlService teamBlService) {
        this.teamBlService = teamBlService;
    }

    @ApiOperation(value = "新增团队", notes = "公司管理员新增团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supervisor", value = "主管账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "area", value = "地区分布", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "operator", value = "添加人", required = true, dataType = "String"),
            @ApiImplicitParam(name = "addTime", value = "添加时间", required = true, dataType = "Date"),
    })
    @RequestMapping(value = "team/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addTeam(@RequestBody TeamAddParameters teamAddParameters) {
        TeamAddResponse teamAddResponse = teamBlService.addTeam(teamAddParameters);
        return new ResponseEntity<>(teamAddResponse, HttpStatus.OK);
    }
}
