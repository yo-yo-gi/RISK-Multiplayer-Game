/**
 * 
 */
package com.soen.risk.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yogesh
 *
 */
public class RiskAttackHelper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4203513605553006857L;

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
