/**
 * 
 */
package com.soen.risk.startegies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.soen.risk.controller.RiskAttackPhase;
import com.soen.risk.controller.RiskReinforcementPhase;
import com.soen.risk.helper.Constants;
import com.soen.risk.helper.RiskGameHelper;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPhase;
import com.soen.risk.model.RiskPhaseType;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.view.RiskPhaseView;

/** 
 * <h2> Risk Human Strategy</h2>
 * The Class RiskHumanStrategy focuses on user interaction for all phases of the game.
 * User is given option to select the countries as per his choice and game proceeds
 * based on user given input.
 * 
 * @author SHASHANK RAO
 * @version 3.0
 */
public class RiskHumanStrategy implements RiskPlayerStrategy, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8182262865311172353L;


	/** The scanner. */
	transient Scanner scanner = new Scanner(System.in);

	/** The attacked map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackedMap;

	/** Risk Phase view as Observable. */
	RiskPhase riskPhase=new RiskPhase();

	/**  Risk Phase view as Observer. */
	RiskPhaseView riskPhaseView=new RiskPhaseView(riskPhase);

	/** The attack counter. */
	boolean cardEarnFlag=false,attackCounter=false;

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#reinforce(java.util.LinkedHashMap, java.util.ArrayList)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforce(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap, ArrayList<RiskContinent> riskContinentList) {


		/** The selected territory index. */
		int selectedTerrIndex = 0;
		/** Player with turn. */
		RiskPlayer currentPlayer = null;
		/** Output map with reinforcement data */
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(
				gameMap);
		/** Reinforcement phase controller */
		RiskReinforcementPhase riskReinforcementPhase=new RiskReinforcementPhase();
		/** Name of current player */
		String currentPlayerName = null;
		/** Decision if user want to exchange cards or not. */
		char decision ;
		/** List of territories owned by current player. */
		ArrayList<RiskTerritory> currentPlayerTerritories = new ArrayList<RiskTerritory>();
		/** Armies remaining after each iteration of reinforcement. */
		int noOfRemainingArmies = 0;
		/** Army calculated for card exchange view. */
		int	cardExchangeViewArmy = 0;
		scanner = new Scanner(System.in);

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
		riskPhase.setCurrentGamePhase(RiskPhaseType.REINFORCEMENT);
		riskPhase.setCurrentPlayerName(currentPlayerName);
		riskPhase.setCurrentAction("Starting Reinforcement");


		/**
		 * Condition to check the army calculated from the card exchange view or number
		 * of number of armies / control value calculation
		 */
		System.out.println("\nCalculation of armies for the  player: "+currentPlayer.getPlayerName());
		if (currentPlayer.getCardOwned().size() >= 3 && currentPlayer.getCardOwned().size() < 5) {
			System.out.println(
					"The number of cards the player has: " + currentPlayer.getCardOwned().size() + "\n" + "Names: "+currentPlayer.getCardOwned());

			System.out.println("Do you want exchange armies for the cards: Y/N");

			do {
				decision=scanner.next().charAt(0);
				if(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N')) {
					System.out.println("Try Again!!");
				}
			}while(!(decision=='Y' || decision=='y' || decision=='n' || decision=='N'));


			switch (decision) {
			case 'y':
			case 'Y':
				cardExchangeViewArmy = riskReinforcementPhase.CardExchangeView(currentPlayer);
				decision = 'N';
				break;
			case 'n':
			case 'N':
				break;
			default:
				System.out.println("please enter again ");
			}
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

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#attack(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attack(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {

		attackedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);
		RiskAttackPhase riskAttackPhase=new RiskAttackPhase();
		boolean currPlayerArmyCheck=true, exitFlag=false;
		char selectedchoice;
		long sourceTCoutner=1,destinationTCoutner=1, attackMoveArmy=0;
		int attackSourceTerritoryIndex=0,attackDestinationTerritoryIndex=0;
		long defenderTerritoryArmy=0;
		long attackerTerritoryArmy=0;
		long attackDestinationArmy=0;
		long attackSourceArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory = null;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;
		List<String> adjTerritoryList;
		List<String> AdjAttackList = null;
		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		Map<String, Object> attackOutputMap = new HashMap<>();
		scanner = new Scanner(System.in);
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackedMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.ATTACK);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Attack Phase");

		do {
			//		System.out.println("Current Player:"+currentPlayer.getPlayerName());
			System.out.println("Select the territory you want to attack from:");

			for (RiskTerritory currTerritory : playerTerritories) {
				System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") -->"+currTerritory.getAdjacents());
				sourceTCoutner++;
			}
			System.out.println(sourceTCoutner+".Exit");
			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				attackSourceTerritoryIndex=scanner.nextInt();
				if(attackSourceTerritoryIndex>sourceTCoutner || attackSourceTerritoryIndex<0) {
					System.out.println("Try Again!!");
				}
			}while(attackSourceTerritoryIndex>sourceTCoutner || attackSourceTerritoryIndex<0);
			if(attackSourceTerritoryIndex==sourceTCoutner) {
				System.out.println("Exit Selected");
				exitFlag=true;
				break;
			}

			attackSourceTerritory=playerTerritories.get(attackSourceTerritoryIndex-1);
			attackSourceTerritoryName=attackSourceTerritory.getTerritoryName();
			attackSourceArmy=attackSourceTerritory.getArmiesPresent()-1;

			if (attackSourceArmy<1) {
				System.out.println("Player needs at least 1 army to attack...\nPlease select another territory or select Exit");
				sourceTCoutner=1;
			}

			adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

			AdjAttackList=new ArrayList<String>(adjTerritoryList);
			for (String currAdj : adjTerritoryList) {
				for (RiskTerritory currTerritory : playerTerritories) {
					if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
						AdjAttackList.remove(currAdj);
					}
				}
			}
			if (AdjAttackList.size()==0) {
				System.out.println("Current territory don't have any adjucents to attack...\nPlease select another territory or select Exit");
				sourceTCoutner=1;
			}

		}while((attackSourceArmy<1) || AdjAttackList.size()==0);

		if(exitFlag==false) {
			System.out.println("Enter the territory you want to attack:");
			for (String territory : AdjAttackList) {
				System.out.println(destinationTCoutner+"." + RiskGameHelper.getRiskTerritoryByName(gameMap, territory).getTerritoryName()
						+" ("+RiskGameHelper.getRiskTerritoryByName(gameMap, territory).getArmiesPresent()+") ");
				destinationTCoutner++;
			}

			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				attackDestinationTerritoryIndex=scanner.nextInt();
				if(attackDestinationTerritoryIndex>=destinationTCoutner || attackDestinationTerritoryIndex<0) {
					System.out.println("Try Again!!");
				}
			}while(attackDestinationTerritoryIndex>=destinationTCoutner || attackDestinationTerritoryIndex<0);

			for(int i=0;i<AdjAttackList.size();i++) {
				if(i==attackDestinationTerritoryIndex-1) {
					defendingTerritory=AdjAttackList.get(i);
				}
			}
			attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(attackedMap, defendingTerritory);
			attackDestinationTerritoryName=attackDestinationTerritory.getTerritoryName();
			attackDestinationArmy=attackDestinationTerritory.getArmiesPresent();


			System.out.println("Attacker Territory Name:"+attackSourceTerritoryName);
			System.out.println("Attacker Army Count:"+attackSourceArmy);
			System.out.println("Defender Territory Name:"+attackDestinationTerritoryName);
			System.out.println("Defender Army count:"+attackDestinationArmy);

			/*Below code will call the function to give user option to attack in Normal or All-Out method*/		
			attackOutputMap=riskAttackPhase.attackPhaseInput(attackSourceTerritoryName,attackSourceArmy, attackDestinationTerritoryName, attackDestinationArmy);

			for (Entry<String, Object> entry : attackOutputMap.entrySet()) {
				if(entry.getKey()==attackSourceTerritoryName) {
					attackerTerritory=attackSourceTerritoryName;
					attackerTerritoryArmy=(long) entry.getValue();
					attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy+1);
				}
				else if(entry.getKey()==attackDestinationTerritoryName) {
					defenderTerritory=attackDestinationTerritoryName;
					defenderTerritoryArmy=(long) entry.getValue();
					attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);
				}
			}

			//		updating the main map for the army after every attack and deleting the territory if army is zero
			attackedMap=RiskGameHelper.updateArmyAfterAttack(attackSourceTerritory,attackDestinationTerritory, attackedMap);
			if (attackedMap.size()==1) {
				System.out.println("*****************************************");
				System.out.println(currentPlayer.getPlayerName() + "wins the match and conquered the world...\n Game Ends");
				System.out.println("*****************************************");
				System.exit(0);
			}

			System.out.println("Attacker:"+attackerTerritory+" has "+attackerTerritoryArmy+" left");
			System.out.println("Defender:"+defenderTerritory+" has "+defenderTerritoryArmy+" left");

			if (defenderTerritoryArmy==0) {
				cardEarnFlag=true;
				attackCounter=true;
				System.out.println("How many armies you want to move to the new conquered territory?");
				do {
					while (!scanner.hasNextInt()) {
						System.out.println("Try Again!!");
						scanner.next();
					}
					attackMoveArmy = scanner.nextInt();
					if(attackMoveArmy>attackerTerritoryArmy || attackMoveArmy<=0) {
						System.out.println("Try Again!!");
					}
				}while(attackMoveArmy>attackerTerritoryArmy || attackMoveArmy<=0);

				//			Moving armies to new territory
				attackedMap=RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackSourceTerritory,attackDestinationTerritory, attackedMap);
			}

			if (!exitFlag) {
				System.out.println("Do you want to attack again?(Y/N)");
				do {
					selectedchoice=scanner.next().charAt(0);
					if(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N')) {
						System.out.println("Try Again!!");
					}
				}while(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N'));
				if(selectedchoice=='Y'||selectedchoice=='y') {
					ArrayList<RiskTerritory> listArmyCheck=new ArrayList<RiskTerritory>();
					listArmyCheck=attackedMap.get(currentPlayer);
					for (RiskTerritory currPlayerTerritory : listArmyCheck) {
						if(currPlayerTerritory.getArmiesPresent()>1) {
							currPlayerArmyCheck=false;
							break;
						}
					}if(!currPlayerArmyCheck) {
						attackedMap=attack(attackedMap);
					}
					else {
						System.out.println("You currently have only 1 army in your territories...\nCannot attack further...Proceed to fortification phase");
					}
				}
				else {
					if(cardEarnFlag && attackCounter) {
						attackedMap=RiskGameHelper.assignRandomCard(attackedMap);	
						attackCounter=false;
					}
				}
			}
		}
		if(cardEarnFlag && attackCounter) {
			attackedMap=RiskGameHelper.assignRandomCard(attackedMap);	
			attackCounter=false;
		}
		System.out.println("Attack completed...");
		return attackedMap;

	}

	/* (non-Javadoc)
	 * @see com.soen.risk.startegies.RiskPlayerStrategy#fortify(java.util.LinkedHashMap)
	 */
	@Override
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortify(
			LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap) {

		boolean exitFlag=false;
		RiskPlayer currentPlayer = null;
		/** The source territory. */
		int sourceTerritory;
		/** The adjacent territory list. */
		ArrayList<String> adjTerritoryList;
		/** The owned territory list. */
		ArrayList<String> OwnedAdjList = new ArrayList<String>();
		scanner = new Scanner(System.in);
		/**  The number of armies to be moved. */
		int finalMoveOfArmies;
		/** The destination territory object. */
		RiskTerritory sourceTerritoryObject = null,destinationTerritoryObject = null;

		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : gameMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(gameMap);
		ArrayList<RiskTerritory> finalFortifyList=new ArrayList<RiskTerritory>(playerTerritories);
		System.out.println();
		System.out.println("Fortification started...");
		System.out.println();
		int destinationTerritory;
		long destinationArmy = Constants.ZERO;
		long sourceArmy = Constants.ZERO;
		String sourceTerritoryName = null, destinationTName = null ;
		System.out.println("Player Name: "+currentPlayer.getPlayerName());

		//		Triggering phase view observer		
		riskPhase.setCurrentGamePhase(RiskPhaseType.FORTIFY);
		riskPhase.setCurrentPlayerName(currentPlayer.getPlayerName());
		riskPhase.setCurrentAction("Starting Fortification Phase");

		do
		{
			do {

				System.out.println();
				System.out.println("Select the source territory to move army from: ");
				int sourceTCoutner=1;

				for (RiskTerritory currTerritory : playerTerritories) {
					System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") ");
					sourceTCoutner++;			
				}
				System.out.println(sourceTCoutner+".Exit");

				do {
					while (!scanner.hasNextInt()) {
						System.out.println("Try Again!!");
						scanner.next();
					}
					sourceTerritory=scanner.nextInt();
					if(sourceTerritory>sourceTCoutner || sourceTerritory<0) {
						System.out.println("Try Again!!");
					}
				}while(sourceTerritory>sourceTCoutner || sourceTerritory<0);

				if(sourceTerritory==sourceTCoutner) {
					System.out.println("Exit Selected");
					exitFlag=true;
					break;
				}
				sourceTerritoryName=playerTerritories.get(sourceTerritory-1).getTerritoryName();
				sourceArmy=playerTerritories.get(sourceTerritory-1).getArmiesPresent();
				if ((playerTerritories.get(sourceTerritory-1).getArmiesPresent()==1)) {
					System.out.println("Source territory must have at least one army \n please select different source territory");
				}
			}while(!(playerTerritories.get(sourceTerritory-1).getArmiesPresent()>1));
			if(exitFlag)
				break;

			adjTerritoryList=new ArrayList<String>();
			for (RiskTerritory currTerritory : playerTerritories) {			
				if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
					adjTerritoryList=currTerritory.getAdjacents();
				}
			}

			OwnedAdjList.clear();
			for (String currAdj : adjTerritoryList) {
				for (RiskTerritory currTerritory : playerTerritories) {
					if (currAdj.equalsIgnoreCase(currTerritory.getTerritoryName())) {
						OwnedAdjList.add(currAdj);
					}
				}
			}
			if(OwnedAdjList.isEmpty())
			{
				System.out.println("The Source Territory does not have adjacency, "
						+ "\nPlease select different source territory");
			}
		}while(OwnedAdjList.isEmpty());

		if (!OwnedAdjList.isEmpty()) {  
			System.out.println("Select the destination territory: ");
			int destinationTCoutner=1;
			for (String currAdj : OwnedAdjList) {
				System.out.println(destinationTCoutner+"." + currAdj);
				destinationTCoutner++;			
			}

			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				destinationTerritory=scanner.nextInt();
				if(destinationTerritory>=destinationTCoutner || destinationTerritory<0) {
					System.out.println("Try Again!!");
				}
			}while(destinationTerritory>=destinationTCoutner || destinationTerritory<0);

			destinationTName=OwnedAdjList.get(destinationTerritory-1);

			for (RiskTerritory currTerritory : playerTerritories) {
				if (currTerritory.getTerritoryName().equalsIgnoreCase(destinationTName)) {
					destinationTerritoryObject=currTerritory;
					destinationArmy=currTerritory.getArmiesPresent();
				}		
				if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
					sourceTerritoryObject=currTerritory;
				}
			}
			System.out.println("Number of armies are: "+sourceArmy+ " in the source territory: "+sourceTerritoryName);
			System.out.println("Number of armies are: "+destinationArmy+ " in the destination territory: "+destinationTName);
			System.out.println("Enter the number of armies to be moved to destination territory");

			do
			{
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				finalMoveOfArmies = scanner.nextInt();
				if(finalMoveOfArmies==sourceArmy)
				{
					System.out.println("All armies cannot be moved."
							+ "\nPlease enter number of armies less than total armies in the territory.");
				}
				if(finalMoveOfArmies<0)
				{
					System.out.println("invalid number of armies entered"
							+ "\nPlease enter valid armies");
				}
				if(finalMoveOfArmies>sourceArmy) {
					System.out.println("Number of armies cannot be greater than that available."
							+ "\nPlease enter valid armies");
				}
			}while(finalMoveOfArmies<0 || finalMoveOfArmies>=sourceArmy);

			if (true) {

				sourceArmy=sourceTerritoryObject.getArmiesPresent()-finalMoveOfArmies;
				destinationArmy=destinationTerritoryObject.getArmiesPresent()+finalMoveOfArmies;
				sourceTerritoryObject.setArmiesPresent(sourceArmy);
				destinationTerritoryObject.setArmiesPresent(destinationArmy);
				finalFortifyList.set(playerTerritories.indexOf(sourceTerritoryObject), sourceTerritoryObject);
				finalFortifyList.set(playerTerritories.indexOf(destinationTerritoryObject), destinationTerritoryObject);
			}			
		}

		System.out.println();
		System.out.println("Fortification completed for the Player "+currentPlayer.getPlayerName());
		if(!exitFlag) {
			System.out.println();
			System.out.println("Source Territory: "+sourceTerritoryName+" has armies: "+sourceArmy);
			System.out.println("Destination Territory: "+destinationTName+" has armies: "+destinationArmy);
			System.out.println();
		}

		fortifiedMap.put(currentPlayer, finalFortifyList);
		//		Triggering Phase View observer
		riskPhase.setCurrentAction("Fortification Phase Completed");
		return fortifiedMap;
	}
}
