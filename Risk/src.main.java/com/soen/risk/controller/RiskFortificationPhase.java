/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	/** The logger. */
	RiskLogger logger= new RiskLogger();
	/** The scanner. */
	Scanner scanner=new Scanner(System.in);

	/** The adjacent territory list. */
	ArrayList<String> adjTerritoryList;
	/** The owned territory list. */
	ArrayList<String> OwnedAdjList;
	/** The number of armies to be moved*/
	int finalMoveOfArmies;
	/** The destination territory object. */
	RiskTerritory sourceTerritoryObject,destinationTerritoryObject;

	/**
	 * Gets the fortified map.
	 *
	 * @param currentPlayer the current player
	 * @param playerTerritories the player territories
	 * @return the fortified map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getFortifiedMap(RiskPlayer currentPlayer, ArrayList<RiskTerritory> playerTerritories)
	{
		logger.doLogging("Inside the fortification phase----------");
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		ArrayList<RiskTerritory> finalFortifyList=new ArrayList<RiskTerritory>(playerTerritories);
		System.out.println("Fortification started...");
		int sourceTerritory, destinationTerritory, sourceArmy, destinationArmy = 0;
		String sourceTerritoryName, destinationTName = null ;
		System.out.println("Player Name: "+currentPlayer.getPlayerName());

		logger.doLogging("currentPlayer name is: "+currentPlayer);

		do
		{
			do {
				System.out.println("Select the source territory: ");
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
	
			logger.doLogging("Selected source territory  "+sourceTerritoryName);
			adjTerritoryList=new ArrayList<String>();
			for (RiskTerritory currTerritory : playerTerritories) {			
				if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
					adjTerritoryList=currTerritory.getAdjacents();
				}
			}
	
			OwnedAdjList=new ArrayList<String>();
			for (String currAdj : adjTerritoryList) {
				for (RiskTerritory currTerritory : playerTerritories) {
					if (currAdj.equalsIgnoreCase(currTerritory.getTerritoryName())) {
						OwnedAdjList.add(currAdj);
					}
				}
			}
			if(OwnedAdjList.isEmpty())
			{
				System.out.println("The Source Territory does not have adjacency, "
						+ "\nPlease select different source territory");
			}
		}while(OwnedAdjList.isEmpty());
		if (!OwnedAdjList.isEmpty()) {  //&& !(sourceArmy<=1

			System.out.println("Select the destination territory: ");
			int destinationTCoutner=1;
			for (String currAdj : OwnedAdjList) {
				System.out.println(destinationTCoutner+"." + currAdj);
				destinationTCoutner++;			
			}
			destinationTerritory=scanner.nextInt();
			scanner.nextLine();
			destinationTName=OwnedAdjList.get(destinationTerritory-1);
			
			logger.doLogging("Selected destination territory-> "+sourceTerritoryName);
			
			for (RiskTerritory currTerritory : playerTerritories) {
				if (currTerritory.getTerritoryName().equalsIgnoreCase(destinationTName)) {
					destinationTerritoryObject=currTerritory;
					destinationArmy=currTerritory.getArmiesPresent();
				}		
				if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
					sourceTerritoryObject=currTerritory;
				}
			}
			System.out.println("Number of armies are: "+sourceArmy+ " in the source territory: "+sourceTerritoryName);
			System.out.println("Number of armies are: "+destinationArmy+ " in the destination territory: "+destinationTName);
			System.out.println("Enter the number of armies to be moved to destination territory");
			
			do
			{
				finalMoveOfArmies = scanner.nextInt();
				
				scanner.nextLine();
				if(finalMoveOfArmies<0 || finalMoveOfArmies>sourceArmy)
				{
					System.out.println("invalid number of armies entered"
							+ "\nPlease enter valid armies");
				}
			}while(finalMoveOfArmies<0 || finalMoveOfArmies>sourceArmy);
			
			if (true) {

				sourceArmy=sourceTerritoryObject.getArmiesPresent()-finalMoveOfArmies;
				destinationArmy=destinationTerritoryObject.getArmiesPresent()+finalMoveOfArmies;
				sourceTerritoryObject.setArmiesPresent(sourceArmy);
				destinationTerritoryObject.setArmiesPresent(destinationArmy);
				finalFortifyList.set(playerTerritories.indexOf(sourceTerritoryObject), sourceTerritoryObject);
				finalFortifyList.set(playerTerritories.indexOf(destinationTerritoryObject), destinationTerritoryObject);

			}else System.out.println("invalid number of armies entered");
		}
		
		System.out.println("Fortification completed for the Player "+currentPlayer.getPlayerName());
		System.out.println("Source Territory: "+sourceTerritoryName+" has armies: "+sourceArmy);
		System.out.println("Destination Territory: "+destinationTName+" has armies: "+destinationArmy);

		fortifiedMap.put(currentPlayer, finalFortifyList);
		logger.doLogging("Fortification successful and the foritified map is: "+fortifiedMap);
		return fortifiedMap;
	}
}
