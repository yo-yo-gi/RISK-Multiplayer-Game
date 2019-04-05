/**
 * 
 */
package com.soen.risk.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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


	public boolean saveGame(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap, ArrayList<RiskContinent> cotinentList) {
		
		ArrayList<Object> gameToBeSaved=new ArrayList<Object>();
		boolean savaStatus=false;
		
//		adding gameMap and continentList in gameToBeSaved list
		gameToBeSaved.add(riskMainMap);
		gameToBeSaved.add(cotinentList);
		
		String filename="testSave";
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
	

	public void resumeGame(String savedGameFile) {		
		
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		
		ArrayList<RiskContinent> riskContinentList=new ArrayList<RiskContinent>();
	
		RiskGameRunner riskGameRunner=new RiskGameRunner();
		
		String filename="testSave";
		String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/savedGames/"+filename+".txt").toAbsolutePath().toString();
		
//		logic to de-serialize riskMainMap and  riskContinentList
		
		try {
			FileInputStream fileIn = new FileInputStream(mapFilePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			System.out.println("Deserialized Data: \n" + in.readObject().toString());
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
