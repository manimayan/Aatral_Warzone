package aatral.warzone.gameplay;

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

public class DeployOrder extends Order{
	
	private String CountryID;
	
	private String armies;
/**
 * execute method is used to execute the game play
 * @param l_gamePlayerObject game player object
 */
	public void execute(GamePlayer l_gamePlayerObject)
	{
		for(Countries country : l_gamePlayerObject.getListOfCountries()) {
			if(country.getCountryId().equals(this.CountryID)) {
				country.setArmies(country.getArmies()+Integer.parseInt(this.armies));
				System.out.println("successfully updated "+ this.CountryID+" "+this.armies);
				break;
			}
		}
	}
	
}
