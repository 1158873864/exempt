package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.TeamBlService;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamBlServiceImpl implements TeamBlService {
    private final TeamDataService teamDataService;

    @Autowired
    public TeamBlServiceImpl(TeamDataService teamDataService) {
        this.teamDataService = teamDataService;
    }

    @Override
    public TeamAddResponse addTeam(TeamAddParameters teamAddParameters) {
        Team team =  new Team(teamAddParameters.getSupervisor(), teamAddParameters.getArea(), teamAddParameters.getStatus(), teamAddParameters.getVerifyCode(), teamAddParameters.getOperator(), teamAddParameters.getAddTime());
        return new TeamAddResponse(team.getId());
    }
}
