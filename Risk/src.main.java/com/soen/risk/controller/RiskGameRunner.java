/**
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhaseType;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.startegies.RiskHumanStrategy;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskGameRunner {

	public void startTurnbyturnGame(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap,ArrayList<RiskContinent> riskContinentList) {
		//		starting turn by turn reinforcement, attack and fortify

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		while(tempMap.size()>1) {

			for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()){
				
				entry.getKey().setCurrentPlayerTurn(true);	
				
				if (entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy) {
					System.out.println("Do you want to save the game?");
					
				}
				entry.getKey().setCurrentPlayerPhase(RiskPhaseType.REINFORCEMENT);				
				riskMainMap=entry.getKey().getPlayerStrategy().reinforce(riskMainMap, riskContinentList);
				
				if (entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy) {
					System.out.println("Do you want to save the game?");
				}
				entry.getKey().setCurrentPlayerPhase(RiskPhaseType.ATTACK);		
				riskMainMap=entry.getKey().getPlayerStrategy().attack(riskMainMap);
				
				if (entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy) {
					System.out.println("Do you want to save the game?");
				}
				entry.getKey().setCurrentPlayerPhase(RiskPhaseType.FORTIFY);		
				riskMainMap=entry.getKey().getPlayerStrategy().fortify(riskMainMap);
				
				
				entry.getKey().setCurrentPlayerTurn(false);
			}
			tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		}
	}
}
