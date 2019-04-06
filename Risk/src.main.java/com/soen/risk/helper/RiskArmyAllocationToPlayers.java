/**
 * 
 */
package com.soen.risk.helper;

import java.io.Serializable;
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
public class RiskArmyAllocationToPlayers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6316522767300342984L;
	
	transient Scanner scanner = new Scanner(System.in);

	/**
	 * Assign armies to each players for the countries they own in round robin fashion.
	 *
	 * @param playerTerritoryMapParam the player territory map parameter
	 * @return the map
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> assignArmiesToPlayers(Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMapParam ) {
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(assignInitialArmy(playerTerritoryMapParam));
		Map<RiskPlayer, Long> countMap = new LinkedHashMap<RiskPlayer, Long>();
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet()){	
			countMap.put(entry.getKey(), entry.getKey().getArmiesOwned());
		}
		System.out.println();
		System.out.println("Allocating armies to players in round robin fashion...");
		System.out.println();
		long totalArmyPresent=Constants.ZERO;
		do {	
			System.out.println();	
			for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : playerTerritoryMap.entrySet()){
				Long currentArmy=countMap.get(entry.getKey());
				if (currentArmy>0) {
					String PlayerName=entry.getKey().getPlayerName();
					System.out.println("Remaining armies for "+ PlayerName+"--> "+countMap.get(entry.getKey()));				
					int territoryCounter=1;
					int selectedTerrIndex=Constants.ZERO;
					long newArmyToSet=Constants.ZERO;
					ArrayList<RiskTerritory> currentTerrList=new ArrayList<RiskTerritory>(entry.getValue());
					RiskTerritory riskTerritory;
					System.out.println(PlayerName+" --> Select territory");
					for (RiskTerritory currTerritory : entry.getValue()) {
						System.out.println(territoryCounter+". "+currTerritory.getTerritoryName()+ " ("+currTerritory.getArmiesPresent()+")");
						territoryCounter++;
					}
					selectedTerrIndex=Constants.ZERO;
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

			totalArmyPresent=Constants.ZERO;
			for (Entry<RiskPlayer, Long> entry : countMap.entrySet()){	
				totalArmyPresent=totalArmyPresent+entry.getValue();
			}			

		}while(totalArmyPresent>0);

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
			long armyOwned=entry.getKey().getArmiesOwned();
			entry.getKey().setArmiesOwned(armyOwned-entry.getValue().size());
			for (RiskTerritory currTerritory : entry.getValue()) {		
				if(armyOwned>0) {
					currTerritory.setArmiesPresent(1);
					armyOwned--;
				}else currTerritory.setArmiesPresent(0);
			}
		}
		return playerTerritoryMap;
	}
}
