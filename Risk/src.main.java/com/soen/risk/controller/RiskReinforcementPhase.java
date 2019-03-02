package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

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
		Scanner sc = new Scanner(System.in);
		String currentPlayerName = null;
		
		ArrayList<RiskTerritory> currentPlayerTerritories;
		int noOfRemainingArmies;
		currentPlayerTerritories=playerTerritories;
		int noOfCountriesOwned = currentPlayerTerritories.size();		
		currentPlayerName=currentPlayer.getPlayerName();
		
		noOfRemainingArmies=calculateArmy(currentPlayer,currentPlayerTerritories, riskContinentList);
		System.out.println("Reinforcement started.....");
		
		System.out.println("Player name : "+currentPlayerName);
		System.out.println("Number of Territories Owned : "+noOfCountriesOwned);
		System.out.println("Contries owned by player "+currentPlayerName+" are :");
		int territoryCounter=1;
		
		for (RiskTerritory riskTerritory : currentPlayerTerritories) {
			System.out.println(territoryCounter+". "+riskTerritory.getTerritoryName());
			territoryCounter++;
		}
		System.out.println("Add Armies to the Countries : ");
		do
		{
		    for(int i=0;i<currentPlayerTerritories.size();i++)  
		    {
		       if(noOfRemainingArmies!=0)
		       {
		    	   System.out.println("The No Of Armies available for reinforcement : "+noOfRemainingArmies);
		    	   System.out.println("Enter the number of Armies for Country : "+currentPlayerTerritories.get(i).getTerritoryName()+" From "+noOfRemainingArmies);
		    	   int userEnteredArmy = sc.nextInt();
		    	   if(userEnteredArmy < 0)
		    	   {
			    	   do 
		 	    	   {
		 	    		   System.out.println("No armies assigned cannot be less than zero, please add armies to the country : "+currentPlayerTerritories.get(i));
		 	    		   userEnteredArmy = sc.nextInt();	 	    		   
		 	    	   }while(userEnteredArmy < 0);
		    	   }
		    	   if(userEnteredArmy > noOfRemainingArmies)
	    		   {
	    			   do
	    			   {
	    				   System.out.println("Invalid Input");
		    			   System.out.println("Enter the number of Armies to reinforce for Country  "+currentPlayerTerritories.get(i).getTerritoryName());
		    			   userEnteredArmy = sc.nextInt();
		    			   
	    			   }while(userEnteredArmy>noOfRemainingArmies);
	    			   noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy;  
	    		   }
	    		   else
	    		   {
	    	      
	    		   noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy; 
	    		   } 
		    	   currentPlayerTerritories.get(i).setArmiesPresent((currentPlayerTerritories.get(i).getArmiesPresent())+(userEnteredArmy));
		       }
		       else
		       {
		    	   break;
		       }
		    }
		    if(noOfRemainingArmies > 0)
		    {
		    	System.out.println("The number of armies is still present"
		    			+ "\nPlease Add armies to the countries to complete the Reinforcement phase \n"
		    			+ "Iterating through the countries again");
		    }
		}while(noOfRemainingArmies > 0);
		
	    reinforcedMap.put(currentPlayer, currentPlayerTerritories);
	    System.out.println("Reinforcement Phase Completed for the Player "+currentPlayerName);
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
		int controlVal=0,noOfArmiesForPlayer=0;
		
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

