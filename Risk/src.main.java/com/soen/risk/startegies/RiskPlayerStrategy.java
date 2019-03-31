package com.soen.risk.startegies;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * It contains declaration of the reinforce, attack and fortify methods
 
 *
 */
public interface RiskPlayerStrategy {

	/**
	 * This method will return strategy Name
	 * 
	 * @return strategy name
	 */
	public String getStrategyName();

	/**
	 * This method will return true if strategy is a bot
	 * 
	 * @return true or false
	 */
	public boolean getIsBot();

	/**
	 * This method will execute reinforce method for the Strategy
	 * 
	 * @param player
	 *            Input Player
	 * @return true or false
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList);
	/**
	 * This method will execute attack method for the Strategy
	 * 
	 * @param attackerPlayer
	 *            Player Object
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap);

	/**
	 * This method will execute fortify method for the Strategy
	 * 
	 * @param player
	 *            Player Object
	 * @return true or false
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap);

}
