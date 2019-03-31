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

	/** The control value. */
	private int controlValue;

	/** The included territories. */
	private ArrayList<String> includedTerritories;

	/**
	 * Instantiates a new risk continent.
	 *
	 * @param continentName the continent name
	 * @param controlValue the control value
	 */
	public RiskContinent(String continentName, int controlValue) {
		setContinentName(continentName);
		setControllValue(controlValue);
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
		return controlValue;
	}

	/**
	 * Sets the controll value.
	 *
	 * @param controllValue the controllValue to set
	 */

	public void setControllValue(int controllValue) {
		this.controlValue = controllValue;
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
