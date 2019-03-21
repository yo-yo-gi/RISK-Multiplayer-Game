/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
// TODO: Auto-generated Javadoc
/**
 * The Class RiskAttackPhase used to attack on different territories based on 2 different modes:
 * a) The normal attack mode is used to attack on any adjacent territory for each player
 * b) The all-out mode is used to attack on adjacent territories with max dice count unless any one 
 * of the attacker or defender wins.
 * 
 * @author Chirag
 * @author Shashank Rao
 * @version 1.0
 */
public class RiskAttackPhase {

	/** The logger. */
	RiskLogger logger= new RiskLogger();

	/** The desired dice cast by defender. */
	int desiredDiceCastByAttacker, desiredDiceCastByDefender,diceCastOfAttacker,diceCastOfDefender;

	/** The scanner. */
	Scanner scanner=new Scanner(System.in);

	/**
	 * Attack phase input for both modes.
	 */
//		public void attackPhaseInput(String attackSourceTerritoryName,int attackSourceArmy, String attackDestinationTerritoryName, int attackDestinationArmy) {
		public Map<String, Object> attackPhaseInput(String attackSourceTerritoryName,int attackSourceArmy, String attackDestinationTerritoryName, int attackDestinationArmy){
//			int att_army =10, def_army = 5 ;
			Map<String, Object> attackOutput = new HashMap<>();
			System.out.println("Do you want to attack in 1)Normal mode 2) All-Out mode");
			int choice= scanner.nextInt();
			switch(choice) {
			case 1:
				attackOutput=rollDiceForNormalAttackMode(attackSourceTerritoryName, attackSourceArmy, attackDestinationTerritoryName,attackDestinationArmy);
				break;
			case 2:
				attackOutput=rollDiceForAllOutAttackMode(attackSourceArmy,attackDestinationArmy);
				break;
	
			default:
				System.out.println("Invalid input");
	
			}
			return attackOutput;
	
		}

	

