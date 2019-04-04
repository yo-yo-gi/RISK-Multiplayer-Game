/**
 * 
 */
package com.soen.risk.startegies;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * The Class RiskCheaterStrategy strategy whose reinforce() method doubles the
 * number of armies on all its countries, whose attack() method automatically
 * conquers all the neighbors of all its countries, and whose fortify() method
 * doubles the number of armies on its countries that have neighbors that belong
 * to other players.
 *
 * @author Chirag Vora
 * @author Yogesh Nimbhorkar
 * @version 3.0
 */
public class RiskCheaterStrategy implements RiskPlayerStrategy {

	/** The current player. */
	RiskPlayer currentPlayer;

	/** The reinforcement territory. */
	ArrayList<RiskTerritory> reinforcementTerritoryList = new ArrayList<RiskTerritory>();

	/** The attacked map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;

	/** The reinforced map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;

	/** The fortified map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;

	/** The army. */
	int army;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#getStrategyName()
	 */
	@Override
	public String getStrategyName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#getIsBot()
	 */
	@Override
	public boolean getIsBot() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#reinforce(java.util.
	 * LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList) {

		ArrayList<RiskTerritory> currPlayerTerritories = new ArrayList<RiskTerritory>();
		reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		// find current player
		currentPlayer = getCurrentPlayer(gameMap);
		// getting current player territories
		currPlayerTerritories = gameMap.get(currentPlayer);
		// Double the armies on all its territories in reinforcement
		reinforcementTerritoryList = doubleArmy(currPlayerTerritories);

		reinforcedMap.put(currentPlayer, reinforcementTerritoryList);

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : reinforcedMap.entrySet()) {
			System.out.println(entry.getKey().getPlayerName());
			for (RiskTerritory currTerritory : entry.getValue()) {
				System.out.println(currTerritory.getTerritoryName() + "(" + currTerritory.getArmiesPresent() + ")");
			}
		}
		return reinforcedMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soen.risk.startegies.RiskPlayerStrategy#attack(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {
		attackedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);
		ArrayList<RiskTerritory> currPlayerList = new ArrayList<RiskTerritory>();
		ArrayList<String> terrListWithOpponentAdj = new ArrayList<String>();
		ArrayList<String> currPlayerStringList = new ArrayList<String>();

		// getting current player list
		currPlayerList.addAll(gameMap.get(currentPlayer));

		// getting own territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			currPlayerStringList.add(currTerritory.getTerritoryName());
		}

		// getting opponent territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			for (String curAdj : currTerritory.getAdjacents()) {
				if (currPlayerStringList.contains(curAdj)) {
					terrListWithOpponentAdj.add(currTerritory.getTerritoryName());
				}
			}
		}
		// adding list of opponent adjacent to current player list
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : gameMap.entrySet()) {
			for (RiskTerritory currTerritory : entry.getValue()) {
				//if (!(terrListWithOpponentAdj.contains(currTerritory.getTerritoryName()))) {
				if (terrListWithOpponentAdj.contains(currTerritory.getTerritoryName())) {
					currPlayerList.add(currTerritory);
				}
			}
		}
		//		Updating map with added territory list of current player with opponent
		attackedMap.put(currentPlayer, currPlayerList);

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : gameMap.entrySet()) {
			ArrayList<RiskTerritory> currentPlayerList = new ArrayList<RiskTerritory>(entry.getValue());
			for (RiskTerritory currTerritory : entry.getValue()) {
				if (terrListWithOpponentAdj.contains(currTerritory.getTerritoryName())
						&& entry.getKey() != currentPlayer) {
					currentPlayerList.remove(currTerritory);
				}
			}

			if (entry.getKey() != currentPlayer) 
				attackedMap.put(entry.getKey(), currentPlayerList);
		}

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(attackedMap);
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : tempMap.entrySet()) {
			if (entry.getValue().size()==0) {
				attackedMap.remove(entry.getKey());
			}
		}

		if (attackedMap.size()==1) {
			System.out.println("*****************************************");
			System.out.println(currentPlayer.getPlayerName() + "wins the match and conquered the world...\n Game Ends");
			System.out.println("*****************************************");
			System.exit(0);
		}


		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackedMap.entrySet()) {
			System.out.println(entry.getKey().getPlayerName());
			for (RiskTerritory currTerritory : entry.getValue()) {
				System.out.println(currTerritory.getTerritoryName() + "(" + currTerritory.getArmiesPresent() + ")");
			}
		}

		return attackedMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soen.risk.startegies.RiskPlayerStrategy#fortify(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {

		fortifiedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		ArrayList<RiskTerritory> currPlayerList = new ArrayList<RiskTerritory>();
		ArrayList<String> terrListWithOpponentAdj = new ArrayList<String>();
		ArrayList<String> currPlayerStringList = new ArrayList<String>();


		// getting current player list
		currPlayerList.addAll(gameMap.get(currentPlayer));

		// getting own territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			currPlayerStringList.add(currTerritory.getTerritoryName());
		}
		ArrayList<RiskTerritory> territoryList = new ArrayList<RiskTerritory>(currPlayerList);

		// getting opponent territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			for (String curAdj : currTerritory.getAdjacents()) {
				if (!(currPlayerStringList.contains(curAdj))) {
					terrListWithOpponentAdj.add(currTerritory.getTerritoryName());
				}
			}
		}

		for (RiskTerritory currTerritory : currPlayerList) {
			int index=territoryList.indexOf(currTerritory);
			army = (currTerritory.getArmiesPresent() * 2);
			currTerritory.setArmiesPresent(army);
			territoryList.set(index, currTerritory);
		}

		fortifiedMap.put(currentPlayer, territoryList);

		return fortifiedMap;
	}
	/**
	 * Gets the current player.
	 *
	 * @param gameMap the game map
	 * @return the current player
	 */
	private RiskPlayer getCurrentPlayer(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : gameMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
			}
		}

		return currentPlayer;
	}

	/**
	 * Double army.
	 *
	 * @param currPlayerTerritories the curr player territories
	 * @return the array list
	 */
	private ArrayList<RiskTerritory> doubleArmy(ArrayList<RiskTerritory> currPlayerTerritories) {
		ArrayList<RiskTerritory> territoryList = new ArrayList<RiskTerritory>(currPlayerTerritories);

		// finding list of current player territories in string format

		for (RiskTerritory currTerritory : currPlayerTerritories) {
			army = ((currTerritory.getArmiesPresent()) * 2);
			currTerritory.setArmiesPresent(army);
			territoryList.set(currPlayerTerritories.indexOf(currTerritory), currTerritory);
		}

		return territoryList;
	}
}
