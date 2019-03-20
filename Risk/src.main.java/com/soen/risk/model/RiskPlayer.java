/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
import java.util.List;


/**
 * <h2>Player Model</h2>
 * This class is used to set and get the values of the parameters for players.
 * 
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */
public class RiskPlayer {

	/** The player id. */
	private String playerId;

	/** The player name. */
	private String playerName;

	/** The occupied territories. */
	private ArrayList<String> occupiedTerritories;

	/** The occupied continents. */
	private ArrayList<String> occupiedContinents=new ArrayList<String>();

	/** The armies owned. */
	private int armiesOwned;
	
	/** Current player turn*/
	private boolean currentPlayerTurn=false;
	
	/** Cards replaced with Armies Count */
	private int cardArmies;
	
	/** The card owned. */
	private ArrayList<RiskCard> cardOwned= new ArrayList<RiskCard>();
	
	/** Cards Return Logic*/
	 
	private int cardViewCount=1;

	RiskCard cardData;

	/**
	 * Instantiates a new risk player.
	 *
	 * @param name the name
	 */

	public RiskPlayer(String name) {
		setPlayerName(name);
	}

	/**
	 * Instantiates a new risk player.
	 */

	public RiskPlayer() {
	}

	/**
	 * Gets the player id.
	 *
	 * @return the playerId
	 */

	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Sets the player id.
	 *
	 * @param playerId the playerId to set
	 */

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * Gets the player name.
	 *
	 * @return the playerName
	 */

	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Sets the player name.
	 *
	 * @param playerName the playerName to set
	 */

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Gets the occupied territories.
	 *
	 * @return the occupiedTerritories
	 */

	public ArrayList<String> getOccupiedTerritories() {
		return occupiedTerritories;
	}

	/**
	 * Sets the occupied territories.
	 *
	 * @param occupiedTerritories the occupiedTerritories to set
	 */

	public void setOccupiedTerritories(ArrayList<String> occupiedTerritories) {
		this.occupiedTerritories = occupiedTerritories;
	}

	/**
	 * Gets the occupied continents.
	 *
	 * @return the occupiedContinents
	 */

	public ArrayList<String> getOccupiedContinents() {
		return occupiedContinents;
	}

	/**
	 * Sets the occupied continents.
	 *
	 * @param occupiedContinents the occupiedContinents to set
	 */

	public void setOccupiedContinents(ArrayList<String> occupiedContinents) {
		this.occupiedContinents = occupiedContinents;
	}

	/**
	 * Gets the armies owned.
	 *
	 * @return the armiesOwned
	 */

	public int getArmiesOwned() {
		return armiesOwned;
	}

	/**
	 * Sets the armies owned.
	 *
	 * @param armiesOwned the armiesOwned to set
	 */

	public void setCardArmyCount(int cardArmyCount)
	{
		this.cardArmies = cardArmyCount;
	}
	public void setArmiesOwned(int armiesOwned) {
		this.armiesOwned = armiesOwned;
	}

	/**
	 * @return the currentPlayerTurn
	 */
	public boolean isCurrentPlayerTurn() {
		return currentPlayerTurn;
	}

	/**
	 * @param currentPlayerTurn the currentPlayerTurn to set
	 */
	public void setCurrentPlayerTurn(boolean currentPlayerTurn) {
		this.currentPlayerTurn = currentPlayerTurn;
	}

	/**
	 * Gets the card owned.
	 *
	 * @return the cardOwned
	 */

	public ArrayList<RiskCard> getCardOwned() {
		return cardOwned;
	}

	/**
	 * Sets the card owned.
	 *
	 * @param cardOwned the cardOwned to set
	 */

	public void setCardOwned(RiskCard card) {
		this.cardOwned.add(card);
	}

	/**
	 * @return the cardViewCount
	 */
	public int getCardViewCount() {
		return cardViewCount;
	}

	/**
	 * @param cardViewCount the cardViewCount to set
	 */
	public void setCardViewCount(int cardViewCount) {
		this.cardViewCount = cardViewCount;
	}

	public int getCardsUsedCount() {
		return this.cardArmies;
	}
	/**
     * Removes either of three Infantry or Artillery or Cavalry cards
     *
     * @param type Sting type of card
     */
    public void removeSimilarThreeCards (RiskCard type) {
        this.cardOwned.remove(type);
        this.cardOwned.remove(type);
        this.cardOwned.remove(type);
    }

	public void removeDistinctCards() {
		 this.cardOwned.remove(RiskCard.INFANT);
	     this.cardOwned.remove(RiskCard.ARTILLERY);
	     this.cardOwned.remove(RiskCard.CAVALRY);
	}
}
