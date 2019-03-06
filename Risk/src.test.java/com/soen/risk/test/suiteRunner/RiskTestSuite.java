/*
 * 
 */
package com.soen.risk.test.suiteRunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.soen.risk.test.controller.RiskFortificationPhaseTest;
import com.soen.risk.test.controller.RiskReinforcementPhaseTest;
import com.soen.risk.test.helper.RiskArmyAllocationToPlayersTest;
import com.soen.risk.test.helper.RiskMapEditorTest;
//import com.soen.risk.test.helper.RiskMapUserCreatorTest;
import com.soen.risk.test.helper.RiskTerritoryAssignmentToPlayerTest;
import com.soen.risk.test.validator.RiskMapValidatorTest;

/**
 * <h2> Test Suite</h2>
 * The Class RiskTestSuite is a test case runner used to automatically run 
 * all the test cases.Test suite includes all the test cases in
 * package src.test.java
 * 
 * @author Chirag Vora
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	RiskMapValidatorTest.class,
	RiskMapEditorTest.class,
	RiskTerritoryAssignmentToPlayerTest.class,
	RiskArmyAllocationToPlayersTest.class,
	RiskReinforcementPhaseTest.class,
	RiskFortificationPhaseTest.class,
})
public class RiskTestSuite {
}
