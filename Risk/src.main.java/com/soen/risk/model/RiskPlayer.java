/**
 * 
 */
package com.soen.risk.model;

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
import com.soen.risk.helper.RiskLogger;
import com.soen.risk.view.RiskCardviewObserver;


/**
 * <h2>Player Model</h2>
 * This class is used to set and get the values of the parameters for players.
 * 
 * @author Yogesh Nimbhorkar
 * @version 1.0
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
	
	/** Current player turn*/
	private boolean currentPlayerTurn=false;
	
	/** Cards replaced with Armies Count */
	private int cardArmies;
	
	/** The card owned. */
	private ArrayList<RiskCard> cardOwned= new ArrayList<RiskCard>();
 
	private int cardViewCount=1;
	private List<RiskCardviewObserver> cardviewObsevers;
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
		notifyAllObservers();
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
	


	//==================================================================================
//	Reinforcement method
//	============================================================================
	
	
	
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
		/** The selected terr index. */
		int selectedTerrIndex = 0;
		RiskPlayer currentPlayer = null;
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(
				riskMainMap);
		RiskReinforcementPhase riskReinforcementPhase=new RiskReinforcementPhase();
		Scanner scanner = new Scanner(System.in);
		String currentPlayerName = null;
		char decision ;
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
	
	
	
//==================================================================================
//	Attack method
//	============================================================================
	
	
	
	
	/**
	 * Gets the attackphase map.
	 *
	 * @param riskMainMap the risk main map
	 * @return the attackphase map
	 */
	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getAttackphaseMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> riskMainMap) {
		RiskAttackPhase riskAttackPhase=new RiskAttackPhase();
		char selectedchoice;
		int sourceTCoutner=1,destinationTCoutner=1, attackMoveArmy=0;
		int attackSourceTerritoryIndex=0,attackSourceArmy=0,attackDestinationTerritoryIndex=0,attackDestinationArmy=0,attackerTerritoryArmy=0,defenderTerritoryArmy=0;
		String attackSourceTerritoryName=null,attackDestinationTerritoryName=null,attackerTerritory=null,defenderTerritory=null;
		RiskTerritory attackDestinationTerritory, attackSourceTerritory;
		String defendingTerritory=null;
		RiskPlayer currentPlayer = null;
		List<String> adjTerritoryList;
		List<String> AdjAttackList;
		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		boolean cardEarnFlag=false;
		Map<String, Object> attackOutputMap = new HashMap<>();

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : riskMainMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}



		do {


			//		System.out.println("Current Player:"+currentPlayer.getPlayerName());
			System.out.println("Select the territory you want to attack from:");
			for (RiskTerritory currTerritory : playerTerritories) {
				System.out.println(sourceTCoutner+"." + currTerritory.getTerritoryName()+" ("+currTerritory.getArmiesPresent()+") ");
				sourceTCoutner++;	

			}
			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				attackSourceTerritoryIndex=scanner.nextInt();
				if(attackSourceTerritoryIndex>=sourceTCoutner || attackSourceTerritoryIndex<0) {
					System.out.println("Try Again!!");
				}
			}while(attackSourceTerritoryIndex>=sourceTCoutner || attackSourceTerritoryIndex<0);

			attackSourceTerritory=playerTerritories.get(attackSourceTerritoryIndex-1);
			attackSourceTerritoryName=attackSourceTerritory.getTerritoryName();
			attackSourceArmy=attackSourceTerritory.getArmiesPresent()-1;

			if (attackSourceArmy==1) {
				System.out.println("Player needs at least 1 army to attack...\n Please select another territory");
			}

		}while(attackSourceArmy==1);



		adjTerritoryList=new ArrayList<String>(attackSourceTerritory.getAdjacents());

		AdjAttackList=new ArrayList<String>(adjTerritoryList);
		for (String currAdj : adjTerritoryList) {
			for (RiskTerritory currTerritory : playerTerritories) {
				if ((currAdj.equalsIgnoreCase(currTerritory.getTerritoryName()))) {
					AdjAttackList.remove(currAdj);
				}
			}
		}


		System.out.println("Enter the territory you want to attack:");
		for (String territory : AdjAttackList) {
			System.out.println(destinationTCoutner+"." + RiskGameHelper.getRiskTerritoryByName(riskMainMap, territory).getTerritoryName()
					+" ("+RiskGameHelper.getRiskTerritoryByName(riskMainMap, territory).getArmiesPresent()+") ");
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
		attackDestinationTerritory = RiskGameHelper.getRiskTerritoryByName(riskMainMap, defendingTerritory);
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
				attackerTerritoryArmy=(int) entry.getValue();
			}
			else if(entry.getKey()==attackDestinationTerritoryName) {
				defenderTerritory=attackDestinationTerritoryName;
				defenderTerritoryArmy=(int) entry.getValue();
			}
		}
		attackSourceTerritory.setArmiesPresent(attackerTerritoryArmy+1);
		attackDestinationTerritory.setArmiesPresent(defenderTerritoryArmy);

		//		updating the main map for the army after every attack and deleting the territory if army is zero
		riskMainMap=RiskGameHelper.updateArmyAfterAttack(attackSourceTerritory,attackDestinationTerritory, riskMainMap);

		if (riskMainMap.size()==1) {
			System.out.println("*****************************************");
			System.out.println(currentPlayer.getPlayerName() + "wins the match and conquered the world...\n Game Ends");
			System.out.println("*****************************************");
			System.exit(0);
		}


		if ((boolean) (attackOutputMap.get("did_attacker_win"))) {
			cardEarnFlag=true;
			System.out.println("Attacker wins...");
		}else {
			System.out.println("Defender wins...");
		}

		System.out.println("Attacker:"+attackerTerritory+" has "+attackerTerritoryArmy+" left");
		System.out.println("Defender:"+defenderTerritory+" has "+defenderTerritoryArmy+" left");

		if (defenderTerritoryArmy==0) {
			System.out.println("How many armies you want to move to the new conquered territory?");
			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				attackMoveArmy = scanner.nextInt();
				if(attackMoveArmy>=attackerTerritoryArmy || attackMoveArmy<=0) {
					System.out.println("Try Again!!");
				}
			}while(attackMoveArmy>=attackerTerritoryArmy || attackMoveArmy<=0);

			//			Moving armies to new territory
			riskMainMap=RiskGameHelper.moveArmyAfterAttack(attackMoveArmy,attackSourceTerritory,attackDestinationTerritory, riskMainMap);
		}


		System.out.println("Do you want to attack again?(Y/N)");
		do {
			selectedchoice=scanner.next().charAt(0);
			if(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N')) {
				System.out.println("Try Again!!");
			}
		}while(!(selectedchoice=='Y' || selectedchoice=='y' || selectedchoice=='n' || selectedchoice=='N'));
		if(selectedchoice=='Y'||selectedchoice=='y') {
			getAttackphaseMap(riskMainMap);
		}else {
			if(cardEarnFlag) {
				riskMainMap=RiskGameHelper.assignRandomCard(riskMainMap);		
			}
			System.out.println("attack completed...");
		}


		return riskMainMap;
	}
	
	
	
	//==================================================================================
