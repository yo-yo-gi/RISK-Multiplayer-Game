/**
 * 
 */
package com.soen.risk.test.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.soen.risk.tournamentControl.Aggressive;
import com.soen.risk.tournamentControl.Benevolent;
import com.soen.risk.tournamentControl.Cheater;
import com.soen.risk.tournamentControl.Common;
import com.soen.risk.tournamentControl.Country;
import com.soen.risk.tournamentControl.Game;
import com.soen.risk.tournamentControl.Human;
import com.soen.risk.tournamentControl.Map;
import com.soen.risk.tournamentControl.PhaseEnum;
import com.soen.risk.tournamentControl.Player;
import com.soen.risk.tournamentControl.PlayerStrategy;
import com.soen.risk.tournamentControl.Random;

/**
 * The Class RiskStartegyTest to test the strategies of Risk Game.
 *
 * @author Chirag
 * @version 3.0
 */
public class RiskStartegyTest {	

	/** The map. */
	Map riskMap;

	/** The gameInit. */
	Game gameInit;

	/** The riskMap to test. */
	String mapToTest = "Africa.map";

	/** The player count. */
	Integer noOfPlayers = 5;

	/**
	 * Test Method for assign countries to player after reading the riskMap.
	 */
	@Before
	public void readMapAndAssignCountries() {
		riskMap = new Map(mapToTest);
		riskMap.readMap();

		gameInit = new Game(riskMap);
		for (int i = 0; i < noOfPlayers; i++) {
			String playerName = "player " + i;
			Player player = new Player(i, playerName);
			gameInit.addPlayer(player);
		}
		gameInit.startUpPhase();

		while (gameInit.getGamePhase() == PhaseEnum.Startup) {
			ArrayList<Country> playerCountries = gameInit.getCurrentPlayer().getAssignedCountryList();

			int id = Common.getRandomNumberInRange(0, playerCountries.size() - 1);

			gameInit.addArmyToCountry(playerCountries.get(id).getCountryName());
		}
	}

	/**
	 * Test Method for Reinforcement for Benevolent Player.
	 */
	@Test
	public void testReinforceBenevolent() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Benevolent();
		currentPlayer.setPlayerStrategy(playerStrategy);

		int minArmies = Integer.MAX_VALUE;
		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		for (Country c : assignedCountryList) {
			if (c.getnoOfArmies() < minArmies)
				minArmies = c.getnoOfArmies();
		}

		HashMap<Country, Integer> CountryArmyMap = new HashMap<Country, Integer>();
		ArrayList<Country> weakestCountries = new ArrayList<Country>();
		for (Country country : currentPlayer.getAssignedCountryList()) {
			if (country.getnoOfArmies() == minArmies) {
				weakestCountries.add(country);
				CountryArmyMap.put(country, country.getnoOfArmies());
			}
		}

		currentPlayer.setNoOfReinforcedArmies(weakestCountries.size());

		currentPlayer.reinforce();

		for (Country country : weakestCountries) {
			assertEquals(country.getnoOfArmies(), CountryArmyMap.get(country) + 1);
		}

