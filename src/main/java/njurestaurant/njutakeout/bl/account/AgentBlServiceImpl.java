package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.AgentBlService;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     */
    @Override
    public AgentAddResponse addAgent(Agent agent){
        return new AgentAddResponse(agentDataService.saveAgent(agent).getId());
    }

    /**
     * delete the agent by id
     *
     * @param id the agent id
     */
    @Override
    public void delAgentById(int id) {
        agentDataService.deleteAgentById(id);
    }

    @Override
    public List<Agent> findAllAgents() {
        List<Agent> agents = agentDataService.getAllAgent();
        if(agents.size() != 0) {
            for(Agent merchant : agents) {
                List<PersonalCard> cardList = merchant.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
            }
        }
        return agents;
    }
}
