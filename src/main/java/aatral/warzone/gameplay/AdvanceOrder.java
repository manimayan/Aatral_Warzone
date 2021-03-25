package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
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
	public GamePlayer gamePlayerObject;
	public HashMap<String,GamePlayer> playerObjectList;
	
	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public AdvanceOrder(String countryFromName, String countryToName, String numArmies)
	{
		this.countryFromName = countryFromName;
		this.countryToName = countryToName;
		this.numArmies = numArmies;
	}
/**
 * execute method is used to execute the game	
 * @param l_playerObjectList player object list
 * @param l_gamePlayerObject game player object
 * 
 */
	public void execute()
	{
		int attackerArmies = getAttackerArmy(this.gamePlayerObject, this.countryFromName);
		int defenderArmies = getDefenderArmy(this.countryToName);
		if(isAttack(this.gamePlayerObject, this.countryToName)) {
			int attackerCanKill = attackerCalc(this.numArmies);
			int defenderCanKill = defenderCalc(this.numArmies);

			if(attackerCanKill>defenderArmies) {
				attackerArmies = attackerArmies - Integer.parseInt(this.numArmies);
				defenderArmies = defenderArmies==0?Integer.parseInt(this.numArmies):(Integer.parseInt(this.numArmies) - attackerCanKill);
				boolean flag=true;
				for(Entry<String, GamePlayer> l_mapEntry : this.playerObjectList.entrySet()) {
					for(Countries l_countryObject : ((GamePlayer)l_mapEntry.getValue()).getListOfCountries()) {
						if(l_countryObject.getCountryName().equals(this.countryToName)) {
							this.gamePlayerObject.getListOfCountries().add(l_countryObject);
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
		log.info("advanceOrderExecution",gamePlayerObject.getPlayerName(),"advance "+this.countryFromName+" "+this.countryToName+" "+this.numArmies, "executed");	
		System.out.println(this.gamePlayerObject.getPlayerName()+" has executed advance order for the country "+this.countryFromName+" to "+this.countryToName+ " successfully with the armies " +this.numArmies);
	}
/**
 * isAttack method is used attack
 * @param l_gamePlayerObject game player object
 * @param countryToName country to name
 * @return false
 */
	public boolean isAttack(GamePlayer l_gamePlayerObject, String countryToName) {
		for(Countries l_countryObject : l_gamePlayerObject.getListOfCountries()) {
			if(l_countryObject.getCountryName().equals(countryToName)) {
				return false;
			}
		}
		return true;
	}
/**
 * setAttackerArmy is used to set the army for attack
 * @param countryFromName country from name
 * @param armies number of armies
 * 
 */
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
/**
 * setDefenderArmy method is used to set the defender army 
 * @param p_countryToName country name
 * @param armies number of armies
 */
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

	
	/**
	 * getAttackerArmy method is used to get attacker army
	 * @param l_gamePlayerObject game player object
	 * @param countryFromName country from name
	 * @return getArmies
	 * 
	 */
	public int getAttackerArmy(GamePlayer l_gamePlayerObject, String countryFromName) {
		for(Countries countryObject : l_gamePlayerObject.getListOfCountries()) {
			if(countryObject.getCountryName().equals(countryFromName)) {
				return countryObject.getArmies();
			}
		}
		return 0;
	}

/**
 * getDefenderArmy method is used to get the defender army
 * @param p_countryToName country to name
 * @return getArmies
 * 
 */
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

	/**
	 * attackerCalc method is used to calculate the attacker value
	 * @param value attacker value
	 * @return mathround value
	 */
	public int attackerCalc(String value) {
		int armies = Integer.parseInt(value);
		return (int) Math.round(armies*0.6);
	}
/**
 * defenderCal is used to calculate the defender value
 * @param value defender value
 * @return mathround value
 */
	public int defenderCalc(String value) {
		int armies = Integer.parseInt(value);
		return (int) Math.round(armies*0.7);
	}

}
