/**
 * 
 */
package com.soen.risk.model;

/**
 * <h2>Card Enum Model</h2>
 * <ul>
 * <li>Model for Card.
 * <li>Setters and getters to set and get the values of the parameters.
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-01-28
 */
public enum RiskCard {

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

	public String toString() {
		return this.name;
	}

}
