package aatral.warzone;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import aatral.warzone.model.Countries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Record
/**
 * 
 * @author William Moses
 * @version 1.0
 * @since 24-02-2021
 */
public class GamePlayer {

	public String playerName;

	public List<Countries> listOfCountries;

	public int armies;
	
	/**
	 * ownedCountries method is used to get the list of countries owned by the
	 * player
	 * 
	 * @return getListOfCountries
	 */
	public List<Countries> ownedCountries() {
		return getListOfCountries();
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
	 * IssueOrders method is used to deploy the armies from player to the designated
	 * countries
	 * 
	 * @param p_gameplayerObj object of game player
	 */
	public boolean IssueOrders(GamePlayer p_gameplayerObj) 
	{ 
		Scanner l_input = new Scanner(System.in);
		boolean flag = false;
		String l_issueCommand;
		String l_deployInput[];
		int l_armies = 0;
		if(p_gameplayerObj.getArmies()>0) {
			flag=true;  
			System.out.println("\nRemaining number of armies in hand for the player " + p_gameplayerObj.getPlayerName() + " is "
					+ p_gameplayerObj.getArmies() + "\n\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies");
			l_issueCommand = l_input.nextLine();
			l_deployInput = validateDeployInput(l_issueCommand);
			l_armies = calculateInputArmies(l_deployInput);
			if (validateInputArmies(l_armies, p_gameplayerObj.getArmies())) {
				l_armies = 0;
				String l_countryNorPresent = validateCountryInput(l_deployInput, p_gameplayerObj);
				if (validateCountryValue(l_countryNorPresent)) {
					for (int i = 0; i < l_deployInput.length; i++) {
						String l_countryID = l_deployInput[i].trim().split(" ")[0];
						String l_armyCount = l_deployInput[i].trim().split(" ")[1];
						int l_deployableArmies = Integer.parseInt(l_armyCount);
						List<Countries> l_list = p_gameplayerObj.getListOfCountries();
						for (Countries l_con : p_gameplayerObj.getListOfCountries()) {
							if (l_con.getCountryId().equalsIgnoreCase(l_countryID)) {
								l_list.get(l_list.indexOf(l_con)).setArmies(l_con.getArmies() + l_deployableArmies);
								p_gameplayerObj.setArmies(p_gameplayerObj.getArmies() - l_deployableArmies);
								p_gameplayerObj.setListOfCountries(l_list);
								break;
							}
						}
					}
				} else {
					System.out.println("The countries entered " + l_countryNorPresent.substring(1)
							+ " are not under player " + p_gameplayerObj.playerName);
				}
			} else {
				System.out.println("Can't deploy " + l_armies + " armies by the player - " + p_gameplayerObj.getPlayerName()
						+ " as he has only " + p_gameplayerObj.getArmies());
			}
		}
		return flag;
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
	
	public String[] validateDeployInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
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
			for (Countries con : p_object.getListOfCountries()) {
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

}