/*
 * 
 */
package com.soen.risk.controller;

import java.util.ArrayList;
import java.util.List;
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

		System.out.println("The Cards owned by the Player: " + player.getCardOwned());

		System.out.println("Do you want to replace the armies for  1)Similar cards \n2)Distinct Cards");
		do {
			decision=scanner.nextInt();
			if(!(decision==1 || decision==2)) {
				System.out.println("Try Again!!");
			}
		}while(!(decision==1 || decision==2));
	
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
							"From the below choices which cards to avail\n1)Infantry \n2)Artillery \n3)Cavalry");
					do {
						decForSameCards=scanner.nextInt();
						if(!(decForSameCards==1 || decForSameCards==2 || decForSameCards==3 )) {
							System.out.println("Try Again!!");
						}
					}while(!(decForSameCards==1 || decForSameCards==2));
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
					System.out.println("Player doesn't have similar cards.");
			} else if (decision == 2) {
				System.out.println("The player owns different cards.");
				player.removeDistinctCards();
			}
		exchangeArmies = player.getCardViewCount() * 5;
		player.setCardViewCount(player.getCardViewCount() + 1);
		return exchangeArmies;
	}

}
