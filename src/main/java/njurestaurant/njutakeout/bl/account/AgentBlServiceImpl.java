package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.AgentBlService;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentBlServiceImpl implements AgentBlService {
    private final AgentDataService agentDataService;

    @Autowired
    public AgentBlServiceImpl(AgentDataService agentDataService) {
        this.agentDataService = agentDataService;
    }

    /**
     * add the agent
     * @param agent the info of the agent
     * @return
     * @throws UsernameIsExistentException
     */
    @Override
    public AgentAddResponse addAgent(Agent agent) throws UsernameIsExistentException {
        if(agentDataService.isAgentExistentByName(agent.getAgentName())) {
            throw new UsernameIsExistentException();
        } else {
            Agent tmp = agentDataService.saveAgent(agent);
            return new AgentAddResponse(tmp.getId());
        }
    }
}
