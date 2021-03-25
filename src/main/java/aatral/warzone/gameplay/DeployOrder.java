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
 * @param l_gamePlayerObject game player object
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
