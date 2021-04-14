package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

class SortbyArmies implements Comparator<Countries> {
	public int compare(Countries a, Countries b) {
		return b.getArmies() - a.getArmies();
	}
}

public class AggressiveBehavior extends PlayerStrategy {
	public String flag = "deploy";
	private Countries countryOb;
	private int deploy_count = 0;

	public AggressiveBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		if (flag.equals("deploy") && GameEngine.l_gameIssueOrder.equals("deploy")) {
			countryOb = gamePlayerObject.getListOfCountries().get(0);
			for (Countries countryObject : gamePlayerObject.getListOfCountries()) {
				if (countryObject.getArmies() > countryOb.getArmies()) {
					countryOb = countryObject;
				}
			}
			flag = "advance";
			System.out.println("DEPLOY");
			gamePlayerObject.commit = false;
			String numArmies = gamePlayerObject.getReinforcementArmies() + "";
			gamePlayerObject.setReinforcementArmies(0);
			this.deploy_count = Integer.parseInt(numArmies);
			return new DeployOrder(countryOb.getCountryId(), numArmies);
		} else if (flag.equals("advance") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String countryFromName = countryOb.getCountryName(), countryToName = "", countryID = "",
					numArmies = countryOb.getArmies() + "";
			boolean attackFound = false;
			for (String countStr : countryOb.getCountryOwnedBorders()) {
				if (isAttack(countStr)) {
					countryFromName = countryOb.getCountryName();
					numArmies = (countryOb.getArmies() + this.deploy_count) + "";
					countryID = countStr;
					attackFound = true;
					break;
				}
			}
			if (!attackFound) {
				countryFromName = "";
				List<Countries> countryObjectList = gamePlayerObject.getListOfCountries();
				Collections.sort(countryObjectList, new SortbyArmies());
				for (Countries countryObject : countryObjectList) {
					System.out.print(countryObject.getArmies() + " ");
					for (String countStr : countryObject.getCountryOwnedBorders()) {
						if (countryObject.getArmies() > 0 && isAttack(countStr)) {
							countryOb = countryObject;
							countryFromName = countryObject.getCountryName();
							numArmies = (countryObject.getArmies()) + "";
							countryID = countStr;
							attackFound = true;
							break;
						}
					}
					if (attackFound) {
						break;
					}
				}
				if (countryFromName.equals("")) {
					countryFromName = "advance";
				}
			}
			for (Countries countryObject : listOfCountries()) {
				if (countryObject.getCountryId().equals(countryID)) {
					countryToName = countryObject.getCountryName();
				}
			}
			flag = "move";
//			System.out.println("ADVANCE" + countryFromName + "  " + countryToName + " " + numArmies);
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
		} else if (flag.equals("move") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String fromCountryName = "";
			String toCountryName = "";
			int numArmies = 0;
			List<Countries> countryObjectList = gamePlayerObject.getListOfCountries();
			for (Countries countryObject : countryObjectList) {
				int max = Integer.MIN_VALUE;
				Set<String> adjacentCountryIDS = countryObject.getCountryOwnedBorders();
				for (String id : adjacentCountryIDS) {
					for (Countries country : countryObjectList) {
						if (country.getCountryId().equals(id)) {
							if (countryObject.getArmies() + country.getArmies() > max) {
								max = countryObject.getArmies() + country.getArmies();
								if ((country.getArmies() > 0 && countryObject.getArmies() > 0)
										|| country.getArmies() > 0) {
									toCountryName = countryObject.getCountryName();
									fromCountryName = country.getCountryName();
									numArmies = country.getArmies();
								} else if (countryObject.getArmies() > 0) {
									fromCountryName = countryObject.getCountryName();
									toCountryName = country.getCountryName();
									numArmies = countryObject.getArmies();

								}
							}

						}
					}
				}
			}
			if (fromCountryName.equals("")) {
				fromCountryName = "move";
			}
			boolean specialCardPresent = false;
			for (Map.Entry playerCard : gamePlayerObject.getSpecialCards().entrySet()) {
				if ((int) playerCard.getValue() != 0) {
					specialCardPresent = true;
					break;
				}
			}
			if (specialCardPresent) {
				flag = "special";
			} else {
				flag = "deploy";
				gamePlayerObject.commit = true;
			}
//			System.out.println("Move" + fromCountryName + "  " + toCountryName + " " + numArmies);
			return new AdvanceOrder(fromCountryName, toCountryName, numArmies + "");
		} else if (flag.equals("special") && GameEngine.l_gameIssueOrder.equals("advance")) {
			for (Map.Entry playerCard : gamePlayerObject.getSpecialCards().entrySet()) {
				if ((int) playerCard.getValue() != 0) {
					switch ((String) playerCard.getKey()) {
					case "bomb":
						String countryID = "";
						List<Countries> countryObjectList = gamePlayerObject.getListOfCountries();
						Collections.sort(countryObjectList, new SortbyArmies());
						for (Countries countryObject : countryObjectList) {
							for (String countStr : countryObject.getCountryOwnedBorders()) {
								if (isAttack(countStr)) {
									countryID = countStr;
									break;
								}
							}
						}
						if (countryID.equals("")) {
							countryID = "bomb";
						}
						flag = "deploy";
						gamePlayerObject.commit = true;
						return new BombCard(countryID);
					case "blockade":
						countryID = "";
						countryObjectList = gamePlayerObject.getListOfCountries();
						Collections.sort(countryObjectList, new SortbyArmies());
						for (Countries countryObject : countryObjectList) {
							for (String countStr : countryObject.getCountryOwnedBorders()) {
								if (!isAttack(countStr)) {
									countryID = countStr;
									break;
								}
							}
						}
						if (countryID.equals("")) {
							countryID = "blockade";
						}
						flag = "deploy";
						gamePlayerObject.commit = true;
						return new BlockadeCard(countryID);
					case "airlift":
						String fromID = "";
						String toID = "";
						String numArmies = "";
						for (Countries countryFrom : gamePlayerObject.getListOfCountries()) {
							for (Countries countryTo : gamePlayerObject.getListOfCountries()) {
								if (!countryFrom.getCountryId().equals(countryTo.getCountryId())) {
									if (!countryFrom.getCountryOwnedBorders().contains(countryTo.getCountryId())) {
										fromID = countryFrom.getCountryId();
										toID = countryTo.getCountryId();
										numArmies = countryFrom.getArmies() + "";
										break;
									}
								}
							}
							if (!fromID.equals("")) {
								break;
							}
						}
						if (fromID.equals("")) {
							fromID = "airlift";
						}
						flag = "deploy";
						gamePlayerObject.commit = true;
						return new AirliftCard(fromID, toID, numArmies);
					case "negotiate":
						String playerName = "";
						for (String playerList : GameEngine.l_playerList) {
							if (!playerList.equals(gamePlayerObject.getPlayerName())) {
								playerName = playerList;
								break;
							}
						}
						flag = "deploy";
						gamePlayerObject.commit = true;
						return new NegotiateCard(playerName);
					}
				}
			}
			flag = "deploy";
			gamePlayerObject.commit = true;
			return null;
		} else {
			flag = "deploy";
			System.out.println("ELSE");
			return null;
		}
	}

	/**
	 * listOfCountries method is used to calculate the list of temporary countries
	 * 
	 * @return temporary country value
	 */
	public List<Countries> listOfCountries() {
		List<Countries> tempCountries = new ArrayList<>();
		for (Entry<String, Continent> mapEntry : GameEngine.l_masterMap.entrySet()) {
			tempCountries.addAll(((Continent) mapEntry.getValue()).getContinentOwnedCountries());
		}
		return tempCountries;
	}

	/**
	 * isAttack method is used attack
	 * 
	 * @param l_gamePlayerObject game player object
	 * @param countryToName      country to name
	 * @return false
	 */
	public boolean isAttack(String countryID) {
		for (Countries l_countryObject : gamePlayerObject.getListOfCountries()) {
			if (l_countryObject.getCountryId().equals(countryID)) {
				return false;
			}
		}
		return true;
	}
}