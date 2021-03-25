package aatral.warzone.gameplay;

import java.util.Map;
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
/**
 * <h1>DeployOrder</h1> The Class is to used to deploy the reinforced armies into their owned countries 
 *
 * @author Manimaran
 * @version 1.0
 * @since 24-02-2021
 */
public class DeployOrder extends Order{
	
	private String CountryID;
	
	private String armies;
	
	public GamePlayer gamePlayerObject;
	
	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);
	
	public DeployOrder(String CountryID, String armies)
	{
		this.CountryID = CountryID;
		this.armies = armies;
	}
/**
 * execute method is used to execute the game play
 */
	public void execute()
	{
		for(Countries country : this.gamePlayerObject.getListOfCountries()) {
			if(country.getCountryId().equals(this.CountryID)) {
				country.setArmies(country.getArmies()+Integer.parseInt(this.armies));
				System.out.println("successfully updated "+ this.CountryID+" "+this.armies);
				break;
			}
		}
		log.info("deployOrderExecution",gamePlayerObject.getPlayerName(),"deploy "+this.CountryID+" "+this.armies, "executed");	
	}
	
}
