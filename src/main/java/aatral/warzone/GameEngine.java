package aatral.warzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.beanio.annotation.Record;

import aatral.warzone.implementation.ComposeGraph;
import aatral.warzone.model.Borders;
import aatral.warzone.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@Record
/**
 * 
 * @author William Moses
 * @version 1.0
 * @since 24-02-2021
 */
public class GameEngine {
	private Scanner l_input = new Scanner(System.in);
	private HashMap<String, GamePlayer> l_playerObjectList = new HashMap<>();
	private HashMap<String, List<Country>> l_continentMap;
	private Map<String, List<Country>> l_borderMap;
	private HashMap<Integer, String> l_playerUsedContinent = new HashMap<>();
	private String l_mapName;

	public GameEngine(String fileName) {
		this.l_mapName = fileName;
		l_continentMap = new ComposeGraph().getContinentMap(fileName);
		l_borderMap = new ComposeGraph().getBorderMap(fileName);
		l_playerUsedContinent.put(0, "nothing");
	}

	/**
	 * assignReinforcements method is used to assign the armies to the game player
	 * 
	 * @param p_object object of gameplayer class
	 * @param p_armies integer of armies
	 */
	public void assignReinforcements(GamePlayer p_object, int p_armies) {
		p_object.setArmies(p_armies);
		System.out.println("The player " + p_object.getPlayerName() + " has been reinforced with " + p_armies+" armies");
	}

