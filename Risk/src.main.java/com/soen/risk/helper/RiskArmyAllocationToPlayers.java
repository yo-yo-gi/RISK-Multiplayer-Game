/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Army Allocation to player class</h2>
 * The Class is used in assignment phase where 
 * the player is given a number of armies in round robin fashion
 * depending on the number of countries he owns.
 *
 * @author Chirag Vora
 * @version 1.0
 */

public class RiskArmyAllocationToPlayers {
	RiskLogger logger= new RiskLogger();
	
	/**
	 * Assign armies to each players for the countries they own in round robin fashion.
	 * @return 
	 */
	
	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignArmiesToPlayers(Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap ) {
		logger.doLogging("Allocating armies to players in round robin fashion------");
        System.out.println();
		System.out.println("Allocating armies to players in round robin fashion...");
        System.out.println();
		ArrayList<RiskTerritory> territories;
		RiskPlayer currentPlayer;

		int lastAssignedTerritoryIndex = Constants.ZERO;
		List<RiskPlayer> playerList = new ArrayList<RiskPlayer>(playerTerritoryMap.keySet());

		for (int j = 0; j < playerList.size(); j++) {
			territories= new ArrayList<RiskTerritory>();
			territories=playerTerritoryMap.get(playerList.get(j));
			currentPlayer=(RiskPlayer) playerList.get(j);
			int armiesToAssign = currentPlayer.getArmiesOwned() ;
			for (int i = 0; i < armiesToAssign; i++) {
				if (lastAssignedTerritoryIndex >= territories.size()) {
					lastAssignedTerritoryIndex = Constants.ZERO;
				}
				
				RiskTerritory lastAssignedTerritory = territories.get(lastAssignedTerritoryIndex);
				Integer currentArmies=lastAssignedTerritory.getArmiesPresent();
				lastAssignedTerritory.setArmiesPresent(currentArmies+1);
				lastAssignedTerritoryIndex++;
			}
			playerTerritoryMap.put(currentPlayer, territories);
            System.out.println();
			System.out.println("Player: "+currentPlayer.getPlayerName()+" has territories with their respective armies");
			for (RiskTerritory riskTerritory : territories) {
				System.out.println(riskTerritory.getTerritoryName()+"("+riskTerritory.getArmiesPresent()+")");
			}
		}
		
		logger.doLogging("playerTerritoryMap returned------"+playerTerritoryMap);
		return playerTerritoryMap;
	}
}
