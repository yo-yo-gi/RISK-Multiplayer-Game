/**
 * 
 */
package com.soen.risk.startegies;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskAttackPhase;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhase;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * The Class RiskCheaterStrategy  strategy whose reinforce() method doubles the number 
 * of armies on all its countries, whose attack() method automatically conquers all the 
 * neighbors of all its countries, and whose fortify() method doubles the number of armies
 * on its countries that have neighbors that belong to other players.
 *
 * @author Chirag Vora
 * @version 3.0
 */
public class RiskCheaterStrategy implements RiskPlayerStrategy{
	
	/** The current player. */
	RiskPlayer currentPlayer;
	
	/** The reinforcement territory. */
	RiskTerritory reinforcementTerritory;
	
	/** The attack territory. */
	RiskTerritory attackTerritory;
	
	/** The attacked map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;
	
	/** The reinforced map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	
	/** The fortified map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;
	
	/** The attack counter. */
	boolean cardEarnFlag=false,attackCounter=false;
	
	/** The risk phase. */
	RiskPhase riskPhase=new RiskPhase();
	
	/** The Adj attack list. */
	List<String> AdjAttackList = null;

	/** The risk attack phase. */
	RiskAttackPhase riskAttackPhase=new RiskAttackPhase();
	
	/** The player territories. */
	ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
	
	/** The adj territory list. */
	ArrayList<String> adjTerritoryList;
	
	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#getStrategyName()
	 */
	@Override
	public String getStrategyName() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#getIsBot()
	 */
	@Override
	public boolean getIsBot() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#reinforce(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList) {
		
		ArrayList<RiskTerritory> currPlayerTerritories=new ArrayList<RiskTerritory>();
		RiskReinforcementHelper riskReinforcementHelper=new RiskReinforcementHelper();
		reinforcedMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);
		
//		find current player
		currentPlayer=getCurrentPlayer(gameMap);
//		getting current player territories
		currPlayerTerritories=gameMap.get(currentPlayer);
//		find strongest territory to reinforce
		reinforcementTerritory=findStrongestTerritory(currPlayerTerritories);
//		calculating and adding reinforced armies in strongest countries
		reinforcedMap=riskReinforcementHelper.getReinforcedMap(gameMap, riskContinentList, reinforcementTerritory);
	
		return reinforcedMap;
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#attack(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#fortify(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {
		// TODO Auto-generated method stub
		return null;
	}

	
	// #######################################################################################
	
	/**
	 * @param gameMap
	 * @return
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
	 * @param currPlayerTerritories
	 * @return
	 */
	private RiskTerritory findStrongestTerritory(ArrayList<RiskTerritory> currPlayerTerritories) {
		ArrayList<RiskTerritory> territoriesWithMaxArmies=new ArrayList<RiskTerritory>();
		ArrayList<String> stringTerritoryList=new ArrayList<String>();
		ArrayList<RiskTerritory> adjListWithOppositeTerritories=new ArrayList<RiskTerritory>();
		int maxArmy=0;
		
//		finding list of current player territories in string foramat
		for (RiskTerritory currTerritory : currPlayerTerritories) {
			stringTerritoryList.add(currTerritory.getTerritoryName());
		}
		
//		finding list of territories which has at least one opposite territory
		for (RiskTerritory currTerr : currPlayerTerritories) {
			for (String  eachAdj: currTerr.getAdjacents()) {
				if (!(stringTerritoryList.contains(eachAdj))) {
					adjListWithOppositeTerritories.add(currTerr);
					break;
				}
			}
		}
		
		
//		getting max army present in above list
		for (RiskTerritory currTerritory : adjListWithOppositeTerritories) {
			if (currTerritory.getArmiesPresent()>maxArmy) {
				maxArmy=currTerritory.getArmiesPresent();
			}
		}
//		finding territory with max army satisfying above condition
		for (RiskTerritory currTerritory : adjListWithOppositeTerritories) {
			if (currTerritory.getArmiesPresent()==maxArmy) {
				territoriesWithMaxArmies.add(currTerritory);
			}
		}
		
		return territoriesWithMaxArmies.get(0);
	}
}
