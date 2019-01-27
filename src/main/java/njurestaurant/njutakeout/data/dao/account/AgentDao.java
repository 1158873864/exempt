package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentDao extends JpaRepository<Agent, Integer> {
    Agent findAgentByAgentName(String name);
    Agent findByUserId(int id);
}
