package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.AgentBlService;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import njurestaurant.njutakeout.response.user.AgentInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<AgentInfoResponse> findAllAgents() {
        List<Agent> agents = agentDataService.getAllAgent();
        List<AgentInfoResponse> agentInfoResponses = new ArrayList<>();
        if(agents.size() != 0) {
            for(Agent merchant : agents) {
                List<PersonalCard> cardList = merchant.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
            }
            agentInfoResponses = agents.stream().map(agent -> {
                double flow = AgentDailyFlow.flow.containsKey(agent.getId()) ? AgentDailyFlow.flow.get(agent.getId()) : 0;
                double commission = AgentDailyFlow.commission.containsKey(agent.getId()) ? AgentDailyFlow.commission.get(agent.getId()) : 0;
                return new AgentInfoResponse(agent.getId(), agent.getUser().getId(), agent.getAgentName(), agent.getStatus(), agent.getAlipay(), agent.getWechat(), agent.getBalance(), agent.getUser(), flow, commission);
            }).collect(Collectors.toList());
        }
        return agentInfoResponses;
    }
}