	/**
	 * issueOrders method is used to deploy the armies from player to the designated
	 * countries
	 * 
	 * @param p_object object of gameplayer class
	 */
	public void IssueOrders() { 
		boolean flag = true;
		do {
			GamePlayer l_gameplayerObj;
			flag = false;
			String l_issueCommand;
			String l_deployInput[];
			int l_armies = 0;
			for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
				l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
				if(l_gameplayerObj.getArmies()>0) {
					flag=true;  
					System.out.println("\nRemaining number of armies in hand for the player " + l_gameplayerObj.getPlayerName() + " is "
							+ l_gameplayerObj.getArmies() + "\n\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies");
					l_issueCommand = l_input.nextLine();
					l_deployInput = validateDeployInput(l_issueCommand);
					l_armies = calculateInputArmies(l_deployInput);
					if (validateInputArmies(l_armies, l_gameplayerObj.getArmies())) {
						l_armies = 0;
						String l_countryNorPresent = validateCountryInput(l_deployInput, l_gameplayerObj);
						if (validateCountryValue(l_countryNorPresent)) {
							for (int i = 0; i < l_deployInput.length; i++) {
								String l_countryID = l_deployInput[i].trim().split(" ")[0];
								String l_armyCount = l_deployInput[i].trim().split(" ")[1];
								int l_deployableArmies = Integer.parseInt(l_armyCount);
								List<Country> l_list = l_gameplayerObj.getListOfCountries();
								for (Country l_con : l_gameplayerObj.getListOfCountries()) {
									if (l_con.getCountryId().equalsIgnoreCase(l_countryID)) {
										l_list.get(l_list.indexOf(l_con)).setArmies(l_con.getArmies() + l_deployableArmies);
										l_gameplayerObj.setArmies(l_gameplayerObj.getArmies() - l_deployableArmies);
										l_gameplayerObj.setListOfCountries(l_list);
										break;
									}
								}
							}
						} else {
							System.out.println("The countries entered " + l_countryNorPresent.substring(1)
									+ " are not under player " + l_gameplayerObj.playerName);
						}
					} else {
						System.out.println("Can't deploy " + l_armies + " armies by the player - " + l_gameplayerObj.getPlayerName()
								+ " as he has only " + l_gameplayerObj.getArmies());
					}
				}
			}
		}while(flag);
		
	}

	/**
	 * NextOrder method is used to display list of orders will be executed
	 */
	public void NextOrder() {
		System.out.println("Next Order()");
	}

	/**
	 * executeOrders method is used to decide the country attack order
	 * 
	 * @param gameplayObject object of gameplayer class.
	 */
	public void executeOrders(GamePlayer gameplayObject) {
		NextOrder();
		System.out.println("Player " + gameplayObject.getPlayerName() + "'s Orders executed !!!");
	}

	/**
	 * startGame method is used to start the game with user's input choice
	 */
	public void startGame() {
		boolean l_flag = true, l_innerLoopflag = true;
		String l_readInput;
		do {
			GamePlayer l_gameplayerObj;
			for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
				l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
				System.out.println("\n\nAssinging reinforcement for the Player " + l_gameplayObject.getKey());
				assignReinforcements(l_gameplayerObj, 5);
				showMapPlayer(l_gameplayerObj);
			}
			IssueOrders();
			for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
				l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
				System.out.println("\n\nExecueting Orders for the player " + l_gameplayerObj.getPlayerName());
				executeOrders(l_gameplayerObj);
			}
			l_innerLoopflag = true;
			while (l_innerLoopflag) {
				System.out.println("Give any of the following command to proceed the gamePlay \n continue \n back");
				l_readInput = l_input.nextLine();
				switch (l_readInput) {
				case "continue":
					l_innerLoopflag = false;
					break;
				case "back":
					l_innerLoopflag = false;
					l_flag = false;
					break;
				default:
					System.out.println("Input is mismatching...Kindly Try again...");
					break;
				}
			}
		} while (l_flag);
	}

	/**
	 * gameUserMenu method allows the user to give input options and execute based
	 * on user input
	 */
	public void gameUserMenu() {
		boolean l_gamePlayFlag = true;
		while (l_gamePlayFlag) {
			System.out.println("\n\nGameplay Format : " + "\n showmap"
					+ "\n gameplayer -add playerName1,playerName2 -remove playerName" + "\n startgame" + "\n exitgame");
			String l_playOption = l_input.nextLine();
			switch (l_playOption.split(" ")[0]) {
			case "startgame":
				System.out.println("Game Play has started\n\n");
				startGame();
				break;
			case "gameplayer":
				boolean l_flag = true;
				String l_playerOption[] = l_playOption.substring(11).split("-");
				HashMap<String, GamePlayer> l_playerObListTempAdd = new HashMap<>();
				List<String> l_playerObListTempRem = new ArrayList<>();
				String l_playerNames[];
				
				for (String l_option : l_playerOption) {
					if (l_option.isEmpty())
						continue;
					switch (l_option.split(" ")[0]) {
					case "add":
						l_playerNames = l_option.substring(3).trim().split(",");
						for (String l_playerName : l_playerNames) {
							l_playerName = l_playerName.trim();
							if (l_playerName.isEmpty())
								continue;
							int l_continentID = getContinentID(l_playerUsedContinent);
							l_playerUsedContinent.put(l_continentID, l_playerName);
							String continentName = getContinentName(l_continentMap.keySet(), l_continentID + "");
							if(l_playerObListTempAdd.containsKey(l_playerName)) {
								  System.out.println("Player name "+l_playerName+" already existing...try this alone again...");
							}else {
								l_playerObListTempAdd.put(l_playerName, new GamePlayer(l_playerName,
										l_continentMap.get(l_continentID + "_" + continentName), 0));
							}
						}
						break;
					case "remove":
						l_playerNames = l_option.substring(6).split(",");
						String l_removeName = "";
						for (String l_playerName : l_playerNames) {
							l_playerName = l_playerName.trim();
							if (!l_playerObjectList.containsKey(l_playerName)) {
								l_removeName += ", " + l_playerName;
								l_flag = false;
							}
							if (!l_flag) {
								System.out.println("Player names " + l_removeName.substring(1)
										+ " doesn't exist/nTry again with valid player names to remove");
							} else {
								l_playerObListTempRem.add(l_playerName);
							}
						}
						break;
					default:
						System.out.println("Different input has been read...Try again");
						l_flag = false;
						break;
					}
				}
				while (l_flag) {
					System.out.println("Give assigncountries to assign it! or 'cancel' to ignore" + "\nFormat : "
							+ "\n assigncountries" + "\n cancel");
					l_playOption = l_input.nextLine();
					switch (l_playOption) {
					case "assigncountries":
						l_playerObjectList.putAll(l_playerObListTempAdd);
						for (String l_removeName : l_playerObListTempRem) {
							l_playerObjectList.remove(l_removeName);
							for (Map.Entry l_mapObject : l_playerUsedContinent.entrySet()) {
								if (l_mapObject.getValue().equals(l_removeName)) {
									l_playerUsedContinent.remove(l_mapObject.getKey());
									break;
								}
							}
						}
						l_flag = false;
						break;
					case "cancel":
						System.out.println("Player's modification are aborted!");
						l_flag = false;
						break;
					default:
						System.out.println("You have entered wrong input...Try again");
						break;
					}
				}
				break;
			case "showmap":
				showMap();
				break;
			case "exitgame":
				System.exit(0);
				break;
			default:
				System.out.println("Input format is not matching... Try again/nCheck again/n");
				break;
			}
		}
	}

	/**
	 * getContinentID method is used to get the value of continent id
	 * 
	 * @param p_playerUsedContinent player used continent hashmap.
	 * @return continent id.
	 */
	public int getContinentID(HashMap<Integer, String> p_playerUsedContinent) {
		int l_continentID = new Random().nextInt(l_continentMap.size());
		while (p_playerUsedContinent.containsKey(l_continentID)) {
			l_continentID = new Random().nextInt(l_continentMap.size());
		}
		return l_continentID;
	}

	/**
	 * getContinentName method is used to get the continent name using continent id
	 * 
	 * @param p_continentMapKeySet Set of continent map key.
	 * @param p_continentID Continent id.
	 * @return continent name.
	 */
	public String getContinentName(Set<String> p_continentMapKeySet, String p_continentID) {
		String l_continentName = "";
		for (String continentKey : p_continentMapKeySet) {
			if (continentKey.split("_")[0].equalsIgnoreCase(p_continentID)) {
				l_continentName = continentKey.substring(continentKey.indexOf("_") + 1);
				break;
			}
		}
		return l_continentName;
	}

	public String[] validateDeployInput(String p_issueCommand) {
		while (!p_issueCommand.split(" ")[0].equalsIgnoreCase("deploy")) {
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(6).split(",");
	}

	/**
	 * calculateInputArmies method is used to validate the deployed armies is less
	 * than or equal to deployed armies on country
	 * 
	 * @param p_deployInput Deploy input string.
	 * @return no of armies.
	 */
	public int calculateInputArmies(String[] p_deployInput) {
		int l_armies = 0;
		for (int i = 0; i < p_deployInput.length; i++) {
			l_armies += Integer.parseInt(p_deployInput[i].trim().split(" ")[1]);
		}
		return l_armies;
	}

	public boolean validateInputArmies(int p_inputArmies, int p_availableArmies) {
		return p_inputArmies <= p_availableArmies;
	}

	/**
	 * validateCountryInput method is used to check the country present in list or
	 * not using country id
	 * 
	 * @param p_deployInput Deploy input string.
	 * @param p_object object for gameplayer class.
	 * @return country not present. 
	 */
	public String validateCountryInput(String[] p_deployInput, GamePlayer p_object) {
		String l_countryNorPresent = "";
		for (int i = 0; i < p_deployInput.length; i++) {
			String l_countryID = p_deployInput[i].trim().split(" ")[0];
			boolean flag = true;
			for (Country con : p_object.getListOfCountries()) {
				if (con.getCountryId().equalsIgnoreCase(l_countryID)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				l_countryNorPresent += ", " + l_countryID;
			}
		}
		return l_countryNorPresent;
	}

	public boolean validateCountryValue(String p_countryNorPresent) {
		return p_countryNorPresent.equals("");
	}

	/**
	 * showMapPlayer method is used to show the map corresponds to game player name
	 * 
	 * @param p_gamePlayer object for gameplayer class.
	 */
	public void showMapPlayer(GamePlayer p_gamePlayer) {
		System.out.print("\nCountry ID\t\tCountry Name\t\t\t\tArmies\t\tOwner");
		for (Country l_co : p_gamePlayer.getListOfCountries()) {
			System.out.print("\n" + l_co.getCountryId() + "\t\t" + l_co.getCountryName() + "\t\t\t\t" + l_co.getArmies()
					+ "\t\t" + p_gamePlayer.getPlayerName() + "\n");
		}
	}

	/**
	 * showMap method is used to display the entire map
	 */
	public void showMap() {
		if (l_playerObjectList.isEmpty()) {
			System.out.println("\nNo player has been created to show the map\n");
		} else {
			System.out.print("\nCountry ID\t\tCountry Name\t\t\t\tArmies\t\tOwner");
			for (Map.Entry l_gamePlayer : l_playerObjectList.entrySet()) {
				for (Country l_co : ((GamePlayer) l_gamePlayer.getValue()).getListOfCountries()) {
					System.out.print("\n" + l_co.getCountryId() + "\t\t" + l_co.getCountryName() + "\t\t\t\t"
							+ l_co.getArmies() + "\t\t" + l_gamePlayer.getKey());
					System.out.println();
				}
			}
		}
	}
}
