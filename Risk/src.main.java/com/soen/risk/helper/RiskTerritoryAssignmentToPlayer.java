/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryAssignmentToPlayer defines number of the armies given to the player based 
 * on the number of players. 
 *
 * @author Chirag
 * @version 1.0
 */
public class RiskTerritoryAssignmentToPlayer {
	RiskMapBuilder riskMapBuilder;


	/**
	 * Assign countries to each player randomly.
	 * @param riskTerritoryList 
	 * @param riskPlayerList 
	 */
	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignTerritory(List<RiskPlayer> riskPlayerList, List<RiskTerritory> riskTerritoryList) {

//two argument List<riskPlayers> and List of all territories
		List<RiskPlayer> players = new ArrayList<RiskPlayer>();
		List<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		riskMapBuilder = new RiskMapBuilder();
		players=riskPlayerList;
		territories=riskTerritoryList;

		List<RiskTerritory> shuffledTerritories = new ArrayList<RiskTerritory>();
		shuffledTerritories=territories;
		Collections.shuffle(shuffledTerritories);

		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
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
				// TODO: Handle adjacent country
				playerTerritories.add(playerAssignedTerritory);
				playerTerritoryMap.put(currentPlayer, playerTerritories);
			}
		}

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> stringListEntry : playerTerritoryMap.entrySet()) {
			String player = stringListEntry.getKey().getPlayerName();
			List<RiskTerritory> value = stringListEntry.getValue();
			System.out.println("-> " + player + " <- has " + value.size() + " Territories");
			System.out.println("\t\t" + value);
		}
		return playerTerritoryMap;
	}
}