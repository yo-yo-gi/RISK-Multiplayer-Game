/**
 * 
 */
package com.soen.risk.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.helper.RiskArmyAllocationToPlayers;
import com.soen.risk.helper.RiskGamehelper;
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapFileWriter;
import com.soen.risk.helper.RiskMapUserCreator;
import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.validator.RiskMapValidator;

/**
 * <h2>Main Game Controller</h2>
 * This class works as a main game controller where it
 * loads game, load user and start reinforcement 
 * and fortify phase turn by turn.
 * 
 * @author Yogesh Nimbhorkar
 * @author Shashank Rao
 * @version 1.0
 */
public class RiskGameBuilder {

	public static void main(String[] args) throws IOException {
		RiskLogger logger= new RiskLogger();
		RiskPlayerBuilder riskPlayerBuilder;
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		RiskMapValidator riskMapValidator = new RiskMapValidator();
		//		RiskMapFileWriter riskMapFileWriter = new RiskMapFileWriter(); 
		Scanner scanner = new Scanner(System.in);
		
		boolean mapInitCompletionStatus=false, mapValidationStatus=false, editCompletionStatus=true, currentMapAvailableStaus=false ;
		List<String> mapFile = null;
		List<String> riskPlayersNames;
		List<RiskPlayer> riskPlayerList;
		List<RiskTerritory> riskTerritoryList;
		ArrayList<RiskContinent> riskContinentList;
		
		int mapType=0, count=1;
		Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		RiskTerritoryAssignmentToPlayer riskTerritoryAssignmentToPlayer=new RiskTerritoryAssignmentToPlayer();
		RiskArmyAllocationToPlayers  riskArmyAllocationToPlayers= new RiskArmyAllocationToPlayers();
		RiskMapUserCreator riskMapUserCreator= new RiskMapUserCreator();
		RiskMapFileWriter riskMapFileWriter=new RiskMapFileWriter();
		RiskReinforcementPhase riskReinforcementPhase=new RiskReinforcementPhase();
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;
		RiskMapEditor riskMapEditor;
		
		char continueEditChoice = Constants.ZERO,editChoice=Constants.ZERO;
		String filename;
		RiskFortificationPhase riskFortifyPhase=new RiskFortificationPhase();
		RiskGamehelper riskGamehelper=new RiskGamehelper();
		logger.doLogging("In RiskGameBuilder class------> ");


		do {
			System.out.println("Welcome to RISK GAME!!!");
			System.out.println();
			System.out.println();
			System.out.println("Game Starting");
			System.out.println("Please enter your choice:");
			System.out.println("1. Upload from existing maps");
			System.out.println("2. Create map from scratch");
			logger.doLogging("Map Loading Phase------> ");

			do {
			while (!(scanner.hasNextInt() || mapType==1 || mapType==2)) {
		 		System.out.println("Try Again!!");
		 		scanner.next();
		 	}
			mapType=scanner.nextInt();
			if(!(mapType==1 || mapType==2)) {
				System.out.println("Try Again!!");
			}
			}while(!(mapType==1 || mapType==2));
				
			if (mapType==1) {
				String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/maps").toAbsolutePath().toString();
				Path path;
				int fileName;
				File folder = new File(mapFilePath);
				File[] listOfFiles = folder.listFiles();
				int fileCounter=1;

				System.out.println("Upload selected.");
				System.out.println("Please select the map you want to upload:");
				for (File file : listOfFiles) {
					System.out.println(fileCounter+". "+file.getName());
					fileCounter++;
				}
				
				do {
					    while (!scanner.hasNextInt()) {
				 		System.out.println("Try Again!!");
				 		scanner.next();
				 	    }
					    fileName=scanner.nextInt();
					    if(fileName>=fileCounter || fileName<0) {
						System.out.println("Try Again!!");
					    }
					}while(fileName>=fileCounter || fileName<0);

				path=Paths.get(System.getProperty("user.dir") + "/src.main.resources/maps/"+(listOfFiles[fileName-1].getName()));	
				mapFile=riskMapBuilder.parseMapFile(path.toAbsolutePath().toString());
				      if (riskMapBuilder.getMapUploadStatus()) {
					  mapInitCompletionStatus=true;
				      }
				  }
			         if (mapType==2) {
				     System.out.println("Create Map selected");
				     mapFile=riskMapUserCreator.mapCreator();
				     mapInitCompletionStatus=true;
			       }
		        }while(!mapInitCompletionStatus);
		
		if(mapType==2) {
			   if(riskMapUserCreator.getcreateStatus()) {
				while(editCompletionStatus) {
					
				boolean continueEdit=true;
				System.out.println("Do you want to edit map you created before starting the game: Yes[Y]/No[N]");
					
					do {
						editChoice=scanner.next().charAt(0);
					if(!(editChoice=='Y' || editChoice=='y' || editChoice=='n' || editChoice=='N')) {
						System.out.println("Try Again!!");
					    }
					}while(!(editChoice=='Y' || editChoice=='y' || editChoice=='n' || editChoice=='N'));
					
					    if(editChoice=='Y'||editChoice=='y') {
						mapValidationStatus=false;
						currentMapAvailableStaus=false;
						riskMapEditor=new RiskMapEditor(mapFile);
						riskMapEditor.editMap();
						mapFile=riskMapEditor.getFullMap();
						
						while(continueEdit) {
							System.out.println("Do you want to edit the map again before starting the game:Yes[Y]/No[N]");
							do {
								continueEditChoice=scanner.next().charAt(0);
							if(!(continueEditChoice=='Y' || continueEditChoice=='y' || continueEditChoice=='n' || continueEditChoice=='N')) {
								System.out.println("Try Again!!");
							   }
						}while(!(editChoice=='Y' || editChoice=='y' || editChoice=='n' || editChoice=='N'));
							
							if(continueEditChoice=='Y'||continueEditChoice=='y') {
								mapValidationStatus=false;
								currentMapAvailableStaus=false;
								riskMapEditor=new RiskMapEditor(mapFile);
								riskMapEditor.editMap();
								mapFile=riskMapEditor.getFullMap();
							}else {
								System.out.println("User selecte No[N].");
								continueEdit=false;
								break;
							}
						}
						break;
					}else {
						System.out.println("User selecte No[N].");
						editCompletionStatus=false;
					}
				}
			}
		}


		//		parsing finally written map file and validating it once again before 
		//		currentMapFile=riskMapBuilder.parseMapFile("C:\\RiskProject\\map.txt");
		mapValidationStatus=riskMapValidator.validateMap(mapFile);
		logger.doLogging("Map validation status------> "+mapValidationStatus);
		if (mapValidationStatus) {
			if(mapType==1) {
				System.out.println("Map loaded and validated successfully...");
				currentMapAvailableStaus=true;
			}else {
				System.out.println("Map loaded and validated successfully...");
				System.out.println("Please enter the filename you want to save the map with:");
				filename=scanner.nextLine();
				riskMapFileWriter.writeMapToTextFile(mapFile, filename);
				currentMapAvailableStaus=true;
			}

		}else {			
			System.out.println("Invalid map after editing...exiting the game....Please choose / enter valid map...");
			System.exit(0);
		}

		if (currentMapAvailableStaus) {
			System.out.println("Loading Game...please wait...");

			riskMapBuilder.loadMapData(mapFile);
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
		riskPlayerBuilder.setUpPlayers();
		riskPlayersNames=new ArrayList<String>();
		riskPlayersNames=riskPlayerBuilder.getPlayersNameList();

		riskPlayerList=new ArrayList<RiskPlayer>();				
		riskPlayerBuilder.addPlayers(riskPlayersNames);
		riskPlayerList=riskPlayerBuilder.getRiskPlayerList();
		System.out.println();
		System.out.println("Players numbers initialized. Game started.");
        //		List<String> playerList= new ArrayList<String>();
        //		playerList=riskPlayerList.toString();
		for(RiskPlayer curPlayer:riskPlayerList) {
			System.out.println("Player #"+count+" -> "+curPlayer.getPlayerName());
			count++;
		}
		System.out.println();

		/*
		 * assigning random territories to players and assigning random armies		 * 
		 */

		riskTerritoryList=riskMapBuilder.getTerritoryList();
		riskContinentList=riskMapBuilder.getContinentList();
		//		passing list of players and List of countries to assign random territories
		riskMainMap=riskTerritoryAssignmentToPlayer.assignTerritory(riskPlayerList,riskTerritoryList);
		//		assigning control value as per territories owned by player
		riskMainMap=riskGamehelper.assignControlValuesToPlayer(riskMainMap,riskContinentList);
		//		assigning round robin army to above map
		riskMainMap=riskArmyAllocationToPlayers.assignArmiesToPlayers(riskMainMap);

		//		Map returned in above line and sent that map for round robin army distribution


		/*		 
		 * Starting game reinforcement state and fortify state		 * 
		 */

		//		starting turn by turn reinforcement, attack and fortify

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet())
		{
			reinforcedMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
			fortifiedMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();

			reinforcedMap=riskReinforcementPhase.getReinforcedMap(entry.getKey(),entry.getValue(), riskContinentList);

			fortifiedMap=riskFortifyPhase.getFortifiedMap(reinforcedMap.keySet().stream().findFirst().get(), reinforcedMap.get(reinforcedMap.keySet().stream().findFirst().get()));

			//			riskMainMap.put(entry.getKey(), reinforcedMap.get(entry.getKey()));

		}


		//		send Map<StringAsPlayerName, List<Countries>> and List of continents to reinforcement state

		//		get same map with reinforced map

		//		send same map for fortify

		//		get fortify map
		
		System.out.println("Reinforcement & Fortification phases complete for all players. Phase 1 complete. Thank You!! ");
		
		scanner.close();
	}

}