//	Fortification method
//	============================================================================
	
	/**
	 * Gets the fortified map.
	 * The Class RiskFortificationPhase to move as many armies 
	 * as you would like from one (and only one) of your territories 
	 * into one (and only one) of your adjacent territories.
	 *
	 * @param currentPlayer the current player
	 * @param playerTerritories the player territories
	 * @return the fortified map
	 */

	public LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> getFortifiedMap(LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> attackMap)
	{
		RiskPlayer currentPlayer = null;
		/** The source territory. */
		int sourceTerritory;
		/** The adjacent territory list. */
		ArrayList<String> adjTerritoryList;
		/** The owned territory list. */
		ArrayList<String> OwnedAdjList;

		/**  The number of armies to be moved. */
		int finalMoveOfArmies;
		/** The destination territory object. */
		RiskTerritory sourceTerritoryObject = null,destinationTerritoryObject = null;

		ArrayList<RiskTerritory> playerTerritories = new ArrayList<RiskTerritory>();
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> entry : attackMap.entrySet()) {
			if (entry.getKey().isCurrentPlayerTurn()) {
				currentPlayer=entry.getKey();
				playerTerritories=entry.getValue();
			}
		}
		logger.doLogging("Inside the fortification phase----------");
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> fortifiedMap= new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>(attackMap);
		ArrayList<RiskTerritory> finalFortifyList=new ArrayList<RiskTerritory>(playerTerritories);
		System.out.println();
		System.out.println("Fortification started...");
		System.out.println();
		int destinationTerritory, sourceArmy, destinationArmy = Constants.ZERO;
		String sourceTerritoryName, destinationTName = null ;
		System.out.println("Player Name: "+currentPlayer.getPlayerName());

		logger.doLogging("currentPlayer name is: "+currentPlayer);

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

				do {
					while (!scanner.hasNextInt()) {
						System.out.println("Try Again!!");
						scanner.next();
					}
					sourceTerritory=scanner.nextInt();
					if(sourceTerritory>=sourceTCoutner || sourceTerritory<0) {
						System.out.println("Try Again!!");
					}
				}while(sourceTerritory>=sourceTCoutner || sourceTerritory<0);

				sourceTerritoryName=playerTerritories.get(sourceTerritory-1).getTerritoryName();
				sourceArmy=playerTerritories.get(sourceTerritory-1).getArmiesPresent();
				if ((playerTerritories.get(sourceTerritory-1).getArmiesPresent()==1)) {
					System.out.println("Source territory must have at least one army \n please select different source territory");
				}
			}while(!(playerTerritories.get(sourceTerritory-1).getArmiesPresent()>1));

			logger.doLogging("Selected source territory  "+sourceTerritoryName);
			adjTerritoryList=new ArrayList<String>();
			for (RiskTerritory currTerritory : playerTerritories) {			
				if (currTerritory.getTerritoryName().equalsIgnoreCase(sourceTerritoryName)) {
					adjTerritoryList=currTerritory.getAdjacents();
				}
			}

			OwnedAdjList=new ArrayList<String>();
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

			logger.doLogging("Selected destination territory-> "+sourceTerritoryName);

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
		System.out.println();
		System.out.println("Source Territory: "+sourceTerritoryName+" has armies: "+sourceArmy);
		System.out.println("Destination Territory: "+destinationTName+" has armies: "+destinationArmy);
		System.out.println();

		fortifiedMap.put(currentPlayer, finalFortifyList);
		logger.doLogging("Fortification successful and the foritified map is: "+fortifiedMap);
		return fortifiedMap;
	}
	
	
}
