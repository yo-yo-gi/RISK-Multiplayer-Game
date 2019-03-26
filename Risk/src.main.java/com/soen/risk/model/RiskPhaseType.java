/**
 * 
 */
package com.soen.risk.model;

/**
 * <h2> Risk Phase Type Class </h2>
 * This class is used to display the name of the game phase currently being played.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public enum RiskPhaseType {
	
	/** The startup. */
	STARTUP("Startup"),
	
	/** The reinforcement. */
	REINFORCEMENT("Renforcement"),
	
	/** The attack. */
	ATTACK("Attack"),
	
	/** The fortify. */
	FORTIFY("Fortify");
	
	/** The phase name. */
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
