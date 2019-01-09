package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Agent;

public interface AgentDataService {
    /**
     * save a agent
     *
     * @param agent the information of agent
     */
    Agent saveAgent(Agent agent);

    /**
     * find the agent whether exist
     * @param name account name of agent
     */
    boolean isAgentExistentByName(String name);
}
