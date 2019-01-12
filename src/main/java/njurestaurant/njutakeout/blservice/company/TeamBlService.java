package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamBlService {
    /**
     * add a new team
     *
     * @param teamAddParameters params of the new team
     * @return
     */
    TeamAddResponse addTeam(TeamAddParameters teamAddParameters) throws IsExistentException, BlankInputException;

    /**
     * load all teams
     * @return
     */
    List<Team> loadAllTeam();
}
