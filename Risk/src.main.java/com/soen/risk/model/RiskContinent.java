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

	private String continentId;
	private String continentName;
	private int controllValue;
	private ArrayList<String> includedTerritories;
	
	public RiskContinent(String continentName, int controllValue) {
		setContinentName(continentName);
		setControllValue(controllValue);
	}
	
	/**
	 * @return the continentId
	 */
	
	public String getContinentId() {
		return continentId;
	}
	
	/**
	 * @param continentId the continentId to set
	 */
	
	public void setContinentId(String continentId) {
		this.continentId = continentId;
	}
	
	/**
	 * @return the continentName
	 */
	
	public String getContinentName() {
		return continentName;
	}
	
	/**
	 * @param continentName the continentName to set
	 */
	
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	
	/**
	 * @return the controllValue
	 */
	
	public int getControllValue() {
		return controllValue;
	}
	
	/**
	 * @param controllValue the controllValue to set
	 */
	
	public void setControllValue(int controllValue) {
		this.controllValue = controllValue;
	}
	
	/**
	 * @return the includedTerritories
	 */
	
	public ArrayList<String> getIncludedTerritories() {
		return includedTerritories;
	}
	
	/**
	 * @param includedTerritories the includedTerritories to set
	 */
	
	public void setIncludedTerritories(ArrayList<String> includedTerritories) {
		this.includedTerritories = includedTerritories;
	}
	
	
}
