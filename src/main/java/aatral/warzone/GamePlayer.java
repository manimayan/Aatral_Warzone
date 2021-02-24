package aatral.warzone;

import java.util.List;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import aatral.warzone.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Record
/**
 * 
 * @author William Moses
 * @version 1.0
 * @since 24-02-2021
 */
public class GamePlayer{
	
	public String playerName;
	
	public List<Country> listOfCountries;
	
	public int armies;
	
	public List<Country> ownedCountries(){
		return getListOfCountries();
	}
	
}