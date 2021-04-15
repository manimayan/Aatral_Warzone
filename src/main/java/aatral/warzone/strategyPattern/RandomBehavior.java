package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

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


/**
 * <h1>RandomBehavior is used to implement random behaviour strategy</h1>
 * @author William moses
 * @since 15.04.2021
 */

public class RandomBehavior extends PlayerStrategy {
	public String flag = "deploy";
	private Countries countryOb;
	private int deploy_count = 0;
	private int MaxRandom = 100;

	public RandomBehavior(GamePlayer p_player) {
		super(p_player);
	}
	/**
	 * createOrder method is used to create the order of the game
	 */
	public Order createOrder() {
		if (flag.equals("deploy") && GameEngine.l_gameIssueOrder.equals("deploy")) {
			countryOb = gamePlayerObject.getListOfCountries()
					.get(new Random().nextInt(gamePlayerObject.getListOfCountries().size()));
			flag = "advance";
			gamePlayerObject.commit = false;
			String numArmies = gamePlayerObject.getReinforcementArmies() + "";
			this.deploy_count = Integer.parseInt(numArmies);
			gamePlayerObject.setReinforcementArmies(0);
			return new DeployOrder(countryOb.getCountryId(), numArmies);
		} else if (flag.equals("advance") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String countryFromName = "", countryToName = "", countryID = "", numArmies = "";
			boolean attackFound = false;
			Countries countryObject = gamePlayerObject.getListOfCountries()
					.get(new Random().nextInt(gamePlayerObject.getListOfCountries().size()));
			for (int i = 0; i < MaxRandom && !attackFound; i++) {
				for (String countStr : countryObject.getCountryOwnedBorders()) {
					if (countryObject.getCountryId().equals(countryOb.getCountryId()) && isAttack(countStr)) {
						countryFromName = countryObject.getCountryName();
						numArmies = (countryOb.getArmies() + this.deploy_count / 2) + "";
						countryID = countStr;
						attackFound = true;
						break;
					} else if (countryObject.getArmies() > 0 && isAttack(countStr)) {
						countryFromName = countryObject.getCountryName();
						numArmies = (countryObject.getArmies()) + "";
						countryID = countStr;
						attackFound = true;
						break;
					}
				}
				if (!attackFound) {
					countryObject = gamePlayerObject.getListOfCountries()
							.get(new Random().nextInt(gamePlayerObject.getListOfCountries().size()));
				}
			}
			if (countryFromName.equals("")) {
				countryFromName = "advance";
			}
			for (Countries countryObjectTo : listOfCountries()) {
				if (countryObjectTo.getCountryId().equals(countryID)) {
					countryToName = countryObjectTo.getCountryName();
				}
			}
			flag = "move";
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
		} else if (flag.equals("move") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String countryFromName = "", countryToName = "", countryID = "", numArmies = "";
			boolean transferFound = false;
			if (!transferFound) {
				Countries countryObject = gamePlayerObject.getListOfCountries()
						.get(new Random().nextInt(gamePlayerObject.getListOfCountries().size()));
				for (int i = 0; i < MaxRandom && !transferFound; i++) {
					for (String countStr : countryObject.getCountryOwnedBorders()) {
						if (countryObject.getCountryId().equals(countryOb.getCountryId()) && isAttack(countStr)) {
							countryFromName = countryObject.getCountryName();
							numArmies = (countryOb.getArmies() + this.deploy_count / 2) + "";
							countryID = countStr;
							transferFound = true;
							break;
						} else if (countryObject.getArmies() > 0 && isAttack(countStr)) {
							countryFromName = countryObject.getCountryName();
							numArmies = (countryObject.getArmies()) + "";
							countryID = countStr;
							transferFound = true;
							break;
						}
					}
					if (!transferFound) {
						countryObject = gamePlayerObject.getListOfCountries()
								.get(new Random().nextInt(gamePlayerObject.getListOfCountries().size()));
					}
				}
			}
			for (Countries countryObject : listOfCountries()) {
				if (countryObject.getCountryId().equals(countryID)) {
					countryToName = countryObject.getCountryName();
				}
			}

			if (countryFromName.equals("")) {
				countryToName = "move";
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
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
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