package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.TeamBlService;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeamBlServiceImpl implements TeamBlService {
    private final TeamDataService teamDataService;

    @Autowired
    public TeamBlServiceImpl(TeamDataService teamDataService) {
        this.teamDataService = teamDataService;
    }

    /**
     *
     * @param teamAddParameters params of the new team
     * @return
     */
    @Override
    public TeamAddResponse addTeam(TeamAddParameters teamAddParameters) {
        Team team =  new Team(teamAddParameters.getSupervisor(), teamAddParameters.getArea(), teamAddParameters.getStatus(), teamAddParameters.getVerifyCode(), teamAddParameters.getOperator(), new Date(), teamAddParameters.getTeamName());
        return new TeamAddResponse(teamDataService.saveTeam(team).getId());
    }

    /**
     * load all teams
     *
     * @return
     */
    @Override
    public List<Team> loadAllTeam() {
        return teamDataService.findAllTeams();
    }
}
