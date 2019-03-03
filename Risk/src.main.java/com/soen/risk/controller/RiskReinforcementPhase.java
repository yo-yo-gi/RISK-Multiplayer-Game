package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * The class defines number of the armies given to the player calculation.
 * In the reinforcements phase, the player is given a number of armies that depends on the 
 * number of countries he owns (# of countries owned divided by 3, rounded down). 
 * If the player owns all the countries of an entire continent
 * else the player is given an amount of armies corresponding to the continent�s control value.
 * 
 * @author Neha
 * @version 1.0
 *
 */
public class RiskReinforcementPhase 
{
	/**
	 * 
	 * Assigning the Armies to the Countries for the Player
	 * @param currentPlayer : Current player is passed
	 * @param playerTerritories : The territories assigned to the player
	 * @param riskContinentList : The total continent list
	 * @return
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getReinforcedMap(RiskPlayer currentPlayer, ArrayList<RiskTerritory> playerTerritories, ArrayList<RiskContinent> riskContinentList)
	{
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		Scanner scanner = new Scanner(System.in);
		String currentPlayerName = null;

		ArrayList<RiskTerritory> currentPlayerTerritories;
		int noOfRemainingArmies;
		currentPlayerTerritories=playerTerritories;
		int noOfCountriesOwned = currentPlayerTerritories.size();		
		currentPlayerName=currentPlayer.getPlayerName();

		noOfRemainingArmies=calculateArmy(currentPlayer,currentPlayerTerritories, riskContinentList);
		System.out.println("\nReinforcement started.....");

		System.out.println("Player name : "+currentPlayerName);
		System.out.println("Number of territories owned : "+noOfCountriesOwned);
		System.out.println("Territories of the player "+currentPlayerName+" are as following: ");
		int territoryCounter=1;
		
		
		
		for (RiskTerritory riskTerritory : currentPlayerTerritories) {
			
			System.out.println(territoryCounter+". "+riskTerritory.getTerritoryName()+" ("+riskTerritory.getArmiesPresent()+") ");
			territoryCounter++;
		}
		System.out.println("Select the territory for reinforcement: ");
		int selectedTerrIndex = scanner.nextInt();
		
		do
		{
			if(noOfRemainingArmies!=0)
			{
				System.out.println("The number of armies available for reinforcement : "+noOfRemainingArmies);
				System.out.println("Enter armies for "+currentPlayerTerritories.get((selectedTerrIndex-1)).getTerritoryName());
				int userEnteredArmy = scanner.nextInt();
				if(userEnteredArmy < 0)
				{
					do 
					{
						System.out.println("No armies assigned cannot be less than zero, please add armies to the country : "+currentPlayerTerritories.get((selectedTerrIndex-1)));
						userEnteredArmy = scanner.nextInt();	 	    		   
					}while(userEnteredArmy < 0);
				}
				if(userEnteredArmy > noOfRemainingArmies)
				{
					do
					{
						System.out.println("Invalid Input");
						System.out.println("Enter the number of Armies to reinforce for Country  "+currentPlayerTerritories.get((selectedTerrIndex-1)).getTerritoryName());
						userEnteredArmy = scanner.nextInt();

					}while(userEnteredArmy>noOfRemainingArmies);
					noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy;  
				}
				else
				{
					noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy; 
				} 
				currentPlayerTerritories.get((selectedTerrIndex-1)).setArmiesPresent((currentPlayerTerritories.get((selectedTerrIndex-1)).getArmiesPresent())+(userEnteredArmy));
			}
			else
			{
				break;
			}
			if(noOfRemainingArmies > 0)
			{
				System.out.println("Please select next territory");
				selectedTerrIndex = scanner.nextInt();
			}
		}while(noOfRemainingArmies > 0);

		reinforcedMap.put(currentPlayer, currentPlayerTerritories);
		System.out.println("Reinforcement Phase Completed for the Player "+currentPlayerName);
		System.out.println("\n");
		return reinforcedMap;
	}
	/**
	 * Cacluation of Armies for the Player
	 * @param currentPlayer
	 * @param currTerritoryList
	 * @param riskContinentList
	 * @return
	 */
	public int calculateArmy(RiskPlayer currentPlayer, ArrayList<RiskTerritory> currTerritoryList, ArrayList<RiskContinent> riskContinentList) {

		List<String> ownedContinents=new ArrayList<>(currentPlayer.getOccupiedContinents()) ;
		int controlVal=Constants.ZERO,noOfArmiesForPlayer=Constants.ZERO;

		if(ownedContinents.size()!=0)
		{
			for (int i = 0; i < riskContinentList.size(); i++) {
				for (int j = 0; j < ownedContinents.size(); j++) {
					if (riskContinentList.get(i).getContinentName().equalsIgnoreCase(ownedContinents.get(j))) {
						controlVal = controlVal + riskContinentList.get(i).getControllValue();
					}
				}
			}
		}

		if (currTerritoryList.size() < 10 && controlVal < 3) {
			noOfArmiesForPlayer = 3;
		} else {
			noOfArmiesForPlayer = controlVal + currTerritoryList.size() / 3;
		}

		return noOfArmiesForPlayer;
	}
	/**
	 * Iteration 2
	 * Implementation of a �card exchange view� using the Observer pattern.
	 * 
	 * @param playerId the playerId to set
	 * @param noOfCountriesOwned the number of countries assigned to the Player
	 * @param noOfPlayers the total number of players set for the game
	 * @param totalCountriesInContinent the total number of Countries in a continent
	 */
	public void CardExchangeView(int playerID, int noOfCountriesOwned, int noOfPlayers,int totalCountriesInContinent)
	{
	}


}

