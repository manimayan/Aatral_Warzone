package aatral.warzone.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import aatral.warzone.model.Continent;
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
public class GamePlayer implements Comparator<Countries> {

	public String playerName;

	public List<Countries> listOfCountries;

	public int reinforcementArmies;

	public List<Order> orderObjects;

	private boolean advanceInput;

	public GamePlayer(String playerName, List<Countries> listOfCountries, int armies) {
		this.orderObjects = new ArrayList<>();
		this.playerName = playerName;
		this.listOfCountries=listOfCountries;
		this.reinforcementArmies=armies;
		Collections.sort(this.listOfCountries, new Comparator<Countries>() {
			@Override
			public int compare(Countries o1, Countries o2) {
				return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
			}
		});
	}

	@Override
	public int compare(Countries o1, Countries o2) {
		// TODO Auto-generated method stub
		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}

	/**
	 * ownedCountries method is used to get the list of countries owned by the
	 * player
	 * 
	 * @return getListOfCountries
	 */
	public List<Countries> ownedCountries() {
		return getListOfCountries();
	}

	public void deployOrder() {
		GamePlayer p_gameplayerObj = this;
		Scanner l_input = new Scanner(System.in);
		String l_issueCommand;
		String l_deployInput;
		int l_armies = 0;
		if(p_gameplayerObj.getReinforcementArmies()>0) {
			boolean l_wrongIP = true;
			while(l_wrongIP) {
				System.out.println("\nPlayer - "+this.playerName+"\nRemaining number of armies in hand is "
						+ p_gameplayerObj.getReinforcementArmies() + "\n\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies");
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
						this.orderObjects.add(new DeployOrder(l_countryID, l_armyCount));
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

	public void setAdvanceInput(boolean value) {
		this.advanceInput = value;
	}

	public boolean getAdvanceInput() {
		return advanceInput;
	}

	public void advanceOrder() {
		Scanner l_input = new Scanner(System.in);
		String l_issueCommand;
		String l_advanceInput;
		boolean l_wrongIP = true;
		while(l_wrongIP) {
			System.out.println("\nPlayer - "+this.playerName+"\nAdvance Order format for attack/transfer or commit : "
					+ "\n advance countrynamefrom countynameto numarmies"
					+ "\n commit");
			l_issueCommand = l_input.nextLine();
			if(l_issueCommand.equals("commit")) {
				this.advanceInput=true;
				break;
			} else {
				l_advanceInput = validateAdvanceInput(l_issueCommand);
				String l_countryFromName = l_advanceInput.split(" ")[0];
				String l_countryToName = l_advanceInput.split(" ")[1];
				String l_numArmies = l_advanceInput.split(" ")[2];
				if(validatefromName(l_countryFromName) && validateToName(l_countryFromName,l_countryToName)) {
					//					if(validateNumArimes(l_countryFromName, l_numArmies)) {
					this.orderObjects.add(new AdvanceOrder(l_countryFromName, l_countryToName, l_numArmies));
					l_wrongIP = false;
					//					} else {
					//						System.out.println("The given no.of armies is more than the present army size to perform the action");
					//					}
				} else {
					if(!validatefromName(l_countryFromName)) {
						System.out.println("CountryFromName is not under player "+this.playerName);
					} else {
						System.out.println("CountryToName is not an adjacent country of any countries under the player "+this.playerName);
					}
				}
			}
		}
	}

	public boolean validatefromName(String p_countryFromName) {
		for(Countries l_countryObject : this.getListOfCountries()) {
			if(l_countryObject.getCountryName().equalsIgnoreCase(p_countryFromName.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean validateToName(String countryFromName,String countryToName) {
		for(Countries country : this.getListOfCountries()) {
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
		for(Countries countryObject : this.getListOfCountries()) {
			if(countryObject.getCountryName().equals(countryFromName) && countryObject.getArmies()>=Integer.parseInt(numArimes)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * issueOrders method is used to deploy the armies from player to the designated
	 * countries
	 * 
	 */
	public void IssueOrders() { 
		switch(GameEngine.l_gamePhase) {
		case "deploy":
			deployOrder();
			break;
		case "advance":
			advanceOrder();
			break;
		}
	}

	/**
	 * NextOrder method is used to display list of orders will be executed
	 * 
	 * @return orderObj
	 */
	public Order NextOrder() {

		Order orderObj = this.orderObjects.get(0);
		this.orderObjects.remove(0);
		return orderObj;

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

	public String validateDeployInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!p_issueCommand.split(" ")[0].equalsIgnoreCase("deploy")) {
			System.out.println("\n\nYour entered input type is invalid...try again");
			System.out.println("\nDeploy Format : deploy countryID1 numArmies, countryID2 numArmies\n");
			p_issueCommand = l_input.nextLine();
		}
		return p_issueCommand.substring(6);
	}

	public String validateAdvanceInput(String p_issueCommand) {
		Scanner l_input = new Scanner(System.in);
		while (!p_issueCommand.split(" ")[0].equalsIgnoreCase("advance")) {
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

}