/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

// TODO: Auto-generated Javadoc
/**
 * The Class ArmyAllocationToPlayers.
 * In the assignment phase, the player is given a number of armies in
 * round robin fashion depending on the number of countries he owns.
 *
 * @author Chirag
 * @version 1.0
 */
public class RiskArmyAllocationToPlayers {
	/**
	 * Assign armies to each players for the countries they own in round robin fashion.
	 */
	public void assignArmiesToPlayers(Map<RiskPlayer, List<RiskTerritory>> playerCountryMap ) {
		List<RiskTerritory> countries;
		RiskPlayer currentPlayer;

		int lastAssignedCountryIndex = 0;
		List<RiskPlayer> playerList = new ArrayList<RiskPlayer>(playerCountryMap.keySet());

		for (int j = 0; j < playerList.size(); j++) {
			countries= new ArrayList<RiskTerritory>();
			countries=playerCountryMap.get(playerList.get(j));
			currentPlayer=(RiskPlayer) playerList.get(j);
			int armiesToAssign = currentPlayer.getArmiesOwned() ;
			for (int i = 0; i < armiesToAssign; i++) {
				if (lastAssignedCountryIndex >= countries.size()) {
					lastAssignedCountryIndex = 0;
				}
				RiskTerritory lastAssignedCountry = countries.get(lastAssignedCountryIndex);
				Integer currentArmies=lastAssignedCountry.getArmiesPresent();
				lastAssignedCountry.setArmiesPresent(currentArmies+1);
				lastAssignedCountryIndex++;
			}
			playerCountryMap.put(currentPlayer, countries);

			for (RiskTerritory riskTerritory : countries) {
				System.out.println(riskTerritory.getTerritoryName() + " armies in " + riskTerritory.getArmiesPresent());
			}
		}
	}
}
