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

import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
/**
 * The Class RiskAttackPhase used to attack on different territories based on 2 different modes:
 * a) The normal attack mode is used to attack on any adjacent territory for each player
 * b) The all-out mode is used to attack on adjacent territories with max dice count unless any one 
 * of the attacker or defender wins.
 * 
 * @author Chirag
 * @author Shashank Rao
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskAttackPhase {

	/** The logger. */
	RiskLogger logger= new RiskLogger();

	/** The scanner. */
	Scanner scanner=new Scanner(System.in);

	/**
	 * Attack phase input for both modes.
	 *
	 * @param attackSourceTerritoryName the attack source territory name
	 * @param attackSourceArmy the attack source army
	 * @param attackDestinationTerritoryName the attack destination territory name
	 * @param attackDestinationArmy the attack destination army
	 * @return the map
	 */
	public Map<String, Object> attackPhaseInput(String attackSourceTerritoryName,int attackSourceArmy, String attackDestinationTerritoryName, int attackDestinationArmy){
		int choice;
		Map<String, Object> attackOutput = new HashMap<>();
		System.out.println("Do you want to attack in 1)Normal Mode 2) All-Out Mode ");
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			choice= scanner.nextInt();
			switch(choice) {
			case 1:
				attackOutput=rollDiceForNormalAttackMode(attackSourceTerritoryName, attackSourceArmy, attackDestinationTerritoryName,attackDestinationArmy);
				break;
			case 2:
				attackOutput=rollDiceForAllOutAttackMode(attackSourceTerritoryName, attackSourceArmy, attackDestinationTerritoryName,attackDestinationArmy);
				break;
			default:
				System.out.println("Invalid input. Try again!!");

			}
		}while(choice>=1 || choice <=2);
		return attackOutput;
	}

	/**
	 * Roll dice for attack.
	 *
	 * @param attackSourceTerritoryName the attack source territory name
	 * @param attackingArmyCount the attacking army count
	 * @param attackDestinationTerritoryName the attack destination territory name
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public Map<String, Object> rollDiceForNormalAttackMode(String attackSourceTerritoryName,int attackingArmyCount, String attackDestinationTerritoryName, int defendingArmyCount) {

		Scanner scanner=new Scanner(System.in);
		int desiredDiceCastByAttacker, desiredDiceCastByDefender;
		System.out.println("Enter the number of dice you want to roll for attack: ");

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByAttacker=scanner.nextInt();
			if (desiredDiceCastByAttacker > 3) {
				System.out.println("Attacker cannot cast more than 3 dice. Try Again!!");
			}
		}while(desiredDiceCastByAttacker > 3);

		System.out.println("Enter the number of dice you want to roll for defence: ");

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByDefender=scanner.nextInt();		
			if (desiredDiceCastByDefender > 2) {
				System.out.println("Defender cannot cast more than 2 dice. Try Again!!");
			}
		}while(desiredDiceCastByDefender > 2);

		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);
		Map<String, Object> output = new HashMap<>();

		List<Integer> attackerDiceList = rollDice(desiredDiceCastByAttacker);
		System.out.println(attackerDiceList);

		List<Integer> defenderDiceList = rollDice(desiredDiceCastByDefender);
		System.out.println(defenderDiceList);

		if (desiredDiceCastByAttacker == 1 || desiredDiceCastByDefender == 1) {
			//Attacker 1, Defender 2
			System.out.println("Checking max attacker's and defender's when desiredDiceCastByAttacker or desiredDiceCastByDefender = 1 ");
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
				output.put("did_defender_win", noDefenderWin > noAttackerWin);
			} else {
				output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
			}
		}
		System.out.println("output="+output.toString());
		logger.doLogging("Attack phase successful and the map with remaining armies-> "+output.toString());
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
	public static Map<String, Object> rollDiceForAllOutAttackMode(String attackSourceTerritoryName,int attackingArmyCount, String attackDestinationTerritoryName, int defendingArmyCount) {
		int diceCastOfAttacker,diceCastOfDefender;
		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);

		Map<String, Object> output = new HashMap<>();
		while (attackingArmyCount>0 && defendingArmyCount>0) {
			if(attackingArmyCount>3) {
				diceCastOfAttacker=3;
			}else {
				diceCastOfAttacker=attackingArmyCount;
			}
			if(defendingArmyCount>2) {
				diceCastOfDefender=2;
			}else {
				diceCastOfDefender=defendingArmyCount;
			}
			if(attackingArmyCount==1 || defendingArmyCount==1) {
				List<Integer> attackerDiceList = rollDice(diceCastOfAttacker);

				System.out.println(attackerDiceList);

				List<Integer> defenderDiceList = rollDice(diceCastOfDefender);

				System.out.println(defenderDiceList);
				//Attacker 1, Defender 2
				System.out.println("Checking max attacker's and defender's when desiredDiceCastByAttacker or desiredDiceCastByDefender = 1 ");
				Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
				Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

				if (maxDiceCastByAttacker > maxDiceCastByDefender) {
					output.put(attackSourceTerritoryName, attackingArmyCount);
					defendingArmyCount--;
					if(defendingArmyCount==0) {
						output.put(attackDestinationTerritoryName, (defendingArmyCount));
						break;
					}

				} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, defendingArmyCount);
					if(attackingArmyCount==0) {
						output.put("attacker_surviving_armies", (attackingArmyCount));
						break;
					}
				}
				else {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, defendingArmyCount);
					if(defendingArmyCount==0) {
						output.put(attackSourceTerritoryName, (attackingArmyCount));
						break;
					}
				}
			}
			else {				
				System.out.println("The number of dice for attack: "+diceCastOfAttacker +" The number of dice for defence: "+diceCastOfDefender);
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
						if(defendingArmyCount==1) {
							break;
						}
					} else {
						attackingArmyCount--;
						noDefenderWin++;
						if(attackingArmyCount<=2) {
							break;
						}
					}
				}

				output.put(attackSourceTerritoryName, attackingArmyCount);
				output.put(attackDestinationTerritoryName, defendingArmyCount);
				System.out.println(attackSourceTerritoryName+ attackingArmyCount);
				System.out.println(attackDestinationTerritoryName+ defendingArmyCount);

				if (noAttackerWin == noDefenderWin) {
					output.put("did_defender_win", noDefenderWin > noAttackerWin);
				} else {
					output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
				}
			}
		}
		//logger.doLogging("Attack phase successful and the map with remaining armies-> "+output.toString());
		System.out.println("Output returned is: "+ output.toString());
		return output;
	}

	/**
	 * Gets the attackphase map.
	 *
	 * @param riskMainMap the risk main map
	 * @return the attackphase map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		char selectedchoice;
		int sourceTCoutner=1,destinationTCoutner=1, attackMoveArmy=0;
		int attackSourceTerritoryIndex=0,attackSourceArmy=0,attackDestinationTerritoryIndex=0,attackDestinationArmy=0,attackerTerritoryArmy=0,defenderTerritoryArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;
		List<String> adjTerritoryList;
		List<String> AdjAttackList;
		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		boolean cardEarnFlag=false;
		Map<String, Object> attackOutputMap = new HashMap<>();

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}



		do {


			//		System.out.println("Current Player:"+currentPlayer.getPlayerName());
			System.out.println("Select the territory you want to attack from:");
			for (RiskTerritory currTerritory : playerTerritories) {
				System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") ");
				sourceTCoutner++;	

			}
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
			attackSourceArmy=attackSourceTerritory.getArmiesPresent()-1;

			if (attackSourceArmy==1) {
				System.out.println("Player needs at least 1 army to attack...\n Please select another territory");
			}

		}while(attackSourceArmy==1);



		adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

		AdjAttackList=new ArrayList<String>(adjTerritoryList);
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
					AdjAttackList.remove(currAdj);
				}
			}
		}


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

		for(int i=0;i<AdjAttackList.size();i++) {
			if(i==attackDestinationTerritoryIndex-1) {
				defendingTerritory=AdjAttackList.get(i);
			}
		}
		attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(riskMainMap, defendingTerritory);
		attackDestinationTerritoryName=attackDestinationTerritory.getTerritoryName();
		attackDestinationArmy=attackDestinationTerritory.getArmiesPresent();


		System.out.println("Attacker Territory Name:"+attackSourceTerritoryName);
		System.out.println("Attacker Army Count:"+attackSourceArmy);
		System.out.println("Defender Territory Name:"+attackDestinationTerritoryName);
		System.out.println("Defender Army count:"+attackDestinationArmy);

		/*Below code will call the function to give user option to attack in Normal or All-Out method*/		
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
		attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy+1);
		attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);

		//		updating the main map for the army after every attack and deleting the territory if army is zero
		riskMainMap=RiskGameHelper.updateArmyAfterAttack(attackSourceTerritory,attackDestinationTerritory, riskMainMap);

		if (riskMainMap.size()==1) {
			System.out.println("*****************************************");
			System.out.println(currentPlayer.getPlayerName() + "wins the match and conquered the world...\n Game Ends");
			System.out.println("*****************************************");
			System.exit(0);
		}


		if ((boolean) (attackOutputMap.get("did_attacker_win"))) {
			cardEarnFlag=true;
			System.out.println("Attacker wins...");
		}else {
			System.out.println("Defender wins...");
		}

		System.out.println("Attacker:"+attackerTerritory+" has "+attackerTerritoryArmy+" left");
		System.out.println("Defender:"+defenderTerritory+" has "+defenderTerritoryArmy+" left");

		if (defenderTerritoryArmy==0) {
			System.out.println("How many armies you want to move to the new conquered territory?");
			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				attackMoveArmy = scanner.nextInt();
				if(attackMoveArmy>=attackerTerritoryArmy || attackMoveArmy<=0) {
					System.out.println("Try Again!!");
				}
			}while(attackMoveArmy>=attackerTerritoryArmy || attackMoveArmy<=0);

			//			Moving armies to new territory
			riskMainMap=RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackSourceTerritory,attackDestinationTerritory, riskMainMap);
		}


		System.out.println("Do you want to attack again?(Y/N)");
		do {
			selectedchoice=scanner.next().charAt(0);
			if(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N')) {
				System.out.println("Try Again!!");
			}
		}while(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N'));
		if(selectedchoice=='Y'||selectedchoice=='y') {
			getAttackphaseMap(riskMainMap);
		}else {
			if(cardEarnFlag) {
				riskMainMap=RiskGameHelper.assignRandomCard(riskMainMap);		
			}
			System.out.println("attack completed...");
		}


		return riskMainMap;
	}
}
