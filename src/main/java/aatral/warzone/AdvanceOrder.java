package aatral.warzone;

import java.util.Map;
import java.util.Set;

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

public class AdvanceOrder extends Order{
	private String countryFromName;
	private String countryToName;
	private String numArmies;
	public void execute()
	{
		int attackerArmies = getAttackerArmy(this.countryFromName);
		int defenderArmies = getDefenderArmy(this.countryToName);
		if(isAttack(this.countryToName)) {
			int attackerCanKill = attackerCalc(this.numArmies);
			int defenderCanKill = defenderCalc(this.numArmies);
			
			if(attackerCanKill>defenderArmies) {
				attackerArmies = attackerArmies - Integer.parseInt(this.numArmies);
				defenderArmies = Integer.parseInt(this.numArmies) - attackerCanKill;
				boolean flag=true;
				for(Map.Entry l_mapEntry : GameEngine.l_playerObjectList.entrySet()) {
					for(Countries l_countryObject : ((GamePlayer)l_mapEntry.getValue()).getListOfCountries()) {
						if(l_countryObject.getCountryName().equals(this.countryToName)) {
							GameEngine.l_gamePlayerObject.getListOfCountries().add(l_countryObject);
							((GamePlayer)l_mapEntry.getValue()).getListOfCountries().remove(l_countryObject);
							flag=false;
							break;
						}
					}
					if(!flag)
						break;
				}
			}else {
				attackerArmies = attackerArmies - defenderCanKill;
				defenderArmies = defenderArmies - attackerCanKill;
			}
		}else {
			attackerArmies = attackerArmies - Integer.parseInt(this.numArmies);
			defenderArmies = defenderArmies + Integer.parseInt(this.numArmies);
		}
		setAttackerArmy(this.countryFromName, attackerArmies);
		setDefenderArmy(this.countryToName, defenderArmies);
		System.out.println(GameEngine.l_gamePlayerObject.getPlayerName()+" advance "+this.countryFromName+"->"+this.countryToName+" done ");
	}
	
	public boolean isAttack(String countryToName) {
		for(Countries l_countryObject : GameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(l_countryObject.getCountryName().equals(countryToName)) {
				return false;
			}
		}
		return true;
	}
	
	public void setAttackerArmy(String countryFromName,int armies) {
		for(Map.Entry l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
				if(l_countryObject.getCountryName().equals(countryFromName)) {
					l_countryObject.setArmies(armies);
					return;
				}
			}
		}
	}
	
	public void setDefenderArmy(String p_countryToName, int armies) {
		for(Map.Entry l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
				if(l_countryObject.getCountryName().equals(p_countryToName)) {
					l_countryObject.setArmies(armies);
					return;
				}
			}
		}
	}
	
	public int getAttackerArmy(String countryFromName) {
		for(Countries countryObject : GameEngine.l_gamePlayerObject.getListOfCountries()) {
			if(countryObject.getCountryName().equals(countryFromName)) {
				return countryObject.getArmies();
			}
		}
		return 0;
	}

	public int getDefenderArmy(String p_countryToName) {
		for(Map.Entry l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			if(((Continent)l_mapEntry.getValue()).getContinentOwnedCountries().contains(p_countryToName)) {
				for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
					if(l_countryObject.getCountryName().equals(p_countryToName)) {
						return l_countryObject.getArmies();
					}
				}
			}
		}
		return 0;
	}
	
	public int attackerCalc(String value) {
		int armies = Integer.parseInt(value);
		return (int) Math.round(armies*0.6);
	}
	public int defenderCalc(String value) {
		int armies = Integer.parseInt(value);
		return (int) Math.round(armies*0.7);
	}
	
	
}
