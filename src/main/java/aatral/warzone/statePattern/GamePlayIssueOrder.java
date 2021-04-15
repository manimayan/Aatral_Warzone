package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.AirliftCard;
import aatral.warzone.gameplay.BlockadeCard;
import aatral.warzone.gameplay.BombCard;
import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.NegotiateCard;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;



/**
 * <h1>GamePlayIssueOrder</h1> This abstract class implements the state pattern for game play issue order
 *
 * @author Vignesh
 * @version 1.0
 * @since 24-02-2021
 */
public class GamePlayIssueOrder extends GamePlay {

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public GamePlayIssueOrder(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	

	@Override
	public void next() {
		gameEngine.setPhase(new GamePlayOrderExecution(gameEngine));
		System.out.println("\nCurrent gamePhase - GameExecuteOrder");
	}

	@Override
	public void showMap(String p_typeOfMap, String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Continent> loadMap(String p_typeOfMap, String p_warZoneMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMap(String p_typeOfMap, String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMap(String p_typeOfMap, String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateMap(String p_typeOfMap, String p_warZoneMap) {
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
		if(!gameEngine.l_gamePlayerObject.issueOrder()) {
			switch(gameEngine.l_gameIssueOrder) {
			case "deploy":
				deployOrder();
				break;
			case "advance":
				advanceOrder();
				break;
			}
		}
	}
/**
 * deployOrder method is used to deploy the order of gameplay
 */
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
						log.info("IssueOrder-Deploy", gameEngine.l_gamePlayerObject.getPlayerName(), l_issueCommand, "Country is not under this player ");
						System.out.println("The countries entered " + l_countryNorPresent
								+ " are not under player " + p_gameplayerObj.playerName);
					}
				} else {
					log.info("IssueOrder-Deploy", gameEngine.l_gamePlayerObject.getPlayerName(), l_issueCommand, "Deployment army is more than the reinforcement");
					System.out.println("Can't deploy " + l_armies + " armies by the player - " + p_gameplayerObj.getPlayerName()
					+ " as he has only " + p_gameplayerObj.getReinforcementArmies());
				}
			}
		}
	}
/**
 * advanceOrder method is used to execute the advance order function
 */
	public void advanceOrder() {
		Scanner l_input = new Scanner(System.in);
		String l_issueCommand;
		String l_advanceInput;
		String l_specialInput;
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
				gameEngine.l_gamePlayerObject.commit=true;
				break;
			} else if(l_issueCommand.startsWith("bomb") && validateCard("bomb")){
				l_specialInput = validateBombCommand(l_issueCommand);
				if(validateAdjCountry(l_specialInput) && isCountryValidate(l_specialInput)) {
					gameEngine.l_gamePlayerObject.orderObjects.add(new BombCard(l_specialInput));
					gameEngine.l_gamePlayerObject.getSpecialCards().put("bomb",gameEngine.l_gamePlayerObject.getSpecialCards().get("bomb")-1);
					l_wrongIP = false;
				}else {
					if(!isCountryValidate(l_specialInput)) {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The country ID is invalid");
						System.out.println("The country ID is invalid ");
					}else {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The country ID given for bomb is not adjacent to the player");
						System.out.println("The country ID given for bomb is not adjacent to the player "+gameEngine.l_gamePlayerObject.getPlayerName());
					}
				}
			} else if(l_issueCommand.startsWith("blockade") && validateCard("blockade")){
				l_specialInput = validateBlockadeCommand(l_issueCommand);
				if(validatePlayerCountryID(l_specialInput)) {
					gameEngine.l_gamePlayerObject.orderObjects.add(new BlockadeCard(l_specialInput));
					gameEngine.l_gamePlayerObject.getSpecialCards().put("blockade",gameEngine.l_gamePlayerObject.getSpecialCards().get("blockade")-1);
					l_wrongIP = false;
				}else {
					log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The country ID given for blockade is not under the player");
					System.out.println("The country ID given for blockade is not under the player "+gameEngine.l_gamePlayerObject.getPlayerName());
				}
			} else if(l_issueCommand.startsWith("airlift") && validateCard("airlift")){
				l_specialInput = validateAirliftCommand(l_issueCommand);
				if(validatePlayerCountryID(l_specialInput.split(" ")[0]) && 
						validatePlayerCountryID(l_specialInput.split(" ")[1]) && 
						validateNumArimesBasedOnID(l_specialInput.split(" ")[0], l_specialInput.split(" ")[2])){
					gameEngine.l_gamePlayerObject.orderObjects.add(new AirliftCard(l_specialInput.split(" ")[0], l_specialInput.split(" ")[1], l_specialInput.split(" ")[2]));
					gameEngine.l_gamePlayerObject.getSpecialCards().put("airlift",gameEngine.l_gamePlayerObject.getSpecialCards().get("airlift")-1);
					l_wrongIP = false;
				}else {
					if(!validatePlayerCountryID(l_specialInput.split(" ")[0])) {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The source country ID is not belonging to the player");
						System.out.println("The source country ID is not belonging to the player - "+gameEngine.l_gamePlayerObject.getPlayerName());
					}else if(!validatePlayerCountryID(l_specialInput.split(" ")[1])) {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The destination country ID is not belonging to the player");
						System.out.println("The destination country ID is not belonging to the player - "+gameEngine.l_gamePlayerObject.getPlayerName());
					}else {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The armies given for airlift is more than the curreny+deployment army");
						System.out.println("The armies given for airlift is more than the curreny+deployment army - "+gameEngine.l_gamePlayerObject.getPlayerName());
					}
				}
			} else if(l_issueCommand.startsWith("negotiate") && validateCard("negotiate")){
				l_specialInput = validateNegotiateCommand(l_issueCommand);
				if(!gameEngine.l_gamePlayerObject.getPlayerName().equals(l_specialInput)) {
					if(validatePlayerID(l_specialInput)) {
						gameEngine.l_gamePlayerObject.orderObjects.add(new NegotiateCard(l_specialInput));
						gameEngine.l_gamePlayerObject.getSpecialCards().put("negotiate",gameEngine.l_gamePlayerObject.getSpecialCards().get("negotiate")-1);
						l_wrongIP = false;
					}else {
						log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The given player ID doesn't exist");
						System.out.println("The given player ID doesn't exist");
					}
				}else {
					log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), l_specialInput, "The entered input ID is same as the current player");
					System.out.println("The entered input ID is same as the current player");
				}
			} else if(l_issueCommand.startsWith("advance")){
				l_advanceInput = validateAdvanceInput(l_issueCommand);
				String l_countryFromName = l_advanceInput.split(" ")[0];
				String l_countryToName = l_advanceInput.split(" ")[1];
				String l_numArmies = l_advanceInput.split(" ")[2];
				if(validatefromName(l_countryFromName) && validateToName(l_countryFromName,l_countryToName)) {
					if(validateNumArimes(l_countryFromName, l_numArmies)) {
						gameEngine.l_gamePlayerObject.orderObjects.add(new AdvanceOrder(l_countryFromName, l_countryToName, l_numArmies));
						l_wrongIP = false;
					} else {
						log.info("IssueOrder-Advance", gameEngine.l_gamePlayerObject.getPlayerName(), l_advanceInput, "The given no.of armies is more than the present+deployment army size to perform the action");
						System.out.println("The given no.of armies is more than the present+deployment army size to perform the action");
					}
				} else {
					if(!validatefromName(l_countryFromName)) {
						log.info("IssueOrder-Advance", gameEngine.l_gamePlayerObject.getPlayerName(), l_advanceInput, "CountryFromName is not under player");
						System.out.println("CountryFromName is not under player "+gameEngine.l_gamePlayerObject.playerName);
					} else {
						log.info("IssueOrder-Advance", gameEngine.l_gamePlayerObject.getPlayerName(), l_advanceInput, "CountryToName is not an adjacent country of any countries under the player");
						System.out.println("CountryToName is not an adjacent country of any countries under the player "+gameEngine.l_gamePlayerObject.playerName);
					}
				}
			}else {
				log.info("IssueOrder", gameEngine.l_gamePlayerObject.getPlayerName(), l_issueCommand, "Invalid Command");
				System.out.println("\nInput is wrong try again...");
			}
		}
	}
