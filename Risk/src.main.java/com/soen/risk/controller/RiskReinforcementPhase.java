package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public HashMap<RiskPlayer, ArrayList<RiskTerritory>> getReinforcedMap(RiskPlayer currentPlayer, ArrayList<RiskTerritory> playerTerritories, ArrayList<RiskContinent> riskContinentList)
	{
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		Scanner sc = new Scanner(System.in);
		String currentPlayerName = null;
		
		ArrayList<RiskTerritory> currentPlayerTerritories;
		int noOfArmiesForPlayer;
		currentPlayerTerritories=playerTerritories;
		int noOfCountriesOwned = currentPlayerTerritories.size();		
		currentPlayerName=currentPlayer.getPlayerName();
		
		noOfArmiesForPlayer=calculateArmy(currentPlayer,currentPlayerTerritories, riskContinentList);
		System.out.println("Player name : "+currentPlayerName);
		System.out.println("The Territories Owned : "+noOfCountriesOwned);
		System.out.println("The No Of Armies the player has : "+noOfArmiesForPlayer);
		System.out.println("Add Armies to the Countries : ");
		
	    for(int i=0;i<currentPlayerTerritories.size();i++)  
	    {
	       if(noOfArmiesForPlayer!=0)
	       {
	    	   System.out.println("The No Of Armies for Player : "+noOfArmiesForPlayer);
	    	   System.out.println("Enter the number of Armies for Country : "+currentPlayerTerritories.get(i).getTerritoryName()+" From "+noOfArmiesForPlayer);
	    	   int armyCal = sc.nextInt();
	    	   if(armyCal == 0)
	    	   {
		    	   do 
	 	    	   {
	 	    		   
	 	    		   System.out.println("No armies assigned cannot be zero, please add armies to the country : "+currentPlayerTerritories.get(i));
	 	    		   armyCal = sc.nextInt();
	 	    		   
	 	    	   }while(armyCal == 0);
	    	   }
	    	   if(armyCal > noOfArmiesForPlayer)
    		   {
    			   do
    			   {
    				   System.out.println("Invalid Input");
	    			   System.out.println("Enter the number of Armies for Country  "+currentPlayerTerritories.get(i).getTerritoryName());
	    			   armyCal = sc.nextInt();
	    			   
    			   }while(armyCal>noOfArmiesForPlayer);
    			   noOfArmiesForPlayer = noOfArmiesForPlayer - armyCal;  
    		   }
    		   else
    		   {
    	      
    		   noOfArmiesForPlayer = noOfArmiesForPlayer - armyCal; 
    		   } 
	    	   currentPlayerTerritories.get(i).setArmiesPresent((currentPlayerTerritories.get(i).getArmiesPresent())+(armyCal));
	       }
	       else
	       {
	    	   System.out.println("Reinforcement Phase Completed for the Player");
	    	   break;
	       }
	    }
	    reinforcedMap.put(currentPlayer, currentPlayerTerritories);
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
					if (riskContinentList.get(i).getContinentName().equals(ownedContinents.get(j))) {
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