	/**
	 * Roll dice for attack.
	 *
	 * @param attackingArmyCount the attacking army count
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public Map<String, Object> rollDiceForNormalAttackMode(String attackSourceTerritoryName,int attackingArmyCount, String attackDestinationTerritoryName, int defendingArmyCount) {

		System.out.println("Enter the number of dice to roll for attack");

		while (!scanner.hasNextInt()) {
			System.out.println("Try Again!!");
			scanner.next();
		}
		desiredDiceCastByAttacker=scanner.nextInt();

		System.out.println("Enter the number of dice to roll for defence");
		while (!scanner.hasNextInt()) {
			System.out.println("Try Again!!");
			scanner.next();
		}
		desiredDiceCastByDefender=scanner.nextInt();

		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);

		Map<String, Object> output = new HashMap<>();
		if (desiredDiceCastByAttacker > 3) {
			throw new RuntimeException("Attacker cannot cast more than 3 dice");
		}
		if (desiredDiceCastByDefender > 2) {
			System.out.println("Defender cannot cast more than 2 dice. Try Again!!");
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByDefender=scanner.nextInt();
		}
		if (attackingArmyCount < 2) {
			throw new RuntimeException("Attacker should have more than 2 armies to attack");
		}
		if (defendingArmyCount < 1) {
			throw new RuntimeException("Defender should have at least 1 army to defend");
		}
		if (defendingArmyCount < desiredDiceCastByDefender) {
			throw new RuntimeException("Defender cannot cast more dice than the armies they have");
		}
		if (desiredDiceCastByAttacker >= attackingArmyCount) {
			throw new RuntimeException("Attacker must have at least one more army in their territory than the dice casted");
		}

		List<Integer> attackerDiceList = rollDice(desiredDiceCastByAttacker);
		//	        List<Integer> attackerDiceList = Arrays.asList(6, 4, 3);
		System.out.println(attackerDiceList);

		List<Integer> defenderDiceList = rollDice(desiredDiceCastByDefender);
		//	        List<Integer> defenderDiceList = Arrays.asList(5, 5);
		System.out.println(defenderDiceList);

		//	        if (desiredDiceCastByAttacker < desiredDiceCastByDefender) {
		if (desiredDiceCastByAttacker == 1 || desiredDiceCastByDefender == 1) {
			//Attacker 1, Defender 2
			System.out.println("Checking max attacker's and defender's");
			Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
			Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

			if (maxDiceCastByAttacker > maxDiceCastByDefender) {
				output.put("did_attacker_win", true);
				output.put(attackSourceTerritoryName, attackingArmyCount);
				output.put(attackDestinationTerritoryName, (defendingArmyCount - 1));
			} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
				output.put("did_defender_win", true);
				output.put(attackSourceTerritoryName, (attackingArmyCount - 1));
				output.put(attackDestinationTerritoryName, defendingArmyCount);
			}
			else {
				output.put("did_defender_win", true);
				output.put(attackSourceTerritoryName, (attackingArmyCount - 1));
				output.put(attackDestinationTerritoryName, defendingArmyCount);
			}
		} else {
			if (attackerDiceList.size() == 3) {
				attackerDiceList = attackerDiceList.subList(0, 2);
			}
			Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
			Integer maxDiceCastByDefender = Collections.max(defenderDiceList);
			Integer minDiceCastByAttacker = Collections.min(attackerDiceList);
			Integer minDiceCastByDefender = Collections.min(defenderDiceList);

			//Attacker 2, Defender 2 or Attacker 3 Defender 1 etc.
			int noAttackerWin = 0;
			int noDefenderWin = 0;
			for (int i = 0; i < desiredDiceCastByDefender; i++) {
				if ((i == 0 && maxDiceCastByAttacker > maxDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > minDiceCastByDefender)) {
					defendingArmyCount--;
					noAttackerWin++;
				} else {
					attackingArmyCount--;
					noDefenderWin++;
				}
			}
			output.put(attackSourceTerritoryName, attackingArmyCount);
			output.put(attackDestinationTerritoryName, defendingArmyCount);

			if (noAttackerWin == noDefenderWin) {
				output.put("did_defender_win", noDefenderWin>noAttackerWin);
			} else {
				output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
			}
		}
		System.out.println("output="+output.toString());
		logger.doLogging("Attack phase successful and the map with remaining armies is: "+output.toString());
		return output;
	}

	/**
	 * Roll dice.
	 *
	 * @param count the count
	 * @return the list
	 */
	public static List<Integer> rollDice(int count) {
		List<Integer> diceList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			diceList.add(rollDice());
		}
		diceList.sort(Collections.reverseOrder());
		return diceList;
	}

	/**
	 * Roll dice.
	 *
	 * @return the int
	 */
	public static int rollDice() {
		return ((int) ((Math.random() * 100) % 6) + 1);
	}

	/**
	 * Roll dice for all out attack mode.
	 *
	 * @param attackingArmyCount the attacking army count
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public static Map<String, Object> rollDiceForAllOutAttackMode(int attackingArmyCount, int defendingArmyCount) {
		int diceCastOfAttacker,diceCastOfDefender;
		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);

		Map<String, Object> output = new HashMap<>();
		while (attackingArmyCount>0 && defendingArmyCount>0) {
			if(attackingArmyCount==1 || defendingArmyCount==1) {
				if(attackingArmyCount>3) {
					diceCastOfAttacker=3;
				}else {
					diceCastOfAttacker=attackingArmyCount;
				}

				if(defendingArmyCount>2) {
					diceCastOfDefender=2;
				}
				else {
					diceCastOfDefender=defendingArmyCount;
				}
				List<Integer> attackerDiceList = rollDice(diceCastOfAttacker);

				System.out.println(attackerDiceList);

				List<Integer> defenderDiceList = rollDice(diceCastOfDefender);

				System.out.println(defenderDiceList);
				//Attacker 1, Defender 2
				System.out.println("Checking max attacker's and defender's");
				Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
				Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

				if (maxDiceCastByAttacker > maxDiceCastByDefender) {
					output.put("did_attacker_win", true);
					output.put("attacker_surviving_armies", attackingArmyCount);
					defendingArmyCount--;

					if(defendingArmyCount==0) {
						output.put("defender_surviving_armies", (defendingArmyCount));
						break;
					}

				} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
					output.put("did_defender_win", true);
					attackingArmyCount--;

					output.put("defender_surviving_armies", defendingArmyCount);
					if(attackingArmyCount==0) {
						output.put("attacker_surviving_armies", (attackingArmyCount));
						break;
					}
				}
				else {
					output.put("did_defender_win", true);
					attackingArmyCount--;

					output.put("defender_surviving_armies", defendingArmyCount);
					if(defendingArmyCount==0) {
						output.put("attacker_surviving_armies", (attackingArmyCount));
						break;
					}
				}
			}
			else {
				diceCastOfAttacker=3; diceCastOfDefender=2;
				System.out.println("The number of dice for attack:"+diceCastOfAttacker +"The number of dice for defence:"+diceCastOfDefender);
				List<Integer> attackerDiceList = rollDice(diceCastOfAttacker);

				System.out.println(attackerDiceList);

				List<Integer> defenderDiceList = rollDice(diceCastOfDefender);

				System.out.println(defenderDiceList);
				if (attackerDiceList.size() == 3) {
					attackerDiceList = attackerDiceList.subList(0, 2);
				}
				Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
				Integer maxDiceCastByDefender = Collections.max(defenderDiceList);
				Integer minDiceCastByAttacker = Collections.min(attackerDiceList);
				Integer minDiceCastByDefender = Collections.min(defenderDiceList);

				//Attacker 2, Defender 2 or Attacker 3 Defender 1 etc.
				int noAttackerWin = 0;
				int noDefenderWin = 0;
				for (int i = 0; i < diceCastOfDefender; i++) {
					if ((i == 0 && maxDiceCastByAttacker > maxDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > minDiceCastByDefender)) {
						defendingArmyCount--;
						noAttackerWin++;
					} else {
						attackingArmyCount--;
						noDefenderWin++;
					}
				}
				output.put("attacker_surviving_armies", attackingArmyCount);
				output.put("defender_surviving_armies", defendingArmyCount);

				if (noAttackerWin == noDefenderWin) {

					output.put("did_defender_win", noDefenderWin>noAttackerWin);
				} else {

					output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
				}
			}
		}
		//	logger.doLogging("Attack phase successful and the map with remaining armies is: "+output.toString());
		System.out.println("Output returned is: "+ output.toString());
		return output;
	}
	/**
	 * @param riskMainMap
	 * @return
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		int sourceTCoutner=1,destinationTCoutner=1;
		int attackSourceTerritoryIndex=0,attackSourceArmy=0,attackDestinationTerritoryIndex=0,attackDestinationArmy=0,attackerTerritoryArmy=0,defenderTerritoryArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,a1=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;
		List<String> adjTerritoryList;
		List<String> AdjAttackList;
		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		ArrayList<String> territoriesList = new ArrayList<String>();
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}


		System.out.println("Current Player:"+currentPlayer.getPlayerName());
		System.out.println("Select the territory you want to attack from:");
		for (RiskTerritory currTerritory : playerTerritories) {
			String a=currTerritory.getTerritoryName();
			territoriesList.add(a);
			System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") ");
			sourceTCoutner++;	

		}

		//		System.out.println(territoriesList);

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			attackSourceTerritoryIndex=scanner.nextInt();
			if(attackSourceTerritoryIndex>=sourceTCoutner || attackSourceTerritoryIndex<0) {
				System.out.println("Try Again!!");
			}
		}while(attackSourceTerritoryIndex>=sourceTCoutner || attackSourceTerritoryIndex<0);

		attackSourceTerritory=playerTerritories.get(attackSourceTerritoryIndex-1);
		attackSourceTerritoryName=attackSourceTerritory.getTerritoryName();
		attackSourceArmy=attackSourceTerritory.getArmiesPresent();


		adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

		/*
		 * for (RiskTerritory currTerritory : playerTerritories) { if
		 * (currTerritory.getTerritoryName().equalsIgnoreCase(attackSourceTerritoryName)
		 * ) { adjTerritoryList=currTerritory.getAdjacents(); }
		 * 
		 * }
		 */



