/*
 * 
 */
package com.soen.risk.test.view;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.soen.risk.test.controller.RiskReinforcementPhaseTest;
import com.soen.risk.test.helper.RiskArmyAllocationToPlayersTest;
import com.soen.risk.test.helper.RiskMapEditorTest;
import com.soen.risk.test.helper.RiskMapUserCreatorTest;
import com.soen.risk.test.helper.RiskTerritoryAssignmentToPlayerTest;
import com.soen.risk.test.validator.RiskMapValidatorTest;

/**
 * The Class RiskTestSuite is a test case runner used to automatically run all the test cases.
 * Test suite includes all the test cases in package src.test.java
 * 
 * @author Chirag
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ RiskReinforcementPhaseTest.class,
	RiskArmyAllocationToPlayersTest.class,
	RiskTerritoryAssignmentToPlayerTest.class,
	RiskMapEditorTest.class,
	RiskMapUserCreatorTest.class,
	RiskMapValidatorTest.class	
	})
public class RiskTestSuite {

}
