package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;

public class GamePlayIssueOrder extends GamePlay {

	public GamePlayIssueOrder(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	

	@Override
	public void next() {
		gameEngine.setPhase(new GamePlayOrderExecution(gameEngine));
		System.out.println(gameEngine.getGamePhase());
	}

	@Override
	public void showMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Continent> loadMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		return null;
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
	public void gamePlayShowMap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGamePlayer(String p_playerName,  ArrayList<String> p_playerObListTempAdd,
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeGamePlayer(boolean p_flag, String p_playerName,
			List<String> l_playerObListTempRem, List<String> p_playerList) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public void assignReinforcements(int p_armies) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void issueOrders() {
		switch(gameEngine.l_gameIssueOrder) {
		case "deploy":
			deployOrder();
			break;
		case "advance":
			advanceOrder();
			break;
		}
	}

	public void deployOrder() {
		GamePlayer p_gameplayerObj = gameEngine.l_gamePlayerObject;
		Scanner l_input = new Scanner(System.in);
		String l_issueCommand;
		String l_deployInput;
		int l_armies = 0;
		if(p_gameplayerObj.getReinforcementArmies()>0) {
			boolean l_wrongIP = true;
			while(l_wrongIP) {
				System.out.println("\nPlayer - "+p_gameplayerObj.playerName+"\nRemaining number of armies in hand is "
						+ p_gameplayerObj.getReinforcementArmies() + "\n\nDeploy Format : deploy countryID numArmies");
				l_issueCommand = l_input.nextLine();
				l_deployInput = validateDeployInput(l_issueCommand);
				l_armies = calculateInputArmies(l_deployInput);
				if (validateInputArmies(l_armies, p_gameplayerObj.getReinforcementArmies())) {
					l_armies = 0;
					String l_countryNorPresent = validateCountryInput(l_deployInput, p_gameplayerObj);
					if (validateCountryValue(l_countryNorPresent)) {
						l_wrongIP = false;
						String l_countryID = l_deployInput.trim().split(" ")[0];
						String l_armyCount = l_deployInput.trim().split(" ")[1];
						gameEngine.l_gamePlayerObject.orderObjects.add(new DeployOrder(l_countryID, l_armyCount));
						int l_deployableArmies = Integer.parseInt(l_armyCount);
						List<Countries> l_list = p_gameplayerObj.getListOfCountries();
						for (Countries l_con : p_gameplayerObj.getListOfCountries()) {
							if (l_con.getCountryId().equalsIgnoreCase(l_countryID)) {
								//l_list.get(l_list.indexOf(l_con)).setArmies(l_con.getArmies() + l_deployableArmies);
								p_gameplayerObj.setReinforcementArmies(p_gameplayerObj.getReinforcementArmies() - l_deployableArmies);
								//p_gameplayerObj.setListOfCountries(l_list);
								break;
							}
						}
					} else {
						System.out.println("The countries entered " + l_countryNorPresent
								+ " are not under player " + p_gameplayerObj.playerName);
					}
				} else {
					System.out.println("Can't deploy " + l_armies + " armies by the player - " + p_gameplayerObj.getPlayerName()
					+ " as he has only " + p_gameplayerObj.getReinforcementArmies());
				}
			}
		}
	}

	public void advanceOrder() {
		Scanner l_input = new Scanner(System.in);
		String l_issueCommand;
		String l_advanceInput;
		boolean l_wrongIP = true;
		while(l_wrongIP) {
			System.out.print("\nPlayer - "+gameEngine.l_gamePlayerObject.playerName+"\nAdvance/Special Order format for attack/transfer or commit : "
					+ "\n advance countrynamefrom countynameto numarmies");
			String printOut = gameEngine.checkIfPlayerHasAnyCards(gameEngine.l_gamePlayerObject);
			if(!printOut.equals("")) {
				System.out.print(printOut);
			}
			System.out.println("\n commit");
			l_issueCommand = l_input.nextLine();
			if(l_issueCommand.equals("commit")) {
				gameEngine.l_gamePlayerObject.advanceInput=true;
				break;
			} else if(l_issueCommand.startsWith("bomb")){
				
			} else if(l_issueCommand.startsWith("blockade")){
				
			} else if(l_issueCommand.startsWith("airlift")){
				
			} else if(l_issueCommand.startsWith("negotiate")){
				
			} else {
				l_advanceInput = validateAdvanceInput(l_issueCommand);
				String l_countryFromName = l_advanceInput.split(" ")[0];
				String l_countryToName = l_advanceInput.split(" ")[1];
				String l_numArmies = l_advanceInput.split(" ")[2];
				if(validatefromName(l_countryFromName) && validateToName(l_countryFromName,l_countryToName)) {
					if(validateNumArimes(l_countryFromName, l_numArmies)) {
						gameEngine.l_gamePlayerObject.orderObjects.add(new AdvanceOrder(l_countryFromName, l_countryToName, l_numArmies));
						l_wrongIP = false;
					} else {
						System.out.println("The given no.of armies is more than the present+deployment army size to perform the action");
					}
				} else {
					if(!validatefromName(l_countryFromName)) {
						System.out.println("CountryFromName is not under player "+gameEngine.l_gamePlayerObject.playerName);
					} else {
						System.out.println("CountryToName is not an adjacent country of any countries under the player "+gameEngine.l_gamePlayerObject.playerName);
					}
				}
			}
		}
	}
	
	public String validateDeployInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("deploy") && p_issueCommand.split(" ").length ==3)) {
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nDeploy Format : deploy countryID numArmies\n");
			p_issueCommand = l_input.nextLine();
		}
		
