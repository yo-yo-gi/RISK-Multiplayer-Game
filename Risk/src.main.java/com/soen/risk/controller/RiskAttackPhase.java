/*
 * 
 */
package com.soen.risk.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.soen.risk.helper.Constants;

/**
 * <h2> Risk Attack Controller</h2>
 * The Class RiskAttackPhase used to attack on different territories based on 2 different modes:
 * a) The normal attack mode is used to attack on any adjacent territory for each player
 * b) The all-out mode is used to attack on adjacent territories with max dice count unless any one 
 * 	  of the attacker or defender wins.
 * 
 * @author Chirag Vora
 * @author Shashank Rao
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskAttackPhase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5702424585304391344L;

	/** The scanner. */
	transient Scanner scanner=new Scanner(System.in);

	/**
	 * Attack phase input for both modes.
	 *
	 * @param attackerSourceTerritoryName the attack source territory name
	 * @param attackSourceArmy the attack source army
	 * @param attackerDestinationTerritoryName the attack destination territory name
	 * @param attackDestinationArmy the attack destination army
	 * @return the map
	 */
	public Map<String, Object> attackPhaseInput(String attackerSourceTerritoryName,long attackSourceArmy, String attackerDestinationTerritoryName, long attackDestinationArmy){
		int choice;
		Map<String, Object> attackOutput = new HashMap<>();
		System.out.println("Do you want to attack in 1)Normal Mode 2) All-Out Mode ");
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			choice= scanner.nextInt();
			if(choice>=3) {
				choice=3;
			}
			switch(choice) {
			case 1:
				attackOutput=rollDiceForNormalAttackMode(attackerSourceTerritoryName, attackSourceArmy, attackerDestinationTerritoryName,attackDestinationArmy);
				break;
			case 2:
				attackOutput=rollDiceForAllOutAttackMode(attackerSourceTerritoryName, attackSourceArmy, attackerDestinationTerritoryName,attackDestinationArmy);
				break;
			default:
				System.out.println("Invalid input. Try again!!");
			}
		}while(choice>=3);
		return attackOutput;
	}

	/**
	 * Roll dice for attack.
	 *
	 * @param attackerSourceTerritoryName the attack source territory name
	 * @param attackSourceArmy the attacking army count
	 * @param attackerDestinationTerritoryName the attack destination territory name
	 * @param attackDestinationArmy the defending army count
	 * @return the map
	 */
	public Map<String, Object> rollDiceForNormalAttackMode(String attackerSourceTerritoryName,long attackSourceArmy, String attackerDestinationTerritoryName, long attackDestinationArmy) {

		Scanner scanner=new Scanner(System.in);
		int desiredDiceCastByAttacker, desiredDiceCastByDefender;
		System.out.println("Enter the number of dice you want to roll for attack: ");

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByAttacker=scanner.nextInt();
			if (desiredDiceCastByAttacker > 3 || desiredDiceCastByAttacker<=0 || desiredDiceCastByAttacker > attackSourceArmy) {
				System.out.println("Attacker cannot cast 0 dice/ more than 3 dice/ more than attackingArmyCount. Try Again!!");
			}
		}while(desiredDiceCastByAttacker > 3 || desiredDiceCastByAttacker<=0 || desiredDiceCastByAttacker > attackSourceArmy);

		System.out.println("Enter the number of dice you want to roll for defence: ");

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			desiredDiceCastByDefender=scanner.nextInt();		
			if (desiredDiceCastByDefender > 2 || desiredDiceCastByDefender<=0 || desiredDiceCastByDefender > attackDestinationArmy) {
				System.out.println("Defender cannot cast 0 dice/ more than 3 dice/ more than defendingArmyCount . Try Again!!");
			}
		}while(desiredDiceCastByDefender > 2 || desiredDiceCastByDefender<=0 || desiredDiceCastByDefender > attackDestinationArmy);

		System.out.println("attackingArmyCount: " + attackSourceArmy + ", defendingArmyCount: " + attackDestinationArmy);
		Map<String, Object> output = new HashMap<>();

		List<Integer> attackerDiceList = rollDice(desiredDiceCastByAttacker);
		System.out.println(attackerDiceList);

		List<Integer> defenderDiceList = rollDice(desiredDiceCastByDefender);
		System.out.println(defenderDiceList);

		if (desiredDiceCastByAttacker == 1 || desiredDiceCastByDefender == 1) {
			//Attacker 1, Defender 2
			Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
			Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

			if (maxDiceCastByAttacker > maxDiceCastByDefender) {
				output.put("did_attacker_win", true);
				output.put(attackerSourceTerritoryName, attackSourceArmy);
				output.put(attackerDestinationTerritoryName, (attackDestinationArmy - 1));
			} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
				output.put("did_defender_win", true);
				output.put(attackerSourceTerritoryName, (attackSourceArmy - 1));
				output.put(attackerDestinationTerritoryName, attackDestinationArmy);
			}
			else {
				output.put("did_defender_win", true);
				output.put(attackerSourceTerritoryName, (attackSourceArmy - 1));
				output.put(attackerDestinationTerritoryName, attackDestinationArmy);
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
					attackDestinationArmy--;
					noAttackerWin++;
				} else {
					attackSourceArmy--;
					noDefenderWin++;
				}
			}
			output.put(attackerSourceTerritoryName, attackSourceArmy);
			output.put(attackerDestinationTerritoryName, attackDestinationArmy);

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
		
		return output;
	}

	/**
	 * Roll dice function to return the dice roll count list. 
	 *
	 * @param diceCastOfAttacker the count
	 * @return the list
	 */
	public static List<Integer> rollDice(long diceCastOfAttacker) {
		List<Integer> diceList = new ArrayList<>();
		for (int i = 0; i < diceCastOfAttacker; i++) {
			diceList.add(rollDice());
		}
		diceList.sort(Collections.reverseOrder());
		return diceList;
	}

	/**
	 * Roll dice function to return the dice roll count.
	 *
	 * @return the int
	 */
	public static int rollDice() {
		return ((int) ((Math.random() * 100) % 6) + 1);
	}

	/**
	 * Roll dice for all out attack mode where the method will execute until the atacker's or defender's army count is 0.
	 *
	 * @param attackSourceTerritoryName the attack source territory name
	 * @param attackSourceArmy the attacking army count
	 * @param attackDestinationTerritoryName the attack destination territory name
	 * @param attackDestinationArmy the defending army count
	 * @return the map
	 */
	public static Map<String, Object> rollDiceForAllOutAttackMode(String attackSourceTerritoryName,long attackSourceArmy, String attackDestinationTerritoryName, long attackDestinationArmy) {
		long diceCastOfAttacker;
		long diceCastOfDefender;
		System.out.println("attackingArmyCount: " + attackSourceArmy + ", defendingArmyCount: " + attackDestinationArmy);

		Map<String, Object> output = new HashMap<>();
		while (attackSourceArmy>Constants.ZERO && attackDestinationArmy>Constants.ZERO) {
			if(attackSourceArmy>3) {
				diceCastOfAttacker=3;
			}else {
				diceCastOfAttacker=attackSourceArmy;
			}
			if(attackDestinationArmy>2) {
				diceCastOfDefender=2;
			}else {
				diceCastOfDefender=attackDestinationArmy;
			}
			if(attackSourceArmy==1 || attackDestinationArmy==1) {
				List<Integer> attackerDiceList = rollDice(diceCastOfAttacker);

				System.out.println(attackerDiceList);

				List<Integer> defenderDiceList = rollDice(diceCastOfDefender);

				System.out.println(defenderDiceList);
				//Attacker 1, Defender 2
				Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
				Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

				if (maxDiceCastByAttacker > maxDiceCastByDefender) {
					output.put(attackSourceTerritoryName, (attackSourceArmy));
					attackDestinationArmy--;
					if(attackDestinationArmy==Constants.ZERO) {
						output.put(attackDestinationTerritoryName, (attackDestinationArmy));
						break;
					}

				} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
					attackSourceArmy--;
					output.put(attackDestinationTerritoryName, (attackDestinationArmy));
					if(attackSourceArmy==Constants.ZERO) {
						output.put(attackSourceTerritoryName, (attackSourceArmy));
						break;
					}
				}
				else {
					attackSourceArmy--;
					output.put(attackDestinationTerritoryName, (attackDestinationArmy));
					if(attackSourceArmy==Constants.ZERO) {
						output.put(attackSourceTerritoryName, (attackSourceArmy));
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
				int noAttackerWin = Constants.ZERO;
				int noDefenderWin = Constants.ZERO;
				for (int i = 0; i < diceCastOfDefender; i++) {
					if ((i == 0 && maxDiceCastByAttacker > maxDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > minDiceCastByDefender)) {
						attackDestinationArmy--;
						noAttackerWin++;
						if(attackDestinationArmy<1) {
							break;
						}
					} else {
						attackSourceArmy--;
						noDefenderWin++;
						if(attackSourceArmy<1) {
							break;
						}
					}
				}

				output.put(attackSourceTerritoryName, attackSourceArmy);
				output.put(attackDestinationTerritoryName, attackDestinationArmy);

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
		return output;
	}
}