//		System.out.println(adjTerritoryList);


		AdjAttackList=new ArrayList<String>(adjTerritoryList);
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
					AdjAttackList.remove(currAdj);
				}
			}
		}
	

//		AdjAttackList=AdjAttackList.stream().distinct().collect(Collectors.toList());


		System.out.println("Adjacent Territories to attack:\n"+AdjAttackList);
		RiskGameHelper riskGameHelper=new RiskGameHelper();
		System.out.println("Enter the territory you want to attack:");
		for (String territory : AdjAttackList) {
			System.out.println(destinationTCoutner+"." + RiskGameHelper.getRiskTerritoryByName(riskMainMap, territory).getTerritoryName()
					+" ("+RiskGameHelper.getRiskTerritoryByName(riskMainMap, territory).getArmiesPresent()+") ");
			destinationTCoutner++;
		}

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			attackDestinationTerritoryIndex=scanner.nextInt();
			if(attackDestinationTerritoryIndex>=destinationTCoutner || attackDestinationTerritoryIndex<0) {
				System.out.println("Try Again!!");
			}
		}while(attackDestinationTerritoryIndex>=destinationTCoutner || attackDestinationTerritoryIndex<0);

//		String defendingTerritory=AdjAttackList.get(attackDestinationTerritoryIndex-1);
		for(int i=0;i<AdjAttackList.size();i++) {
			if(i==attackDestinationTerritoryIndex-1) {
				defendingTerritory=AdjAttackList.get(i);
			}
		}