		return p_issueCommand.substring(6);
	}

	public String validateAdvanceInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("advance") && p_issueCommand.split(" ").length ==4)) {
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("Advance Order format for attack/transfer : "
					+ "\n advance countrynamefrom countynameto numarmies\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(8);
	}

	/**
	 * calculateInputArmies method is used to validate the deployed armies is less
	 * than or equal to deployed armies on country
	 * 
	 * @param p_deployInput Deploy input string.
	 * @return no of armies.
	 */
	public int calculateInputArmies(String p_deployInput) {
		return Integer.parseInt(p_deployInput.trim().split(" ")[1]);
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
	public String validateCountryInput(String p_deployInput, GamePlayer p_object) {
		String l_countryNorPresent = "";
		String l_countryID = p_deployInput.trim().split(" ")[0];
		boolean flag = true;
		for (Countries con : p_object.getListOfCountries()) {
			if (con.getCountryId().equalsIgnoreCase(l_countryID)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			l_countryNorPresent = l_countryID;
		}
		return l_countryNorPresent;
	}

	public boolean validateCountryValue(String p_countryNorPresent) {
		return p_countryNorPresent.equals("");
	}
	
	public boolean validatefromName(String p_countryFromName) {
		for(Countries l_countryObject : gameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(l_countryObject.getCountryName().equalsIgnoreCase(p_countryFromName.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean validateToName(String countryFromName,String countryToName) {
		for(Countries country : gameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(country.getCountryName().equals(countryFromName)) {
				for(Map.Entry mapEntryObj : GameEngine.l_masterMap.entrySet()) {
					for(Countries countryMapObj : ((Continent)mapEntryObj.getValue()).getContinentOwnedCountries()) {
						if(countryMapObj.getCountryName().equals(countryToName) && country.getCountryOwnedBorders().contains(countryMapObj.getCountryId())) {
							return true;
						}
					}
				}
				break;
			}
		}
		return false;
	}

	public boolean validateNumArimes(String countryFromName, String numArimes) {
		List<DeployOrder> deployOrdersList = new ArrayList<DeployOrder>();
		for(Order object : gameEngine.l_gamePlayerObject.getOrderObjects()) {
			if(object instanceof DeployOrder) {
				deployOrdersList.add((DeployOrder) object);
			}
		}
		int l_totArmiesUnderCountry=0;
		for(Countries countryObject : gameEngine.l_gamePlayerObject.getListOfCountries())
		{
			if(countryObject.getCountryName().equals(countryFromName)) {
			l_totArmiesUnderCountry += countryObject.getArmies();
			for(DeployOrder order : deployOrdersList) {
				if(order.getCountryID().equals(countryObject.getCountryId()))
				{
					l_totArmiesUnderCountry+= Integer.parseInt(order.getArmies());
				}
			}
			break;
			}	
			
		}
		return l_totArmiesUnderCountry >= Integer.parseInt(numArimes);
	}


	@Override
	public void executeOrders() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public HashMap<String, GamePlayer> assignCountries(List<String> p_playerList) {
		// TODO Auto-generated method stub
		return null;
	}


}
