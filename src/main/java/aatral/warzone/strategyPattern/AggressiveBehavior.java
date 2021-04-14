package aatral.warzone.strategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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
	private int deploy_count = 0;
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
			this.deploy_count = Integer.parseInt(numArmies);
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
				countryFromName = "";
				List<Countries>countryObjectList = gamePlayerObject.getListOfCountries();
				Collections.sort(countryObjectList, new SortbyArmies());
				for(Countries countryObject : countryObjectList) {
					System.out.print(countryObject.getArmies()+" ");
					for(String countStr : countryObject.getCountryOwnedBorders()) {
						if(countryObject.getArmies()+this.deploy_count >0 && isAttack(countStr)) {
							countryOb = countryObject;
							countryFromName = countryObject.getCountryName(); 
							numArmies = (countryObject.getArmies()+this.deploy_count)+"";
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
			System.out.println("ADVANCE"+countryFromName+"  "+countryToName+" "+numArmies);
			return new AdvanceOrder(countryFromName, countryToName, numArmies);
		}else if(flag.equals("move") && GameEngine.l_gameIssueOrder.equals("advance")) {
			String fromCountryName="";
			String toCountryName="";
			int numArmies = 0;
			List<Countries>countryObjectList = gamePlayerObject.getListOfCountries();
			for(Countries countryObject : countryObjectList) {
				int max = Integer.MIN_VALUE;
				Set<String> adjacentCountryIDS = countryObject.getCountryOwnedBorders();
				for(String id : adjacentCountryIDS)
				{
				   for(Countries country : countryObjectList)
				   {
					   if(country.getCountryId().equals(id))
					   {
						  if(countryObject.getArmies() + country.getArmies()>max)
						  {
							  max = countryObject.getArmies() + country.getArmies();
							  if((country.getArmies() > 0 && countryObject.getArmies() >0 ) || country.getArmies() > 0) {
							  toCountryName = countryObject.getCountryName();
							  fromCountryName = country.getCountryName();
							  numArmies = country.getArmies();
							  }
							  else if(countryObject.getArmies() > 0 )
							  {
								  fromCountryName = countryObject.getCountryName();
								  toCountryName = country.getCountryName();
								  numArmies = countryObject.getArmies();
								  
							  }
						  }
						   
					   }
				   }
				}
				
			}
			
			flag="deploy";
			gamePlayerObject.advanceInput=true;
			System.out.println("MOVE");
			return new AdvanceOrder(fromCountryName, toCountryName, numArmies+"");
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