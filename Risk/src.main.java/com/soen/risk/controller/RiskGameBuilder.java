/**
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.validator.RiskMapValidator;

/**
 * <h2>Main Game Controller</h2>
 * <ul>
 * <li>Loading game.
 * <li>loading user.
 * <li>turn by turn starting reinforcement and fortify phases
 *
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-02-17
 */
public class RiskGameBuilder {
	
public static void main(String[] args) {
		
		RiskPlayerBuilder riskPlayerBuilder;
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		RiskMapValidator riskMapValidator = new RiskMapValidator();
		Scanner scanner;
		boolean mapInitCompletionStatus=false, mapValidationStatus=false, editCompletionStatus=false, currentMapAvailableStaus=false ;
		List<String> mapFile = null, currentMapFile=null;
		List<String> riskPlayersNames;
		List<RiskPlayer> riskPlayerList;
		List<RiskTerritory> riskTerritoryList;
		List<RiskContinent> riskContinentList;
		int mapType=1, editType = 1;
		Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		
		
		
		
		do {
		System.out.println("Select appropriate number");
		System.out.println("1. Upload");
		System.out.println("2. Create");		
		scanner = new Scanner(System.in);
		mapType=scanner.nextInt();	//need to add validation
		
		if (mapType==1) {
			mapFile=riskMapBuilder.parseMapFile("D:\\riskProject\\EarthMap.txt");
			if (riskMapBuilder.getMapUploadStatus()) {
				mapInitCompletionStatus=true;
			}
		}
		if (mapType==2) {
//			call constructor
//			if (riskMapWriter.getMapWriteStatus()) {			//need to add code for create map status
//			mapFile=riskMapWriter.getInitialMap();
//				mapInitStatus=true;
//			}
		}
		}while(!mapInitCompletionStatus); //need to add logic for checking status of map upload or create
		
		mapValidationStatus=riskMapValidator.validateMap(mapFile);
		
		if (mapValidationStatus) {
			System.out.println("Map loaded and validated successfully...");
//			riskMapFileWriter.writeMapToTextFile(mapFile);
//			if(riskMapFileWriter.getCompletionStatus){
//			currentMapAvailableStaus=true;
//		}
		}else {			
			System.out.println("Invalid map file...exiting the game....Please choose / enter valid map...");
			System.exit(0);
		}
		
		do {
			System.out.println("Do you want to edit map before starting game?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			scanner = new Scanner(System.in);
			editType=scanner.nextInt();	//need to add validation
			
			if(editType==1) {
				mapValidationStatus=false;
				currentMapAvailableStaus=false;
//				call edit method 
//				parse newly created current map text file
//				send new map string arraylist
//				get and check copletion status of edit i.e. editStatus
//				if (riskMapEditor.getEditStatus) {
//				mapFile=riskMapEditor.getEditedMap();
				editCompletionStatus=true;
//				}				
			}
			
			if(editType==2)
			break;
			
		}while(!editCompletionStatus);
		
//		parsing finally written map file and validating it once again before 
		currentMapFile=riskMapBuilder.parseMapFile("D:\\riskProject\\EarthMap.txt");
		mapValidationStatus=riskMapValidator.validateMap(currentMapFile);
		
		if (mapValidationStatus) {
			System.out.println("Map loaded and validated successfully...");
//			riskMapFileWriter.writeMapToTextFile(mapFile);		//need to ask TA
//			if(riskMapFileWriter.getCompletionStatus){
			currentMapAvailableStaus=true;
//		}
		}else {			
			System.out.println("Invalid map after editing...exiting the game....Please choose / enter valid map...");
			System.exit(0);
		}
		
		if (currentMapAvailableStaus) {
			System.out.println("loading game...please wait...");
			riskMapBuilder.loadMapData();
		}else {
			System.out.println("Map file not available...Please provide the current map file to load game...");
			System.exit(0);
		}
		
		
		/* 
		 * 
		 * Game map loading and validation completed..... 
		 * 
		 */
		
		
		
		
		/*		  
		 * Adding players, assigning initial armies to players and loading appropriate graphs...		 * 
		 */
		
		riskPlayerBuilder=new RiskPlayerBuilder();
		riskPlayersNames=new ArrayList<String>();
		riskPlayersNames=riskPlayerBuilder.getPlayersNameList();
		
		riskPlayerList=new ArrayList<RiskPlayer>();				
		riskPlayerBuilder.addPlayers(riskPlayersNames);
		riskPlayerList=riskPlayerBuilder.getRiskPlayerList();
		
		/*
		 * assigning random territories to players and assigning random armies		 * 
		 */
		
		riskTerritoryList=riskMapBuilder.getTerritoryList();
		
//		passing list of players and List of countries to assign random territories
//		riskMainMap=assignRandomTerritories(riskPlayerList,riskTerritoryList);
//		Map returned in above line and sent that map for round robin army distribution
		
		
		/*		 
		 * Starting game reinforcement state and fortigy state		 * 
		 */
		riskContinentList=riskMapBuilder.getContinentList();
//		starting turn by turn reinforcement, attack and fortify
		
//		send Map<StringAsPlayerName, List<Countries>> and List of continents to reinforcement state
		
//		get same map with reinforced map
		
		
//		send same map for fortify
		
//		get fortify map
		
		scanner.close();
	}

}