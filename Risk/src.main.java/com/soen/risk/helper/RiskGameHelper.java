/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.model.RiskCard;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskDomination;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.view.RiskCardviewView;
import com.soen.risk.view.RiskDominationView;

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
	 * @param riskMainMap       map of players and territories own by player
	 * @param riskContinentList list of continents objects
	 * @return controlValueAssignedMap updated map of players and territories own by player
	 */

	public static LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> assignControlValuesToPlayer(
			Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, ArrayList<RiskContinent> riskContinentList) {

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> controlValueAssignedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		List<RiskContinent> continentList = new ArrayList<RiskContinent>(riskContinentList);

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {

			RiskPlayer currentPlayer = entry.getKey();
			ArrayList<String> currentPlayerOwnedContinents = new ArrayList<String>(
					currentPlayer.getOccupiedContinents());
			ArrayList<RiskTerritory> currentPlayerTerritories = new ArrayList<RiskTerritory>(entry.getValue());
			List<String> currPlayerTerritoriesNames = new ArrayList<String>();
			ArrayList<String> currContTerriNames;

			for (RiskTerritory currTerritory : currentPlayerTerritories) {
				currPlayerTerritoriesNames.add(currTerritory.getTerritoryName());
			}

			// Populating currentPlayerOwnedContinents list
			for (RiskContinent currContinent : continentList) {
				currContTerriNames = new ArrayList<String>(currContinent.getIncludedTerritories());
				if (currPlayerTerritoriesNames.containsAll(currContTerriNames)) {
					currentPlayerOwnedContinents.add(currContinent.getContinentName());
				}
			}
			// Updating current player list of occupied continents
			currentPlayer.setOccupiedContinents(currentPlayerOwnedContinents);

			// Re populating map with updated player object
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
		float percentage = ((float) ownedTerritories) / totalTerritories;
		mapControll = String.format("%2.02f",(float)(percentage*100));
		return mapControll;
	}

	/**
	 * Finds the territory by name to use in attack phase
	 * 
	 * @param riskMap       map of player
	 * @param territoryName name of territory to search
	 * @return riskTerritory territory to be returned
	 */
	public static RiskTerritory getRiskTerritoryByName(Map<RiskPlayer, ArrayList<RiskTerritory>> riskMap,
			String territoryName) {
		RiskTerritory riskTerritory = new RiskTerritory();
		ArrayList<RiskTerritory> territoryList = new ArrayList<RiskTerritory>();
		boolean foundFlag = false;
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMap.entrySet()) {
			territoryList.addAll(entry.getValue());
		}

		for (RiskTerritory currTerritory : territoryList) {
			if (currTerritory.getTerritoryName().equalsIgnoreCase(territoryName)) {
				riskTerritory = currTerritory;
				foundFlag = true;
			}
		}

		if (!foundFlag) {
			riskTerritory.setArmiesPresent(-1);
		}

		return riskTerritory;
	}

	/**
	 * Method to update army after attack and to delete defender's territory. Also
	 * checks if defenders territory is last available then assign all cards to
	 * current player
	 * 
	 * @param attackSourceTerritory      source territory of attack
	 * @param attackDestinationTerritory destination territory of attack
	 * @param riskMainMap                Map of players with respective territory
	 * @return updatedMap Updated map after army updation
	 */
	public static LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> updateArmyAfterAttack(
			RiskTerritory attackSourceTerritory, RiskTerritory attackDestinationTerritory,
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		
		boolean defenderArmyZeroCheckFlag = false;
		ArrayList<RiskTerritory> currPlayerList = new ArrayList<RiskTerritory>();
		RiskPlayer currentPlayer = null, defenderPlayer = null;
		ArrayList<RiskTerritory> defenderPlayerTerritories = new ArrayList<RiskTerritory>();
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> updatedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
//		Finding current player and calculating total territories in map
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
				currPlayerList.addAll(entry.getValue());
				break;
			}			
		}

		
//		Updating source and destination armies after attack
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			for (RiskTerritory currRiskTerritory : entry.getValue()) {
				ArrayList<RiskTerritory> tempTerritoriesList = new ArrayList<RiskTerritory>(entry.getValue());
				
				if (currRiskTerritory.getTerritoryName().equalsIgnoreCase(attackSourceTerritory.getTerritoryName())) {
					tempTerritoriesList.set(entry.getValue().indexOf(currRiskTerritory), attackSourceTerritory);
					updatedMap.put(entry.getKey(), tempTerritoriesList);
				}
				if (currRiskTerritory.getTerritoryName().equalsIgnoreCase(attackDestinationTerritory.getTerritoryName())) {
					tempTerritoriesList.set(entry.getValue().indexOf(currRiskTerritory), attackDestinationTerritory);
					updatedMap.put(entry.getKey(), tempTerritoriesList);
				}				
			}
		}
