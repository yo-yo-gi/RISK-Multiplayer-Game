/**
 * 
 */
package com.soen.risk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskSavedGameController implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3090511003077001459L;
	transient static Scanner scanner=new Scanner(System.in);


	public boolean saveGame(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, ArrayList<RiskContinent> cotinentList) {
		
		List<Object> gameToBeSaved=new ArrayList<Object>();
		boolean savaStatus=false;
		 gameToBeSaved = Arrays.asList(riskMainMap,cotinentList);

		String filename;
		
		System.out.println("Please enter the filename you want to save the map with:");
		filename=scanner.next();
		String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/savedGames/"+filename+".txt").toAbsolutePath().toString();
		
//		Serialize gameToBeSaved
		try {
			FileOutputStream fileOut = new FileOutputStream(mapFilePath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(gameToBeSaved);
			out.close();
			fileOut.close();
			System.out.println("\nSerialization Successful... Checkout your specified output file..\n");
			savaStatus=true;
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return savaStatus;
	}
	

	@SuppressWarnings("unchecked")
	public void resumeGame() {		
		
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		
		ArrayList<RiskContinent> riskContinentList=new ArrayList<RiskContinent>();
	
		RiskGameRunner riskGameRunner=new RiskGameRunner();
		
				
		String loadGamesPath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/savedGames").toAbsolutePath().toString();
		Path path;
		int fileName;
		File folder = new File(loadGamesPath);
		File[] listOfFiles = folder.listFiles();
		int fileCounter=1;

		
//		logic to get all saved games
		System.out.println("Please select game you want to load:");
		for (File file : listOfFiles) {
			System.out.println(fileCounter+". "+file.getName());
			fileCounter++;
		}
		
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Invalid option. Please select from given options.");
				scanner.nextLine();
			}
			fileName=scanner.nextInt();
			if(fileName>=fileCounter || fileName<0) {
				System.out.println("Invalid option. Please select from given options.");
			}
		}while(fileName>=fileCounter || fileName<0);

		path=Paths.get(System.getProperty("user.dir") + "/src.main.resources/savedGames/"+(listOfFiles[fileName-1].getName()));
		
//		logic to de-serialize riskMainMap and  riskContinentList
		
		try {
			FileInputStream fileIn = new FileInputStream(path.toString());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			List<Object> savedGame= (List<Object>)in.readObject();
			riskMainMap=(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>) savedGame.get(0);
			riskContinentList=(ArrayList<RiskContinent>) savedGame.get(1);
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		riskGameRunner.startTurnbyturnGame(riskMainMap, riskContinentList);
	}
}
