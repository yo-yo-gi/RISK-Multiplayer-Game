/**
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.RiskPlayerHelper;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.validator.RiskPlayerValidator;
/**
 * <h2>Player Builder Class</h2>
 * The class takes the number of players and names from user to randomly allocate the chance of player.
 * 
 * @author pooja
 * @version 1.0
 */
public class RiskPlayerBuilder {

private ArrayList<RiskPlayer> riskPlayerList;
private ArrayList<String> playersNameList;
	
public RiskPlayerBuilder(){
		 playersNameList = new ArrayList<String>();
	     }
	     public void setUpPlayers() {
		 Scanner s = new Scanner(System.in);
		 int numberOfPlayers = -1;
		 int playerCounter=1;
		 
		 RiskPlayerValidator riskPlayerValidator=new RiskPlayerValidator();
//		 To take number of players
		 System.out.println("Enter no. of players");
			
		 while(numberOfPlayers<0) {
		    	
			 try {
		    		numberOfPlayers = s.nextInt();
//                  To check whether the numberofPlayers are between 2 to 6		    		
		    		if(numberOfPlayers <2 || numberOfPlayers>6) {
		    			System.out.println("Try Again!!");
		    			numberOfPlayers = -1;
		    		    }
	              }catch(Exception e) {
//	            	 Validation for entering only number not character. 
		               System.out.println("Please enter number");
			           numberOfPlayers = -1;
			           s.next();
	                   }
		        }
//                  Validation for names entering should not be repeated.
		           System.out.println("Enter names");
		           int count = 0;
		           while(count<numberOfPlayers) {
			       String name = s.next();
	    		   if(playersNameList!=null) {
				        if(!playersNameList.contains(name)) {
					    playersNameList.add(name);
					    count++;					
				    }else {
					 System.out.println("Player " + name+ " already exists");
				    }
			      }
		        }		

		       ArrayList numbers = new ArrayList();
		       for (int j = 1; j <= numberOfPlayers; j++) {
			   numbers.add(j);
		       }
//             Collections used to shuffle the numbers and give number of position to player to start the game with.		
		       Collections.shuffle(numbers);
		       System.out.println(numbers);
	           }
	
    void addPlayers(List<String> riskPlayersNames){
		ArrayList<RiskPlayer> tempRiskPlayerList=new ArrayList<RiskPlayer>();
		RiskPlayer riskPlayer; 
		RiskPlayerHelper riskPlayerHelper=new RiskPlayerHelper();
		
		int initialArmy=riskPlayerHelper.calculateInitialArmies(riskPlayersNames);
		
		for (String currerntPlayerName: riskPlayersNames) {
			riskPlayer=new RiskPlayer();
			riskPlayer.setPlayerName(currerntPlayerName);
			riskPlayer.setArmiesOwned(initialArmy);
			tempRiskPlayerList.add(riskPlayer);
		}
		riskPlayerList= tempRiskPlayerList;
        }
	
	/**
	 * @return the riskPlayerList
	 */
	public ArrayList<RiskPlayer> getRiskPlayerList() {
		return riskPlayerList;
	}

	/**
	 * @param riskPlayerList the riskPlayerList to set
	 */
	
	public void setRiskPlayerList(ArrayList<RiskPlayer> riskPlayerList) {
		this.riskPlayerList = riskPlayerList;
	}

	/**
	 * @return the playersNameList
	 */
	public ArrayList<String> getPlayersNameList() {
		return playersNameList;
	}

	/**
	 * @param playersNameList the playersNameList to set
	 */
	public void setPlayersNameList(ArrayList<String> playersNameList) {
		this.playersNameList = playersNameList;
	}
	
}
