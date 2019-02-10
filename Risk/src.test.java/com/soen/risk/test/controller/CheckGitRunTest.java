/**
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.soen.risk.controller.CheckGitRun;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class CheckGitRunTest {
	CheckGitRun checkGitRun=new CheckGitRun();
	
	/**
	 * Test method for {@link com.soen.risk.controller.CheckGitRun#CheckJunit(java.lang.String)}.
	 */
	@Test
	public void testCheckJunit() {
		assertEquals("testSuccess", checkGitRun.CheckJunit("testSuccess"));
	}

}
