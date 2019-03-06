/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
/**
 * <h2>Continent Model</h2>
 * This class is used to set and get the values of the parameters for continents.
 * 
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */
public class RiskContinent {

	/** The continent id. */
	private String continentId;

	/** The continent name. */
	private String continentName;

	/** The controll value. */
	private int controllValue;

	/** The included territories. */
	private ArrayList<String> includedTerritories;

	/**
	 * Instantiates a new risk continent.
	 *
	 * @param continentName the continent name
	 * @param controllValue the controll value
	 */
	public RiskContinent(String continentName, int controllValue) {
		setContinentName(continentName);
		setControllValue(controllValue);
	}

	/**
	 * Gets the continent id.
	 *
	 * @return the continentId
	 */

	public String getContinentId() {
		return continentId;
	}

	/**
	 * Sets the continent id.
	 *
	 * @param continentId the continentId to set
	 */

	public void setContinentId(String continentId) {
		this.continentId = continentId;
	}

	/**
	 * Gets the continent name.
	 *
	 * @return the continentName
	 */

	public String getContinentName() {
		return continentName;
	}

	/**
	 * Sets the continent name.
	 *
	 * @param continentName the continentName to set
	 */

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * Gets the controll value.
	 *
	 * @return the controllValue
	 */

	public int getControllValue() {
		return controllValue;
	}

	/**
	 * Sets the controll value.
	 *
	 * @param controllValue the controllValue to set
	 */

	public void setControllValue(int controllValue) {
		this.controllValue = controllValue;
	}

	/**
	 * Gets the included territories.
	 *
	 * @return the includedTerritories
	 */

	public ArrayList<String> getIncludedTerritories() {
		return includedTerritories;
	}

	/**
	 * Sets the included territories.
	 *
	 * @param includedTerritories the includedTerritories to set
	 */

	public void setIncludedTerritories(ArrayList<String> includedTerritories) {
		this.includedTerritories = includedTerritories;
	}


}
