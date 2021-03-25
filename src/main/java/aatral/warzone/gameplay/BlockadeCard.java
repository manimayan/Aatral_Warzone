package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map.Entry;

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
 * <h1>BlockadeCard</h1> The Class is to increase the armies thrice and making the teritory into neutral 
 *
 * @author Tejeswini
 * @version 1.0
 * @since 24-02-2021
 */
public class BlockadeCard extends Order{
	public String countryID;
	public GamePlayer gamePlayerObject;
	public HashMap<String,GamePlayer> playerObjectList;
	
	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public BlockadeCard(String countryID) {
		this.countryID = countryID;
	}
/**
 * execute method is used to execute the game play
 */
	public void execute() {
	
		for(Entry<String, GamePlayer> l_mapEntry : this.playerObjectList.entrySet()) {
			boolean flag = false;
			for(Countries l_countryObject : ((GamePlayer)l_mapEntry.getValue()).getListOfCountries()) {
				if(l_countryObject.getCountryId().equals(this.countryID)){
					l_countryObject.setArmies(l_countryObject.getArmies()*3);
					((GamePlayer)l_mapEntry.getValue()).getListOfCountries().remove(l_countryObject);
					GameEngine.l_neutralCountries.add(l_countryObject);
					flag=true;
					break;
				}
			}
			if(flag)
				break;
		}
		log.info("specialOrderExecution",gamePlayerObject.getPlayerName(),"blockade "+this.countryID, "executed");	
	}

}