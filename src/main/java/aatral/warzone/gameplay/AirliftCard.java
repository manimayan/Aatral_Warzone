package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map.Entry;

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
/**
 * <h1>AirliftCard</h1> The Class is the transfer the armies under the same player without having the restriction of adjacency 
 *
 * @author Tejeswini
 * @version 1.0
 * @since 24-02-2021
 */
public class AirliftCard extends Order{
	public String sourceCountryID;
	public String targetCountryID;
	public String numArmies;
	public GamePlayer gamePlayerObject;
	
	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public AirliftCard(String sourceCountryID, String targetCountryID, String numArmies) {
		this.sourceCountryID = sourceCountryID;
		this.targetCountryID = targetCountryID;
		this.numArmies = numArmies;
	}
/**
 * execute method is used to execute the game play	
 */
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
		log.info("specialOrderExecution",gamePlayerObject.getPlayerName(),"airlift "+this.sourceCountryID+" "+this.targetCountryID+" "+this.numArmies, "executed");	
	}
	

}
