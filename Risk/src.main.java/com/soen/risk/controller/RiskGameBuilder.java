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
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapFileWriter;
import com.soen.risk.helper.RiskMapUserCreator;
import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.validator.RiskMapValidator;
import com.soen.risk.view.RiskMapUserCreatorView;


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

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		RiskLogger logger= new RiskLogger();
		RiskPlayerBuilder riskPlayerBuilder;
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		RiskMapValidator riskMapValidator = new RiskMapValidator();
	 
		Scanner scanner = new Scanner(System.in);

		boolean mapInitCompletionStatus=false, mapValidationStatus=false, editCompletionStatus=true, currentMapAvailableStaus=false,checkflag=false ;
		List<String> mapFile = null, currentMap=null;
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
		RiskMapUserCreatorView riskMapUserCreatorView=new RiskMapUserCreatorView();
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap;
		RiskMapEditor riskMapEditor;

		char continueEditChoice = Constants.ZERO,editChoice=Constants.ZERO;
		String filename;
		RiskFortificationPhase riskFortifyPhase=new RiskFortificationPhase();
		RiskGameHelper riskGameHelper=new RiskGameHelper();
		logger.doLogging("In RiskGameBuilder class------> ");
		
		System.out.println("Welcome to RISK GAME!!!");
		System.out.println();
		System.out.println();
		System.out.println("Game Starting");

		do {
			
			System.out.println("\nPlease enter your choice:");
			System.out.println("1. Upload from existing maps");
			System.out.println("2. Create map from scratch");
			logger.doLogging("Map Loading Phase------> ");

			do {
				while (!(scanner.hasNextInt() || mapType==1 || mapType==2)) {
					System.out.println("Invalid option. Please select from given options.");
					scanner.next();
				}
				mapType=scanner.nextInt();
				if(!(mapType==1 || mapType==2)) {
					System.out.println("Invalid option. Please select from given options.");
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
						System.out.println("Invalid option. Please select from given options.");
						scanner.next();
					}
					fileName=scanner.nextInt();
					if(fileName>=fileCounter || fileName<0) {
						System.out.println("Invalid option. Please select from given options.");
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
				checkflag=riskMapUserCreator.getcreateStatus();
				if(checkflag) {
					mapInitCompletionStatus=true; 
				}
				else {
					mapInitCompletionStatus=false; 
				}
			}
		}while(!mapInitCompletionStatus);

		if(mapType==2) {
			riskMapUserCreatorView.displayMap(mapFile);	
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
							riskMapUserCreatorView.displayMap(mapFile);	
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
								System.out.println("No[N] selected.");
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


		
		currentMap=new ArrayList<String>();
		for (String currLine : mapFile) {
			currentMap.add(currLine.replace("\\s", "").toLowerCase());
		}

		mapValidationStatus=riskMapValidator.validateMap(currentMap);
		logger.doLogging("Map validation status------> "+mapValidationStatus);
		if (mapValidationStatus) {
			if(mapType==1) {
				System.out.println("Map loaded and validated successfully...");
				currentMapAvailableStaus=true;
			}else {
				System.out.println("Map loaded and validated successfully...");
				System.out.println("Please enter the filename you want to save the map with:");
				filename=scanner.next();
				riskMapFileWriter.writeMapToTextFile(currentMap, filename);
				currentMapAvailableStaus=true;
			}

		}else {			
			System.out.println("Invalid map. Please choose / enter valid map...");
			System.out.println("Please restart the game");
			System.exit(0);
		}

		if (currentMapAvailableStaus) {
			System.out.println("Loading Game...please wait...");

			riskMapBuilder.loadMapData(currentMap);
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
		riskMainMap=riskGameHelper.assignControlValuesToPlayer(riskMainMap,riskContinentList);
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

			System.out.print("Player -->"+entry.getKey().getPlayerName() +" Do you want to fortify?(Y/N)");
			char selection1;
			do {
				selection1=scanner.next().charAt(0);
				if(!(selection1=='Y' || selection1=='y' || selection1=='n' || selection1=='N')) {
					System.out.println("Try Again!!");
				}
			}while(!(selection1=='Y' || selection1=='y' || selection1=='n' || selection1=='N'));
			if(selection1=='Y'||selection1=='y') {
				fortifiedMap=riskFortifyPhase.getFortifiedMap(reinforcedMap.keySet().stream().findFirst().get(), reinforcedMap.get(reinforcedMap.keySet().stream().findFirst().get()));
			}else System.out.println("Fortification phase skipped...");
			

		}
		System.out.println("Reinforcement & Fortification phases complete for all players. \r\n Phase 1 completed. Thank You!! ");

		scanner.close();
	}

}


