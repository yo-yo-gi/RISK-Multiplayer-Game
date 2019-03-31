/*
 * 
 */
package com.soen.risk.startegies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskAttackPhase;
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * The Class RiskAggressiveStartegy.
 * @author Chirag
 * @version 3.0
 */
public class RiskAggressiveStartegy implements RiskPlayerStrategy{
	
	RiskPlayer currentPlayer;
	RiskTerritory reinforcementTerritory;
	RiskTerritory attackTerritory;
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;
	boolean cardEarnFlag=false,attackCounter=false;

	@Override
	public String getStrategyName() {
		return null;
	}

	@Override
	public boolean getIsBot() {
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
		attackedMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);
		
//		getting strongest territory to attack
		attackTerritory=reinforcementTerritory;
		
//		getting adjacent list to attack
//		Need to addd
		
//		Attack operation with strong territory and adjacent list
		getAttackphaseMap(attackedMap, attackTerritory);
		
		return attackedMap;
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
		
//		finding list of highest army territory
		territoriesWithAdjacents=getTerritoriesWithAdjacents(currentPlayerTerritories);
		
//		find out 1st adjucent and write logic to move army to that territory
		
		
		
		
		
		
		
		return fortifiedMap;
	}
	

//===============================================================================================
	
	
	
	
	
	
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
	
	
	
