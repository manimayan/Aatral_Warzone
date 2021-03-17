package aatral.warzone;

import java.util.List;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import aatral.warzone.model.InputCountry;
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
public class GamePlayer {

	public String playerName;

	public List<InputCountry> listOfCountries;

	public int armies;

	/**
	 * ownedCountries method is used to get the list of countries owned by the
	 * player
	 * 
	 * @return getListOfCountries
	 */
	public List<InputCountry> ownedCountries() {
		return getListOfCountries();
	}

}