/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.soen.risk.helper.RiskLogger;
import com.soen.risk.startegies.RiskPlayerStrategy;
import com.soen.risk.view.RiskCardviewObserver;
import com.soen.risk.view.RiskPhaseView;

/**
 * <h2>Player Model</h2>
 * This class is used to set and get the values of the parameters for players.
 * 
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskPlayer implements RiskCardviewObservable {

	/** The logger. */
	RiskLogger logger= new RiskLogger();
	/** The scanner. */
	Scanner scanner=new Scanner(System.in);	
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

	/**  Current player turn. */
	private boolean currentPlayerTurn=false;	

	/**  Cards replaced with Armies Count. */
	private int cardArmies;	
	/** The list of card owned. */
	private ArrayList<RiskCard> cardOwned= new ArrayList<RiskCard>();

	/**  Card exchange count for  player. */
	private int cardViewCount=1;
	
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;

	/**  Risk card view obsever interface object. */
	private List<RiskCardviewObserver> cardviewObsevers;
	/** Risk Phase view as Obsevable. */
	RiskPhase riskPhase=new RiskPhase();

	boolean cardEarnFlag=false,attackCounter=false;

	/**  Risk Phase view as Obsever. */
	RiskPhaseView riskPhaseView=new RiskPhaseView(riskPhase);
	
	RiskPlayerStrategy playerStrategy;

	/**
	 * @return the playerStrategy
	 */
	public RiskPlayerStrategy getPlayerStrategy() {
		return playerStrategy;
	}

	/**
	 * @param playerStrategy the playerStrategy to set
	 */
	public void setPlayerStrategy(RiskPlayerStrategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	/**
	 * Instantiates a new risk player.
	 *
	 * @param name the name
	 */

	public RiskPlayer(String name) {
		setPlayerName(name);
		cardviewObsevers=new ArrayList<RiskCardviewObserver>();
	}

	/**
	 * Instantiates a new risk player.
	 */

	public RiskPlayer() {
		cardviewObsevers=new ArrayList<RiskCardviewObserver>();
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
	 * @param cardArmyCount the new card army count
	 */

	public void setCardArmyCount(int cardArmyCount)
	{
		this.cardArmies = cardArmyCount;
	}

	/**
	 * Sets the armies owned.
	 *
	 * @param armiesOwned the new armies owned
	 */
	public void setArmiesOwned(int armiesOwned) {
		this.armiesOwned = armiesOwned;
	}

	/**
	 * Checks if is current player turn.
	 *
	 * @return the currentPlayerTurn
	 */
	public boolean isCurrentPlayerTurn() {
		return currentPlayerTurn;
	}

	/**
	 * Sets the current player turn.
	 *
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
	 * @param card the new card owned
	 */

	public void setCardOwned(RiskCard card) {
		this.cardOwned.add(card);
		notifyAllObservers();
	}

	/**
	 * Gets the card view count.
	 *
	 * @return the cardViewCount
	 */
	public int getCardViewCount() {
		return cardViewCount;
	}

	/**
	 * Sets the card view count.
	 *
	 * @param cardViewCount the cardViewCount to set
	 */
	public void setCardViewCount(int cardViewCount) {
		this.cardViewCount = cardViewCount;
	}

	/**
	 * Gets the cards used count.
	 *
	 * @return the cards used count
	 */
	public int getCardsUsedCount() {
		return this.cardArmies;
	}

	/**
	 * Removes either of three Infantry or Artillery or Cavalry cards.
	 *
	 * @param type Sting type of card
	 */
	public void removeSimilarThreeCards (RiskCard type) {
		this.cardOwned.remove(type);
		this.cardOwned.remove(type);
		this.cardOwned.remove(type);
	}

	/**
	 * Removes the distinct cards.
	 */
	public void removeDistinctCards() {
		this.cardOwned.remove(RiskCard.INFANT);
		this.cardOwned.remove(RiskCard.ARTILLERY);
		this.cardOwned.remove(RiskCard.CAVALRY);
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskCardviewObservable#addObserver(com.soen.risk.view.RiskCardviewObserver)
	 */
	@Override
	public void addObserver(RiskCardviewObserver observer) {
		cardviewObsevers.add(observer);

	}

	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskCardviewObservable#removeObserver(com.soen.risk.view.RiskCardviewObserver)
	 */
	@Override
	public void removeObserver(RiskCardviewObserver observer) {
		cardviewObsevers.remove(observer);

	}

	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskCardviewObservable#notifyAllObservers()
	 */
	@Override
	public void notifyAllObservers() {
		for (RiskCardviewObserver currObserver : cardviewObsevers) {
			currObserver.update(this.cardOwned);
		}
	}
}
