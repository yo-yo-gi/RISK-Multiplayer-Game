package com.soen.risk.helper;
import java.io.Serializable;
/**
 * <h2>Reinforcement Phase Controller Tournament Mode</h2>
 * The class defines number of the armies given to the player calculation. 
 * In the reinforcements phase, the player is given a number of armies that 
 * depends on the number of countries he owns (# of countries owned divided by 3, rounded down).
 * If the player owns all the countries of an entire continent else the player is given an amount
 * of armies corresponding to the continent's control value.
 * Card Exchange for Army
 * 
 * @author Neha Dighe
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import com.soen.risk.model.RiskCard;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

public class RiskReinforcementHelper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 992509886649834869L;
	transient Scanner scanner = new Scanner(System.in);


	/**
	 * Assigning the Armies to the Countries for the Player.
	 *
	 * @param riskMainMap the risk main map
	 * @param riskContinentList : The total continent list
	 * @param reinforcementTerritory 
	 * @return the reinforced map
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getReinforcedMap(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap,
			ArrayList<RiskContinent> riskContinentList, RiskTerritory reinforcementTerritory) {		
		/** Player with turn. */
		RiskPlayer currentPlayer = null;
		/** Output map with reinforcement data */
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(
				riskMainMap);
		/** Reinforcement phase controller */
		RiskReinforcementHelper riskReinforcementPhase=new RiskReinforcementHelper();
	
		/** Name of current player */
		String currentPlayerName = null;
		/** List of territories owned by current player. */
		ArrayList<RiskTerritory> currentPlayerTerritories = new ArrayList<RiskTerritory>();
		/** Armies remaining after each iteration of reinfocement. */
		int noOfRemainingArmies = 0;
		/** Army calculated for card exchange view. */
		int	cardExchangeViewArmy = 0;
		//		finding current player to use in this phase
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : reinforcedMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
				currentPlayerTerritories = entry.getValue();
			}
		}
		currentPlayerName = currentPlayer.getPlayerName();
		/** Territories owned by current player */
		int noOfCountriesOwned = currentPlayerTerritories.size();

		//		Triggering phase view observer		
	/*	riskPhase.setCurrentGamePhase(RiskPhaseType.REINFORCEMENT);
		riskPhase.setCurrentPlayerName(currentPlayerName);
		riskPhase.setCurrentAction("Starting Reinforcement");*/


		/**
		 * Condition to check the army calculated from the card exchange view or number
		 * of number of armies / control value calculation
		 */
		System.out.println("\nCalculation of armies for the  player: "+currentPlayer.getPlayerName());
		if (currentPlayer.getCardOwned().size() >= 3 && currentPlayer.getCardOwned().size() < 5) {
			System.out.println(
					"The number of cards the player has: " + currentPlayer.getCardOwned().size() + "\n" + "Names: "+currentPlayer.getCardOwned());
			cardExchangeViewArmy = riskReinforcementPhase.CardExchangeView(currentPlayer);
		}
		if (currentPlayer.getCardOwned().size() >= 5) {
			System.out.println("The players has 5 cards and must exchange cards with armies");
			cardExchangeViewArmy = riskReinforcementPhase.CardExchangeView(currentPlayer);
		}
		noOfRemainingArmies = riskReinforcementPhase.calculateArmy(currentPlayer, currentPlayerTerritories, riskContinentList);
		noOfRemainingArmies = noOfRemainingArmies + cardExchangeViewArmy;

		System.out.println("The number of armies calculated for reinforcement : " + noOfRemainingArmies);
		System.out.println("\nReinforcement started.....");
		System.out.println("Player name : " + currentPlayerName);
		System.out.println("Number of territories owned : " + noOfCountriesOwned);
		System.out.println("Territories of the player " + currentPlayerName + " are as following: ");

		int territoryCounter = 1;

		for (RiskTerritory riskTerritory : currentPlayerTerritories) {

			System.out.println(territoryCounter + ". " + riskTerritory.getTerritoryName() + " ("
					+ riskTerritory.getArmiesPresent() + ") ");
			territoryCounter++;
		}
		
		
		reinforcementTerritory.setArmiesPresent(reinforcementTerritory.getArmiesPresent()+noOfRemainingArmies);		
		currentPlayerTerritories.set(currentPlayerTerritories.indexOf(reinforcementTerritory), reinforcementTerritory);		
		reinforcedMap.put(currentPlayer, currentPlayerTerritories);
		System.out.println("Reinforcement Phase Completed for the Player " + currentPlayerName);
		for (RiskTerritory riskTerritory : currentPlayerTerritories) {
			System.out.println(riskTerritory.getTerritoryName()+"("+riskTerritory.getArmiesPresent()+")");
		}
		System.out.println("\n");
		return reinforcedMap;
	}

	/**
	 * Calculation of Armies for the Player.
	 *
	 * @param currentPlayerForCalArmy the current player
	 * @param currTerritoryList       the current territory list
	 * @param riskContinentList       the risk continent list
	 * @return the integer
	 */
	public int calculateArmy(RiskPlayer currentPlayerForCalArmy, ArrayList<RiskTerritory> currTerritoryList,
			ArrayList<RiskContinent> riskContinentList) {

		List<String> ownedContinents = new ArrayList<>(currentPlayerForCalArmy.getOccupiedContinents());
		int controlVal = Constants.ZERO, noOfArmiesForPlayer = Constants.ZERO;

		if (ownedContinents.size() != 0) {
			for (int i = 0; i < riskContinentList.size(); i++) {
				for (int j = 0; j < ownedContinents.size(); j++) {
					if (riskContinentList.get(i).getContinentName().equalsIgnoreCase(ownedContinents.get(j))) {
						controlVal = controlVal + riskContinentList.get(i).getControllValue();
					}
				}
			}
		}

		if (currTerritoryList.size() < 10 && controlVal < 3) {
			noOfArmiesForPlayer = 3;
		} else {
			noOfArmiesForPlayer = controlVal + currTerritoryList.size() / 3;
		}

		return noOfArmiesForPlayer;
	}

	/**
	 * Iteration 2 Implementation of a card exchange view using the Observer
	 * pattern.
	 *
	 * @param player Player information
	 * @return the int
	 */

	public int CardExchangeView(RiskPlayer player) {

		int exchangeArmies = Constants.ZERO;
		boolean exchangeArmy = false;
		if (player.getCardOwned().size() != 3) {
			System.out.println("Cards cannot be greater");
		}
		ArrayList<RiskCard> selectedCards = new ArrayList<RiskCard>();
		selectedCards.add(RiskCard.INFANT);
		selectedCards.add(RiskCard.ARTILLERY);
		selectedCards.add(RiskCard.CAVALRY);

		System.out.println("The Cards owned by the Player: " + player.getCardOwned());

		int infantCounter=Constants.ZERO;
		int cavCounter=Constants.ZERO;
		int artilleryCounter=Constants.ZERO;

		ArrayList<RiskCard> distinctCards = new ArrayList<RiskCard>();
		distinctCards.add(RiskCard.INFANT);
		distinctCards.add(RiskCard.ARTILLERY);
		distinctCards.add(RiskCard.CAVALRY);

		for(RiskCard currCard:player.getCardOwned()) {
			if (currCard.equals(RiskCard.INFANT)) {
				infantCounter = infantCounter + 1;
			}
			if(currCard.equals(RiskCard.ARTILLERY))
			{
				artilleryCounter = artilleryCounter + 1;
			}
			if(currCard.equals(RiskCard.CAVALRY))
			{
				cavCounter = cavCounter + 1;
			}
		}
		if(infantCounter>=3 || artilleryCounter >= 3 || cavCounter >= 3)
		{
			if (infantCounter == 3) {
				System.out.println("Infantry Cards removed");
				player.removeSimilarThreeCards(RiskCard.INFANT);
				exchangeArmy = true;
			} else if (artilleryCounter == 3) {
				System.out.println("Artillery Cards remvoed");
				player.removeSimilarThreeCards(RiskCard.ARTILLERY);
				exchangeArmy = true;
			} else if (cavCounter==3) {
				System.out.println("Cavalry Cards removed");
				player.removeSimilarThreeCards(RiskCard.CAVALRY);
				exchangeArmy = true;
			}
		} 
		else
		{
			if (player.getCardOwned().containsAll(distinctCards))
			{
				System.out.println("Player doesn't have similar cards. Exchaging distinct cards.");
				player.removeDistinctCards();
				exchangeArmy = true;
			}
		}
		if(exchangeArmy == true)
		{
			exchangeArmies = player.getCardViewCount() * 5;
			player.setCardViewCount(player.getCardViewCount() + 1);
		}
		else
		{
			System.out.println("Cards cannot be replaced with armies");
		}
		return exchangeArmies;
	}

}
