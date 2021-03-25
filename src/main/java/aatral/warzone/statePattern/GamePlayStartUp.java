package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;



/**
 * <h1>GamePlayStartUp</h1> This abstract class implements the state pattern for Game Play StartUp phase
 *
 * @author Tejeswini
 * @version 1.0
 * @since 24-02-2021
 */
public class GamePlayStartUp extends GamePlay {

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public GamePlayStartUp(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	/**
	 * LoadMap method is used to Load the map and convert into continent,countries
	 * and borders
	 * 
	 * @param p_warZoneMap war zone map
	 * @return masterMap 
	 */
	@Override
	public Map<String, Continent> loadMap(String p_warZoneMap) {

		List<InputContinent> l_inputContinentList = new ContinentMapReader().readContinentFile(p_warZoneMap);
		List<InputCountry> l_inputCountryList = new CountryMapreader().readCountryMap(p_warZoneMap);
		List<InputBorders> l_inputBordersList = new CountryBorderReader().mapCountryBorderReader(p_warZoneMap);

		Map<String, Continent> masterMap = new HashMap<>();
		for (InputContinent l_continent : l_inputContinentList) {

			Set<Countries> continentOwnedCountries = new HashSet<>();
			for (InputCountry l_Country : l_inputCountryList) {
				if (l_continent.getContinentId().equals(l_Country.getContinentId())) {

					Set<String> l_countryOwnedBorders = new HashSet<>();
					for (InputBorders l_Borders : l_inputBordersList) {
						if (l_Borders.getCountryId().equals(l_Country.getCountryId())) {
							l_countryOwnedBorders.addAll(l_Borders.getAdjacentCountries());
						}

					}
					Countries addtToCountrySet = new Countries(l_Country, l_countryOwnedBorders);
					continentOwnedCountries.add(addtToCountrySet);
				}

			}
			Continent addToMaster = new Continent(l_continent, continentOwnedCountries);
			masterMap.put(l_continent.getContinentId(), addToMaster);
		}
		return masterMap;
	}

	
	public void addGamePlayer(String l_playerName, ArrayList<String> p_playerObListTempAdd,
			List<String> p_playerList) {

		if (p_playerObListTempAdd.contains(l_playerName) || p_playerList.contains(l_playerName)) {
			log.info("StartUp", gameEngine.l_gamePlayerObject.getPlayerName(), l_playerName, "Player already exist");
			System.out.println("Player name " + l_playerName + " already existing...try this alone again...");
		} else {
			p_playerObListTempAdd.add(l_playerName);
		}
	}
	
	public boolean removeGamePlayer(boolean p_flag, String p_playerName, List<String> p_playerObListTempRem,List<String> p_playerList) 
	{
		String l_removeName = "";
		if (!p_playerList.contains(p_playerName)) {
			l_removeName = p_playerName;
			p_flag = false;
		}
		if (!p_flag) {
			log.info("StartUp", gameEngine.l_gamePlayerObject.getPlayerName(), l_removeName, "Player name doesn't exist");
			System.out.println("Player names " + l_removeName.substring(1)+ " doesn't exist\nTry again with valid player names to remove");
		} else {
			p_playerObListTempRem.add(p_playerName);
		}
		return p_flag;
	}
	
	public HashMap<String, GamePlayer> assignCountries(List<String> p_playerList) {
		HashMap<String, GamePlayer> l_playerObjectList = new HashMap<>();
		int l_totalNoOfCountry = gameEngine.totalCountries();
		List<Countries> totalCountries = gameEngine.listOfCountries();
		boolean l_isCountryAssigned[] = new boolean[l_totalNoOfCountry];
		boolean l_flag = true;
		while (l_flag) {
			for (String str : p_playerList) {
				if (gameEngine.allCountriesAssigned(l_isCountryAssigned)) {
					l_flag = false;
					break;
				}
				int l_countryID = gameEngine.getCountryID(l_totalNoOfCountry, l_isCountryAssigned);
				if (!l_playerObjectList.containsKey(str)) {
					l_playerObjectList.put(str, new GamePlayer(str, new ArrayList<Countries>(), 0));
				}
				l_playerObjectList.get(str).getListOfCountries().add(totalCountries.get(l_countryID));
				l_isCountryAssigned[l_countryID] = true;
			}
		}
		return l_playerObjectList;
	}
	
	/**
	 * assignReinforcements method is used to assign the armies to the game player
	 * 
	 * @param p_armies integer of armies
	 */
	public void assignReinforcements(int p_armies) {
		gameEngine.l_gamePlayerObject.setReinforcementArmies(p_armies);
		log.info("StartUp", gameEngine.l_gamePlayerObject.getPlayerName(), p_armies+"", "Reinforcement Assigned");
		System.out.println("The player " + gameEngine.l_gamePlayerObject.getPlayerName() + " has been reinforced with " + p_armies+" armies");
	}

	@Override
	public void next() {
		gameEngine.setPhase(new GamePlayIssueOrder(gameEngine));
		System.out.println(gameEngine.getGamePhase());
	}

	@Override
	public void showMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveMap(String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMap(String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void issueOrders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeOrders() {
		// TODO Auto-generated method stub
		
	}

}
