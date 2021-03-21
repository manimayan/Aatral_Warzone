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
	import aatral.warzone.implementation.MapEditor;
	import aatral.warzone.model.Borders;
	import aatral.warzone.model.Continent;
	import aatral.warzone.model.Countries;
	import aatral.warzone.model.InputCountry;
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
		private ArrayList<String> l_playerList = new ArrayList<>();
		public static HashMap<String, GamePlayer> l_playerObjectList;
		public static Map<String, Continent> l_masterMap = new HashMap<>();
		private String l_mapName;
		private GamePlayer l_gamePlayerObject;
		private boolean l_gamePlayPopulateFlag;
		public GameEngine(String fileName) {
			this.l_mapName = fileName;
			this.l_gamePlayerObject = new GamePlayer();
			l_masterMap = new MapEditor().loadMap(fileName);
		}
	
		public int totalCountries() {
			int count = 0;
			for (Map.Entry mapEntry : l_masterMap.entrySet()) {
				Set<Countries> set = ((Continent) mapEntry.getValue()).getContinentOwnedCountries();
				count += set.size();
			}
			return count;
		}
	
		public List<Countries> listOfCountries() {
			List<Countries> tempCountries = new ArrayList<>();
			for (Map.Entry mapEntry : l_masterMap.entrySet()) {
				tempCountries.addAll(((Continent) mapEntry.getValue()).getContinentOwnedCountries());
			}
			return tempCountries;
		}
	
		public boolean allCountriesAssigned(boolean p_isCountryAssigned[]) {
			for(int i=0;i<p_isCountryAssigned.length;i++) {
				if(!p_isCountryAssigned[i])
					return false;
			}
			return true;
		}
		
		public int getCountryID(int p_totalCountryNumber, boolean []p_isCountryAssigned) {
			int l_countryID = new Random().nextInt(p_totalCountryNumber);
			while(p_isCountryAssigned[l_countryID]) {
				l_countryID = new Random().nextInt(p_totalCountryNumber);
			}
			return l_countryID;
		}
		
		public void startNewGame() {
			l_playerObjectList = new HashMap<>();
			int l_totalNoOfCountry = totalCountries();
			List<Countries> totalCountries = listOfCountries();
			boolean l_isCountryAssigned[] = new boolean[l_totalNoOfCountry];
			boolean l_flag = true;
			while(l_flag){
				for(String str : l_playerList) {
					if(allCountriesAssigned(l_isCountryAssigned)) {
						l_flag = false;
						break;
					}
					int l_countryID = getCountryID(l_totalNoOfCountry, l_isCountryAssigned);
					if(!l_playerObjectList.containsKey(str)) {
						l_playerObjectList.put(str,
								new GamePlayer(str, new ArrayList<Countries>(), 0));
					}
					l_playerObjectList.get(str).getListOfCountries().add(totalCountries.get(l_countryID));
					l_isCountryAssigned[l_countryID]=true;
				}
			}
		}
	
		/**
		 * startGame method is used to start the game with user's input choice
		 */
		public void startGame() {
			if (l_playerList.isEmpty()) {
				System.out.println("\nNo player has been created to start the game\n");
			} else {
				boolean l_flag = true, l_innerLoopflag = true;
				String l_readInput;
				if(!l_gamePlayPopulateFlag) {
					if (l_playerObjectList == null) {
						startNewGame();
					} else {
						while (l_innerLoopflag) {
							System.out.println(
									"Give any of the following command to proceed the gamePlay \n startnewgame \n continue");
							l_readInput = l_input.nextLine();
							switch (l_readInput) {
							case "startnewgame":
								startNewGame();
								l_innerLoopflag = false;
								break;
							case "continue":
								l_innerLoopflag = false;
								break;
							default:
								System.out.println("Input is mismatching...Kindly Try again...");
								break;
							}
						}
						l_innerLoopflag = true;
					}
				}else {
					l_gamePlayPopulateFlag = false;
				}
				
				boolean l_isFirst = true;
				do {
					GamePlayer l_gameplayerObj;
					for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
						l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
						System.out.println("\n\nAssinging reinforcement for the Player " + l_gameplayObject.getKey());
						if(l_isFirst)
							l_gameplayerObj.assignReinforcements(5);
						else
							l_gameplayerObj.assignReinforcements(calAssignReinforcements(l_gameplayerObj));
						
						showMapPlayer(l_gameplayerObj);
					}
					l_isFirst = false;
					boolean flag = true;
					do {
						flag=false;
						for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
							 ((GamePlayer) l_gameplayObject.getValue()).IssueOrders();
							 if(!flag && ((GamePlayer) l_gameplayObject.getValue()).getReinforcementArmies()>0) {
								 flag=true;
							 }
						}
					} while (flag);
					
					boolean ordersExist = true;
					while(ordersExist) {
						ordersExist = false;
					for (Map.Entry l_gameplayObject : l_playerObjectList.entrySet()) {
						l_gameplayerObj = (GamePlayer) l_gameplayObject.getValue();
						if(l_gameplayerObj.orderObjects.size() >0) {
						System.out.println("\n\nExecueting Orders for the player " + l_gameplayerObj.getPlayerName());
						Order orderObj = l_gameplayerObj.NextOrder();
						if(orderObj instanceof DeployOrder)
						{
							DeployOrder deployOrderObj = (DeployOrder)orderObj;
							deployOrderObj.execute();
						}
						ordersExist = true;
						}		
					}
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
					ArrayList<String> l_playerObListTempAdd = new ArrayList<>();
					List<String> l_playerObListTempRem = new ArrayList<>();
					String l_playerNames[];
					String l_playerName;
					for (String l_option : l_playerOption) {
						if (l_option.isEmpty())
							continue;
						switch (l_option.split(" ")[0]) {
						case "add":
							l_playerName = l_option.substring(3).trim();
							l_playerName = l_playerName.trim();
							if (l_playerName.isEmpty())
								continue;
							if (l_playerObListTempAdd.contains(l_playerName) || l_playerList.contains(l_playerName)) {
								System.out.println("Player name " + l_playerName + " already existing...try this alone again...");
							} else {
								l_playerObListTempAdd.add(l_playerName);
							}
							break;
						case "remove":
							String l_removeName = "";
							l_playerName = l_option.substring(6).trim();
							l_playerName = l_playerName.trim();
							if (!l_playerList.contains(l_playerName)) {
								l_removeName = l_playerName;
								l_flag = false;
							}
							if (!l_flag) {
								System.out.println("Player names " + l_removeName.substring(1)
										+ " doesn't exist/nTry again with valid player names to remove");
							} else {
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
							l_playerList.addAll(l_playerObListTempAdd);
							l_playerList.removeAll(l_playerObListTempRem);
							l_playerObjectList = null;
							startNewGame();
							l_gamePlayPopulateFlag = true;
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
		 * getContinentName method is used to get the continent name using continent id
		 * 
		 * @param p_continentMapKeySet Set of continent map key.
		 * @param p_continentID        Continent id.
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
	
		
		public int calAssignReinforcements(GamePlayer p_player) {		
			List<Countries> l_listOfCountries = p_player.getListOfCountries();
			int l_reinforcementCount = Math.round(l_listOfCountries.size()/3);
			for(Map.Entry continent : l_masterMap.entrySet())
			{
				Set<Countries> l_CountriesUnderContinent = ((Continent) continent.getValue()).getContinentOwnedCountries();
				boolean exists_all = false;
				if(!l_CountriesUnderContinent.isEmpty()) {
					exists_all = true;
					for(Countries c : l_CountriesUnderContinent)
					{
						if(!l_listOfCountries.contains(c.getCountryId()))
						{
							exists_all = false;
							break;
						}
					}
				}
				
				
				if(exists_all)
				{
					l_reinforcementCount += Integer.parseInt(((Continent) continent.getValue()).getContinentValue());
				}
			}
			return l_reinforcementCount<3?3:l_reinforcementCount;
		}
	
		/**
		 * showMapPlayer method is used to show the map corresponds to game player name
		 * 
		 * @param p_gamePlayer object for gameplayer class.
		 */
		public void showMapPlayer(GamePlayer p_gamePlayer) {
			System.out.print("\nCountry ID\t\tCountry Name\t\t\t\tArmies\t\tOwner");
			for (Countries l_co : p_gamePlayer.getListOfCountries()) {
				System.out.print("\n" + l_co.getCountryId() + "\t\t" + l_co.getCountryName() + "\t\t\t\t" + l_co.getArmies()
						+ "\t\t" + p_gamePlayer.getPlayerName() + "\n");
			}
		}
	
		/**
		 * showMap method is used to display the entire map
		 */
		public void showMap() {
			if (l_playerList.isEmpty()) {
				System.out.println("\nNo player has been created to show the map\n");
			} else {
				System.out.print("\nCountry ID\t\tCountry Name\t\t\t\tArmies\t\tOwner");
				for (Map.Entry l_gamePlayer : l_playerObjectList.entrySet()) {
					for (Countries l_co : ((GamePlayer) l_gamePlayer.getValue()).getListOfCountries()) {
						System.out.print("\n" + l_co.getCountryId() + "\t\t" + l_co.getCountryName() + "\t\t\t\t"
								+ l_co.getArmies() + "\t\t" + l_gamePlayer.getKey());
						System.out.println();
					}
				}
			}
		}
	}
