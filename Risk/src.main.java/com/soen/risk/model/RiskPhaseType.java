/**
 * 
 */
package com.soen.risk.model;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public enum RiskPhaseType {
	
	STARTUP("Startup"),
	REINFORCEMENT("Renforcement"),
	ATTACK("Attack"),
	FORTIFY("Fortify");
	
	private final String phaseName;
	
	/**
	 * Instantiates a new Phase name.
	 *
	 * @param phaseName the name of phase
	 */
	private RiskPhaseType(String phaseName) {
		this.phaseName = phaseName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return this.phaseName;
	}

}
