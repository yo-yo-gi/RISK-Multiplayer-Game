/**
 * 
 */
package com.soen.risk.startegies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhase;
import com.soen.risk.model.RiskPhaseType;
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
public class RiskCheaterStrategy implements RiskPlayerStrategy, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5949805971444027113L;

	/** The current player. */
	RiskPlayer currentPlayer;

	/** The reinforcement territory. */
	ArrayList<RiskTerritory> reinforcementTerritoryList;

	/** The attacked map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;

	/** The reinforced map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;

	/** The fortified map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;

	RiskPhase riskPhase=new RiskPhase();

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

		// find current player
		currentPlayer = getCurrentPlayer(gameMap);
				
//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.REINFORCEMENT);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Reinforcement Phase");

		ArrayList<RiskTerritory> currPlayerTerritories = new ArrayList<RiskTerritory>();
		reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		
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

//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.ATTACK);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Attack Phase");
		
		// getting current player list
		currPlayerList.addAll(gameMap.get(currentPlayer));

		// getting own territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			currPlayerStringList.add(currTerritory.getTerritoryName());
		}

		// getting opponent territories in string format
		for (RiskTerritory currTerritory : currPlayerList) {
			for (String curAdj : currTerritory.getAdjacents()) {
				if (!(currPlayerStringList.contains(curAdj)) && !(terrListWithOpponentAdj.contains(curAdj))) {
					terrListWithOpponentAdj.add(curAdj);
				}
			}
		}
		// adding list of opponent adjacent to current player list
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : gameMap.entrySet()) {
			ArrayList<RiskTerritory> tempList=new ArrayList<RiskTerritory>();
			for (RiskTerritory currTerritory : entry.getValue()) {
				if (terrListWithOpponentAdj.contains(currTerritory.getTerritoryName())) {
					tempList.add(currTerritory);
				}
			}
			currPlayerList.addAll(tempList);
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
			System.out.println(currentPlayer.getPlayerName() + " wins the match and conquered the world...\n Game Ends");
			System.out.println("*****************************************");
			System.exit(0);
		}


		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackedMap.entrySet()) {
			System.out.println(entry.getKey().getPlayerName());
			for (RiskTerritory currTerritory : entry.getValue()) {
				System.out.println(currTerritory.getTerritoryName() + "(" + currTerritory.getArmiesPresent() + ")");
			}
		}
		attackedMap=RiskGameHelper.assignRandomCard(attackedMap);
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


//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.FORTIFY);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Fortification Phase");
		
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
			if (terrListWithOpponentAdj.contains(currTerritory.getTerritoryName())) {
				RiskTerritory tempTerritory=currTerritory;
				int army = (tempTerritory.getArmiesPresent() * 2);
				tempTerritory.setArmiesPresent(army);
				territoryList.set(currPlayerList.indexOf(currTerritory), tempTerritory);
			}
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

		
		for (RiskTerritory currTerritory : currPlayerTerritories) {
			int army = ((currTerritory.getArmiesPresent()) * 2);
			currTerritory.setArmiesPresent(army);
			territoryList.set(currPlayerTerritories.indexOf(currTerritory), currTerritory);
		}

		return territoryList;
	}
}
