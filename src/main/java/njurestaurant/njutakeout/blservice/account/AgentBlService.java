package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import org.springframework.stereotype.Service;

@Service
public interface AgentBlService {
    /**
     * add a new agent
     * @param agent the info of the agent
     * @return
     */
    AgentAddResponse addAgent(Agent agent) throws UsernameIsExistentException;
}
