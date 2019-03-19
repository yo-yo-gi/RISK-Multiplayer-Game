/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class RiskGameHelper {


	/**
	 * updates existing map player with continents own by players.
	 *
	 * @param riskMainMap map of players and territories own by player
	 * @param riskContinentList list of continents objects
	 * @return controlValueAssignedMap updated map of players and territories own by player
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> assignControlValuesToPlayer(
			Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, ArrayList<RiskContinent> riskContinentList) {

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> controlValueAssignedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		List<RiskContinent> continentList= new ArrayList<RiskContinent>(riskContinentList);

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()){

			RiskPlayer currentPlayer=entry.getKey();
			ArrayList<String> currentPlayerOwnedContinents=new ArrayList<String>(currentPlayer.getOccupiedContinents());
			ArrayList<RiskTerritory> currentPlayerTerritories=new ArrayList<RiskTerritory>(entry.getValue());
			List<String> currPlayerTerritoriesNames=new ArrayList<String>();
			ArrayList<String> currContTerriNames;
			


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
	
	/**
	 * Calculate initial armies.
	 *
	 * @param riskPlayersNames the risk players names
	 * @return the int
	 */
	public static int calculateInitialArmies(List<String> riskPlayersNames) {
		int numOfPlayers = riskPlayersNames.size();
		int armies = Constants.ZERO;

		if (numOfPlayers == 3)
			armies = 5;
		else if (numOfPlayers == 4)
			armies = 30; 
		else if (numOfPlayers == 5)
			armies = 25;
		else if (numOfPlayers == 6)
			armies = 20;

		return armies;
	}
	
	/**
	 * This method calculates map control for the domination view
	 * 
	 * @param totalTerritories total territories in map
	 * @param ownedTerritories player owned territories
	 * @return mapControll percentage of map controlled by player
	 */
	public static String calculateDominationMapControlled(long totalTerritories, long ownedTerritories) {
		String mapControll = null;
		
		long calculation = Math.round((totalTerritories/ownedTerritories)*100.0);
		mapControll=Long.toString(calculation);
		return mapControll;
	}
	
	/**
	 * Finds the territory by name to use in attack phase
	 * 
	 * @param riskMap map of player
	 * @param territoryName name of territory to search
	 * @return riskTerritory territory to be returned
	 */
	public static RiskTerritory getRiskTerritoryByName(Map<RiskPlayer, ArrayList<RiskTerritory>> riskMap, String territoryName) {
		RiskTerritory riskTerritory = new RiskTerritory();
		ArrayList<RiskTerritory> territoryList=new ArrayList<RiskTerritory>();
		boolean foundFlag=false;
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMap.entrySet()){
			territoryList.addAll(entry.getValue());
		}
		
		for (RiskTerritory currTerritory : territoryList) {
			if (currTerritory.getTerritoryName().equalsIgnoreCase(territoryName)) {
				riskTerritory=currTerritory;
				foundFlag=true;
			}
		}
		
		if (!foundFlag) {
			riskTerritory.setArmiesPresent(-1);
		}
		
		return riskTerritory;
	}
}
