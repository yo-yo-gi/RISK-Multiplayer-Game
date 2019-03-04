/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Game play helper</h2>
 * This class is used for writing utility functions related to game play.
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0
  */
public class RiskGamehelper {

	
	/**
	 * updates existing map player with continents own by players
	 * 
	 * @param riskMainMap map of players and territories own by player
	 * @param riskContinentList list of continents objects
	 * @return controlValueAssignedMap updated map of players and territories own by player
	 */
	
	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignControlValuesToPlayer(
			Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, ArrayList<RiskContinent> riskContinentList) {
			
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> controlValueAssignedMap= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		List<RiskContinent> continentList= new ArrayList<RiskContinent>(riskContinentList);
				
		 for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()){
		        
			 RiskPlayer currentPlayer=entry.getKey();
			 ArrayList<String> currentPlayerOwnedContinents=new ArrayList<String>(currentPlayer.getOccupiedContinents());
			 ArrayList<RiskTerritory> currentPlayerTerritories=new ArrayList<RiskTerritory>(entry.getValue());
			 List<String> currPlayerTerritoriesNames=new ArrayList<String>();
			 ArrayList<String> currContTerriNames;
            //		System.out.println(entry.getKey()+" "+entry.getValue());
			 
			 
			 for (RiskTerritory currTerritory : currentPlayerTerritories) {
				 currPlayerTerritoriesNames.add(currTerritory.getTerritoryName());
			}
			 
            //		Populating currentPlayerOwnedContinents list
			 for (RiskContinent currContinent : continentList) {
				 currContTerriNames=new ArrayList<String>(currContinent.getIncludedTerritories());
				 if (currPlayerTerritoriesNames.containsAll(currContTerriNames)) {
					 currentPlayerOwnedContinents.add(currContinent.getContinentName());
				}
			}
            //		Updating current player list of occupied continents
			currentPlayer.setOccupiedContinents(currentPlayerOwnedContinents);
			 
            //		Re populating map with updated player object
			controlValueAssignedMap.put(currentPlayer, currentPlayerTerritories);			 
			 
		    }

		return controlValueAssignedMap;
	}

}
