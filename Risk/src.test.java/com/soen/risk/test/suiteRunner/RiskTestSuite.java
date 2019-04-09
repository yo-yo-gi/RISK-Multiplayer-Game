/*
 * 
 */
package com.soen.risk.test.suiteRunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.soen.risk.test.controller.RiskAttackPhaseTest;
import com.soen.risk.test.controller.RiskFortificationPhaseTest;
import com.soen.risk.test.controller.RiskReinforcementPhaseTest;
import com.soen.risk.test.controller.RiskTournamentModeTest;
import com.soen.risk.test.controller.RiskSavedGameControllerTest;
import com.soen.risk.test.helper.RiskArmyAllocationToPlayersTest;
import com.soen.risk.test.helper.RiskGameHelperTest;
import com.soen.risk.test.helper.RiskMapEditorTest;
import com.soen.risk.test.helper.RiskTerritoryAssignmentToPlayerTest;
import com.soen.risk.test.strategies.RiskStartegyTest;
import com.soen.risk.test.validator.RiskMapValidatorTest;

/**
 * <h2> Test Suite</h2>
 * The Class RiskTestSuite is a test case runner used to automatically run 
 * all the test cases.Test suite includes all the test cases in
 * package src.test.java
 * 
 * @author Chirag Vora
 * @version 3.0
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	RiskMapValidatorTest.class,
	RiskMapEditorTest.class,
	RiskTerritoryAssignmentToPlayerTest.class,
	RiskArmyAllocationToPlayersTest.class,
	RiskReinforcementPhaseTest.class,
	RiskAttackPhaseTest.class,
	RiskGameHelperTest.class,
	RiskStartegyTest.class,
	RiskSavedGameControllerTest.class,
	RiskTournamentModeTest.class,
	RiskFortificationPhaseTest.class
})
public class RiskTestSuite {
}
