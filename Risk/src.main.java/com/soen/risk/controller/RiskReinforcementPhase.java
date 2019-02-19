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
 * else the player is given an amount of armies corresponding to the continent’s control value.
 * 
 * @author Neha
 * @version 1.0
 *
 */
public class RiskReinforcementPhase 
{

	/**
	 * Iteration 1
     * Calculation the number of armies assigned to a players
     * 
	 * @param playerId the playerId to set
	 * @param noOfCountriesOwned the number of countries assigned to the Player
	 * @param noOfPlayers the total number of players set for the game
	 * @param totalCountriesInContinent the total number of Countries in a continent
	 */
     
	public HashMap<RiskPlayer, ArrayList<RiskTerritory>> armyCalculationPerPlayer(HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap, ArrayList<RiskContinent> riskContinentList)
	{
		
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>(playerMap);
		Scanner sc = new Scanner(System.in);
		String currentPlayerName = null;
		List<String> ownedContinents=new ArrayList<>();
		for ( RiskPlayer key : playerMap.keySet() ) {
			currentPlayerName=key.getPlayerName();
			ownedContinents=key.getOccupiedContinents();
		}
		
	
//		String currentPlayerName=playerMap.keySet().toArray()[0].toString();
		ArrayList<RiskTerritory> currentPlayerTerritories;
		int noOfArmiesForPlayer;

		playerMap.containsKey(currentPlayerName);
		currentPlayerTerritories=playerMap.get(currentPlayerName);
		int noOfCountriesOwned = currentPlayerTerritories.size();
		noOfArmiesForPlayer = noOfCountriesOwned/playerMap.size();
		
		int i = 0;
		
		System.out.println("Player name "+currentPlayerName);
		System.out.println("Player has Countries "+noOfCountriesOwned);
		System.out.println("And the player has no Of Armies"+noOfArmiesForPlayer);
		System.out.println("Add Armies to the Countries");
		
		//iterating ArrayList
		
	    for(i=0;i<currentPlayerTerritories.size();i++)  
	    {
	       if(noOfArmiesForPlayer!=0)
	       {
	    	   System.out.println("The No Of Armies for Player "+noOfArmiesForPlayer);
	    	   System.out.println("Enter the number of Armies for Country "+currentPlayerTerritories.get(i)+" From "+noOfArmiesForPlayer);
	    	   int armyCal = sc.nextInt();
	    	   
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
	    	   //Map<Player><List of territories with updated armies>
	    	   currentPlayerTerritories.get(i).setArmiesPresent((currentPlayerTerritories.get(i).getArmiesPresent())+(noOfArmiesForPlayer));
	       }
	       else
	       {
	    	   System.out.println("Reinforcement Phase Completed for the Player");
	    	   break;
	       }
	    }
	    reinforcedMap.put((RiskPlayer) playerMap.keySet().toArray()[0], currentPlayerTerritories);
	    return reinforcedMap;
	}
	
	
	/**
	 * Iteration 2
     * Implementation of a “card exchange view” using the Observer pattern.
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