/**
 * isCountryValidate method is used to validate the country ID
 * @param countryID country ID value
 * @return true
 */
	public boolean isCountryValidate(String countryID) {
		for(Countries countryObj : gameEngine.listOfCountries()) {
			if(countryObj.getCountryId().equals(countryID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * validateCard method is used to validate the card using name
	 * @param cardName name of the card
	 * @return true
	 */
	public boolean validateCard(String cardName) {
		for(Map.Entry cardList : gameEngine.l_gamePlayerObject.getSpecialCards().entrySet()) {
			if(cardList.getKey().equals(cardName) && (int)cardList.getValue()>0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * validatePlayerID method is used to validate the player ID
	 * @param playerID player ID
	 * @return true
	 */
	public boolean validatePlayerID(String playerID) {
		if(GameEngine.l_playerList.contains(playerID)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 	validateNegotiateCommand method is used to validate the negotiate input command
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */
	public String validateNegotiateCommand(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("negotiate") && p_issueCommand.split(" ").length ==2)) {
			log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nNegotiate Format : negotiate playerID\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(10);
	}
	
	/**
	 * validateAirliftCommand method is used to validate the air lift input command
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */
	public String validateAirliftCommand(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("airlift") && p_issueCommand.split(" ").length ==4)) {
			log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nAirlift Format : airlift sourcecountryID targetcountryID numarmies\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(8);
	}
	/**
	 * validateBlockadeCommand method is used to validate the validateBlockadeCommand
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */	
	public String validateBlockadeCommand(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("blockade") && p_issueCommand.split(" ").length ==2)) {
			log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nBlockade Format : blockade countryID\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(9);
	}
	
	/**
	 * validateBombCommand method is used to validate the validateBombCommand
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */		
	public String validateBombCommand(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("bomb") && p_issueCommand.split(" ").length ==2)) {
			log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nBomb Format : bomb countryID\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(5);
	}
	
	/**
	 * validateDeployInput method is used to validate the validateDeployInput
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */			
	public String validateDeployInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("deploy") && p_issueCommand.split(" ").length ==3)) {
			log.info("IssueOrder-Special", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nDeploy Format : deploy countryID numArmies\n");
			p_issueCommand = l_input.nextLine();
		}
		
		return p_issueCommand.substring(6);
	}
	
	/**
	 * validateAdvanceInput method is used to validate the validateAdvanceInput
	 * @param p_issueCommand issue command
	 * @return substring of input value
	 */	
	public String validateAdvanceInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!(p_issueCommand.split(" ")[0].equalsIgnoreCase("advance") && p_issueCommand.split(" ").length ==4)) {
			log.info("IssueOrder-Advance", gameEngine.l_gamePlayerObject.getPlayerName(), p_issueCommand, "Invalid Command");
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
	
	/**
	 * validateInputArmies method is used to verify the greatest army value
	 * @param p_inputArmies input army
	 * @param p_availableArmies available army
	 * @return greater armies value
	 */
	public boolean validateInputArmies(int p_inputArmies, int p_availableArmies) {
		return p_inputArmies <= p_availableArmies;
	}
	
	/**
	 * validateAdjCountry method is used to validate the adjacency
	 * @param countryID country ID
	 * @return true
	 */
	public boolean validateAdjCountry(String countryID) {
		boolean flag=true;
		for(Countries countryObj : gameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(countryObj.getCountryId().equals(countryID)) {
				flag=false;
				break;
			}
			for(String adjCountryID : countryObj.getCountryOwnedBorders()) {
				if(adjCountryID.equals(countryID)) {
					flag=true;
				}
			}
		}
		return flag;
	}
	/**
	 * validatePlayerCountryID method is used to validate the player country ID
	 * @param countryID country ID
	 * @return true
	 */
	public boolean validatePlayerCountryID(String countryID) {
		for(Countries countryObj : gameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(countryObj.getCountryId().equals(countryID))
			{
				return true;
			}
		}
		return false;
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
/**
 * validateCountryValue method is used to validate country is null or not
 * @param p_countryNorPresent country not present
 * @return boolean true
 */ 
	public boolean validateCountryValue(String p_countryNorPresent) {
		return p_countryNorPresent.equals("");
	}

/**
 * validatefromName method is used to validate the country from name
 * @param p_countryFromName country from name
 * @return boolean value true
 */
	public boolean validatefromName(String p_countryFromName) {
		for(Countries l_countryObject : gameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(l_countryObject.getCountryName().equalsIgnoreCase(p_countryFromName.trim())) {
				return true;
			}
		}
		return false;
	}
/**
 * validateToName method is used to validate the name and country from name
 * @param countryFromName country from name
 * @param countryToName country to name
 * @return boolean true value
 */
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
/**
 * validateNumArimesBasedOnID method is used to validate the number of
 * armies based on the country ID
 * @param countryID ID of the country 
 * @param numArimes number of armies
 * @return larger value
 */
	public boolean validateNumArimesBasedOnID(String countryID, String numArimes) {
		List<DeployOrder> deployOrdersList = new ArrayList<DeployOrder>();
		for(Order object : gameEngine.l_gamePlayerObject.getOrderObjects()) {
			if(object instanceof DeployOrder) {
				deployOrdersList.add((DeployOrder) object);
			}
		}
		int l_totArmiesUnderCountry=0;
		for(Countries countryObject : gameEngine.l_gamePlayerObject.getListOfCountries())
		{
			if(countryObject.getCountryId().equals(countryID)) {
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

	
/**
 * validateNumArimes method is used to validate the number of armies
 * @param countryFromName country from name
 * @param numArimes number of armies
 * @return greater value
 */
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
	public HashMap<String, GamePlayer> assignCountries(HashMap<String, GamePlayer> p_playerObjectList, List<String> p_playerList) {
		// TODO Auto-generated method stub
		return null;
	}


}
