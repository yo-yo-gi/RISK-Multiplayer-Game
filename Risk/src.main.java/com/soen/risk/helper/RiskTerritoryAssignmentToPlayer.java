/**
 * 
 */
package com.soen.risk.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Territory assignment class</h2>
 * The Class CountryAssignmentToPlayer defines number of the armies 
 * given to the player based on the number of players. 
 *
 * @author Chirag Vora
 * @version 1.0
 */
public class RiskTerritoryAssignmentToPlayer implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6801860287742689318L;

	/** The risk map builder. */
	RiskMapBuilder riskMapBuilder;

	/**
	 * Assign countries to each player randomly.
	 *
	 * @param riskPlayerList the risk player list
	 * @param riskTerritoryList the risk territory list
	 * @return the map
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> assignTerritory(List<RiskPlayer> riskPlayerList, List<RiskTerritory> riskTerritoryList) {
		System.out.println();
		System.out.println("Start up phase...");
		System.out.println("Assignment of territories to Player...");
		List<RiskPlayer> players = new ArrayList<RiskPlayer>();
		List<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		riskMapBuilder = new RiskMapBuilder();
		players=riskPlayerList;
		territories=riskTerritoryList;

		List<RiskTerritory> shuffledTerritories = new ArrayList<RiskTerritory>();
		shuffledTerritories=territories;
		Collections.shuffle(shuffledTerritories);

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		for (RiskPlayer currentPlayer : riskPlayerList) {
			playerTerritoryMap.put(currentPlayer, new ArrayList<RiskTerritory>());
		}

		for (int i = 0; i < shuffledTerritories.size(); i = i + players.size()) {
			for (int j = 0; j < players.size(); j++) {
				if ((i + j) >= shuffledTerritories.size()) {
					break;
				}

				RiskPlayer currentPlayer = players.get(j);
				ArrayList<RiskTerritory> playerTerritories = playerTerritoryMap.get(currentPlayer);
				if (playerTerritories.size() == 0 || null==playerTerritories) {
					playerTerritories = new ArrayList<>();
				}
				RiskTerritory playerAssignedTerritory = shuffledTerritories.get(i + j);
				playerTerritories.add(playerAssignedTerritory);
				playerTerritoryMap.put(currentPlayer, playerTerritories);
			}
		}

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> stringListEntry : playerTerritoryMap.entrySet()) {
			String player = stringListEntry.getKey().getPlayerName();
			List<RiskTerritory> value = stringListEntry.getValue();
			System.out.println("Player: "+"-> " + player + " <- has " + value.size() + " Territories");

		}
		return playerTerritoryMap;
	}
}
