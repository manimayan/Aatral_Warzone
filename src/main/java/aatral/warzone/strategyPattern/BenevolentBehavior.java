package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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


public class BenevolentBehavior extends PlayerStrategy{
	
	public String flag="deploy";
	private Countries countryOb;
	private int deploy_count = 0;
	private List<Countries> adjCountries = new ArrayList<>();
	public BenevolentBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		
		if(flag.equals("deploy") && GameEngine.l_gameIssueOrder.equals("deploy")) {
			countryOb = gamePlayerObject.getListOfCountries().get(0);
			for(Countries countryObject : gamePlayerObject.getListOfCountries()) {
				if(countryObject.getArmies() < countryOb.getArmies()) {
					countryOb = countryObject;
				}
			}
			flag="move";
			gamePlayerObject.commit=false; 
			String numArmies = gamePlayerObject.getReinforcementArmies()+"";
			gamePlayerObject.setReinforcementArmies(0);
			this.deploy_count = Integer.parseInt(numArmies);
			return new DeployOrder(countryOb.getCountryId(), numArmies);
		} else if (flag.equals("move") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String fromCountryName = "";
			String toCountryName = "";
			int numArmies = 0;
			List<Countries> countryObjectList = gamePlayerObject.getListOfCountries();
			if(adjCountries.size()==0) {
				for (String countStr : countryOb.getCountryOwnedBorders()) {
					if (!isAttack(countStr)) {
						for(Countries country : countryObjectList) {
							if(country.getCountryId().equals(countStr)) {
								adjCountries.add(country);
								break;
							}
						}
					}
				}
			}else {
				fromCountryName = adjCountries.get(0).getCountryName();
				toCountryName = countryOb.getCountryName();
				numArmies = countryOb.getArmies()/2;
				adjCountries.remove(0);
			}
			if(adjCountries.size()==0) {
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
			}
			
			if (fromCountryName.equals("")) {
				fromCountryName = "move";
			}
			
			return new AdvanceOrder(fromCountryName, toCountryName, numArmies + "");
		} else if (flag.equals("special") && GameEngine.l_gameIssueOrder.equals("advance")) {
			for (Map.Entry playerCard : gamePlayerObject.getSpecialCards().entrySet()) {
				if ((int) playerCard.getValue() != 0) {
					switch ((String) playerCard.getKey()) {
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
						boolean specialCardPresent = false;
						for (Map.Entry playerCardSpecial : gamePlayerObject.getSpecialCards().entrySet()) {
							if ((int) playerCardSpecial.getValue() != 0) {
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
						return new AirliftCard(fromID, toID, numArmies);
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