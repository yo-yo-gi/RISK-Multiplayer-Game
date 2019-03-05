/**
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.soen.risk.controller.CheckGitRun;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckGitRunTest.
 *
 * @author Yogesh Nimbhorkar
 */
public class CheckGitRunTest {
	
	/** The check git run. */
	CheckGitRun checkGitRun=new CheckGitRun();
	
	/**
	 * Test method for {@link com.soen.risk.controller.CheckGitRun#CheckJunit(java.lang.String)}.
	 * Class to be removed later
	 */
	@Test
	public void testCheckJunit() {
		assertEquals("testSuccess", checkGitRun.CheckJunit("testSuccess"));
	}

}
