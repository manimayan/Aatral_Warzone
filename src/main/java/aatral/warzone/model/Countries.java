package aatral.warzone.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Countries implements Comparator<Countries>{

	private String countryId;

	private String countryName;

	private String continentId;

	private String continentName;

	private int armies;

	public Countries(String countryId, String countryName, String continentId) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.continentId = continentId;
	}

	public Countries(InputCountry p_Country, Set<String> l_countryOwnedBorders) {
		this.countryId = p_Country.getCountryId();
		this.countryName = p_Country.getCountryName();
		this.continentId = p_Country.getContinentId();
		this.countryOwnedBorders = l_countryOwnedBorders;
	}

	public Countries(String p_countryId, String p_continentId) {
		this.countryId = p_countryId;
		this.continentId = p_continentId;
	}
	
	public Countries(InputCountry inputCountry) {
		// TODO Auto-generated constructor stub
	}

	Set<String> countryOwnedBorders = new HashSet<>();

	@Override
	public int compare(Countries o1, Countries o2) {
		// TODO Auto-generated method stub
		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}
}
