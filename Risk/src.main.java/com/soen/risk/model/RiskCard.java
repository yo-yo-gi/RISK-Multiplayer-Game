/**
 * 
 */
package com.soen.risk.model;

import java.io.Serializable;

/**
 * <h2>Card Enum Model</h2>
 * This class is used to set and get the values of the parameters for cards.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public enum RiskCard implements Serializable{

	/** The infant. */
	INFANT("Infant"),
	/** The cavalry. */
	CAVALRY("Cavalry"),
	/** The artillery. */
	ARTILLERY("Artillery");

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new card.
	 *
	 * @param name the name
	 */

	private RiskCard(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return this.name;
	}

}
