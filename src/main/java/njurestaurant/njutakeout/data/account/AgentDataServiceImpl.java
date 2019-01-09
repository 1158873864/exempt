package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.AgentDao;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentDataServiceImpl implements AgentDataService {

    private final AgentDao agentDao;

    @Autowired
    public AgentDataServiceImpl(AgentDao agentDao) {
        this.agentDao = agentDao;
    }

    /**
     * save a agent
     *
     * @param agent the information of agent
     */
    @Override
    public Agent saveAgent(Agent agent) {
        return agentDao.save(agent);
    }

    @Override
    public boolean isAgentExistentByName(String name) {
        return agentDao.findAgentByAgentName(name) != null;
    }
}
