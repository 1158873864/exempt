package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.TeamDao;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDataServiceImpl implements TeamDataService {
    private final TeamDao teamDao;

    @Autowired
    public TeamDataServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team saveTeam(Team team) {
        return teamDao.save(team);
    }
}
