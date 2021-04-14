package aatral.warzone.strategyPattern;

import java.util.Comparator;

import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Countries;

class SortbyArmiesAscending implements Comparator<Countries>
{
    public int compare(Countries a, Countries b)
    {
        return a.getArmies() - b.getArmies();
    }
}


public class BenevolentBehavior extends PlayerStrategy{
	
	public String flag="deploy";
	private Countries countryOb;
	private int deploy_count = 0;
	
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
			flag="advance";
			System.out.println("DEPLOY");
			gamePlayerObject.commit=false; 
			String numArmies = gamePlayerObject.getReinforcementArmies()+"";
			gamePlayerObject.setReinforcementArmies(0);
			this.deploy_count = Integer.parseInt(numArmies);
			return new DeployOrder(countryOb.getCountryId(), numArmies);
		}
		
		else if(flag.equals("advance") && GameEngine.l_gameIssueOrder.equals("advance")) {	
		
		
	}
		return null;
}
	
}
