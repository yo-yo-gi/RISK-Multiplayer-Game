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
	 * @return 
	 */
	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignArmiesToPlayers(Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap ) {
		ArrayList<RiskTerritory> territories;
		RiskPlayer currentPlayer;

		int lastAssignedTerritoryIndex = 0;
		List<RiskPlayer> playerList = new ArrayList<RiskPlayer>(playerTerritoryMap.keySet());

		for (int j = 0; j < playerList.size(); j++) {
			territories= new ArrayList<RiskTerritory>();
			territories=playerTerritoryMap.get(playerList.get(j));
			currentPlayer=(RiskPlayer) playerList.get(j);
			int armiesToAssign = currentPlayer.getArmiesOwned() ;
			for (int i = 0; i < armiesToAssign; i++) {
				if (lastAssignedTerritoryIndex >= territories.size()) {
					lastAssignedTerritoryIndex = 0;
				}
				RiskTerritory lastAssignedTerritory = territories.get(lastAssignedTerritoryIndex);
				Integer currentArmies=lastAssignedTerritory.getArmiesPresent();
				lastAssignedTerritory.setArmiesPresent(currentArmies+1);
				lastAssignedTerritoryIndex++;
			}
			playerTerritoryMap.put(currentPlayer, territories);

			for (RiskTerritory riskTerritory : territories) {
				System.out.println(riskTerritory.getTerritoryName() + " armies in " + riskTerritory.getArmiesPresent());
			}
		}
		return playerTerritoryMap;
	}
}