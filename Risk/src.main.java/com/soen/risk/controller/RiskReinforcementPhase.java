/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.soen.risk.helper.Constants;
import com.soen.risk.model.RiskCard;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Reinforcement Phase Controller</h2> The class defines number of the
 * armies given to the player calculation. In the reinforcements phase, the
 * player is given a number of armies that depends on the number of countries he
 * owns (# of countries owned divided by 3, rounded down). If the player owns
 * all the countries of an entire continent else the player is given an amount
 * of armies corresponding to the continent's control value.
 * 
 * @author Neha Dighe
 * @version 1.0
 */
public class RiskReinforcementPhase {

	/** The selected terr index. */
	int selectedTerrIndex = 0;

	RiskPlayer currentPlayer = null;

	/**
	 * Assigning the Armies to the Countries for the Player.
	 *
	 * @param currentPlayer     : Current player is passed
	 * @param playerTerritories : The territories assigned to the player
	 * @param riskContinentList : The total continent list
	 * @return the reinforced map
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getReinforcedMap(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap,
			ArrayList<RiskContinent> riskContinentList) {
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(
				riskMainMap);
		Scanner scanner = new Scanner(System.in);
		String currentPlayerName = null;
		String decision = "";
		ArrayList<RiskTerritory> currentPlayerTerritories = new ArrayList<RiskTerritory>();
		int noOfRemainingArmies = 0, cardExchangeViewArmy = 0;
		ArrayList<RiskTerritory> playerTerritories = null;

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : reinforcedMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer = entry.getKey();
				playerTerritories = entry.getValue();
			}
		}

		currentPlayerTerritories = playerTerritories;
		currentPlayerName = currentPlayer.getPlayerName();
		int noOfCountriesOwned = currentPlayerTerritories.size();
		/**
		 * Condition to check the army calculated from the card exchange view or number
		 * of number of armies / control value calculation
		 */
	//	do {
			if (currentPlayer.getCardOwned().size() >= 3 && currentPlayer.getCardOwned().size() < 5) {
				System.out.println(
						"The players number of cards: " + currentPlayer.getCardOwned().size() + "\n" + "Names");

				System.out.println("Do you want exchange armies for the Cards Y/N");
				decision = scanner.nextLine();

				switch (decision) {
				case "Y":
					cardExchangeViewArmy = CardExchangeView(currentPlayer);
					decision = "N";
					break;
				case "N":
					break;
				default:
					System.out.println("please enter again ");
				}
			}
			if (currentPlayer.getCardOwned().size() >= 5) {
				System.out.println("The players has 5 cards and must exchange cards with armies");
				cardExchangeViewArmy = CardExchangeView(currentPlayer);
			}

			noOfRemainingArmies = calculateArmy(currentPlayer, currentPlayerTerritories, riskContinentList);
			noOfRemainingArmies = noOfRemainingArmies + cardExchangeViewArmy;

			
		//} while (currentPlayer.getCardOwned().size() >= 3 && decision.contentEquals("Y"));

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
		System.out.println("Select the territory for reinforcement: ");
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Try Again!!");
				scanner.next();
			}
			selectedTerrIndex = scanner.nextInt();
			if (selectedTerrIndex >= territoryCounter || selectedTerrIndex < 0) {
				System.out.println("Try Again!!");
			}
		} while (selectedTerrIndex >= territoryCounter || selectedTerrIndex < 0);

		do {
			if (noOfRemainingArmies != 0) {

				System.out.println(
						"Enter armies for " + currentPlayerTerritories.get((selectedTerrIndex - 1)).getTerritoryName());
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				int userEnteredArmy = scanner.nextInt();
				if (userEnteredArmy < 0) {
					do {
						System.out
								.println("Armies assigned cannot be less than zero, please add armies to the country : "
										+ currentPlayerTerritories.get((selectedTerrIndex - 1)).getTerritoryName());
						while (!scanner.hasNextInt()) {
							System.out.println("Try Again!!");
							scanner.next();
						}
						userEnteredArmy = scanner.nextInt();
					} while (userEnteredArmy < 0);
				}
				if (userEnteredArmy > noOfRemainingArmies) {
					do {
						System.out.println("Invalid Input");
						System.out.println("Enter the number of Armies to reinforce for Country  "
								+ currentPlayerTerritories.get((selectedTerrIndex - 1)).getTerritoryName());
						while (!scanner.hasNextInt()) {
							System.out.println("Try Again!!");
							scanner.next();
						}
						userEnteredArmy = scanner.nextInt();
					} while (userEnteredArmy > noOfRemainingArmies);

					noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy;
				} else {
					noOfRemainingArmies = noOfRemainingArmies - userEnteredArmy;
				}
				currentPlayerTerritories.get((selectedTerrIndex - 1)).setArmiesPresent(
						(currentPlayerTerritories.get((selectedTerrIndex - 1)).getArmiesPresent()) + (userEnteredArmy));
			} else {
				break;
			}
			if (noOfRemainingArmies > 0) {
				selectedTerrIndex = 0;
				System.out.println("Please select next territory");
				do {
					while (!scanner.hasNextInt()) {
						System.out.println("Try Again!!");
						scanner.next();
					}
					selectedTerrIndex = scanner.nextInt();
					if (selectedTerrIndex >= territoryCounter || selectedTerrIndex < 0) {
						System.out.println("Try Again!!");
					}
				} while (selectedTerrIndex >= territoryCounter || selectedTerrIndex < 0);
			}
		} while (noOfRemainingArmies > 0);

		reinforcedMap.put(currentPlayer, currentPlayerTerritories);
		System.out.println("Reinforcement Phase Completed for the Player " + currentPlayerName);
		System.out.println("\n");
		return reinforcedMap;
	}

	/**
	 * Cacluation of Armies for the Player.
	 *
	 * @param currentPlayerForCalArmy the current player
	 * @param currTerritoryList       the curr territory list
	 * @param riskContinentList       the risk continent list
	 * @return the int
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
	 * @param playerID                  the player ID
	 * @param noOfCountriesOwned        the number of countries assigned to the
	 *                                  Player
	 * @param noOfPlayers               the total number of players set for the game
	 * @param totalCountriesInContinent the total number of Countries in a continent
	 */

	public int CardExchangeView(RiskPlayer player) {

		int exchangeArmies = 0;
		Scanner scanner = new Scanner(System.in);
		int decision;
		int decForSameCards = 0;
		if (player.getCardOwned().size() != 3) {
			System.out.println("Cards cannot be greater");
		}
		ArrayList<RiskCard> selectedCards = new ArrayList<RiskCard>();
		selectedCards.add(RiskCard.INFANT);
		selectedCards.add(RiskCard.ARTILLERY);
		selectedCards.add(RiskCard.CAVALRY);

//        for (int i = 0; i < player.getCardOwned().size(); i++) {
//            selectedCards[i] = player.getCardOwned().get(i);
//        }
		// Map<String, Integer> cardType = new HashMap<>();
		System.out.println("The Card Owned by the Player" + player.getCardOwned());

		System.out.println("Do you want to replace the armies for  1) Similar cards \n2)Distinct Cards");
		decision = scanner.nextInt();

		if (player.getCardOwned().size() >= 3) // remove
		{
			if (decision == 1) {
				ArrayList<RiskCard> infantry = new ArrayList<RiskCard>();
				ArrayList<RiskCard> Artillery = new ArrayList<RiskCard>();
				ArrayList<RiskCard> Cavalry = new ArrayList<RiskCard>();
				for (int i = 0; i < 3; i++) {
					infantry.add(RiskCard.INFANT);
					Artillery.add(RiskCard.ARTILLERY);
					Cavalry.add(RiskCard.CAVALRY);
				}
				if (player.getCardOwned().containsAll(infantry) || player.getCardOwned().containsAll(Artillery)
						|| player.getCardOwned().containsAll(Cavalry)) {

					System.out.println(
							"From the below choices which cards to avail\n 1)Infantry \n 2)Artillery \n 3) Cavalry");
					decForSameCards = scanner.nextInt();
					if (decForSameCards == 1) {
						player.removeSimilarThreeCards(RiskCard.INFANT);
					} else if (decForSameCards == 2) {
						player.removeSimilarThreeCards(RiskCard.ARTILLERY);
					} else if (decForSameCards == 3) {
						player.removeSimilarThreeCards(RiskCard.CAVALRY);
					} else {
						System.out.println("Invalid input");
					}
				} else
					System.out.println("Player doesn't have similar cards...");
			} else if (decision == 2) {
				System.out.println("The player owns different cards");
				player.removeDistinctCards();
			}
		}
		exchangeArmies = player.getCardViewCount() * 5;
		player.setCardViewCount(player.getCardViewCount() + 1);
		currentPlayer = player;
//        System.out.println(player.getPlayerName() + ": Cards have been exchanged. " + (player.getArmiesOwned()*firstIterationCount*i) + " armies");

		return exchangeArmies;
	}

}
