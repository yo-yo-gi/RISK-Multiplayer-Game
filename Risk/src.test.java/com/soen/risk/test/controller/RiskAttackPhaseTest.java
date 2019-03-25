/*
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskAttackPhase;

/**
 * The Class RiskAttackPhaseTest is Junit class to test the dice roll functionalities of the attack phase.
 * 
 * @author Chirag
 * @version 2.0
 */
public class RiskAttackPhaseTest {
	
	/** The output. */
	Map<String,Object> output=new HashMap<String,Object>();
	
	/** The attack destination territory name. */
	String attackSourceTerritoryName, attackDestinationTerritoryName;
	
	/** The defending army count. */
	int attackingArmyCount, defendingArmyCount;
	
	/** The risk attack phase. */
	RiskAttackPhase riskAttackPhase = new RiskAttackPhase();

	/**
	 * Sets the data for the test methods.
	 */
	@Before
	public void setUp() {
		attackSourceTerritoryName="Alaska";
		attackDestinationTerritoryName="Peru"; 
		attackingArmyCount=7;
		defendingArmyCount=3;
	}

	/**
	 * Test roll dice for normal attack mode.
	 */
	@Test
	public void testRollDiceForNormalAttackMode() {
		output=riskAttackPhase.rollDiceForNormalAttackMode(attackSourceTerritoryName, attackingArmyCount, attackDestinationTerritoryName, defendingArmyCount);
		assertNotNull(output);
	}

	/**
	 * Test roll dice for all out attack mode.
	 */
	@Test
	public void testRollDiceForAllOutAttackMode() {
		output=RiskAttackPhase.rollDiceForAllOutAttackMode(attackSourceTerritoryName, attackingArmyCount, attackDestinationTerritoryName, defendingArmyCount);
		assertNotNull(output);
	}


}
