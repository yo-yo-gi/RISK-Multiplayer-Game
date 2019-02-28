/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.soen.risk.helper.RiskLogger;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

// TODO: Auto-generated Javadoc
/**
 * The Class RiskFortificationPhase to move as many armies 
 * as you would like from one (and only one) of your territories 
 * into one (and only one) of your adjacent territories.
 * 
 * @author Chirag
 * @version 1.0
 */
public class RiskFortificationPhase {
	
	//private static final Logger LOGGER = Logger.getLogger(RiskPlayer.class.getName());
	RiskLogger logger= new RiskLogger();
	/** The scanner. */
	Scanner scanner=new Scanner(System.in);
	
	/** The adjacent territory list. */
	ArrayList<String> adjTerritoryList;
	
	/** The destination territory object. */
	RiskTerritory sourceTerritoryObject,destinationTerritoryObject;
	
	/**
	 * Gets the fortified map.
	 *
	 * @param currentPlayer the current player
	 * @param playerTerritories the player territories
	 * @return the fortified map
	 */
	public HashMap<RiskPlayer, ArrayList<RiskTerritory>> getFortifiedMap(RiskPlayer currentPlayer, ArrayList<RiskTerritory> playerTerritories)
	{
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		ArrayList<RiskTerritory> finalFortifyList=new ArrayList<RiskTerritory>(playerTerritories);
		System.out.println("Fortification started...");
		int sourceTerritory, destinationTerritory, sourceArmy, destinationArmy, sourceIndex, destinationIndex;
		String sourceTerritoryName, destinationTName;
		System.out.println(currentPlayer.getPlayerName());
	
		logger.doLogging("currentPlayer name is: "+currentPlayer);
		
		do {
		System.out.println("select the source territory");
		int sourceTCoutner=1;
		
		for (RiskTerritory currTerritory : playerTerritories) {
			System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName());
			sourceTCoutner++;			
		}
		sourceTerritory=scanner.nextInt();
		scanner.nextLine();
		sourceTerritoryName=playerTerritories.get(sourceTerritory-1).getTerritoryName();
		sourceArmy=playerTerritories.get(sourceTerritory-1).getArmiesPresent();
		
		}while(!(playerTerritories.get(sourceTerritory-1).getArmiesPresent()>1));
		
//		--------------------------------------------------------------------------------
		adjTerritoryList=new ArrayList<String>();
		for (RiskTerritory currTerritory : playerTerritories) {			
			if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
				adjTerritoryList=currTerritory.getAdjacents();
			}
		}
//		----------------------------------------------------------------------------------
		ArrayList<String> OwnedAdjList=new ArrayList<String>();
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if (currAdj.equalsIgnoreCase(currTerritory.getTerritoryName())) {
					OwnedAdjList.add(currAdj);
				}
			}
		}
		
	
		
//		----------------------------------------------------
		
		if (!OwnedAdjList.isEmpty()) {
			
		
		
		System.out.println("select the destination territory");
		int destinationTCoutner=1;
		for (String currAdj : OwnedAdjList) {
			System.out.println(destinationTCoutner+"." + currAdj);
			destinationTCoutner++;			
		}
		destinationTerritory=scanner.nextInt();
		scanner.nextLine();
		destinationTName=OwnedAdjList.get(destinationTerritory-1);
		
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		for (RiskTerritory currTerritory : playerTerritories) {
			if (currTerritory.getTerritoryName().equalsIgnoreCase(destinationTName)) {
				destinationTerritoryObject=currTerritory;
				destinationArmy=currTerritory.getArmiesPresent();
				
			}		
			if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
				sourceTerritoryObject=currTerritory;
				
			}
		}
	
//		------------------------------------------------------
		System.out.println("Enter the number of armies to be moved to destination territory:  ");
		int finalMoveOfArmies = scanner.nextInt();
		scanner.nextLine();
		if (finalMoveOfArmies>0 && finalMoveOfArmies<sourceArmy) {
			
			sourceArmy=sourceTerritoryObject.getArmiesPresent()-finalMoveOfArmies;
			destinationArmy=destinationTerritoryObject.getArmiesPresent()+finalMoveOfArmies;
			sourceTerritoryObject.setArmiesPresent(sourceArmy);
			destinationTerritoryObject.setArmiesPresent(destinationArmy);
			finalFortifyList.set(playerTerritories.indexOf(sourceTerritoryObject), sourceTerritoryObject);
			finalFortifyList.set(playerTerritories.indexOf(destinationTerritoryObject), destinationTerritoryObject);
			
		}else System.out.println("Wrong number of army selected");
		
	}
		
		
		System.out.println("Fortification complete");
		
		
		
		fortifiedMap.put(currentPlayer, finalFortifyList);
		
		return fortifiedMap;
	}
}
