package com.soen.risk.test.controller;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskAttackPhase;

public class RiskAttackPhaseTest {
	Map<String,Object> output=new HashMap<String,Object>();
	String attackSourceTerritoryName, attackDestinationTerritoryName;
	int attackingArmyCount, defendingArmyCount;
	RiskAttackPhase riskAttackPhase = new RiskAttackPhase();

	@Before
	public void setUp() {
		attackSourceTerritoryName="Alaska";
		attackDestinationTerritoryName="Peru"; 
		attackingArmyCount=7;
		defendingArmyCount=3;
	}

	@Test
	public void testRollDiceForNormalAttackMode() {
		output=riskAttackPhase.rollDiceForNormalAttackMode(attackSourceTerritoryName, attackingArmyCount, attackDestinationTerritoryName, defendingArmyCount);
		assertNotNull(output);
	}

	@Test
	public void testRollDiceForAllOutAttackMode() {
		output=RiskAttackPhase.rollDiceForAllOutAttackMode(attackSourceTerritoryName, attackingArmyCount, attackDestinationTerritoryName, defendingArmyCount);
		assertNotNull(output);
	}


}
