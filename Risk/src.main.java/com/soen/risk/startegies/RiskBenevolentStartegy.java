/*
 * 
 */
package com.soen.risk.startegies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.soen.risk.helper.RiskReinforcementHelper;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhase;
import com.soen.risk.model.RiskPhaseType;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Risk Benevolent Strategy</h2>
 * The Class RiskBenevolentStartegy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move 
 * armies to weaker countries).
 *
 * @author Chirag Vora
 * @version 3.0
 */
public class RiskBenevolentStartegy implements RiskPlayerStrategy, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3964270414016765507L;

	/** The current player. */
	RiskPlayer currentPlayer;

	/** The reinforcement territory. */
	RiskTerritory reinforcementTerritory;

	/** The reinforced map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;

	/** The fortified map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;

	/** The risk phase. */
	RiskPhase riskPhase=new RiskPhase();

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#reinforce(java.util.LinkedHashMap, java.util.ArrayList)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList) {
		ArrayList<RiskTerritory> currPlayerTerritories=new ArrayList<RiskTerritory>();
		RiskReinforcementHelper riskReinforcementHelper=new RiskReinforcementHelper();
		reinforcedMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		//		find current player
		currentPlayer=getCurrentPlayer(gameMap);

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.REINFORCEMENT);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Reinforcement Phase");

		//		getting current player territories
		currPlayerTerritories=gameMap.get(currentPlayer);

		for (RiskTerritory riskTerritory : currPlayerTerritories) {
			if (riskTerritory.getArmiesPresent()>1000000) {
				return reinforcedMap;
			}
		}

		//		find strongest territory to reinforce
		reinforcementTerritory=findWeakestTerritory(currPlayerTerritories);
		//		calculating and adding reinforced armies in weakest countries
		reinforcedMap=riskReinforcementHelper.getReinforcedMap(gameMap, riskContinentList, reinforcementTerritory);

		return reinforcedMap;
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#attack(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {
		return gameMap;
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#fortify(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {

		fortifiedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		ArrayList<RiskTerritory> territoriesWithAdjacents=new ArrayList<RiskTerritory>();
		ArrayList<RiskTerritory> currentPlayerTerritories=new ArrayList<RiskTerritory>();
		RiskTerritory fortifySourceTerritoy;
		RiskTerritory fortifyAdjTerritory = new RiskTerritory();
		int fortifySourceTerritoyIndex, fortifyAdjTerritoryIndex;
		ArrayList<RiskTerritory> adjacencyList=new ArrayList<RiskTerritory>();

		currentPlayerTerritories=fortifiedMap.get(currentPlayer);

		for (RiskTerritory riskTerritory : currentPlayerTerritories) {
			if (riskTerritory.getArmiesPresent()>1000000) {
				return fortifiedMap;
			}
		}

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.FORTIFY);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Fortification Phase");

		//		finding list of highest army territory
		territoriesWithAdjacents=getTerritoriesWithAdjacents(currentPlayerTerritories);

		if (territoriesWithAdjacents.size()==0) {
			System.out.println("Fortify skipped as no fortification condition matching");
			return fortifiedMap;
		}
		//		find out 1st adjacent and write logic to move army to that territory
		fortifySourceTerritoy=territoriesWithAdjacents.get(0);	
		fortifySourceTerritoyIndex=currentPlayerTerritories.indexOf(fortifySourceTerritoy);

		//		finding adjacent list of RiskTerritory objects
		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			if (fortifySourceTerritoy.getAdjacents().contains(currTerritory.getTerritoryName())) {
				adjacencyList.add(currTerritory);
			}				
		}

		//		finding territory with max armies in adjacent
		long adjMaxArmy=0;
		for (RiskTerritory currAdjTerritory : adjacencyList) {
			if (currAdjTerritory.getArmiesPresent()>adjMaxArmy) {
				adjMaxArmy=currAdjTerritory.getArmiesPresent();
			}
		}
		for (RiskTerritory currAdjTerritory : adjacencyList) {
			if (currAdjTerritory.getArmiesPresent()==adjMaxArmy) {
				fortifyAdjTerritory=currAdjTerritory;
			}
		}
		fortifyAdjTerritoryIndex=currentPlayerTerritories.indexOf(fortifyAdjTerritory);

		//		fortifying or moving armies from source to destination
		fortifySourceTerritoy.setArmiesPresent(fortifySourceTerritoy.getArmiesPresent()+(fortifyAdjTerritory.getArmiesPresent()/2));
		fortifyAdjTerritory.setArmiesPresent((fortifyAdjTerritory.getArmiesPresent()%2));

		//		Updating map before return
		currentPlayerTerritories.set(fortifySourceTerritoyIndex, fortifySourceTerritoy);
		currentPlayerTerritories.set(fortifyAdjTerritoryIndex, fortifyAdjTerritory);
		fortifiedMap.put(currentPlayer, currentPlayerTerritories);

		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			System.out.println(currTerritory.getTerritoryName()+"("+currTerritory.getArmiesPresent()+")");
		}
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
				currentPlayer=entry.getKey();
			}
		}
		return currentPlayer;
	}

	/**
	 * Find weakest territory.
	 *
	 * @param currPlayerTerritories the current player territories
	 * @return the risk territory
	 */
	private RiskTerritory findWeakestTerritory(ArrayList<RiskTerritory> currPlayerTerritories) {
		ArrayList<RiskTerritory> territoriesWithMinArmies=new ArrayList<RiskTerritory>();

		long maxArmy = 0;
		//		getting max army present in above list	
		for (RiskTerritory currTerritory : currPlayerTerritories) {
			if (currTerritory.getArmiesPresent()>maxArmy) {
				maxArmy=currTerritory.getArmiesPresent();
			}
		}

		long minArmy = maxArmy;
		//		getting minimum army present in above list
		for (RiskTerritory currTerritory : currPlayerTerritories) {
			if (currTerritory.getArmiesPresent()<=minArmy) {
				minArmy=currTerritory.getArmiesPresent();
			}
		}

		//		finding territory with minimum army satisfying above condition
		for (RiskTerritory currTerritory : currPlayerTerritories) {
			if (currTerritory.getArmiesPresent()==minArmy) {
				territoriesWithMinArmies.add(currTerritory);
			}
		}
		return territoriesWithMinArmies.get(0);
	}

	/**
	 * Gets the territories with adjacents.
	 *
	 * @param currentPlayerTerritories the current player territories
	 * @return the territories with adjacents
	 */
	private ArrayList<RiskTerritory> getTerritoriesWithAdjacents(ArrayList<RiskTerritory> currentPlayerTerritories) {
		ArrayList<RiskTerritory> returnList=new ArrayList<RiskTerritory>();
		ArrayList<String> currPlayerAdjacentsStringTerritories=new ArrayList<String>();
		ArrayList<String> currPlayerStringTerritories=new ArrayList<String>();
		LinkedHashMap<RiskTerritory, ArrayList<String>>	fortifyAdjacencyMap=new LinkedHashMap<RiskTerritory, ArrayList<String>>();
		long currentPlayerMaxArmy=0;
		long currentPlayerMinArmy=1;
		int skipFortifyCounter=0;
		//		finding maximum number of armies present in territory list
		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			currPlayerStringTerritories.add(currTerritory.getTerritoryName());
			if (currTerritory.getArmiesPresent()>=currentPlayerMaxArmy) {
				currentPlayerMaxArmy=currTerritory.getArmiesPresent();				
			}
			if (currTerritory.getArmiesPresent()<=currentPlayerMinArmy) {
				currentPlayerMinArmy=currTerritory.getArmiesPresent();				
			}
			if (currTerritory.getArmiesPresent()==1) {
				skipFortifyCounter++;
			}
		}

		//		returning null list if all armies presents are 1
		if (currentPlayerMinArmy==1 || (skipFortifyCounter==currentPlayerTerritories.size()-1)) {
			return returnList;
		}

		//		finding the territory with adjacent of own and containing max armies
		while (fortifyAdjacencyMap.size() < 1) {
			for (RiskTerritory currTerritory : currentPlayerTerritories) {

				for (String currTerritoryName : currTerritory.getAdjacents()) {
					if (currPlayerStringTerritories.contains(currTerritoryName)) {
						currPlayerAdjacentsStringTerritories.add(currTerritoryName);
					}
				}
				if (currTerritory.getArmiesPresent() == currentPlayerMinArmy
						&& currPlayerAdjacentsStringTerritories.size() > 0) {
					fortifyAdjacencyMap.put(currTerritory, currPlayerAdjacentsStringTerritories);
				}
			}
			if(currentPlayerMaxArmy>0) currentPlayerMinArmy++;
			else break;			
		}
		//		putting all territories with max armies in list
		for ( RiskTerritory key : fortifyAdjacencyMap.keySet() ) {
			returnList.add(key);
		}
		return returnList;
	}
}
