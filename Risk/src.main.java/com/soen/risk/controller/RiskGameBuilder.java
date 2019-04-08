/**
 * 
 */
package com.soen.risk.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.helper.RiskArmyAllocationToPlayers;
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapFileWriter;
import com.soen.risk.helper.RiskMapUserCreator;
import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.startegies.RiskAggressiveStartegy;
import com.soen.risk.startegies.RiskBenevolentStartegy;
import com.soen.risk.startegies.RiskCheaterStrategy;
import com.soen.risk.startegies.RiskHumanStrategy;
import com.soen.risk.startegies.RiskRandomStrategy;
import com.soen.risk.tournamentControl.TournamentModeController;
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
 * @version 2.0
 */
public class RiskGameBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2690671575352872926L;
	transient static Scanner scanner=new Scanner(System.in);
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		RiskPlayerBuilder riskPlayerBuilder;
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		RiskMapValidator riskMapValidator = new RiskMapValidator();

		

		boolean mapInitCompletionStatus=false, mapValidationStatus=false, editCompletionStatus=true, currentMapAvailableStaus=false,checkflag=false ;
		List<String> mapFile = null, currentMap=null;
		List<String> riskPlayersNames;
		List<RiskPlayer> riskPlayerList;
		List<RiskTerritory> riskTerritoryList;
		ArrayList<RiskContinent> riskContinentList;

		int mapType= Constants.ZERO, count=1, gameType=1;
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		RiskTerritoryAssignmentToPlayer riskTerritoryAssignmentToPlayer=new RiskTerritoryAssignmentToPlayer();
		RiskArmyAllocationToPlayers  riskArmyAllocationToPlayers= new RiskArmyAllocationToPlayers();
		RiskMapUserCreator riskMapUserCreator= new RiskMapUserCreator();
		RiskMapFileWriter riskMapFileWriter=new RiskMapFileWriter();
		RiskMapUserCreatorView riskMapUserCreatorView=new RiskMapUserCreatorView();
		RiskMapEditor riskMapEditor;

		char continueEditChoice = Constants.ZERO,editChoice=Constants.ZERO;
		String filename;

		System.out.println("Welcome to RISK GAME!!! \n");
		System.out.println("Game Starting");
		
		System.out.println("\nPlease enter your choice:");
		System.out.println("1. Single mode");
		System.out.println("2. Tournament mode");
		
		do {
			while (!(scanner.hasNextInt())) {
				System.out.println("Invalid option. Please select from given options.");
				scanner.next();
			}
			gameType=scanner.nextInt();
			if(!(gameType==1 || gameType==2)) {
				System.out.println("Invalid option. Please select from given options.");
			}
		}while(!(gameType==1 || gameType==2));
		
		if(gameType==2) {
//			Call tournament controller
		TournamentModeController.startTournamentMode();
			
		}else {
		do {

			System.out.println("\nPlease enter your choice:");
			System.out.println("1. Upload from existing maps");
			System.out.println("2. Create map from scratch");
			System.out.println("3. Load existing saved games");


			do {
				while (!(scanner.hasNextInt())) {
					System.out.println("Invalid option. Please select from given options.");
					scanner.next();
				}
				mapType=scanner.nextInt();
				if(!(mapType==1 || mapType==2 || mapType==3)) {
					System.out.println("Invalid option. Please select from given options.");
				}
			}while(!(mapType==1 || mapType==2 || mapType==3));

			if (mapType==3) {
				RiskSavedGameController riskSavedGameController=new RiskSavedGameController();
				riskSavedGameController.resumeGame();
			}
			else if (mapType==1) {
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
			}else if (mapType==2) {
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
						System.out.println("User selected No[N].");
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

		riskPlayerBuilder=new RiskPlayerBuilder();
		riskPlayerBuilder.setUpPlayers();
		riskPlayersNames=new ArrayList<String>();
		riskPlayersNames=riskPlayerBuilder.getPlayersNameList();

		riskPlayerList=new ArrayList<RiskPlayer>();				
		riskPlayerBuilder.addPlayers(riskPlayersNames);
		riskPlayerList=riskPlayerBuilder.getRiskPlayerList();
		
		for (RiskPlayer player : riskPlayerList) {
			System.out.println("\nEnter Strategy of the Player "+player.getPlayerName());
			System.out.println("1. Human");
			System.out.println("2. Aggressive");
			System.out.println("3. Benevolent");
			System.out.println("4. Random");
			System.out.println("5. Cheater");
			
			
			int playerstrategy;
			do {
				while (!(scanner.hasNextInt())) {
					System.out.println("Invalid option. Please select from given options.");
					scanner.next();
				}
				playerstrategy = scanner.nextInt();
				if((playerstrategy>5 || playerstrategy<0)) {
					System.out.println("Invalid option. Please select from given options.");
				}
			}while((playerstrategy>5 || playerstrategy<0));
			
			if (playerstrategy == 1)
				player.setPlayerStrategy(new RiskHumanStrategy());
			else if (playerstrategy == 2)
				player.setPlayerStrategy(new RiskAggressiveStartegy());
			else if (playerstrategy == 3)
				player.setPlayerStrategy(new RiskBenevolentStartegy());
			else if (playerstrategy == 4)
				player.setPlayerStrategy(new RiskRandomStrategy());
			else if (playerstrategy == 5)
				player.setPlayerStrategy(new RiskCheaterStrategy());
		}
		
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
		riskMainMap=RiskGameHelper.assignControlValuesToPlayer(riskMainMap,riskContinentList);
		//		assigning round robin army to above map
		riskMainMap=riskArmyAllocationToPlayers.assignArmiesToPlayers(riskMainMap);

		//		Map returned in above line and sent that map for round robin army distribution


		/*		 
		 * Calling riskGameRunner	 * 
		 */
		RiskGameRunner riskGameRunner=new RiskGameRunner();
		riskGameRunner.startTurnbyturnGame(riskMainMap, riskContinentList);

	}
				scanner.close();
				
				System.out.println("Risk Game completed...Thank You...");
	}
}


