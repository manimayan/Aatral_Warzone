package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
	
	public void execute(HashMap<String, GamePlayer> l_playerObjectList, GamePlayer l_gamePlayerObject)
	{
		int attackerArmies = getAttackerArmy(l_gamePlayerObject, this.countryFromName);
		int defenderArmies = getDefenderArmy(this.countryToName);
		if(isAttack(l_gamePlayerObject, this.countryToName)) {
			int attackerCanKill = attackerCalc(this.numArmies);
			int defenderCanKill = defenderCalc(this.numArmies);

			if(attackerCanKill>defenderArmies) {
				attackerArmies = attackerArmies - Integer.parseInt(this.numArmies);
				defenderArmies = defenderArmies==0?Integer.parseInt(this.numArmies):(Integer.parseInt(this.numArmies) - attackerCanKill);
				boolean flag=true;
				for(Entry<String, GamePlayer> l_mapEntry : l_playerObjectList.entrySet()) {
					for(Countries l_countryObject : ((GamePlayer)l_mapEntry.getValue()).getListOfCountries()) {
						if(l_countryObject.getCountryName().equals(this.countryToName)) {
							l_gamePlayerObject.getListOfCountries().add(l_countryObject);
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
		System.out.println(l_gamePlayerObject.getPlayerName()+" advance "+this.countryFromName+"->"+this.countryToName+" done ");
	}

	public boolean isAttack(GamePlayer l_gamePlayerObject, String countryToName) {
		for(Countries l_countryObject : l_gamePlayerObject.getListOfCountries()) {
			if(l_countryObject.getCountryName().equals(countryToName)) {
				return false;
			}
		}
		return true;
	}

	public void setAttackerArmy(String countryFromName,int armies) {
		for(Entry<String, Continent> l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
				if(l_countryObject.getCountryName().equals(countryFromName)) {
					l_countryObject.setArmies(armies);
					return;
				}
			}
		}
	}

	public void setDefenderArmy(String p_countryToName, int armies) {
		for(Entry<String, Continent> l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
				if(l_countryObject.getCountryName().equals(p_countryToName)) {
					l_countryObject.setArmies(armies);
					return;
				}
			}
		}
	}

	public int getAttackerArmy(GamePlayer l_gamePlayerObject, String countryFromName) {
		for(Countries countryObject : l_gamePlayerObject.getListOfCountries()) {
			if(countryObject.getCountryName().equals(countryFromName)) {
				return countryObject.getArmies();
			}
		}
		return 0;
	}

	public int getDefenderArmy(String p_countryToName) {
		for(Entry<String, Continent> l_mapEntry : GameEngine.l_masterMap.entrySet()) {
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
