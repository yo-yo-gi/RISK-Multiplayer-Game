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
 * @version 2.0
 */
public class RiskReinforcementPhase {
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
	 * @param player	Player information
	 */

	public int CardExchangeView(RiskPlayer player) {

		int exchangeArmies = Constants.ZERO;
		Scanner scanner = new Scanner(System.in);
		int decision;
		int decForSameCards = Constants.ZERO;
		if (player.getCardOwned().size() != 3) {
			System.out.println("Cards cannot be greater");
		}
		ArrayList<RiskCard> selectedCards = new ArrayList<RiskCard>();
		selectedCards.add(RiskCard.INFANT);
		selectedCards.add(RiskCard.ARTILLERY);
		selectedCards.add(RiskCard.CAVALRY);

		System.out.println("The Cards owned by the Player: " + player.getCardOwned());

		System.out.println("Do you want to replace the armies for  1)Similar cards 2)Distinct Cards");
		do {
			decision=scanner.nextInt();
			if(!(decision==1 || decision==2)) {
				System.out.println("Try Again!!");
			}
		}while(!(decision==1 || decision==2));
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
		if (decision == 1) {

			if(infantCounter>=3 || artilleryCounter >= 3 || cavCounter >= 3)
			{
				if (infantCounter == 3) {
					System.out.println("Infantry Cards removed");
					player.removeSimilarThreeCards(RiskCard.INFANT);
				} else if (artilleryCounter == 3) {
					System.out.println("Artillery Cards remvoed");
					player.removeSimilarThreeCards(RiskCard.ARTILLERY);
				} else if (cavCounter==3) {
					System.out.println("Cavalry Cards removed");
					player.removeSimilarThreeCards(RiskCard.CAVALRY);
				}
			} 
			else
			{
				System.out.println("Player doesn't have similar cards. Exchaging distinct cards.");
				player.removeDistinctCards();
			}
		} else if (decision == 2) {

			if (player.getCardOwned().containsAll(distinctCards))
			{
				System.out.println("Exchanging distinct cards.");
				player.removeDistinctCards();
			}
			else
			{
				System.out.println("The player does not contain distinct cards,hence exchanging for similar cards");
				if (infantCounter == 3) {
					System.out.println("Infantry Cards removed");
					player.removeSimilarThreeCards(RiskCard.INFANT);
				} else if (artilleryCounter == 3) {
					System.out.println("Artillery Cards remvoed");
					player.removeSimilarThreeCards(RiskCard.ARTILLERY);
				} else if (cavCounter==3) {
					System.out.println("Cavalry Cards removed");
					player.removeSimilarThreeCards(RiskCard.CAVALRY);
				}
			}
		}
		exchangeArmies = player.getCardViewCount() * 5;
		player.setCardViewCount(player.getCardViewCount() + 1);
		return exchangeArmies;
	}

}
