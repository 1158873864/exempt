package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team, String> {
    Team findTeamByTeamName(String teamName);
}
