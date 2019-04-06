/**
 * 
 */
package com.soen.risk.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
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
public class RiskGameRunner implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4138186835044512635L;
	RiskSavedGameController riskSavedGameController=new RiskSavedGameController();
	transient static Scanner scanner=new Scanner(System.in);

	public void startTurnbyturnGame(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap,ArrayList<RiskContinent> riskContinentList) {
		//		starting turn by turn reinforcement, attack and fortify

		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
		boolean riskSaveFlag=false;
		while(tempMap.size()>1) {

			Iterator<Map.Entry<RiskPlayer, ArrayList<RiskTerritory>>> iterator = riskMainMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<RiskPlayer, ArrayList<RiskTerritory>> entry=iterator.next();
				
//				if next player not present then remove it from the iterator and try for next
				if (!(riskMainMap.containsKey(entry.getKey()))) {
					iterator.remove();
					continue;
				}
				
				if(isNoPlayerAcive(riskMainMap))
				entry.getKey().setCurrentPlayerTurn(true);
					
				if ((entry.getKey().getCurrentPlayerPhase()==(null)  || !(entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy)) && entry.getKey().isCurrentPlayerTurn()) {
//					reinforcement call
					riskMainMap=entry.getKey().getPlayerStrategy().reinforce(riskMainMap, riskContinentList);					
				}

				if (entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy && entry.getKey().getCurrentPlayerPhase()==null) {
					System.out.println("Do you want to save the game: Yes[Y]/No[N]");
					char decision;
					do {
						decision=scanner.next().charAt(0);
						if(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N')) {
							System.out.println("Try Again!!");
						}
					}while(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N'));
//					if yes then execute below 2 lines
					if(decision=='Y' || decision=='y') {	
					entry.getKey().setCurrentPlayerPhase(RiskPhaseType.ATTACK);
					riskSavedGameController.saveGame(riskMainMap,riskContinentList);
					riskSaveFlag=true;
					break;
					}else entry.getKey().setCurrentPlayerPhase(RiskPhaseType.ATTACK);
				}
				if ((entry.getKey().getCurrentPlayerPhase()==RiskPhaseType.ATTACK || !(entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy)) && entry.getKey().isCurrentPlayerTurn()) {
//				attack call
				riskMainMap=entry.getKey().getPlayerStrategy().attack(riskMainMap);
				entry.getKey().setCurrentPlayerPhase(null);
				}
				
				if (entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy && entry.getKey().getCurrentPlayerPhase()==null) {
					System.out.println("Do you want to save the game: Yes[Y]/No[N]");
					char decision;
					do {
						decision=scanner.next().charAt(0);
						if(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N')) {
							System.out.println("Try Again!!");
						}
					}while(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N'));
//					if yes then execute below 2 lines
					if(decision=='Y' || decision=='y') {
					entry.getKey().setCurrentPlayerPhase(RiskPhaseType.FORTIFY);	
					riskSavedGameController.saveGame(riskMainMap,riskContinentList);
					riskSaveFlag=true;
					break;
					}else entry.getKey().setCurrentPlayerPhase(RiskPhaseType.FORTIFY);
				}
				if ((entry.getKey().getCurrentPlayerPhase()==(RiskPhaseType.FORTIFY) || !(entry.getKey().getPlayerStrategy() instanceof RiskHumanStrategy)) && entry.getKey().isCurrentPlayerTurn()) {	
//				fortify call				
				riskMainMap=entry.getKey().getPlayerStrategy().fortify(riskMainMap);
				entry.getKey().setCurrentPlayerPhase(null);
				}
				
				entry.getKey().setCurrentPlayerTurn(false);
			}
			tempMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(riskMainMap);
			if (riskSaveFlag) {
				break;
			}
		}
	}

	private boolean isNoPlayerAcive(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		boolean noPlayerActive=true;
		
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()){
			if ((entry.getKey().isCurrentPlayerTurn())) {
				noPlayerActive=false;
				break;
			}
		}
		return noPlayerActive;
	}
}
