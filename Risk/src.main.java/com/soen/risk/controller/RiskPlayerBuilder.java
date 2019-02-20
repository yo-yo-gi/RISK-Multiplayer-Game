package com.soen.risk.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.RiskPlayerHelper;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.validator.RiskPlayerValidator;
/**
 * 
 * @author pooja
 *
 */
public class RiskPlayerBuilder {

	private ArrayList<RiskPlayer> riskPlayerList;
	private ArrayList<String> playersNameList;
	
	
 //public static void main(String args[]) {
	public RiskPlayerBuilder(){

		Scanner s = new Scanner(System.in);
		int numberOfPlayers;
		playersNameList = new ArrayList<String>();
		int playerCounter=1;
		RiskPlayerValidator riskPlayerValidator=new RiskPlayerValidator();

		do {
			System.out.println("Enter no. of players");
			numberOfPlayers = s.nextInt();
		} while (!riskPlayerValidator.isValid(numberOfPlayers));
		
		System.out.println("Enter names");
		
		
		while( playerCounter<=numberOfPlayers) {
//			System.out.println(s.next());
			playersNameList.add(s.next());
			playerCounter++;
		}
		

		ArrayList numbers = new ArrayList();
		for (int j = 1; j <= numberOfPlayers; j++) {
			numbers.add(j);
		}
		
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


