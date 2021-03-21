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

public class DeployOrder extends Order{
	
	private String CountryID;
	
	private String armies;
	
	public void execute()
	{
		for(Map.Entry m : GameEngine.l_masterMap.entrySet()) {
			for(Countries country : ((Continent)m.getValue()).getContinentOwnedCountries()) {
				if(country.getCountryId().equals(this.CountryID)) {
					country.setArmies(country.getArmies()+Integer.parseInt(this.armies));
					System.out.println("successfully updated "+ this.CountryID+" "+this.armies);
					break;
				}
			}
		}
	}
	
}
