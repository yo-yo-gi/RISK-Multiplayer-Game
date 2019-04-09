/*
 * 
 */
package com.soen.risk.startegies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskAttackPhase;
import com.soen.risk.helper.RiskAttackHelper;
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.helper.RiskReinforcementHelper;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhase;
import com.soen.risk.model.RiskPhaseType;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Risk Aggressive Strategy</h2>
 * The Class RiskAggressiveStartegy strategy that focuses on attack (reinforces 
 * its strongest country, then always attack with it until it cannot attack anymore,
 * then fortifies in order to maximize aggregation of forces in one country).
 * 
 * @author Yogesh Nimbhorkar
 * @version 3.0
 */
public class RiskAggressiveStartegy implements RiskPlayerStrategy, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9154840920766860201L;

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

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.REINFORCEMENT);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Reinforcement Phase");

		//		getting current player territories
		currPlayerTerritories=gameMap.get(currentPlayer);
		//		find strongest territory to reinforce
		reinforcementTerritory=findStrongestTerritory(currPlayerTerritories);

		for (RiskTerritory riskTerritory : currPlayerTerritories) {
			if (riskTerritory.getArmiesPresent()>1000000) {
				return reinforcedMap;
			}
		}

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

		//		Attack operation with strong territory and adjacent list
		attackedMap=getAttackphaseMap(attackedMap, attackTerritory);

		for(Entry<RiskPlayer, ArrayList<RiskTerritory>> entry:attackedMap.entrySet()) {
			System.out.println(entry.getKey().getPlayerName());
			for (RiskTerritory currTerritory : entry.getValue()) {
				System.out.println(currTerritory.getTerritoryName()+"("+currTerritory.getArmiesPresent()+")");
			}
		}

		return attackedMap;
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#fortify(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {

		fortifiedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.FORTIFY);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Fortify Phase");

		ArrayList<RiskTerritory> territoriesWithAdjacents=new ArrayList<RiskTerritory>();
		ArrayList<RiskTerritory> currentPlayerTerritories=new ArrayList<RiskTerritory>();
		RiskTerritory fortifySourceTerritoy;
		RiskTerritory fortifyAdjTerritory = new RiskTerritory();
		int fortifySourceTerritoyIndex, fortifyAdjTerritoryIndex;
		ArrayList<RiskTerritory> adjucencyList=new ArrayList<RiskTerritory>();

		currentPlayerTerritories=fortifiedMap.get(currentPlayer);

		for (RiskTerritory riskTerritory : currentPlayerTerritories) {
			if (riskTerritory.getArmiesPresent()>1000000) {
				return fortifiedMap;
			}
		}

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
				adjucencyList.add(currTerritory);
			}				
		}

		//		finding territory with max armies in adjacent
		long adjMaxArmy=0;
		for (RiskTerritory currAdjTerritory : adjucencyList) {
			if (currAdjTerritory.getArmiesPresent()>adjMaxArmy) {
				adjMaxArmy=currAdjTerritory.getArmiesPresent();
			}
		}
		for (RiskTerritory currAdjTerritory : adjucencyList) {
			if (currAdjTerritory.getArmiesPresent()==adjMaxArmy) {
				fortifyAdjTerritory=currAdjTerritory;
			}
		}
		fortifyAdjTerritoryIndex=currentPlayerTerritories.indexOf(fortifyAdjTerritory);

		//		fortifying or moving armies from source to destination
		fortifySourceTerritoy.setArmiesPresent(fortifySourceTerritoy.getArmiesPresent()+fortifyAdjTerritory.getArmiesPresent()-1);
		fortifyAdjTerritory.setArmiesPresent(1);

		//		Updating map before return
		currentPlayerTerritories.set(fortifySourceTerritoyIndex, fortifySourceTerritoy);
		currentPlayerTerritories.set(fortifyAdjTerritoryIndex, fortifyAdjTerritory);
		fortifiedMap.put(currentPlayer, currentPlayerTerritories);

		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			System.out.println(currTerritory.getTerritoryName()+"("+currTerritory.getArmiesPresent()+")");
		}

		return fortifiedMap;
	}


	//===============================================================================================






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
	 * Find strongest territory.
	 *
	 * @param currPlayerTerritories the curr player territories
	 * @return the risk territory
	 */
	private RiskTerritory findStrongestTerritory(ArrayList<RiskTerritory> currPlayerTerritories) {
		ArrayList<RiskTerritory> territoriesWithMaxArmies=new ArrayList<RiskTerritory>();
		ArrayList<String> stringTerritoryList=new ArrayList<String>();
		ArrayList<RiskTerritory> adjListWithOppositeTerritories=new ArrayList<RiskTerritory>();
		long maxArmy=0;

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



	//==================================================================================
	//	Attack method
	//	============================================================================

	/**
	 * Gets the attackphase map.
	 *
	 * @param riskMainMap the risk main map
	 * @param attackTerritory the attack territory
	 * @return the attackphase map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, RiskTerritory attackTerritory) {
		attackedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);


		long sourceTCoutner=1,destinationTCoutner=1, attackMoveArmy=0;
		long attackSourceTerritoryIndex=0,attackDestinationTerritoryIndex=0,attackerTerritoryArmy=0,defenderTerritoryArmy=0;
		long attackDestinationArmy=0;
		long attackSourceArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory = null;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;


		Map<String, Object> attackOutputMap = new HashMap<>();

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackedMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.ATTACK);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Attack Phase");

		System.out.println("Select the territory you want to attack from:");


		attackSourceTerritory=attackTerritory;
		attackSourceTerritoryName=attackSourceTerritory.getTerritoryName();
		attackSourceArmy=attackSourceTerritory.getArmiesPresent()-1;


		if (attackSourceArmy<1) {
			System.out.println("Player needs at least 1 army to attack...\nPlease select another territory or select Exit");
			sourceTCoutner=1;
		}


		System.out.println("Enter the territory you want to attack:");


		attackDestinationTerritoryIndex=1;

		adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

		AdjAttackList=new ArrayList<String>(adjTerritoryList);
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
					AdjAttackList.remove(currAdj);
				}
			}
		}

		for(int i=0;i<AdjAttackList.size();i++) {
			if(i==attackDestinationTerritoryIndex-1) {
				defendingTerritory=AdjAttackList.get(i);
			}
		}
		attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(attackedMap, defendingTerritory);
		attackDestinationTerritoryName=attackDestinationTerritory.getTerritoryName();
		attackDestinationArmy=attackDestinationTerritory.getArmiesPresent();


		System.out.println("Attacker Territory Name:"+attackSourceTerritoryName);
		System.out.println("Attacker Army Count:"+attackSourceArmy);
		System.out.println("Defender Territory Name:"+attackDestinationTerritoryName);
		System.out.println("Defender Army count:"+attackDestinationArmy);

		/*Below code will call the function to attack  All-Out method*/		
		attackOutputMap=RiskAttackHelper.rollDiceForAllOutAttackMode(attackSourceTerritoryName,attackSourceArmy, attackDestinationTerritoryName, attackDestinationArmy);

		for (Entry<String, Object> entry : attackOutputMap.entrySet()) {
			if(entry.getKey()==attackSourceTerritoryName) {
				attackerTerritory=attackSourceTerritoryName;
				attackerTerritoryArmy=(long) entry.getValue();
				attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy+1);
			}
			else if(entry.getKey()==attackDestinationTerritoryName) {
				defenderTerritory=attackDestinationTerritoryName;
				defenderTerritoryArmy=(long) entry.getValue();
				attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);
			}
		}



		//		updating the main map for the army after every attack and deleting the territory if army is zero also to move territory
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


		System.out.println("Do you want to attack again?(Y/N)");
		playerTerritories=attackedMap.get(currentPlayer);
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
					AdjAttackList.remove(currAdj);
				}
			}
		}

		if(attackSourceTerritory.getArmiesPresent()>1 && AdjAttackList.size()>0) {

			//					calling recursively  to same method
			getAttackphaseMap(attackedMap, attackSourceTerritory);

		}
		if(cardEarnFlag && attackCounter) {
			attackedMap=RiskGameHelper.assignRandomCard(attackedMap);	
			attackCounter=false;
		}
		System.out.println("Attack completed...");
		return attackedMap;
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
		int skipFortifyCounter=0;
		//		finding maximum number of armies present in territory list
		for (RiskTerritory currTerritory : currentPlayerTerritories) {
			currPlayerStringTerritories.add(currTerritory.getTerritoryName());
			if (currTerritory.getArmiesPresent()>=currentPlayerMaxArmy) {
				currentPlayerMaxArmy=currTerritory.getArmiesPresent();				
			}
			if (currTerritory.getArmiesPresent()==1) {
				skipFortifyCounter++;
			}

		}

		//		returning null list if all armies presents are 1
		if (currentPlayerMaxArmy==1 || (skipFortifyCounter==currentPlayerTerritories.size()-1)) {
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

				if (currTerritory.getArmiesPresent() == currentPlayerMaxArmy
						&& currPlayerAdjacentsStringTerritories.size() > 0) {
					fortifyAdjacencyMap.put(currTerritory, currPlayerAdjacentsStringTerritories);
				}

			}
			if(currentPlayerMaxArmy>0) currentPlayerMaxArmy--;
			else break;			
		}

		//		putting all territories with max armies in list
		for ( RiskTerritory key : fortifyAdjacencyMap.keySet() ) {
			returnList.add(key);
		}


		return returnList;
	}


}