		assertEquals(currentPlayer.getNoOfReinforcedArmies(), 0);

	}

	/**
	 * Test Method for Reinforcement for Cheater Player.
	 */
	@Test
	public void testReinforcePhaseCheater() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Cheater();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		HashMap<Country, Integer> CountryArmyMap = new HashMap<Country, Integer>();

		for (Country country : assignedCountryList) {
			CountryArmyMap.put(country, country.getnoOfArmies());
		}

		currentPlayer.reinforce();

		for (Country country : assignedCountryList) {
			assertEquals(country.getnoOfArmies(), CountryArmyMap.get(country) * 2);
		}
	}

	/**
	 * Test Method for Reinforcement for Random Player.
	 */
	@Test
	public void testReinforcePhaseRandom() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Random();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		int totalOldArmies = 0;
		for (Country country : assignedCountryList) {
			totalOldArmies = totalOldArmies + country.getnoOfArmies();
		}

		int reinforcedArmies = 5;

		currentPlayer.setNoOfReinforcedArmies(reinforcedArmies);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), reinforcedArmies);
		currentPlayer.reinforce();
		int totalNewArmies = 0;
		for (Country country : assignedCountryList) {
			totalNewArmies = totalNewArmies + country.getnoOfArmies();
		}

		assertEquals(totalOldArmies + reinforcedArmies, totalNewArmies);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), 0);

	}

	/**
	 * Test Method for Reinforcement for Aggressive Player.
	 */
	@Test
	public void testReinforcePhaseAggressive() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Aggressive();
		currentPlayer.setPlayerStrategy(playerStrategy);

		Country strongestCountry = null;
		int armiesCount = 0;

		for (Country c : currentPlayer.getAssignedCountryList()) {
			if (c.getnoOfArmies() > armiesCount) {
				armiesCount = c.getnoOfArmies();
				strongestCountry = c;
			}
		}

		int oldArmiesCount = strongestCountry.getnoOfArmies();
		int reinforcedArmies = 5;
		currentPlayer.setNoOfReinforcedArmies(reinforcedArmies);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), reinforcedArmies);

		currentPlayer.reinforce();

		assertEquals(strongestCountry.getnoOfArmies(), oldArmiesCount + reinforcedArmies);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), 0);

	}

	/**
	 * Test Method for Reinforcement for Human Player.
	 */
	@Test
	public void testReinforcePhaseHuman() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Human();
		currentPlayer.setPlayerStrategy(playerStrategy);

		Country country = currentPlayer.getAssignedCountryList().get(0);
		int oldArmiesCount = country.getnoOfArmies();
		currentPlayer.setToCountry(country);
		int reinforcedArmies = 5;
		currentPlayer.setNoOfReinforcedArmies(reinforcedArmies);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), reinforcedArmies);

		currentPlayer.reinforce();
		assertEquals(country.getnoOfArmies(), oldArmiesCount + 1);
		assertEquals(currentPlayer.getNoOfReinforcedArmies(), reinforcedArmies - 1);

	}

	/**
	 * Test Method for Attack for Benevolent Player.
	 */
	@Test
	public void testAttackPhaseBenevolent() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Benevolent();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		int totalOldArmies = 0;
		for (Country country : assignedCountryList) {
			totalOldArmies = totalOldArmies + country.getnoOfArmies();
		}

		currentPlayer.attackPhase();

		int totalNewArmies = 0;
		for (Country country : assignedCountryList) {
			totalNewArmies = totalNewArmies + country.getnoOfArmies();
		}

		assertEquals(totalOldArmies, totalNewArmies);

	}

	/**
	 * Test Method for Attack for Cheater Player.
	 */
	@Test
	public void testAttackPhaseCheater() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Cheater();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountries = currentPlayer.getAssignedCountryList();
		HashMap<Country, Integer> neighbourCountries = new HashMap<Country, Integer>();

		for (Country country : assignedCountries) {
			if (!neighbourCountries.containsKey(country)
					&& country.getPlayer().getPlayerId() != currentPlayer.getPlayerId())
				neighbourCountries.put(country, country.getnoOfArmies());
		}

		currentPlayer.attackPhase();

		for (Country country : neighbourCountries.keySet()) {
			assertEquals(currentPlayer.getPlayerId(), country.getPlayer().getPlayerId());
		}
	}

	/**
	 * Test Method for Attack for Random Player.
	 */
	@Test
	public void testAttackPhaseRandom() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Random();
		currentPlayer.setPlayerStrategy(playerStrategy);

		currentPlayer.setIsConquered(false);
		currentPlayer.attackPhase();

		if (currentPlayer.isConquered()) {
			Country toCountry = currentPlayer.getToCountry();

			assertEquals(currentPlayer.getPlayerId(), toCountry.getPlayer().getPlayerId());
		}

	}

	/**
	 * Test Method for Attack for Aggressive Player.
	 */
	@Test
	public void testAttackPhaseAggressive() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Aggressive();
		currentPlayer.setPlayerStrategy(playerStrategy);

		Country strongestCountry = null;
		int armiesCount = 0;
		for (Country c : currentPlayer.getAssignedCountryList()) {
			if (c.getnoOfArmies() > armiesCount) {
				armiesCount = c.getnoOfArmies();
				strongestCountry = c;
			}
		}

		ArrayList<Country> CountriesToAttack = currentPlayer
				.getUnAssignedNeighbouringCountriesObject(strongestCountry.getCountryName());

		currentPlayer.setIsConquered(false);
		currentPlayer.attackPhase();

		if (currentPlayer.isConquered()) {
			Country toCountry = currentPlayer.getToCountry();
			assertEquals(currentPlayer.getPlayerId(), toCountry.getPlayer().getPlayerId());
		} else {
			for (Country country : CountriesToAttack) {
				assertNotEquals(currentPlayer.getPlayerId(), country.getPlayer().getPlayerId());
			}
		}

	}

	/**
	 * Test Method for Attack for Human Player.
	 */
	@Test
	public void testAttackPhaseHuman() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Human();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<String> attackingCountryList = currentPlayer.getCountriesWithArmiesGreaterThanOne();
		ArrayList<String> attackedCountryList;
		Country attackingCountry, defendingCountry;
		int attackingDiceCount, defendingDiceCount, attackingCountryArmyCount, defendingCountryArmyCount;
		Player defenderPlayer;

		for (String attackingCountryName : attackingCountryList) {
			attackedCountryList = currentPlayer.getUnAssignedNeighbouringCountries(attackingCountryName);
			attackingCountry = gameInit.getCountryFromName(attackingCountryName);
			attackingCountryArmyCount = attackingCountry.getnoOfArmies();
			for (String attackedCountryName : attackedCountryList) {
				defendingCountry = gameInit.getCountryFromName(attackedCountryName);
				defenderPlayer = defendingCountry.getPlayer();

				defendingCountryArmyCount = defendingCountry.getnoOfArmies();

				attackingDiceCount = 1;
				defendingDiceCount = 1;

				currentPlayer.setFromCountry(attackingCountry);
				currentPlayer.setToCountry(defendingCountry);
				currentPlayer.setAttackedPlayer(defenderPlayer);
				currentPlayer.rollDice(attackingDiceCount);
				defenderPlayer.rollDice(defendingDiceCount);

				currentPlayer.attackPhase();

				if (defendingCountryArmyCount > defendingCountry.getnoOfArmies()) {
					assertEquals(defendingCountryArmyCount, defendingCountry.getnoOfArmies() + 1);
					assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies());
				} else if (attackingCountryArmyCount > attackingCountry.getnoOfArmies()) {
					if (currentPlayer.isConquered()) {
						assertEquals(defendingCountry.getnoOfArmies(), 1);
						assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies() + 1);
						assertEquals(defendingCountry.getPlayer().getPlayerId(), currentPlayer.getPlayerId());
					} else {
						assertEquals(defendingCountryArmyCount, defendingCountry.getnoOfArmies());
						assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies() + 1);
					}

				}
				break;
			}
			break;
		}
	}

	/**
	 * Test Method for Fortification for Benevolent Player.
	 */
	@Test
	public void testFortificationPhaseBenevolent() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Benevolent();
		currentPlayer.setPlayerStrategy(playerStrategy);

	}

	/**
	 * Test Method for Fortification for Random Player.
	 */
	@Test
	public void testFortificationPhaseRandom() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Random();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> sourceCountryList = currentPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		HashMap<Country, Integer> neighbourCountryArmyMap = new HashMap<Country, Integer>();
		ArrayList<Country> neigbouringCountries = null;
		int oldArmiesCount = 0;

		for (Country country : sourceCountryList) {
			neigbouringCountries = currentPlayer.getConnectedCountriesRecursively(country,
					(ArrayList<Country>) currentPlayer.getAssignedCountryList().clone(), new ArrayList<Country>());
			if (!neighbourCountryArmyMap.containsKey(country)) {
				neighbourCountryArmyMap.put(country, country.getnoOfArmies());
				oldArmiesCount = oldArmiesCount + country.getnoOfArmies();
			}
			for (Country neighbourCountry : neigbouringCountries) {
				if (!neighbourCountryArmyMap.containsKey(neighbourCountry)) {
					neighbourCountryArmyMap.put(neighbourCountry, neighbourCountry.getnoOfArmies());
					oldArmiesCount = oldArmiesCount + neighbourCountry.getnoOfArmies();
				}
			}
		}

		currentPlayer.fortificationPhase();

		int newArmyCount = 0;
		for (Country country : neighbourCountryArmyMap.keySet()) {
			newArmyCount = newArmyCount + country.getnoOfArmies();
		}

		assertEquals(oldArmiesCount, newArmyCount);

	}

	/**
	 * Test Method for Fortification for Cheater Player.
	 */
	@Test
	public void testFortificationPhaseCheater() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Cheater();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		HashMap<Country, Integer> neighbourCountryArmyMap = new HashMap<Country, Integer>();

		for (Country country : assignedCountryList) {
			ArrayList<Country> assignedCountryListTemp = currentPlayer
					.getUnAssignedNeighbouringCountriesObject(country.getCountryName());
			for (Country neighbourCountry : assignedCountryListTemp) {
				if (neighbourCountry.getPlayer().getPlayerId() == currentPlayer.getPlayerId()
						&& !neighbourCountryArmyMap.containsKey(neighbourCountry)) {
					neighbourCountryArmyMap.put(neighbourCountry, neighbourCountry.getnoOfArmies());
				}
			}
		}

		currentPlayer.fortificationPhase();

		Iterator it = neighbourCountryArmyMap.entrySet().iterator();

		while (it.hasNext()) {
			HashMap.Entry<Country, Integer> pair = (HashMap.Entry<Country, Integer>) it.next();
			assertEquals((Integer) pair.getValue() * 2, pair.getKey().getnoOfArmies());

		}
	}

	/**
	 * Test Method for Fortification for Aggressive Player.
	 */
	@Test
	public void testFortificationPhaseAggressive() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Aggressive();
		currentPlayer.setPlayerStrategy(playerStrategy);

		ArrayList<Country> assignedCountryList = currentPlayer.getAssignedCountryList();
		HashMap<Country, Integer> neighbourCountryArmyMap = new HashMap<Country, Integer>();
		ArrayList<Country> neighborCountries = null;

		int oldArmiesCount = 0;
		for (Country c : assignedCountryList) {
			if (!neighbourCountryArmyMap.containsKey(c)) {

				neighbourCountryArmyMap.put(c, c.getnoOfArmies());
				oldArmiesCount = oldArmiesCount + c.getnoOfArmies();
			}
			neighborCountries = currentPlayer.getConnectedCountriesRecursively(c,
					(ArrayList<Country>) currentPlayer.getAssignedCountryList().clone(), new ArrayList<Country>());

			for (Country c2 : neighborCountries) {
				if (!neighbourCountryArmyMap.containsKey(c2)) {
					neighbourCountryArmyMap.put(c2, c2.getnoOfArmies());
					oldArmiesCount = oldArmiesCount + c2.getnoOfArmies();
				}
			}

		}

		currentPlayer.fortificationPhase();

		int newArmiesCount = 0;
		Iterator it = neighbourCountryArmyMap.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry<Country, Integer> pair = (HashMap.Entry<Country, Integer>) it.next();
			newArmiesCount = newArmiesCount + pair.getKey().getnoOfArmies();
		}

		assertEquals(oldArmiesCount, newArmiesCount);

	}

	/**
	 * Test Method for Fortification for Human Player.
	 */
	@Test
	public void testFortificationPhaseHuman() {
		Player currentPlayer = gameInit.getCurrentPlayer();
		PlayerStrategy playerStrategy = new Human();
		currentPlayer.setPlayerStrategy(playerStrategy);
		ArrayList<Country> fromCountryList = currentPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		Country fromCountry = fromCountryList.get(1);

		for (int i = 0; i < fromCountryList.size(); i++) {
			fromCountry = fromCountryList.get(i);
			if (currentPlayer.getAssignedNeighbouringCountries(fromCountry.getCountryName()).size() > 1) {
				break;
			}
		}

		ArrayList<Country> toCountryList = fromCountry.getNeighbourCountries();

		Country toCountry = null;
		for (int i = 0; i < toCountryList.size(); i++) {
			if (currentPlayer.getPlayerId() == toCountryList.get(i).getPlayer().getPlayerId()) {
				toCountry = toCountryList.get(i);
				break;
			}
		}
		if (fromCountry != null && toCountry != null) {
			int fromCountryArmyCount = fromCountry.getnoOfArmies();
			int noOfArmiesToMove = fromCountryArmyCount - 1;
			int toCountryArmyCount = toCountry.getnoOfArmies();
			currentPlayer.setFromCountry(fromCountry);
			currentPlayer.setToCountry(toCountry);
			currentPlayer.setNoOfArmiesToMove(noOfArmiesToMove);
			currentPlayer.fortificationPhase();

			assertEquals(fromCountry.getnoOfArmies(), 1);
			assertEquals(toCountryArmyCount + noOfArmiesToMove, toCountry.getnoOfArmies());
		}
	}

	/**
	 * This method will tear down variables.
	 */
	@After
	public void tearDown() {
		riskMap = null;
		gameInit = null;
		mapToTest = null;
		noOfPlayers = null;
	}
}
