/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Army Allocation to player class</h2>
 * The Class is used in assignment phase where 
 * the player is given a number of armies in round robin fashion
 * depending on the number of countries he owns.
 *
 * @author Chirag Vora
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */

public class RiskArmyAllocationToPlayers {
	RiskLogger logger= new RiskLogger();
	
	/**
	 * Assign armies to each players for the countries they own in round robin fashion.
	 * @return 
	 */
	
	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignArmiesToPlayers(Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMapParam ) {
		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(assignInitialArmy(playerTerritoryMapParam));
		logger.doLogging("Allocating armies to players in round robin fashion------");
        System.out.println();
		System.out.println("Allocating armies to players in round robin fashion...");
        System.out.println();	
		Scanner scanner = new Scanner(System.in);
		int initArmy=((RiskPlayer)(playerTerritoryMapParam.keySet().toArray()[0])).getArmiesOwned();
		do {
			System.out.println();
			System.out.println("Remaining armies - "+initArmy);
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet()){	
			String PlayerName=entry.getKey().getPlayerName();
			int territoryCounter=1;
			int selectedTerrIndex=0;
			int newArmyToSet=0;
			ArrayList<RiskTerritory> currentTerrList=new ArrayList<RiskTerritory>(entry.getValue());
			RiskTerritory riskTerritory;
			System.out.println(PlayerName+" --> Select territory");
			for (RiskTerritory currTerritory : entry.getValue()) {
				System.out.println(territoryCounter+". "+currTerritory.getTerritoryName()+ " ("+currTerritory.getArmiesPresent()+")");
				territoryCounter++;
			}
			selectedTerrIndex=0;
	    	  do {
	  			while (!scanner.hasNextInt()) {
	  		 		System.out.println("Try Again!!");
	  		 		scanner.next();
	  		 	    }
	  			selectedTerrIndex=scanner.nextInt();
	  			if(selectedTerrIndex>=territoryCounter || selectedTerrIndex<0) {
	  				System.out.println("Try Again!!");
	  			    }
	  			}while(selectedTerrIndex>=territoryCounter || selectedTerrIndex<0);
	    	  riskTerritory=currentTerrList.get(selectedTerrIndex-1);
	    	  newArmyToSet=riskTerritory.getArmiesPresent()+1;
	    	  riskTerritory.setArmiesPresent(newArmyToSet);
	    	  currentTerrList.set(selectedTerrIndex-1,riskTerritory);
	    	  entry.setValue(currentTerrList);			
		}	
		initArmy--;		
		}while(initArmy>0);
		
		logger.doLogging("playerTerritoryMap returned------"+playerTerritoryMap);
		return playerTerritoryMap;
	}

	/**
	 * @param playerTerritoryMap
	 * @return
	 */
	private Map<? extends RiskPlayer, ? extends ArrayList<RiskTerritory>> assignInitialArmy(
			Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap) {
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet())
		{	
			int armyOwned=entry.getKey().getArmiesOwned();
			entry.getKey().setArmiesOwned(armyOwned-1);
			for (RiskTerritory currTerritory : entry.getValue()) {
				currTerritory.setArmiesPresent(1);
			}
		}		
		return playerTerritoryMap;
	}
}
