/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class RiskCountryAssignmentToPlayer {
	RiskMapBuilder riskMapBuilder;


	/**
	 * Assign countries to each player randomly.
	 */
	public void assignCountries() {

//two argument List<riskPlayers> and List of all territories
		List<RiskPlayer> players = new ArrayList<RiskPlayer>();

		List<RiskTerritory> countries = new ArrayList<RiskTerritory>();
		riskMapBuilder = new RiskMapBuilder();
		countries=riskMapBuilder.getTerritoryList();

		List<RiskTerritory> shuffledCountries = new ArrayList<RiskTerritory>();
		shuffledCountries=countries;
		Collections.shuffle(shuffledCountries);

		Map<RiskPlayer, List<RiskTerritory>> playerCountryMap = new HashMap<RiskPlayer, List<RiskTerritory>>();
		for (int i = 0; i < shuffledCountries.size(); i = i + players.size()) {
			for (int j = 0; j < players.size(); j++) {
				if ((i + j) >= shuffledCountries.size()) {
					break;
				}

				RiskPlayer currentPlayer = players.get(j);
				List<RiskTerritory> playerCountries = playerCountryMap.get(currentPlayer);
				if (playerCountries.size() == 0) {
					playerCountries = new ArrayList<>();
				}
				RiskTerritory playerAssignedCountry = shuffledCountries.get(i + j);
				// TODO: Handle adjacent country
				playerCountries.add(playerAssignedCountry);
				playerCountryMap.put(currentPlayer, playerCountries);
			}
		}

		for (Map.Entry<RiskPlayer, List<RiskTerritory>> stringListEntry : playerCountryMap.entrySet()) {
			String player = stringListEntry.getKey().getPlayerName();
			List<RiskTerritory> value = stringListEntry.getValue();
			System.out.println("-> " + player + " <- has " + value.size() + " countries");
			System.out.println("\t\t" + value);
		}
	}
}