//	Need to implement
	/**
	 * @param currPlayerTerritories
	 * @return
	 */
	private RiskTerritory findStrongestTerritory(ArrayList<RiskTerritory> currPlayerTerritories) {
		
		for (RiskTerritory riskTerritory : currPlayerTerritories) {
			
		}
		
		return currPlayerTerritories.get(0);
	}
	
	
	
	//==================================================================================
	//	Attack method
	//	============================================================================

	/**
	 * Gets the attackphase map.
	 *
	 * @param riskMainMap the risk main map
	 * @param attackTerritory 
	 * @return the attackphase map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, RiskTerritory attackTerritory) {
		attackedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		RiskAttackPhase riskAttackPhase=new RiskAttackPhase();
		boolean currPlayerArmyCheck=true, exitFlag=false;
		char selectedchoice;
		int sourceTCoutner=1,destinationTCoutner=1, attackMoveArmy=0;
		int attackSourceTerritoryIndex=0,attackSourceArmy=0,attackDestinationTerritoryIndex=0,attackDestinationArmy=0,attackerTerritoryArmy=0,defenderTerritoryArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory = attackTerritory;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;
		List<String> adjTerritoryList;
		List<String> AdjAttackList = null;
		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();

		Map<String, Object> attackOutputMap = new HashMap<>();

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackedMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}

		//		Triggering phase view observer		
//		riskPhase.setCurrentGamePhase(RiskPhaseType.ATTACK);
//		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
//		riskPhase.setCurrentAction("Starting Attack Phase");

		do {


			attackSourceTerritoryName=attackSourceTerritory.getTerritoryName();
			attackSourceArmy=attackSourceTerritory.getArmiesPresent()-1;

			if (attackSourceArmy<1) {
				System.out.println("Player needs at least 1 army to attack...\nPlease select another territory or select Exit");
			}

			adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

			AdjAttackList=new ArrayList<String>(adjTerritoryList);
			for (String currAdj : adjTerritoryList) {
				for (RiskTerritory currTerritory : playerTerritories) {
					if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
						AdjAttackList.remove(currAdj);
					}
				}
			}
			if (AdjAttackList.size()==0) {
				System.out.println("Current territory don't have any adjucents to attack...\nPlease select another territory or select Exit");
			}

		}while((attackSourceArmy<1) || AdjAttackList.size()==0);





			attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(attackedMap, AdjAttackList.get(0));
			attackDestinationTerritoryName=attackDestinationTerritory.getTerritoryName();
			attackDestinationArmy=attackDestinationTerritory.getArmiesPresent();


			System.out.println("Attacker Territory Name:"+attackSourceTerritoryName);
			System.out.println("Attacker Army Count:"+attackSourceArmy);
			System.out.println("Defender Territory Name:"+attackDestinationTerritoryName);
			System.out.println("Defender Army count:"+attackDestinationArmy);

			/*Below code will call the function to give user option to attack in Normal or All-Out method*/		
			attackOutputMap=RiskAttackHelper.rollDiceForAllOutAttackMode(attackSourceTerritoryName,attackSourceArmy, attackDestinationTerritoryName, attackDestinationArmy);

			for (Entry<String, Object> entry : attackOutputMap.entrySet()) {
				if(entry.getKey()==attackSourceTerritoryName) {
					attackerTerritory=attackSourceTerritoryName;
					attackerTerritoryArmy=(int) entry.getValue();
					attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy+1);
				}
				else if(entry.getKey()==attackDestinationTerritoryName) {
					defenderTerritory=attackDestinationTerritoryName;
					defenderTerritoryArmy=(int) entry.getValue();
					attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);
				}
			}



			//		updating the main map for the army after every attack and deleting the territory if army is zero
			attackedMap=RiskGameHelper.updateArmyAfterAttack(attackSourceTerritory,attackDestinationTerritory, attackedMap);
			if (attackedMap.size()==1) {
				System.out.println("*****************************************");
				System.out.println(currentPlayer.getPlayerName() + "wins the match and conquered the world...\n Game Ends");
				System.out.println("*****************************************");
				System.exit(0);
			}

			System.out.println("Attacker:"+attackerTerritory+" has "+attackerTerritoryArmy+" left");
			System.out.println("Defender:"+defenderTerritory+" has "+defenderTerritoryArmy+" left");

			if (defenderTerritoryArmy==0) {
				cardEarnFlag=true;
				attackCounter=true;


				//			Moving armies to new territory
				attackedMap=RiskGameHelper.moveArmyAfterAttack(1,attackSourceTerritory,attackDestinationTerritory, attackedMap);
			}


					attackSourceTerritory=attackedMap.get(currentPlayer).get(attackedMap.get(currentPlayer).indexOf(attackSourceTerritory));
					
						if(attackSourceTerritory.getArmiesPresent()>1) {
							currPlayerArmyCheck=false;
						}
					if(!currPlayerArmyCheck) {
						attackSourceTerritory=attackedMap.get(currentPlayer).get(attackedMap.get(currentPlayer).indexOf(attackSourceTerritory));
						getAttackphaseMap(attackedMap, attackSourceTerritory);
					}
					else {
						System.out.println("You currently have only 1 army in your territories...\nCannot attack further...Proceed to fortification phase");
					}
				
				
					if(cardEarnFlag && attackCounter) {
						attackedMap=RiskGameHelper.assignRandomCard(attackedMap);	
						attackCounter=false;
					}
		
		System.out.println("Attack completed...");
		return attackedMap;
	}	
	
	/**
	 * @param currentPlayerTerritories
	 * @return
	 */
	private ArrayList<RiskTerritory> getTerritoriesWithAdjacents(ArrayList<RiskTerritory> currentPlayerTerritories) {
		ArrayList<RiskTerritory> returnList=new ArrayList<RiskTerritory>();
		ArrayList<String> currPlayerAdjacentsStringTerritories=new ArrayList<String>();
		ArrayList<String> currPlayerStringTerritories=new ArrayList<String>();
		LinkedHashMap<RiskTerritory, ArrayList<String>>	fortifyAdjacencyMap=new LinkedHashMap<RiskTerritory, ArrayList<String>>();
		int currentPlayerMaxArmy=0;
		
		
		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			currPlayerStringTerritories.add(currTerritory.getTerritoryName());
			if (currTerritory.getArmiesPresent()>currentPlayerMaxArmy) {
				currentPlayerMaxArmy=currTerritory.getArmiesPresent();
			}
		}
		
		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			
			for (String currTerritoryName : currTerritory.getAdjacents()) {
				if (currPlayerStringTerritories.contains(currTerritoryName)) {
					currPlayerAdjacentsStringTerritories.add(currTerritoryName);
				}
			}
			if (currTerritory.getArmiesPresent()==currentPlayerMaxArmy && currPlayerAdjacentsStringTerritories.size()>0) {
				fortifyAdjacencyMap.put(currTerritory,currPlayerAdjacentsStringTerritories);		
			}
			
		}		
		
		for ( RiskTerritory key : fortifyAdjacencyMap.keySet() ) {
			returnList.add(key);
		}
		
		
		return returnList;
	}

	
}
