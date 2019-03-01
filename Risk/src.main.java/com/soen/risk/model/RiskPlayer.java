/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;

/**
 * <h2>Player Model</h2>
 * <ul>
 * <li>Model for game player.
 * <li>Setters and getters to set and get the values of the parameters.
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-01-28
 */
public class RiskPlayer {

	private String playerId;
	private String playerName;
	private ArrayList<String> occupiedTerritories;
	private ArrayList<String> occupiedContinents=new ArrayList<String>();
	private int armiesOwned;
	private ArrayList<String> cardOwned;
	/**
	 * @param string
	 */
	public RiskPlayer(String name) {
		setPlayerName(name);
	}

	/**
	 * 
	 */
	public RiskPlayer() {
	}

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the occupiedTerritories
	 */
	public ArrayList<String> getOccupiedTerritories() {
		return occupiedTerritories;
	}
	/**
	 * @param occupiedTerritories the occupiedTerritories to set
	 */
	public void setOccupiedTerritories(ArrayList<String> occupiedTerritories) {
		this.occupiedTerritories = occupiedTerritories;
	}
	/**
	 * @return the occupiedContinents
	 */
	public ArrayList<String> getOccupiedContinents() {
		return occupiedContinents;
	}
	/**
	 * @param occupiedContinents the occupiedContinents to set
	 */
	public void setOccupiedContinents(ArrayList<String> occupiedContinents) {
		this.occupiedContinents = occupiedContinents;
	}
	/**
	 * @return the armiesOwned
	 */
	public int getArmiesOwned() {
		return armiesOwned;
	}
	/**
	 * @param armiesOwned the armiesOwned to set
	 */
	public void setArmiesOwned(int armiesOwned) {
		this.armiesOwned = armiesOwned;
	}
	/**
	 * @return the cardOwned
	 */
	public ArrayList<String> getCardOwned() {
		return cardOwned;
	}
	/**
	 * @param cardOwned the cardOwned to set
	 */
	public void setCardOwned(ArrayList<String> cardOwned) {
		this.cardOwned = cardOwned;
	}


}
