/*
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.tournamentControl.TournamentModeController;

/**
 * <h2> RiskTournamentModeTest </h2>
 * The Class RiskTournamentModeTest.
 * 
 * @author Chirag Vora
 * @version 3.0
 */
public class RiskTournamentModeTest {
	
	/** The tournament mode controller. */
	TournamentModeController tournamentModeController;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		tournamentModeController =new TournamentModeController();
	}
	
	/**
	 * Test tournament mode.
	 */
	@Test
	public void testTournamentMode() {
		tournamentModeController.startTournamentMode();
		assertNotNull(tournamentModeController.toString());
	}
}
