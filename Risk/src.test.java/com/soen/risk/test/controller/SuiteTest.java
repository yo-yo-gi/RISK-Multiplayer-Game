package com.soen.risk.test.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.test.helper.RiskArmyAllocationToPlayersTest;
import com.soen.risk.test.helper.RiskMapEditorTest;
import com.soen.risk.test.helper.RiskTerritoryAssignmentToPlayerTest;

@RunWith(Suite.class)
@SuiteClasses({ RiskReinforcementPhaseTest.class,
				RiskFortificationPhaseTest.class,
				RiskMapEditorTest.class,
				RiskArmyAllocationToPlayersTest.class,
				RiskTerritoryAssignmentToPlayerTest.class,
				RiskMapValidatorTest.class
				})
public class SuiteTest {

}
