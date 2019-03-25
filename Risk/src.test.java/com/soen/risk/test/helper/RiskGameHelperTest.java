/*
 * 
 */
package com.soen.risk.test.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * The Class RiskGameHelperTest is test class to check the functionalities of the helper class 
 * based on the army count of attacker and defender.
 * 
 * @author Chirag
 * @version 2.0
 */
public class RiskGameHelperTest {

	/** The risk main map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap = new LinkedHashMap<>();

	/** The risk player 1. */
	RiskPlayer riskPlayer1 = new RiskPlayer();

	/** The risk player 2. */
	RiskPlayer riskPlayer2 = new RiskPlayer();

	/** The attacker source territory. */
	RiskTerritory attackerSourceTerritory= new RiskTerritory();

	/** The attacker destination territory. */
	RiskTerritory attackerDestinationTerritory= new RiskTerritory();

	/** The attacker list. */
	ArrayList<RiskTerritory> attackerList = new ArrayList<>();

	/** The defender list. */
	ArrayList<RiskTerritory> defenderList = new ArrayList<>();

	/** The attack move army. */
	int attackMoveArmy;
	
	/**
	 * Sets the data for testing the functionalities of the methods.
	 */
	@Before
	public void setUp() {
		riskPlayer1.setPlayerName("Attacker1");
		riskPlayer2.setPlayerName("Defender1");
		riskPlayer1.setCurrentPlayerTurn(true);
		attackerSourceTerritory.setTerritoryName("Alaska");
		attackerSourceTerritory.setArmiesPresent(5);
		attackerDestinationTerritory.setTerritoryName("Peru");	
		attackerDestinationTerritory.setArmiesPresent(3);
		attackerList.add(attackerSourceTerritory);
		defenderList.add(attackerDestinationTerritory);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap.put(riskPlayer2, defenderList);
		attackMoveArmy=1;
	}

	/**
	 * Test update army after attack for attacker positive case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForAttackerPositiveCase() {
		attackerSourceTerritory.setArmiesPresent(7);
		attackerDestinationTerritory.setArmiesPresent(5);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertEquals(7,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for attacker negative case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForAttackerNegativeCase() {
		attackerSourceTerritory.setArmiesPresent(7);
		attackerDestinationTerritory.setArmiesPresent(3);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertNotEquals(8,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for defender positive case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForDefenderPositiveCase() {
		attackerSourceTerritory.setArmiesPresent(6);
		attackerDestinationTerritory.setArmiesPresent(4);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertEquals(4,riskMainMap.get(riskPlayer2).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for defender negative case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForDefenderNegativeCase() {
		attackerSourceTerritory.setArmiesPresent(7);
		attackerDestinationTerritory.setArmiesPresent(3);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertNotEquals(2,riskMainMap.get(riskPlayer2).get(0).getArmiesPresent());
	}
	
	/**
	 * Test move army after attack positive case.
	 */
	@Test
	public void testMoveArmyAfterAttackPositiveCase() {
		attackerSourceTerritory.setArmiesPresent(8);
		attackerDestinationTerritory.setArmiesPresent(0);
		attackerList.set(0,attackerSourceTerritory);
		attackerList.add(attackerDestinationTerritory);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap= RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertEquals(7,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
		assertEquals(1,riskMainMap.get(riskPlayer1).get(1).getArmiesPresent());
		
	}
	
	/**
	 * Test move army after attack negative case.
	 */
	@Test
	public void testMoveArmyAfterAttackNegativeCase() {
		attackerSourceTerritory.setArmiesPresent(6);
		attackerDestinationTerritory.setArmiesPresent(1);
		attackerList.set(0,attackerSourceTerritory);
		attackerList.add(attackerDestinationTerritory);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap= RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackerSourceTerritory, attackerDestinationTerritory, riskMainMap);
		assertNotEquals(1,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
		assertNotEquals(3,riskMainMap.get(riskPlayer1).get(1).getArmiesPresent());
		
	}
}
