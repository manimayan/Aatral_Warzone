package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map.Entry;

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

public class AirliftCard extends Order{
	public String sourceCountryID;
	public String targetCountryID;
	public String numArmies;
	public GamePlayer gamePlayerObject;
	public AirliftCard(String sourceCountryID, String targetCountryID, String numArmies) {
		this.sourceCountryID = sourceCountryID;
		this.targetCountryID = targetCountryID;
		this.numArmies = numArmies;
	}
	
	public void execute() {
		
		for(Entry<String, Continent> l_mapEntry : GameEngine.l_masterMap.entrySet()) {
			for(Countries l_countryObject : ((Continent)l_mapEntry.getValue()).getContinentOwnedCountries()) {
				if(l_countryObject.getCountryId().equals(sourceCountryID)) {
					l_countryObject.setArmies(l_countryObject.getArmies()-Integer.parseInt(numArmies));
				}
				if(l_countryObject.getCountryId().equals(targetCountryID)) {
					l_countryObject.setArmies(l_countryObject.getArmies()+Integer.parseInt(numArmies));
				}
			}
		}
	}
	

}
