/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.soen.risk.helper.RiskLogger;
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
		}while(!(choice>=1 || choice <=2));
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
			if (desiredDiceCastByAttacker > 3 || desiredDiceCastByAttacker==0) {
				System.out.println("Attacker cannot cast more than 3 or 0 dice. Try Again!!");
			}
		}while(desiredDiceCastByAttacker > 3 || desiredDiceCastByAttacker==0);

		System.out.println("Enter the number of dice you want to roll for defence: ");

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByDefender=scanner.nextInt();		
			if (desiredDiceCastByDefender > 2 || desiredDiceCastByDefender==0) {
				System.out.println("Defender cannot cast more than 2 or 0 dice. Try Again!!");
			}
		}while(desiredDiceCastByDefender > 2 || desiredDiceCastByDefender==0);

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

			if (noAttackerWin > noDefenderWin) {
				output.put("did_attacker_win", true);
			} else { 
				if(noAttackerWin < noDefenderWin) {
					output.put("did_defender_win", true);
				}
				else {
					output.put("did_attacker_win", true);
					output.put("did_defender_win", true);
				     }
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
					output.put(attackSourceTerritoryName, (attackingArmyCount));
					defendingArmyCount--;
					if(defendingArmyCount==0) {
						output.put(attackDestinationTerritoryName, (defendingArmyCount));
						break;
					}

				} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, (defendingArmyCount));
					if(attackingArmyCount==0) {
						output.put(attackSourceTerritoryName, (attackingArmyCount));
						break;
					}
				}
				else {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, (defendingArmyCount));
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
				System.out.println(attackSourceTerritoryName + attackingArmyCount);
				System.out.println(attackDestinationTerritoryName + defendingArmyCount);

				if (noAttackerWin > noDefenderWin) {
					output.put("did_attacker_win", true);
				} else { 
					if(noAttackerWin < noDefenderWin) 
					{
						output.put("did_defender_win", true);
					}
					else {
						output.put("did_attacker_win", true);
						output.put("did_defender_win", true);
				     	     }
				}
			}
		}
		//logger.doLogging("Attack phase successful and the map with remaining armies-> "+output.toString());
		System.out.println("Output returned is: "+ output.toString());
		return output;
	}


}
