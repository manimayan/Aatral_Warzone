package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;

class SortbyArmies implements Comparator<Countries>
{
    public int compare(Countries a, Countries b)
    {
        return b.getArmies() - a.getArmies();
    }
}

public class AggressiveBehavior extends PlayerStrategy {
	public String flag="deploy";
	private Countries countryOb;
	public AggressiveBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		if(flag.equals("deploy") && GameEngine.l_gameIssueOrder.equals("deploy")) {
			countryOb = gamePlayerObject.getListOfCountries().get(0);
			for(Countries countryObject : gamePlayerObject.getListOfCountries()) {
				if(countryObject.getArmies()>countryOb.getArmies()) {
					countryOb = countryObject;
				}
			}
			flag="advance";
			System.out.println("DEPLOY");
			gamePlayerObject.advanceInput=false; 
			String numArmies = gamePlayerObject.getReinforcementArmies()+"";
			gamePlayerObject.setReinforcementArmies(0);
			return new DeployOrder(countryOb.getCountryId(), numArmies);
		}else if(flag.equals("advance") && GameEngine.l_gameIssueOrder.equals("advance")) {	
			String countryFromName=countryOb.getCountryName(), countryToName="", countryID = "",  numArmies =countryOb.getArmies()+"";
			boolean attackFound = false;
			for(String countStr : countryOb.getCountryOwnedBorders()) {
				if(isAttack(countStr)) {
					countryFromName = countryOb.getCountryName(); 
					numArmies = countryOb.getArmies()+"";
					countryID = countStr;
					attackFound = true;
					break;
				}
			}
			if(!attackFound) {
				List<Countries>countryObjectList = gamePlayerObject.getListOfCountries();
				Collections.sort(countryObjectList, new SortbyArmies());
				for(Countries countryObject : countryObjectList) {
					System.out.print(countryObject.getArmies()+" ");
					for(String countStr : countryObject.getCountryOwnedBorders()) {
						if(isAttack(countStr)) {
							countryOb = countryObject;
							countryFromName = countryObject.getCountryName(); 
							numArmies = countryObject.getArmies()+"";
							countryID = countStr;
							attackFound = true;
							break;
						}
					}
					if(attackFound) {
						break;
					}
				}
			}
			for(Countries countryObject : listOfCountries()) {
				if(countryObject.getCountryId().equals(countryID)) {
					countryToName = countryObject.getCountryName();
				}
			}
			flag="move";
			System.out.println("ADVANCE");
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
		}else if(flag.equals("move") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String countryFromName=countryOb.getCountryName(), countryToName="", countryID = "",  numArmies =countryOb.getArmies()+"";
			boolean transferFound = false;
//			for(String countStr : countryOb.getCountryOwnedBorders()) {
//				if(!isAttack(countStr)) {
//					countryFromName = countryOb.getCountryName(); 
//					numArmies = countryOb.getArmies()+"";
//					countryID = countStr;
//					transferFound = true;
//					break;
//				}
//			}
//			if(!transferFound) {
				List<Countries>countryObjectList = gamePlayerObject.getListOfCountries();
				Collections.sort(countryObjectList, new SortbyArmies());
				for(Countries countryObject : countryObjectList) {
					if(countryObject.getCountryId().equals(countryOb.getCountryId())) {
						continue;
					}
					for(String countStr : countryObject.getCountryOwnedBorders()) {
						if(isAttack(countStr)) {
							countryFromName = countryObject.getCountryName(); 
							numArmies = countryObject.getArmies()+"";
							countryID = countStr;
							transferFound = true;
							break;
						}
					}
					if(transferFound) {
						break;
					}
				}
//			}
			for(Countries countryObject : listOfCountries()) {
				if(countryObject.getCountryId().equals(countryID)) {
					countryToName = countryObject.getCountryName();
				}
			}
			flag="deploy";
			gamePlayerObject.advanceInput=true;
			System.out.println("MOVE");
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
		}else{
			flag="deploy";
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
