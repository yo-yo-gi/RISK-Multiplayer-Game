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

	/** The territory id. */
	protected int territoryId;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The territory name. */
	private String territoryName;

	/** The armies present. */
	private int armiesPresent;

	/** The continent. */
	private String continent;

	/** The continent id. */
	private int continentId;

	/** The territory owner. */
	private RiskPlayer territoryOwner;

	/** The adjacents. */
	private ArrayList<String> adjacents;

	/**
	 * Instantiates a new risk territory.
	 *
	 * @param parsedTerritory the parsed territory
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
	 * Gets the territory id.
	 *
	 * @return the territoryId
	 */

	public int getTerritoryId() {
		return territoryId;
	}

	/**
	 * Sets the territory id.
	 *
	 * @param territoryId the territoryId to set
	 */

	public void setTerritoryId(int territoryId) {
		this.territoryId = territoryId;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */

	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the x to set
	 */

	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */

	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the y to set
	 */

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the territory name.
	 *
	 * @return the territoryName
	 */

	public String getTerritoryName() {
		return territoryName;
	}

	/**
	 * Sets the territory name.
	 *
	 * @param territoryName the territoryName to set
	 */

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	/**
	 * Gets the armies present.
	 *
	 * @return the armiesPresent
	 */

	public int getArmiesPresent() {
		return armiesPresent;
	}

	/**
	 * Sets the armies present.
	 *
	 * @param armiesPresent the armiesPresent to set
	 */

	public void setArmiesPresent(int armiesPresent) {
		this.armiesPresent = armiesPresent;
	}

	/**
	 * Gets the continent.
	 *
	 * @return the continent
	 */

	public String getContinent() {
		return continent;
	}

	/**
	 * Sets the continent.
	 *
	 * @param continent the continent to set
	 */

	public void setContinent(String continent) {
		this.continent = continent;
	}

	/**
	 * Gets the continent id.
	 *
	 * @return the continentId
	 */

	public int getContinentId() {
		return continentId;
	}

	/**
	 * Sets the continent id.
	 *
	 * @param continentId the continentId to set
	 */

	public void setContinentId(int continentId) {
		this.continentId = continentId;
	}

	/**
	 * Gets the terrtory owner.
	 *
	 * @return the terrtoryOwner
	 */

	public RiskPlayer getTerrtoryOwner() {
		return territoryOwner;
	}

	/**
	 * Sets the terrtory owner.
	 *
	 * @param terrtoryOwner the terrtoryOwner to set
	 */

	public void setTerrtoryOwner(RiskPlayer terrtoryOwner) {
		this.territoryOwner = terrtoryOwner;
	}

	/**
	 * Gets the adjacents.
	 *
	 * @return the adjacents
	 */

	public ArrayList<String> getAdjacents() {
		return adjacents;
	}

	/**
	 * Sets the adjacents.
	 *
	 * @param adjacents the adjacents to set
	 */

	public void setAdjacents(ArrayList<String> adjacents) {
		this.adjacents = adjacents;
	}

}
