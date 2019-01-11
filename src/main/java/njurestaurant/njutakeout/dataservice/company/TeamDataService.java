package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.Team;

import java.util.List;

public interface TeamDataService {
    /**
     * save the team
     *
     * @param team
     * @return
     */
    Team saveTeam(Team team);

    /**
     * find all teams
     *
     * @return
     */
    List<Team> findAllTeams();
}
