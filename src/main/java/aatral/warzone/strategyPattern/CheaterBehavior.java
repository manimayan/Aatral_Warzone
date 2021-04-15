package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;

public class CheaterBehavior extends PlayerStrategy{
	public String flag = "deploy";
	private Countries countryOb;
	
	public CheaterBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		gamePlayerObject.setReinforcementArmies(0);
		
		List<String> conquers = getAdjacentEnemyCountries();

		for(String countryID : conquers) {
		for (Entry<String, GamePlayer> l_mapEntry : GameEngine.l_playerObjectList.entrySet()) {
			for (Countries l_countryObject : ((GamePlayer) l_mapEntry.getValue()).getListOfCountries()) {
					if (l_countryObject.getCountryId().equals(countryID)) {
						gamePlayerObject.getListOfCountries().add(l_countryObject);
						((GamePlayer) l_mapEntry.getValue()).getListOfCountries().remove(l_countryObject);
						break; 
					}
				}
			}
		}
		conquers = getAdjacentEnemyCountries();
		List<Countries> countryObjectList = listOfCountries();
		for(Countries countryObject : countryObjectList) {
			String remove = "";
			for(String countryID : conquers) {
				if(countryObject.getCountryId().equals(countryID)) {
					countryObject.setArmies(countryObject.getArmies()*2);
					remove = countryID;
					break;
				}
			}
			if(!remove.equals("")) {
				conquers.remove(remove);
			}
		}
		System.out.println("Cheater has executed!!!");
		gamePlayerObject.commit = true;
		return new Order();
	}
	
	public List<String> getAdjacentEnemyCountries(){
		List<String> conquers = new ArrayList<>();
		for(Countries country : gamePlayerObject.getListOfCountries()) {
			for (String countStr : country.getCountryOwnedBorders()) {
				if (isAttack(countStr)) {
					conquers.add(countStr);
					break;
				}
			}
		}
		return conquers;
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
	
}
