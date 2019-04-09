/*
 * 
 */
package com.soen.risk.startegies;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Risk Player Strategy</h2>
 * RiskPlayerStrategy interface
 * It contains declaration of the reinforce, attack and fortify methods.
 * 
 * @author Chirag Vora
 * @version 3.0
 */
public interface RiskPlayerStrategy {

	/**
	 * This method will execute reinforce method for the Strategy.
	 *
	 * @param gameMap the game map
	 * @param riskContinentList the risk continent list
	 * @return true or false
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList);

	/**
	 * This method will execute attack method for the Strategy.
	 *
	 * @param gameMap the game map
	 * @return the linked hash map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap);

	/**
	 * This method will execute fortify method for the Strategy.
	 *
	 * @param gameMap the game map
	 * @return true or false
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap);

}