//		System.out.println("Defending Territory name:"+defendingTerritory);
		attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(riskMainMap, defendingTerritory);
		attackDestinationTerritoryName=attackDestinationTerritory.getTerritoryName();
		attackDestinationArmy=attackDestinationTerritory.getArmiesPresent();


		System.out.println("Attacker Territory Name:"+attackSourceTerritoryName);
		System.out.println("Attacker Army Count:"+attackSourceArmy);
		System.out.println("Defender Territory Name:"+attackDestinationTerritoryName);
		System.out.println("Defender Army count:"+attackDestinationArmy);
		
		Map<String, Object> attackOutputMap = new HashMap<>();
		
		attackOutputMap=attackPhaseInput(attackSourceTerritoryName,attackSourceArmy, attackDestinationTerritoryName, attackDestinationArmy);
		
		for (Entry<String, Object> entry : attackOutputMap.entrySet()) {
			if(entry.getKey()==attackSourceTerritoryName) {
				attackerTerritory=attackSourceTerritoryName;
				attackerTerritoryArmy=(int) entry.getValue();
			}
			else if(entry.getKey()==attackDestinationTerritoryName) {
				defenderTerritory=attackDestinationTerritoryName;
				defenderTerritoryArmy=(int) entry.getValue();
			}
		}
		System.out.println("Attacker:"+attackerTerritory+" has "+attackerTerritoryArmy+" left");
		System.out.println("Defender:"+defenderTerritory+" has "+defenderTerritoryArmy+" left");
		
		
		attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy);
		attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);
		

		riskMainMap=RiskGameHelper.updateArmyAfterAttack(attackSourceTerritory,attackDestinationTerritory, riskMainMap);
		

		return riskMainMap;
	}
}
