/**
 * 
 */
package com.soen.risk.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhaseType;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskSavedGameController {
	
	

	public boolean saveGame(LinkedHashMap<RiskPlayerBuilder, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> cotinentList) {
		
//		Need to write logic to save 
		
		
		
		return true;
	}

	public void resumeGame(File savedGameFile) {
		
		
		
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		ArrayList<RiskContinent> riskContinentList=new ArrayList<RiskContinent>();
		
		while(tempMap.size()>1) {

			for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()){
				
				entry.getKey().setCurrentPlayerTurn(true);
				
				
				riskMainMap=entry.getKey().getPlayerStrategy().reinforce(riskMainMap, riskContinentList);
				
				riskMainMap=entry.getKey().getPlayerStrategy().attack(riskMainMap);
				
				riskMainMap=entry.getKey().getPlayerStrategy().fortify(riskMainMap);
				
				
				entry.getKey().setCurrentPlayerTurn(false);
			}
			tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		}
	}
}
