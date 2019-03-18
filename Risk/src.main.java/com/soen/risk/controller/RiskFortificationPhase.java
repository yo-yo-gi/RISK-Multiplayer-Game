/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Fortification Phase Controller</h2>
 * The Class RiskFortificationPhase to move as many armies 
 * as you would like from one (and only one) of your territories 
 * into one (and only one) of your adjacent territories.
 * 
 * @author Chirag Vora
 * @version 1.0
 */
public class RiskFortificationPhase {

	/** The logger. */
	RiskLogger logger= new RiskLogger();
	/** The scanner. */
	Scanner scanner=new Scanner(System.in);

	/** The source territory. */
	int sourceTerritory;
	/** The adjacent territory list. */
	ArrayList<String> adjTerritoryList;
	/** The owned territory list. */
	ArrayList<String> OwnedAdjList;

	/**  The number of armies to be moved. */
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

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getFortifiedMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackMap)
	{
		RiskPlayer currentPlayer = null;
		ArrayList<RiskTerritory> playerTerritories = null;
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}
		logger.doLogging("Inside the fortification phase----------");
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		ArrayList<RiskTerritory> finalFortifyList=new ArrayList<RiskTerritory>(playerTerritories);
		System.out.println();
		System.out.println("Fortification started...");
		System.out.println();
		int destinationTerritory, sourceArmy, destinationArmy = Constants.ZERO;
		String sourceTerritoryName, destinationTName = null ;
		System.out.println("Player Name: "+currentPlayer.getPlayerName());

		logger.doLogging("currentPlayer name is: "+currentPlayer);

		do
		{
			do {
				
				System.out.println();
				System.out.println("Select the source territory to move army from: ");
				int sourceTCoutner=1;

				for (RiskTerritory currTerritory : playerTerritories) {
					System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") ");
					sourceTCoutner++;			
				}

				do {
					while (!scanner.hasNextInt()) {
						System.out.println("Try Again!!");
						scanner.next();
					}
					sourceTerritory=scanner.nextInt();
					if(sourceTerritory>=sourceTCoutner || sourceTerritory<0) {
						System.out.println("Try Again!!");
					}
				}while(sourceTerritory>=sourceTCoutner || sourceTerritory<0);

				sourceTerritoryName=playerTerritories.get(sourceTerritory-1).getTerritoryName();
				sourceArmy=playerTerritories.get(sourceTerritory-1).getArmiesPresent();
				if ((playerTerritories.get(sourceTerritory-1).getArmiesPresent()==1)) {
					System.out.println("Source territory must have at least one army \n please select different source territory");
				}
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

		if (!OwnedAdjList.isEmpty()) {  
			System.out.println("Select the destination territory: ");
			int destinationTCoutner=1;
			for (String currAdj : OwnedAdjList) {
				System.out.println(destinationTCoutner+"." + currAdj);
				destinationTCoutner++;			
			}

			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				destinationTerritory=scanner.nextInt();
				if(destinationTerritory>=destinationTCoutner || destinationTerritory<0) {
					System.out.println("Try Again!!");
				}
			}while(destinationTerritory>=destinationTCoutner || destinationTerritory<0);

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
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				finalMoveOfArmies = scanner.nextInt();
				if(finalMoveOfArmies==sourceArmy)
				{
					System.out.println("All armies cannot be moved."
							+ "\nPlease enter number of armies less than total armies in the territory.");
				}
				if(finalMoveOfArmies<0)
				{
					System.out.println("invalid number of armies entered"
							+ "\nPlease enter valid armies");
				}
				if(finalMoveOfArmies>sourceArmy) {
					System.out.println("Number of armies cannot be greater than that available."
							+ "\nPlease enter valid armies");
				}
			}while(finalMoveOfArmies<0 || finalMoveOfArmies>=sourceArmy);

			if (true) {

				sourceArmy=sourceTerritoryObject.getArmiesPresent()-finalMoveOfArmies;
				destinationArmy=destinationTerritoryObject.getArmiesPresent()+finalMoveOfArmies;
				sourceTerritoryObject.setArmiesPresent(sourceArmy);
				destinationTerritoryObject.setArmiesPresent(destinationArmy);
				finalFortifyList.set(playerTerritories.indexOf(sourceTerritoryObject), sourceTerritoryObject);
				finalFortifyList.set(playerTerritories.indexOf(destinationTerritoryObject), destinationTerritoryObject);

			}
			
		}

		System.out.println();
		System.out.println("Fortification completed for the Player "+currentPlayer.getPlayerName());
		System.out.println();
		System.out.println("Source Territory: "+sourceTerritoryName+" has armies: "+sourceArmy);
		System.out.println("Destination Territory: "+destinationTName+" has armies: "+destinationArmy);
		System.out.println();

		fortifiedMap.put(currentPlayer, finalFortifyList);
		logger.doLogging("Fortification successful and the foritified map is: "+fortifiedMap);
		return fortifiedMap;
	}
}
