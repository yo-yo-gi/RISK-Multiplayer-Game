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

// TODO: Auto-generated Javadoc
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

	/** The attacker source terr. */
	RiskTerritory attackerSourceTerr= new RiskTerritory();

	/** The attacker destination terr. */
	RiskTerritory attackerDestinationTerr= new RiskTerritory();

	/** The attacker list. */
	ArrayList<RiskTerritory> attackerList = new ArrayList<>();

	/** The defender list. */
	ArrayList<RiskTerritory> defenderList = new ArrayList<>();

	/** The attack move army. */
	int attackMoveArmy;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		riskPlayer1.setPlayerName("Attacker1");
		riskPlayer2.setPlayerName("Defender1");
		riskPlayer1.setCurrentPlayerTurn(true);
		attackerSourceTerr.setTerritoryName("Alaska");
		attackerSourceTerr.setArmiesPresent(5);
		attackerDestinationTerr.setTerritoryName("Peru");	
		attackerDestinationTerr.setArmiesPresent(3);
		attackerList.add(attackerSourceTerr);
		defenderList.add(attackerDestinationTerr);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap.put(riskPlayer2, defenderList);
		attackMoveArmy=1;
	}

	/**
	 * Test update army after attack for attacker positive case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForAttackerPositiveCase() {
		attackerSourceTerr.setArmiesPresent(7);
		attackerDestinationTerr.setArmiesPresent(5);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertEquals(7,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for attacker negative case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForAttackerNegativeCase() {
		attackerSourceTerr.setArmiesPresent(7);
		attackerDestinationTerr.setArmiesPresent(3);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertNotEquals(8,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for defender positive case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForDefenderPositiveCase() {
		attackerSourceTerr.setArmiesPresent(6);
		attackerDestinationTerr.setArmiesPresent(4);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertEquals(4,riskMainMap.get(riskPlayer2).get(0).getArmiesPresent());
	}

	/**
	 * Test update army after attack for defender negative case.
	 */
	@Test
	public void testUpdateArmyAfterAttackForDefenderNegativeCase() {
		attackerSourceTerr.setArmiesPresent(7);
		attackerDestinationTerr.setArmiesPresent(3);
		riskMainMap= RiskGameHelper.updateArmyAfterAttack(attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertNotEquals(2,riskMainMap.get(riskPlayer2).get(0).getArmiesPresent());
	}
	
	/**
	 * Test move army after attack positive case.
	 */
	@Test
	public void testMoveArmyAfterAttackPositiveCase() {
		attackerSourceTerr.setArmiesPresent(8);
		attackerDestinationTerr.setArmiesPresent(0);
		attackerList.set(0,attackerSourceTerr);
		attackerList.add(attackerDestinationTerr);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap= RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertEquals(7,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
		assertEquals(1,riskMainMap.get(riskPlayer1).get(1).getArmiesPresent());
		
	}
	
	/**
	 * Test move army after attack negative case.
	 */
	@Test
	public void testMoveArmyAfterAttackNegativeCase() {
		attackerSourceTerr.setArmiesPresent(6);
		attackerDestinationTerr.setArmiesPresent(1);
		attackerList.set(0,attackerSourceTerr);
		attackerList.add(attackerDestinationTerr);
		riskMainMap.put(riskPlayer1, attackerList);
		riskMainMap= RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackerSourceTerr, attackerDestinationTerr, riskMainMap);
		assertNotEquals(1,riskMainMap.get(riskPlayer1).get(0).getArmiesPresent());
		assertNotEquals(3,riskMainMap.get(riskPlayer1).get(1).getArmiesPresent());
		
	}
}
