package aatral.warzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
	private HashSet<Integer> l_playerUsedContinent = new HashSet<>();
	private String l_mapName;

	public GameEngine(String fileName) {
		this.l_mapName = fileName;
		l_continentMap = new ComposeGraph().getContinentMap(fileName);
		l_borderMap = new ComposeGraph().getBorderMap(fileName);
	}

	public void assignReinforcements(GamePlayer p_object, int p_armies) {
		p_object.setArmies(p_armies);
		System.out.println("The player " + p_object.getPlayerName() + " has been reinforced with " + p_armies);
	}

	public void IssueOrders(GamePlayer p_object) {
		String l_issueCommand;
		String l_deployInput[];
		int l_index = 0;
		int l_armies = 0;
		do {
			if (l_index == 0)
				System.out.print("\nInitial ");
			else
				System.out.print("\nRemaining ");
			System.out.println("number of armies in hand for the player " + p_object.getPlayerName() + " is "+ p_object.getArmies()
				+"\n\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies");
			l_issueCommand = l_input.nextLine();
			while (!l_issueCommand.split(" ")[0].equalsIgnoreCase("deploy")) {
				System.err.println("\n\nEnter input type is not deploy...try again");
				System.out.println("\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies");
				l_issueCommand = l_input.nextLine();
			}
			l_deployInput = l_issueCommand.substring(6).split(",");
			l_armies = 0;
			for (int i = 0; i < l_deployInput.length; i++) {
				l_armies += Integer.parseInt(l_deployInput[i].trim().split(" ")[1]);
			}
			if (l_armies <= p_object.getArmies()) {
				l_armies = 0;
				String l_countryNorPresent = "";
				for (int i = 0; i < l_deployInput.length; i++) {
					String l_countryID = l_deployInput[i].trim().split(" ")[0];
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
				if (l_countryNorPresent.equals("")) {
					for (int i = 0; i < l_deployInput.length; i++) {
						String l_countryID = l_deployInput[i].trim().split(" ")[0];
						String l_armyCount = l_deployInput[i].trim().split(" ")[1];
						int l_deployableArmies = Integer.parseInt(l_armyCount);
						List<Country> l_list = p_object.getListOfCountries();
						for (Country l_con : p_object.getListOfCountries()) {
							if (l_con.getCountryId().equalsIgnoreCase(l_countryID)) {
								l_list.get(l_list.indexOf(l_con)).setArmies(l_con.getArmies() + l_deployableArmies);
								p_object.setArmies(p_object.getArmies() - l_deployableArmies);
								p_object.setListOfCountries(l_list);
								l_index = 1;
								break;
							}
						}
					}
				} else {
					System.err.println("The countries entered " + l_countryNorPresent.substring(1)
							+ " are not under player " + p_object.playerName);
				}
			} else {
				System.err.println("Can't deploy " + l_armies + " armies by the player - " + p_object.getPlayerName()
						+ " as he has only " + p_object.getArmies());
			}
		} while (l_armies > p_object.getArmies() || p_object.getArmies() != 0);
	}

	public void NextOrder() {
		System.out.println("Next Order()");
	}

	public void ExecuteOrders(GamePlayer gameplayObject) {
		NextOrder();
		System.out.println("Player " + gameplayObject.getPlayerName() + "'s Orders executed !!!");
	}

	public void GameUserMenu() {
		boolean l_gamePlayFlag = true;
		while (l_gamePlayFlag) {
			System.out.println("Gameplay Format : " + "\n showmap"
					+ "\n gameplayer -add playerName1,playerName2 -remove playerName" + "\n startgame" + "\n exitgame");
			String l_playOption = l_input.nextLine();
			switch (l_playOption.split(" ")[0]) {
			case "startgame":
				System.out.println("Game Play has started\n\n");
				GamePlayer l_gameplayerObj;
				for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
					l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
					System.out.println("\n\nAssinging reinforcement for the Player " + l_gameplayObject.getKey());
					assignReinforcements(l_gameplayerObj, 5);
					showMapPlayer(l_gameplayerObj);
				}
				for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
					l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
					System.out.println("\n\nIssue Orders for the player " + l_gameplayerObj.getPlayerName());
					IssueOrders(l_gameplayerObj);
				}
				for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
					l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
					System.out.println("\n\nExecueting Orders for the player " + l_gameplayerObj.getPlayerName());
					ExecuteOrders(l_gameplayerObj);
				}
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
							l_playerUsedContinent.add(0);
							if (l_playerName.isEmpty())
								continue;
							int continentID = new Random().nextInt(l_continentMap.size());
							while (l_playerUsedContinent.contains(continentID)) {
								continentID = new Random().nextInt(l_continentMap.size());
							}
							l_playerUsedContinent.add(continentID);
							String continentName = "";
							for (String continentKey : l_continentMap.keySet()) {
								if (continentKey.split("_")[0].equalsIgnoreCase(continentID + "")) {
									continentName = continentKey.substring(continentKey.indexOf("_") + 1);
									break;
								}
							}
							l_playerObListTempAdd.put(l_playerName, new GamePlayer(l_playerName,
									l_continentMap.get(continentID + "_" + continentName), 0));
						}
						break;
					case "remove":
						l_playerNames = l_option.substring(6).split(",");
						for (String l_playerName : l_playerNames) {
							l_playerName = l_playerName.trim();
							l_playerObListTempRem.add(l_playerName);
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
						for (String l_removeName : l_playerObListTempRem)
							l_playerObjectList.remove(l_removeName);
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

	public void showMapPlayer(GamePlayer p_gamePlayer) {
		System.out.print("\nCountry ID\t\tCountry Name\t\t\t\tArmies\t\tOwner");
		for (Country l_co : p_gamePlayer.getListOfCountries()) {
			System.out.print("\n" + l_co.getCountryId() + "\t\t" + l_co.getCountryName() + "\t\t\t\t" + l_co.getArmies()
					+ "\t\t" + p_gamePlayer.getPlayerName()+"\n");
		}
	}

	public void showMap() {
		if(l_playerObjectList.isEmpty()) {
			System.out.println("\nNo player has been created to show the map\n");
		}else {
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
