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
	 * @param attackingArmyCount the attacking army count
	 * @param attackDestinationTerritoryName the attack destination territory name
	 * @param defendingArmyCount the defending army count
	 * @return the map
	 */
	public static Map<String, Object> rollDiceForAllOutAttackMode(String attackSourceTerritoryName,int attackingArmyCount, String attackDestinationTerritoryName, int defendingArmyCount) {
		int diceCastOfAttacker,diceCastOfDefender;
		System.out.println("attackingArmyCount: " + attackingArmyCount + ", defendingArmyCount: " + defendingArmyCount);

		Map<String, Object> output = new HashMap<>();
		while (attackingArmyCount>Constants.ZERO && defendingArmyCount>Constants.ZERO) {
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
				Integer maxDiceCastByAttacker = Collections.max(attackerDiceList);
				Integer maxDiceCastByDefender = Collections.max(defenderDiceList);

				if (maxDiceCastByAttacker > maxDiceCastByDefender) {
					output.put(attackSourceTerritoryName, (attackingArmyCount));
					defendingArmyCount--;
					if(defendingArmyCount==Constants.ZERO) {
						output.put(attackDestinationTerritoryName, (defendingArmyCount));
						break;
					}

				} else if( maxDiceCastByDefender > maxDiceCastByAttacker) {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, (defendingArmyCount));
					if(attackingArmyCount==Constants.ZERO) {
						output.put(attackSourceTerritoryName, (attackingArmyCount));
						break;
					}
				}
				else {
					attackingArmyCount--;
					output.put(attackDestinationTerritoryName, (defendingArmyCount));
					if(attackingArmyCount==Constants.ZERO) {
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
				int noAttackerWin = Constants.ZERO;
				int noDefenderWin = Constants.ZERO;
				for (int i = 0; i < diceCastOfDefender; i++) {
					if ((i == 0 && maxDiceCastByAttacker > maxDiceCastByDefender) || (i == 1 && minDiceCastByAttacker > minDiceCastByDefender)) {
						defendingArmyCount--;
						noAttackerWin++;
						if(defendingArmyCount<1) {
							break;
						}
					} else {
						attackingArmyCount--;
						noDefenderWin++;
						if(attackingArmyCount<1) {
							break;
						}
					}
				}

				output.put(attackSourceTerritoryName, attackingArmyCount);
				output.put(attackDestinationTerritoryName, defendingArmyCount);

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
