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
import java.util.Scanner;

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
	public void attackPhaseInput() {
		int att_army =10, def_army = 5 ;
		System.out.println("Do you want to attack in 1)Normal mode 2) All-Out mode");
		int choice= scanner.nextInt();
		switch(choice) {
		case 1:
			rollDiceForNormalAttackMode(att_army,def_army);
			break;
		case 2:
			rollDiceForAllOutAttackMode(att_army,def_army);
			break;

		default:
			System.out.println("Invalid input");

		}

	}

	/**
	 * Roll dice for attack.
	 *
	 * @param attackingArmyCount the attacking army count
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public Map<String, Object> rollDiceForNormalAttackMode(int attackingArmyCount, int defendingArmyCount) {

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
			throw new RuntimeException("Defender cannot cast more than 2 dice");
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
			System.out.println("Checking max attacker's");
			Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
			Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

			if (maxDiceCastByAttacker > maxDiceCastByDefender) {
				output.put("did_attacker_win", true);
				output.put("attacker_surviving_armies", attackingArmyCount);
				output.put("defender_surviving_armies", (defendingArmyCount - 1));
			} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
				output.put("did_defender_win", true);
				output.put("attacker_surviving_armies", (attackingArmyCount - 1));
				output.put("defender_surviving_armies", defendingArmyCount);
			}
			else {
				output.put("did_defender_win", true);
				output.put("attacker_surviving_armies", (attackingArmyCount - 1));
				output.put("defender_surviving_armies", defendingArmyCount);
			}

			// output.put("is_draw", false);
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
				if ((i == 0 && maxDiceCastByAttacker > minDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > maxDiceCastByDefender)) {
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
				//output.put("is_draw", true);
				output.put("did_attacker_win", false);
			} else {
				//output.put("is_draw", false);
				output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
			}
		}
		logger.doLogging("Attack phase successful and the map with remaining armies is: "+output);
		return output;
	}

	/**
	 * Roll dice.
	 *
	 * @param count the count
	 * @return the list
	 */
	public List<Integer> rollDice(int count) {
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
	public int rollDice() {
		return ((int) ((Math.random() * 100) % 6) + 1);
	}

	/**
	 * Roll dice for all out attack mode.
	 *
	 * @param attackingArmyCount the attacking army count
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public Map<String, Object> rollDiceForAllOutAttackMode(int attackingArmyCount, int defendingArmyCount) {
		diceCastOfAttacker=3; diceCastOfDefender=2;
		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);
		System.out.println("The number of dice for attack:"+diceCastOfAttacker +"The number of dice for defence:"+diceCastOfDefender);
		Map<String, Object> output = new HashMap<>();
		while (diceCastOfAttacker==3 && diceCastOfDefender==2) {
			if(attackingArmyCount>=3 && defendingArmyCount>=2) {
				List<Integer> attackerDiceList = rollDice(desiredDiceCastByAttacker);

				System.out.println(attackerDiceList);

				List<Integer> defenderDiceList = rollDice(desiredDiceCastByDefender);

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
					if ((i == 0 && maxDiceCastByAttacker > minDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > maxDiceCastByDefender)) {
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
					//output.put("is_draw", true);
					output.put("did_attacker_win", false);
				} else {
					//output.put("is_draw", false);
					output.put("did_attacker_win", (noAttackerWin > noDefenderWin));
				}
			}
			else if(attackingArmyCount==2 && defendingArmyCount==2) {
				diceCastOfAttacker=attackingArmyCount;
				diceCastOfDefender=defendingArmyCount;

			}
			else if(attackingArmyCount==1 && defendingArmyCount==1) {
				diceCastOfAttacker=attackingArmyCount;
				diceCastOfDefender=defendingArmyCount;
			}
			logger.doLogging("Attack phase successful and the map with remaining armies is: "+output);
			//return output;

		}
		return output;
	}

	/**
	 * @param riskMainMap
	 * @return
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(
			Map<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		
		return null;
	}
}
