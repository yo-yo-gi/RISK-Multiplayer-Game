/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;

/**
 * <h2>Territory Model</h2>
 * This class is used to set and get the values of the parameters for territories.
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */

public class RiskTerritory {
	protected int territoryId;
	private int x;
	private int y;
	private String territoryName;
	private int armiesPresent;
	private String continent;
	private int continentId;
	private RiskPlayer terrtoryOwner;
	private ArrayList<String> adjacents;

	/**
	 * @param parsedTerritory
	 */
	
	public RiskTerritory(String[] parsedTerritory) {
		ArrayList<String> adjTerritories=new ArrayList<String>();
		setTerritoryName(parsedTerritory[0]);
		setContinent(parsedTerritory[1]);
		for(int i=2;i<parsedTerritory.length;i++) {
			adjTerritories.add(parsedTerritory[i]);
		}
		setAdjacents(adjTerritories);
	}
	
	/**
	 * @return the territoryId
	 */
	
	public int getTerritoryId() {
		return territoryId;
	}
	
	/**
	 * @param territoryId the territoryId to set
	 */
	
	public void setTerritoryId(int territoryId) {
		this.territoryId = territoryId;
	}
	
	/**
	 * @return the x
	 */
	
	public int getX() {
		return x;
	}
	
	/**
	 * @param x the x to set
	 */
	
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	
	public int getY() {
		return y;
	}
	
	/**
	 * @param y the y to set
	 */
	
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return the territoryName
	 */
	
	public String getTerritoryName() {
		return territoryName;
	}
	
	/**
	 * @param territoryName the territoryName to set
	 */
	
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}
	
	/**
	 * @return the armiesPresent
	 */
	
	public int getArmiesPresent() {
		return armiesPresent;
	}
	
	/**
	 * @param armiesPresent the armiesPresent to set
	 */
	
	public void setArmiesPresent(int armiesPresent) {
		this.armiesPresent = armiesPresent;
	}
	
	/**
	 * @return the continent
	 */
	
	public String getContinent() {
		return continent;
	}
	
	/**
	 * @param continent the continent to set
	 */
	
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	/**
	 * @return the continentId
	 */
	
	public int getContinentId() {
		return continentId;
	}
	
	/**
	 * @param continentId the continentId to set
	 */
	
	public void setContinentId(int continentId) {
		this.continentId = continentId;
	}
	
	/**
	 * @return the terrtoryOwner
	 */
	
	public RiskPlayer getTerrtoryOwner() {
		return terrtoryOwner;
	}
	
	/**
	 * @param terrtoryOwner the terrtoryOwner to set
	 */
	
	public void setTerrtoryOwner(RiskPlayer terrtoryOwner) {
		this.terrtoryOwner = terrtoryOwner;
	}
	
	/**
	 * @return the adjacents
	 */
	
	public ArrayList<String> getAdjacents() {
		return adjacents;
	}
	
	/**
	 * @param adjacents the adjacents to set
	 */
	
	public void setAdjacents(ArrayList<String> adjacents) {
		this.adjacents = adjacents;
	}

}