//		Removing territory from defenders list and adding to attacker's if defender loses all army
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			ArrayList<RiskTerritory> tempTerritoryList = new ArrayList<RiskTerritory>(entry.getValue());
			for (RiskTerritory currRiskTerritory : tempTerritoryList) {
				if (currRiskTerritory.getArmiesPresent() == 0) {
					defenderArmyZeroCheckFlag = true;
					currPlayerList.add(currRiskTerritory);
					defenderPlayerTerritories = entry.getValue();
					defenderPlayer = entry.getKey();
					defenderPlayerTerritories.remove(defenderPlayerTerritories.indexOf(attackDestinationTerritory));
				}
			}
		}

		
//		Removing territory from defenders territory list and updating map
		if (defenderArmyZeroCheckFlag) {
			updatedMap.put(currentPlayer, currPlayerList);
			updatedMap.put(defenderPlayer, defenderPlayerTerritories);

			if (defenderPlayerTerritories.size() == 0) {
				for (RiskCard currCard : defenderPlayer.getCardOwned()) {
					currentPlayer.setCardOwned(currCard);
				}
				updatedMap.remove(defenderPlayer);
				updatedMap.put(currentPlayer, currPlayerList);
			}
		}

		return updatedMap;
	}

	/**
	 * 
	 * This function moves armies after win
	 * 
	 * @param attackMoveArmy
	 * @param attackSourceTerritory
	 * @param attackDestinationTerritory
	 * @param riskMainMap
	 * 
	 * @return
	 */
	public static LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> moveArmyAfterAttack(int attackMoveArmy,
			RiskTerritory attackSourceTerritory, RiskTerritory attackDestinationTerritory,
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {

		RiskTerritory sourceTerritory = null;
		RiskTerritory destinationTerritory = null;
		RiskPlayer currentPlayer = null;
		ArrayList<RiskTerritory> currPlayerList = new ArrayList<RiskTerritory>();
//		Domination view observer object initialization
		RiskDomination riskDominationObservable=new RiskDomination();
		RiskDominationView riskDominationObserver=new RiskDominationView(riskDominationObservable);
		int totalMapTerritories=0;

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
				currPlayerList.addAll(entry.getValue());
			}
			totalMapTerritories=totalMapTerritories+entry.getValue().size();
			for (RiskTerritory currTerriory : entry.getValue()) {
				if (currTerriory.getTerritoryName().equalsIgnoreCase(attackSourceTerritory.getTerritoryName())) {
					sourceTerritory = currTerriory;
				}
				if (currTerriory.getTerritoryName().equalsIgnoreCase(attackDestinationTerritory.getTerritoryName())) {
					destinationTerritory = currTerriory;
				}
			}
		}

		sourceTerritory.setArmiesPresent(sourceTerritory.getArmiesPresent() - attackMoveArmy);
		destinationTerritory.setArmiesPresent(destinationTerritory.getArmiesPresent() + attackMoveArmy);
		currPlayerList.set(currPlayerList.indexOf(attackSourceTerritory), sourceTerritory);
		currPlayerList.set(currPlayerList.indexOf(attackDestinationTerritory), destinationTerritory);
		
//		Calculating current player total armies in all territories and updating current player
		int currentPlayerArmies=0;
		for (RiskTerritory currTerritory : currPlayerList) {
			currentPlayerArmies=currentPlayerArmies+currTerritory.getArmiesPresent();
		}
		currentPlayer.setArmiesOwned(currentPlayerArmies);
		riskMainMap.put(currentPlayer, currPlayerList);
		
//		Triggering domination view when there is addition of new territory			
		riskDominationObservable.setPercentMapContr((RiskGameHelper.calculateDominationMapControlled(totalMapTerritories, currPlayerList.size())));
		riskDominationObservable.setContinentsContr(currentPlayer.getOccupiedContinents());
		riskDominationObservable.setArmiesOwned(currentPlayer.getArmiesOwned());
		
		return riskMainMap;
	}

	/**
	 * Function to assign random cards to the player after winning the territories
	 * 
	 * @param riskMainMap map with all players and respective territories
	 * @return riskMainMap map with all players with cards updated
	 */
	public static LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> assignRandomCard(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {

		RiskPlayer currentPlayer = null;
//		Finding current player turn
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
				break;
			}
		}
		RiskCardviewView riskCardviewView = new RiskCardviewView(currentPlayer);
		int randomCardNumber = (int) ((Math.random() * 100) % 3);

		switch (randomCardNumber) {
		case 1:
			currentPlayer.setCardOwned(RiskCard.INFANT);
			break;

		case 2:
			currentPlayer.setCardOwned(RiskCard.CAVALRY);
			break;

		case 3:
			currentPlayer.setCardOwned(RiskCard.ARTILLERY);
			break;
		}
		return riskMainMap;
	}
}
