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

	/** The logger. */
	RiskLogger logger= new RiskLogger();

	/**
	 * Assign armies to each players for the countries they own in round robin fashion.
	 *
	 * @param playerTerritoryMapParam the player territory map param
	 * @return the map
	 */

	public Map<RiskPlayer, ArrayList<RiskTerritory>> assignArmiesToPlayers(Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMapParam ) {
		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(assignInitialArmy(playerTerritoryMapParam));
		Map<RiskPlayer, Integer> countMap = new LinkedHashMap<RiskPlayer, Integer>();
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet()){	
			countMap.put(entry.getKey(), entry.getKey().getArmiesOwned());
		}
		logger.doLogging("Allocating armies to players in round robin fashion------");
		System.out.println();
		System.out.println("Allocating armies to players in round robin fashion...");
		System.out.println();	
		Scanner scanner = new Scanner(System.in);
		int totalArmyPresent=0;
		do {	
			System.out.println();	
			for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet()){
				int currentArmy=countMap.get(entry.getKey());
				if (currentArmy>0) {
				String PlayerName=entry.getKey().getPlayerName();
				System.out.println("Remaining armies for "+ PlayerName+"--> "+countMap.get(entry.getKey()));				
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
				countMap.put(entry.getKey(), currentArmy-1);
				System.out.println("1 army added to "+riskTerritory.getTerritoryName());
				}
			}
			
			totalArmyPresent=0;
			for (Entry<RiskPlayer, Integer> entry : countMap.entrySet()){	
				totalArmyPresent=totalArmyPresent+entry.getValue();
			}			
			
		}while(totalArmyPresent>0);

		
		logger.doLogging("playerTerritoryMap returned------"+playerTerritoryMap);
		return playerTerritoryMap;
	}

	/**
	 * Assign initial army.
	 *
	 * @param playerTerritoryMap the player territory map
	 * @return the map
	 */
	private Map<RiskPlayer, ArrayList<RiskTerritory>> assignInitialArmy(
			Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap) {
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet())
		{	
			int armyOwned=entry.getKey().getArmiesOwned();
			entry.getKey().setArmiesOwned(armyOwned-entry.getValue().size());
			for (RiskTerritory currTerritory : entry.getValue()) {
				currTerritory.setArmiesPresent(1);
			}
		}		
		return playerTerritoryMap;
	}
}
