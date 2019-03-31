/**
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.model.RiskPlayer;

/**
 * <h2>Player Builder Class</h2>
 * The class takes the number of players and names from user to randomly allocate the chance of player.
 * 
 * @author Pooja Dhir
 * @version 1.0
 */
public class RiskPlayerBuilder {

	/** The risk player list. */
	private ArrayList<RiskPlayer> riskPlayerList;

	/** The players name list. */
	private ArrayList<String> playersNameList;

	/**
	 * Instantiates a new risk player builder.
	 */
	public RiskPlayerBuilder(){
		playersNameList = new ArrayList<String>();
	}

	/**
	 * Sets the players.
	 */
	public void setUpPlayers() {
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = -1;

		//		 To take number of players
		System.out.println();
		System.out.println("Enter number of players");

		while(numberOfPlayers<0) {

			try {
				numberOfPlayers = s.nextInt();
				//		To check whether the numberofPlayers are between 2 to 6		    		
				if(numberOfPlayers <3 || numberOfPlayers>6) {
					if(numberOfPlayers<3) {
						System.out.println("Number of players cannot be less than 3");
					}
					if(numberOfPlayers>6) {
						System.out.println("Number of players cannot be greater than 6");
					}
					numberOfPlayers = -1;
				}
			}catch(Exception e) {
				//		Validation for entering only number not character. 
				System.out.println("Please enter number");
				numberOfPlayers = -1;
				s.next();
			}
		}
		//		Validation for names entering should not be repeated.
		System.out.println("Enter names of Players");
		int count = Constants.ZERO;
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
	}

	/**
	 * Adds the players.
	 *
	 * @param riskPlayersNames the risk players names
	 */
	void addPlayers(List<String> riskPlayersNames){
		ArrayList<RiskPlayer> tempRiskPlayerList=new ArrayList<RiskPlayer>();
		RiskPlayer riskPlayer; 
		int initialArmy=RiskGameHelper.calculateInitialArmies(riskPlayersNames);

		for (String currerntPlayerName: riskPlayersNames) {
			riskPlayer=new RiskPlayer();
			riskPlayer.setPlayerName(currerntPlayerName);
			riskPlayer.setArmiesOwned(initialArmy);
			tempRiskPlayerList.add(riskPlayer);
		}
		Collections.shuffle(tempRiskPlayerList);
		riskPlayerList= tempRiskPlayerList;
	}

	/**
	 * Gets the risk player list.
	 *
	 * @return the riskPlayerList
	 */

	public ArrayList<RiskPlayer> getRiskPlayerList() {
		return riskPlayerList;
	}

	/**
	 * Sets the risk player list.
	 *
	 * @param riskPlayerList the riskPlayerList to set
	 */

	public void setRiskPlayerList(ArrayList<RiskPlayer> riskPlayerList) {
		this.riskPlayerList = riskPlayerList;
	}

	/**
	 * Gets the players name list.
	 *
	 * @return the playersNameList
	 */

	public ArrayList<String> getPlayersNameList() {
		return playersNameList;
	}

	/**
	 * Sets the players name list.
	 *
	 * @param playersNameList the playersNameList to set
	 */

	public void setPlayersNameList(ArrayList<String> playersNameList) {
		this.playersNameList = playersNameList;
	}

}
